// Listar todas as avaliações
async function listarAvaliacoes() {
    const avaliacoes = await GET("/avaliacoes");
    if (!avaliacoes) return;

    const tbody = document.querySelector("#tabelaAvaliacoes tbody");
    tbody.innerHTML = "";

    avaliacoes.forEach(a => {
        const tr = document.createElement("tr");
        const idAvaliacao = a._id || a.id || a.idAvaliacao;
        const idLivro = a.id_livro || a.livro?.id_livro || a.livro?.id || "—";
        const idUsuario = a.id_usuario || a.usuario?.id_usuario || a.usuario?.id || "—";
        const nota = a.nota ? parseFloat(a.nota).toFixed(1) : "0.0";
        const dataAvaliacao = a.data_avaliacao ? new Date(a.data_avaliacao).toLocaleString('pt-BR') : "—";
        
        tr.innerHTML = `
            <td>${idAvaliacao}</td>
            <td>${idLivro}</td>
            <td>${idUsuario}</td>
            <td>${nota}</td>
            <td>${a.titulo || "—"}</td>
            <td>${a.descricao || "—"}</td>
            <td>${dataAvaliacao}</td>
            <td>
                <button onclick="editarAvaliacao('${idAvaliacao}')">Editar</button>
                <button onclick="deletarAvaliacao('${idAvaliacao}')">Deletar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Criar nova avaliação
async function criarAvaliacao() {
    const livroId = document.getElementById("livroId").value;
    const usuarioId = document.getElementById("usuarioId").value;
    const nota = document.getElementById("nota").value;
    const titulo = document.getElementById("titulo").value;
    const descricao = document.getElementById("descricao").value;
    const dataAvaliacao = document.getElementById("dataAvaliacao").value;

    if (!livroId || !usuarioId || !nota || !titulo || !dataAvaliacao) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

    const novaAvaliacao = {
        id_livro: parseInt(livroId),
        id_usuario: parseInt(usuarioId),
        nota: parseFloat(nota),
        titulo: titulo,
        descricao: descricao || null,
        data_avaliacao: dataAvaliacao
    };

    const resultado = await POST("/avaliacoes", novaAvaliacao);
    
    if (resultado) {
        alert("Avaliação criada com sucesso!");
        limparFormulario();
        listarAvaliacoes();
    }
}

// Editar avaliação existente
async function editarAvaliacao(id) {
    const avaliacao = await GET(`/avaliacoes/${id}`);
    
    if (avaliacao) {
        const idAvaliacao = avaliacao._id || avaliacao.id || avaliacao.idAvaliacao;
        const idLivro = avaliacao.id_livro || avaliacao.livro?.id_livro || avaliacao.livro?.id || "";
        const idUsuario = avaliacao.id_usuario || avaliacao.usuario?.id_usuario || avaliacao.usuario?.id || "";
        
        // Converter data para formato datetime-local
        let dataFormatada = "";
        if (avaliacao.data_avaliacao) {
            const data = new Date(avaliacao.data_avaliacao);
            dataFormatada = data.toISOString().slice(0, 16);
        }
        
        document.getElementById("avaliacaoId").value = idAvaliacao;
        document.getElementById("livroId").value = idLivro;
        document.getElementById("usuarioId").value = idUsuario;
        document.getElementById("nota").value = avaliacao.nota || "";
        document.getElementById("titulo").value = avaliacao.titulo || "";
        document.getElementById("descricao").value = avaliacao.descricao || "";
        document.getElementById("dataAvaliacao").value = dataFormatada;
        
        document.getElementById("btnSalvar").style.display = "none";
        document.getElementById("btnAtualizar").style.display = "inline-block";
    }
}

// Atualizar avaliação
async function atualizarAvaliacao() {
    const id = document.getElementById("avaliacaoId").value;
    const livroId = document.getElementById("livroId").value;
    const usuarioId = document.getElementById("usuarioId").value;
    const nota = document.getElementById("nota").value;
    const titulo = document.getElementById("titulo").value;
    const descricao = document.getElementById("descricao").value;
    const dataAvaliacao = document.getElementById("dataAvaliacao").value;

    if (!livroId || !usuarioId || !nota || !titulo || !dataAvaliacao) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

    const avaliacaoAtualizada = {
        id_livro: parseInt(livroId),
        id_usuario: parseInt(usuarioId),
        nota: parseFloat(nota),
        titulo: titulo,
        descricao: descricao || null,
        data_avaliacao: dataAvaliacao
    };

    const resultado = await PUT(`/avaliacoes/${id}`, avaliacaoAtualizada);
    
    if (resultado) {
        alert("Avaliação atualizada com sucesso!");
        limparFormulario();
        listarAvaliacoes();
    }
}

// Deletar avaliação
async function deletarAvaliacao(id) {
    if (!confirm("Deseja realmente deletar esta avaliação?")) {
        return;
    }

    const resultado = await DELETE(`/avaliacoes/${id}`);
    
    if (resultado !== undefined) {
        alert("Avaliação deletada com sucesso!");
        listarAvaliacoes();
    }
}

// Limpar formulário
function limparFormulario() {
    document.getElementById("avaliacaoId").value = "";
    document.getElementById("livroId").value = "";
    document.getElementById("usuarioId").value = "";
    document.getElementById("nota").value = "";
    document.getElementById("titulo").value = "";
    document.getElementById("descricao").value = "";
    document.getElementById("dataAvaliacao").value = "";
    
    document.getElementById("btnSalvar").style.display = "inline-block";
    document.getElementById("btnAtualizar").style.display = "none";
}
