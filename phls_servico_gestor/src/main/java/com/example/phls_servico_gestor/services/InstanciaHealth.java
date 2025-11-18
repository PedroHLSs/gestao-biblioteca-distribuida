    package com.example.phls_servico_gestor.services;

    public class InstanciaHealth {
        
        private final String url;
        private final int maxFalhasConsecutivas;
        private final long tempoQuarentenaMs;

        private int falhasConsecutivas;
        private int totalFalhas;
        private int totalSucessos;
        private long ultimoTempoFalha;

        public InstanciaHealth(String url, int maxFalhasConsecutivas, long tempoQuarentenaMs){
            this.url = url;
            this.maxFalhasConsecutivas = maxFalhasConsecutivas;
            this.tempoQuarentenaMs = tempoQuarentenaMs;
            this.falhasConsecutivas = 0;
            this.totalFalhas = 0;
            this.totalSucessos = 0;
            this.ultimoTempoFalha = 0;
        }

        //Chamado quando o Gestor tenta enviar uma requisição e a instância falha
        public synchronized void registrarFalha(){
            falhasConsecutivas++;
            totalFalhas++;
            ultimoTempoFalha = System.currentTimeMillis();
        }
        //É chamado quando a instancia tem sucesso em uma requisição
        public synchronized void registrarSucesso(){
            falhasConsecutivas = 0;
            totalSucessos++;
        }
        //Diz se a instância está disponível para receber requisições
        public synchronized boolean disponivel(){
            if(falhasConsecutivas < maxFalhasConsecutivas){
                return true;
            }
            long tempoDecorrido = System.currentTimeMillis() - ultimoTempoFalha;
            if(tempoDecorrido > tempoQuarentenaMs){
                falhasConsecutivas = Math.max(0, falhasConsecutivas - 1);
                return true;
            }
            return false;
        }
        public synchronized int getFalhasConsecutivas() {
            return falhasConsecutivas;
        }

        public synchronized int getTotalFalhas() {
            return totalFalhas;
        }

        public synchronized int getTotalSucessos() {
            return totalSucessos;
        }

        public String getUrl() {
            return url;
        }

        public long getTempoQuarentenaMs() {
            return tempoQuarentenaMs;
        }
            
    }
