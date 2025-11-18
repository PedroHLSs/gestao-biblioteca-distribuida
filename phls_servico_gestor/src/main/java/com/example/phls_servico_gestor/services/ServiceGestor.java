package com.example.phls_servico_gestor.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Service
public class ServiceGestor {

    private RestTemplate restTemplate;

    // Lista de URLs das bibliotecas disponíveis
    private List<String> bibliotecaUrls;

    private AtomicInteger currentBibliotecaIndex;

    // Heath Controll instancia
    private Map<String, InstanciaHealth> heatlhStatus;
    private static final int MAX_FALHAS_CONSECUTIVAS = 3;
    private static final long TEMPO_QUARENTENA_MS = 30000; // 30 segundos

    private String url_serv1_biblioteca;
    private String url_serv2_biblioteca;
    private String url_serv3_biblioteca;

    public ServiceGestor() {
        restTemplate = new RestTemplate();
        // Inicializa a lista de URLs do servico 1 de biblioteca
        bibliotecaUrls = new ArrayList<>();
        bibliotecaUrls.add("http://localhost:8081");
        bibliotecaUrls.add("http://localhost:8082");

        currentBibliotecaIndex = new AtomicInteger(0);

        // Inicializa o controle de saúde das instâncias
        heatlhStatus = new ConcurrentHashMap<>();
        for (String url : bibliotecaUrls) {
            heatlhStatus.put(url, new InstanciaHealth(url, MAX_FALHAS_CONSECUTIVAS, TEMPO_QUARENTENA_MS));
        }
        this.url_serv1_biblioteca = "http://localhost:8081";
        this.url_serv2_biblioteca = "http://localhost:8083";
        this.url_serv3_biblioteca = "http://localhost:8084";
    }

    private String getProximaBibliotecaUrl() {
        if (bibliotecaUrls.isEmpty()) {
            throw new RuntimeException("Nenhuma URL de biblioteca disponível");
        }

        // Tentativa de encontrar uma instância disponível
        int tentativa = bibliotecaUrls.size();
        for (int i = 0; i < tentativa; i++) {
            int index = currentBibliotecaIndex.getAndIncrement() % bibliotecaUrls.size();
            String url = bibliotecaUrls.get(index);

            InstanciaHealth instanciaHealth = heatlhStatus.get(url);
            if (instanciaHealth != null && instanciaHealth.disponivel()) {
                System.out.println("Usando instância: " + url);
                return url;
            } else if (instanciaHealth != null) {
                System.out.println("Instância em quarentena: " + url);
            }
        }

        return bibliotecaUrls.get(0);
    }

    private void registrarSucesso(String url) {
        InstanciaHealth instanciaHealth = heatlhStatus.get(url);
        if (instanciaHealth != null) {
            instanciaHealth.registrarSucesso();
        }
    }

    private void registrarFalha(String url) {
        InstanciaHealth instanciaHealth = heatlhStatus.get(url);
        if (instanciaHealth != null) {
            instanciaHealth.registrarFalha();
        }
        if (!instanciaHealth.disponivel()) {
            System.out.println("Instância " + url
                    + " entrou em quarentena após " + instanciaHealth.getFalhasConsecutivas() + " falhas consecutivas");
        }
    }

    public ResponseEntity<String> executarComFallback(BibliotecaOperation operation) {
        int tentativas = bibliotecaUrls.size();
        Exception ultimaExcecao = null;

        for (int i = 0; i < tentativas; i++) {
            String url = getProximaBibliotecaUrl();
            try {
                System.out.println("Tentativa " + (i + 1) + " - Enviando requisição para: " + url);
                ResponseEntity<String> response = operation.execute(url);
                System.out.println("Requisição bem-sucedida para: " + url);
                registrarSucesso(url);
                return response;

            } catch (ResourceAccessException e) {
                System.out.println("Instância " + url + " não está disponivel: " + e.getMessage() +
                        ". Tentando próxima instância...");
                registrarFalha(url);
                ultimaExcecao = e;

            } catch (HttpServerErrorException e) {
                System.out.println("Erro no servidor " + url + ": " + e.getMessage() +
                        ". Tentando próxima instância...");
                registrarFalha(url);
                ultimaExcecao = e;

            } catch (HttpClientErrorException e) {
                System.out.println("Erro do cliente na requisição para " + url + ": " + e.getMessage());
                throw e;

            } catch (Exception e) {
                System.out.println("Erro ao acessar " + url + ": " + e.getMessage());
                registrarFalha(url);
                ultimaExcecao = e;
            }
        }

        System.out.println("Todas as " + tentativas + " instâncias falharam!");
        throw new RuntimeException(
                "Todas as instâncias do Serviço estão indisponíveis",
                ultimaExcecao);
    }
}
