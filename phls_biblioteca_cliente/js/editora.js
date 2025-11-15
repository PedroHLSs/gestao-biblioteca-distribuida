// Listar todas as editoras
async function listarEditoras() {
    const editoras = await GET("/editoras");
    if (!editoras) return;

    const tbody = document.querySelector("#tabelaEditoras tbody");
    tbody.innerHTML = "";

    editoras.forEach(e => {
        const tr = document.createElement("tr");
        const idEditora = e.id_editora || e.id || e.idEditora;
        tr.innerHTML = `
            <td>${idEditora}</td>
            <td>${e.nome}</td>
            <td>${e.cidade || "—"}</td>
            <td>${e.estado || "—"}</td>
            <td>${e.pais || "—"}</td>
            <td>
                <button onclick="editarEditora(${idEditora})">Editar</button>
                <button onclick="deletarEditora(${idEditora})">Deletar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Criar nova editora
async function criarEditora() {
    const nome = document.getElementById("nome").value;
    const cidade = document.getElementById("cidade").value;
    const estado = document.getElementById("estado").value;
    const pais = document.getElementById("pais").value;

    if (!nome) {
        alert("Preencha o campo nome!");
        return;
    }

    const novaEditora = {
        nome: nome,
        cidade: cidade || null,
        estado: estado || null,
        pais: pais || null
    };

    const resultado = await POST("/editoras", novaEditora);
    
    if (resultado) {
        alert("Editora criada com sucesso!");
        limparFormulario();
        listarEditoras();
    }
}

// Editar editora existente
async function editarEditora(id) {
    const editora = await GET(`/editoras/${id}`);
    
    if (editora) {
        const idEditora = editora.id_editora || editora.id || editora.idEditora;
        document.getElementById("editoraId").value = idEditora;
        document.getElementById("nome").value = editora.nome;
        document.getElementById("cidade").value = editora.cidade || "";
        document.getElementById("estado").value = editora.estado || "";
        document.getElementById("pais").value = editora.pais || "";
        
        document.getElementById("btnSalvar").style.display = "none";
        document.getElementById("btnAtualizar").style.display = "inline-block";
    }
}

// Atualizar editora
async function atualizarEditora() {
    const id = document.getElementById("editoraId").value;
    const nome = document.getElementById("nome").value;
    const cidade = document.getElementById("cidade").value;
    const estado = document.getElementById("estado").value;
    const pais = document.getElementById("pais").value;

    if (!nome) {
        alert("Preencha o campo nome!");
        return;
    }

    const editoraAtualizada = {
        nome: nome,
        cidade: cidade || null,
        estado: estado || null,
        pais: pais || null
    };

    const resultado = await PUT(`/editoras/${id}`, editoraAtualizada);
    
    if (resultado) {
        alert("Editora atualizada com sucesso!");
        limparFormulario();
        listarEditoras();
    }
}

// Deletar editora
async function deletarEditora(id) {
    if (!confirm("Deseja realmente deletar esta editora?")) {
        return;
    }

    const resultado = await DELETE(`/editoras/${id}`);
    
    if (resultado !== undefined) {
        alert("Editora deletada com sucesso!");
        listarEditoras();
    }
}

// Limpar formulário
function limparFormulario() {
    document.getElementById("editoraId").value = "";
    document.getElementById("nome").value = "";
    document.getElementById("cidade").value = "";
    document.getElementById("estado").value = "";
    document.getElementById("pais").value = "";
    
    document.getElementById("btnSalvar").style.display = "inline-block";
    document.getElementById("btnAtualizar").style.display = "none";
}
