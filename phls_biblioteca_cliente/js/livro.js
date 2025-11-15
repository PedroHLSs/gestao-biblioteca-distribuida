// Listar todos os livros
async function listarLivros() {
    const livros = await GET("/livros");
    if (!livros) return;

    const tbody = document.querySelector("#tabelaLivros tbody");
    tbody.innerHTML = "";

    livros.forEach(l => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${l.id}</td>
            <td>${l.titulo}</td>
            <td>${l.anoPublicacao}</td>
            <td>${l.isbn || "—"}</td>
            <td>${l.autor?.nome || "—"}</td>
            <td>${l.categoria?.nome || "—"}</td>
            <td>${l.editora?.nome || "—"}</td>
            <td>${l.quantidade_total || 0}</td>
            <td>${l.quantidade_disponivel || 0}</td>
            <td>
                <button onclick="editarLivro(${l.id})">Editar</button>
                <button onclick="deletarLivro(${l.id})">Deletar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Criar novo livro
async function criarLivro() {
    const titulo = document.getElementById("titulo").value;
    const ano = document.getElementById("ano").value;
    const isbn = document.getElementById("isbn").value;
    const autorId = document.getElementById("autorId").value;
    const categoriaId = document.getElementById("categoriaId").value;
    const editoraId = document.getElementById("editoraId").value;
    const quantidadeTotal = document.getElementById("quantidadeTotal").value;
    const quantidadeDisponivel = document.getElementById("quantidadeDisponivel").value;

    if (!titulo || !ano) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

      const novoLivro = {
        titulo: titulo,
        anoPublicacao: parseInt(ano), 
        isbn: isbn || null,
        autor: autorId ? { id: parseInt(autorId) } : null,  
        categoria: categoriaId ? { id: parseInt(categoriaId) } : null,  
        editora: editoraId ? { id: parseInt(editoraId) } : null, 
        quantidadeTotal: quantidadeTotal ? parseInt(quantidadeTotal) : 0, 
        quantidadeDisponivel: quantidadeDisponivel ? parseInt(quantidadeDisponivel) : 0 
    };

    const resultado = await POST("/livros", novoLivro);
    
    if (resultado) {
        alert("Livro criado com sucesso!");
        limparFormulario();
        listarLivros();
    }
}

// Editar livro existente
async function editarLivro(id) {
    if (!id) {
        alert("ID inválido!");
        return;
    }
    
    console.log("Buscando livro com ID:", id);
    const livro = await GET(`/livros/${id}`);
    
    if (!livro) {
        alert("Livro não encontrado!");
        return;
    }
    
    console.log("Livro recebido:", livro);
    
    document.getElementById("livroId").value = livro.id || "";
    document.getElementById("titulo").value = livro.titulo || "";
    document.getElementById("ano").value = livro.anoPublicacao || livro.ano_publicacao || "";
    document.getElementById("isbn").value = livro.isbn || "";
    document.getElementById("autorId").value = livro.autor?.id || livro.autor?.id_autor || "";
    document.getElementById("categoriaId").value = livro.categoria?.id || livro.categoria?.id_categoria || "";
    document.getElementById("editoraId").value = livro.editora?.id || livro.editora?.id_editora || "";
    document.getElementById("quantidadeTotal").value = livro.quantidadeTotal || livro.quantidade_total || 0;
    document.getElementById("quantidadeDisponivel").value = livro.quantidadeDisponivel || livro.quantidade_disponivel || 0;
    
    document.getElementById("btnSalvar").style.display = "none";
    document.getElementById("btnAtualizar").style.display = "inline-block";
}

// Atualizar livro
async function atualizarLivro() {
    const id = document.getElementById("livroId").value;
    const titulo = document.getElementById("titulo").value;
    const ano = document.getElementById("ano").value;
    const isbn = document.getElementById("isbn").value;
    const autorId = document.getElementById("autorId").value;
    const categoriaId = document.getElementById("categoriaId").value;
    const editoraId = document.getElementById("editoraId").value;
    const quantidadeTotal = document.getElementById("quantidadeTotal").value;
    const quantidadeDisponivel = document.getElementById("quantidadeDisponivel").value;

    if (!titulo || !ano) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

    const livroAtualizado = {
        titulo: titulo,
        anoPublicacao: parseInt(ano),
        isbn: isbn || null,
        autor: autorId ? { id: parseInt(autorId) } : null,
        categoria: categoriaId ? { id: parseInt(categoriaId) } : null,
        editora: editoraId ? { id: parseInt(editoraId) } : null,
        quantidadeTotal: quantidadeTotal ? parseInt(quantidadeTotal) : 0,
        quantidadeDisponivel: quantidadeDisponivel ? parseInt(quantidadeDisponivel) : 0
    };

    const resultado = await PUT(`/livros/${id}`, livroAtualizado);
    
    if (resultado) {
        alert("Livro atualizado com sucesso!");
        limparFormulario();
        listarLivros();
    }
}

// Deletar livro
async function deletarLivro(id) {
    if (!confirm("Deseja realmente deletar este livro?")) {
        return;
    }

    const resultado = await DELETE(`/livros/${id}`);
    
    if (resultado !== undefined) {
        alert("Livro deletado com sucesso!");
        listarLivros();
    }
}

// Limpar formulário
function limparFormulario() {
    document.getElementById("livroId").value = "";
    document.getElementById("titulo").value = "";
    document.getElementById("ano").value = "";
    document.getElementById("isbn").value = "";
    document.getElementById("autorId").value = "";
    document.getElementById("categoriaId").value = "";
    document.getElementById("editoraId").value = "";
    document.getElementById("quantidadeTotal").value = "";
    document.getElementById("quantidadeDisponivel").value = "";
    
    document.getElementById("btnSalvar").style.display = "inline-block";
    document.getElementById("btnAtualizar").style.display = "none";
}
