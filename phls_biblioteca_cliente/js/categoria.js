// Listar todas as categorias
async function listarCategorias() {
    const categorias = await GET("/categorias");
    if (!categorias) return;

    const tbody = document.querySelector("#tabelaCategorias tbody");
    tbody.innerHTML = "";

    categorias.forEach(c => {
        const tr = document.createElement("tr");
        const idCategoria = c.id_categoria || c.id || c.idCategoria;
        tr.innerHTML = `
            <td>${idCategoria}</td>
            <td>${c.nome}</td>
            <td>${c.descricao || "—"}</td>
            <td>
                <button onclick="editarCategoria(${idCategoria})">Editar</button>
                <button onclick="deletarCategoria(${idCategoria})">Deletar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Criar nova categoria
async function criarCategoria() {
    const nome = document.getElementById("nome").value;
    const descricao = document.getElementById("descricao").value;

    if (!nome) {
        alert("Preencha o campo nome!");
        return;
    }

    const novaCategoria = {
        nome: nome,
        descricao: descricao || null
    };

    const resultado = await POST("/categorias", novaCategoria);
    
    if (resultado) {
        alert("Categoria criada com sucesso!");
        limparFormulario();
        listarCategorias();
    }
}

// Editar categoria existente
async function editarCategoria(id) {
    const categoria = await GET(`/categorias/${id}`);
    
    if (categoria) {
        const idCategoria = categoria.id_categoria || categoria.id || categoria.idCategoria;
        document.getElementById("categoriaId").value = idCategoria;
        document.getElementById("nome").value = categoria.nome;
        document.getElementById("descricao").value = categoria.descricao || "";
        
        document.getElementById("btnSalvar").style.display = "none";
        document.getElementById("btnAtualizar").style.display = "inline-block";
    }
}

// Atualizar categoria
async function atualizarCategoria() {
    const id = document.getElementById("categoriaId").value;
    const nome = document.getElementById("nome").value;
    const descricao = document.getElementById("descricao").value;

    if (!nome) {
        alert("Preencha o campo nome!");
        return;
    }

    const categoriaAtualizada = {
        nome: nome,
        descricao: descricao || null
    };

    const resultado = await PUT(`/categorias/${id}`, categoriaAtualizada);
    
    if (resultado) {
        alert("Categoria atualizada com sucesso!");
        limparFormulario();
        listarCategorias();
    }
}

// Deletar categoria
async function deletarCategoria(id) {
    if (!confirm("Deseja realmente deletar esta categoria?")) {
        return;
    }

    const resultado = await DELETE(`/categorias/${id}`);
    
    if (resultado !== undefined) {
        alert("Categoria deletada com sucesso!");
        listarCategorias();
    }
}

// Limpar formulário
function limparFormulario() {
    document.getElementById("categoriaId").value = "";
    document.getElementById("nome").value = "";
    document.getElementById("descricao").value = "";
    
    document.getElementById("btnSalvar").style.display = "inline-block";
    document.getElementById("btnAtualizar").style.display = "none";
}
