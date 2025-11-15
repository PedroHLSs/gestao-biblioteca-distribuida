// Listar todos os autores
async function listarAutores() {
    const autores = await GET("/autores");
    if (!autores) return;

    const tbody = document.querySelector("#tabelaAutores tbody");
    tbody.innerHTML = "";

    autores.forEach(a => {
        const tr = document.createElement("tr");
        const idAutor = a.id_autor || a.id || a.idAutor;
        tr.innerHTML = `
            <td>${idAutor}</td>
            <td>${a.nome}</td>
            <td>${a.nacionalidade || "—"}</td>
            <td>${a.data_nascimento || "—"}</td>
            <td>
                <button onclick="editarAutor(${idAutor})">Editar</button>
                <button onclick="deletarAutor(${idAutor})">Deletar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Criar novo autor
async function criarAutor() {
    const nome = document.getElementById("nome").value;
    const nacionalidade = document.getElementById("nacionalidade").value;
    const dataNascimento = document.getElementById("dataNascimento").value;

    if (!nome) {
        alert("Preencha o campo nome!");
        return;
    }

    const novoAutor = {
        nome: nome,
        nacionalidade: nacionalidade || null,
        data_nascimento: dataNascimento || null
    };

    const resultado = await POST("/autores", novoAutor);
    
    if (resultado) {
        alert("Autor criado com sucesso!");
        limparFormulario();
        listarAutores();
    }
}

// Editar autor existente
async function editarAutor(id) {
    const autor = await GET(`/autores/${id}`);
    
    if (autor) {
        const idAutor = autor.id_autor || autor.id || autor.idAutor;
        document.getElementById("autorId").value = idAutor;
        document.getElementById("nome").value = autor.nome;
        document.getElementById("nacionalidade").value = autor.nacionalidade || "";
        document.getElementById("dataNascimento").value = autor.data_nascimento || "";
        
        document.getElementById("btnSalvar").style.display = "none";
        document.getElementById("btnAtualizar").style.display = "inline-block";
    }
}

// Atualizar autor
async function atualizarAutor() {
    const id = document.getElementById("autorId").value;
    const nome = document.getElementById("nome").value;
    const nacionalidade = document.getElementById("nacionalidade").value;
    const dataNascimento = document.getElementById("dataNascimento").value;

    if (!nome) {
        alert("Preencha o campo nome!");
        return;
    }

    const autorAtualizado = {
        nome: nome,
        nacionalidade: nacionalidade || null,
        data_nascimento: dataNascimento || null
    };

    const resultado = await PUT(`/autores/${id}`, autorAtualizado);
    
    if (resultado) {
        alert("Autor atualizado com sucesso!");
        limparFormulario();
        listarAutores();
    }
}

// Deletar autor
async function deletarAutor(id) {
    if (!confirm("Deseja realmente deletar este autor?")) {
        return;
    }

    const resultado = await DELETE(`/autores/${id}`);
    
    if (resultado !== undefined) {
        alert("Autor deletado com sucesso!");
        listarAutores();
    }
}

// Limpar formulário
function limparFormulario() {
    document.getElementById("autorId").value = "";
    document.getElementById("nome").value = "";
    document.getElementById("nacionalidade").value = "";
    document.getElementById("dataNascimento").value = "";
    
    document.getElementById("btnSalvar").style.display = "inline-block";
    document.getElementById("btnAtualizar").style.display = "none";
}
