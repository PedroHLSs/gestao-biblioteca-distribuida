    // Listar todas as sugestões
async function listarSugestoes() {
    const sugestoes = await GET("/sugestoes");
    if (!sugestoes) return;

    const tbody = document.querySelector("#tabelaSugestoes tbody");
    tbody.innerHTML = "";

    sugestoes.forEach(s => {
        const tr = document.createElement("tr");
        const idSugestao = s._id || s.id || s.idSugestao;
        const idUsuario = s.id_usuario || s.usuario?.id_usuario || s.usuario?.id || "—";
        const dataSugestao = s.data_sugestao ? new Date(s.data_sugestao).toLocaleString('pt-BR') : "—";
        
        tr.innerHTML = `
            <td>${idSugestao}</td>
            <td>${idUsuario}</td>
            <td>${s.titulo_livro || "—"}</td>
            <td>${s.autor || "—"}</td>
            <td>${s.comentario || "—"}</td>
            <td>${dataSugestao}</td>
            <td>
                <button onclick="editarSugestao('${idSugestao}')">Editar</button>
                <button onclick="deletarSugestao('${idSugestao}')">Deletar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Criar nova sugestão
async function criarSugestao() {
    const usuarioId = document.getElementById("usuarioId").value;
    const tituloLivro = document.getElementById("tituloLivro").value;
    const autor = document.getElementById("autor").value;
    const comentario = document.getElementById("comentario").value;
    const dataSugestao = document.getElementById("dataSugestao").value;

    if (!usuarioId || !tituloLivro || !autor || !dataSugestao) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

    const novaSugestao = {
        id_usuario: parseInt(usuarioId),
        titulo_livro: tituloLivro,
        autor: autor,
        comentario: comentario || null,
        data_sugestao: dataSugestao
    };

    const resultado = await POST("/sugestoes", novaSugestao);
    
    if (resultado) {
        alert("Sugestão criada com sucesso!");
        limparFormulario();
        listarSugestoes();
    }
}

// Editar sugestão existente
async function editarSugestao(id) {
    const sugestao = await GET(`/sugestoes/${id}`);
    
    if (sugestao) {
        const idSugestao = sugestao._id || sugestao.id || sugestao.idSugestao;
        const idUsuario = sugestao.id_usuario || sugestao.usuario?.id_usuario || sugestao.usuario?.id || "";
        
        // Converter data para formato datetime-local
        let dataFormatada = "";
        if (sugestao.data_sugestao) {
            const data = new Date(sugestao.data_sugestao);
            dataFormatada = data.toISOString().slice(0, 16);
        }
        
        document.getElementById("sugestaoId").value = idSugestao;
        document.getElementById("usuarioId").value = idUsuario;
        document.getElementById("tituloLivro").value = sugestao.titulo_livro || "";
        document.getElementById("autor").value = sugestao.autor || "";
        document.getElementById("comentario").value = sugestao.comentario || "";
        document.getElementById("dataSugestao").value = dataFormatada;
        
        document.getElementById("btnSalvar").style.display = "none";
        document.getElementById("btnAtualizar").style.display = "inline-block";
    }
}

// Atualizar sugestão
async function atualizarSugestao() {
    const id = document.getElementById("sugestaoId").value;
    const usuarioId = document.getElementById("usuarioId").value;
    const tituloLivro = document.getElementById("tituloLivro").value;
    const autor = document.getElementById("autor").value;
    const comentario = document.getElementById("comentario").value;
    const dataSugestao = document.getElementById("dataSugestao").value;

    if (!usuarioId || !tituloLivro || !autor || !dataSugestao) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

    const sugestaoAtualizada = {
        id_usuario: parseInt(usuarioId),
        titulo_livro: tituloLivro,
        autor: autor,
        comentario: comentario || null,
        data_sugestao: dataSugestao
    };

    const resultado = await PUT(`/sugestoes/${id}`, sugestaoAtualizada);
    
    if (resultado) {
        alert("Sugestão atualizada com sucesso!");
        limparFormulario();
        listarSugestoes();
    }
}

// Deletar sugestão
async function deletarSugestao(id) {
    if (!confirm("Deseja realmente deletar esta sugestão?")) {
        return;
    }

    const resultado = await DELETE(`/sugestoes/${id}`);
    
    if (resultado !== undefined) {
        alert("Sugestão deletada com sucesso!");
        listarSugestoes();
    }
}

// Limpar formulário
function limparFormulario() {
    document.getElementById("sugestaoId").value = "";
    document.getElementById("usuarioId").value = "";
    document.getElementById("tituloLivro").value = "";
    document.getElementById("autor").value = "";
    document.getElementById("comentario").value = "";
    document.getElementById("dataSugestao").value = "";
    
    document.getElementById("btnSalvar").style.display = "inline-block";
    document.getElementById("btnAtualizar").style.display = "none";
}
