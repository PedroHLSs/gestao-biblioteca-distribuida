// Listar todos os usuários
async function listarUsuarios() {
    const usuarios = await GET("/usuarios");
    if (!usuarios) return;

    const tbody = document.querySelector("#tabelaUsuarios tbody");
    tbody.innerHTML = "";

    usuarios.forEach(u => {
        const tr = document.createElement("tr");
        const idUsuario = u.id || u.id_usuario || u.idUsuario;
        tr.innerHTML = `
            <td>${idUsuario}</td>
            <td>${u.nome}</td>
            <td>${u.email}</td>
            <td>${u.telefone || "—"}</td>
            <td>${u.endereco || "—"}</td>
            <td>
                <button onclick="editarUsuario(${idUsuario})">Editar</button>
                <button onclick="deletarUsuario(${idUsuario})">Deletar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Criar novo usuário
async function criarUsuario() {
    const nome = document.getElementById("nome").value;
    const email = document.getElementById("email").value;
    const telefone = document.getElementById("telefone").value;
    const endereco = document.getElementById("endereco").value;

    if (!nome || !email) {
        alert("Preencha os campos obrigatórios (Nome e Email)!");
        return;
    }

    const novoUsuario = {
        nome: nome,
        email: email,
        telefone: telefone || null,
        endereco: endereco || null
    };

    const resultado = await POST("/usuarios", novoUsuario);
    
    if (resultado) {
        alert("Usuário criado com sucesso!");
        limparFormulario();
        listarUsuarios();
    }
}

// Editar usuário existente
async function editarUsuario(id) {
    if (!id) {
        alert("ID inválido!");
        return;
    }
    
    console.log("Buscando usuário com ID:", id);
    const usuario = await GET(`/usuarios/${id}`);
    
    if (!usuario) {
        alert("Usuário não encontrado!");
        return;
    }
    
    console.log("Usuário recebido:", usuario);
    
    document.getElementById("usuarioId").value = usuario.id || usuario.id_usuario || "";
    document.getElementById("nome").value = usuario.nome || "";
    document.getElementById("email").value = usuario.email || "";
    document.getElementById("telefone").value = usuario.telefone || "";
    document.getElementById("endereco").value = usuario.endereco || "";
    
    document.getElementById("btnSalvar").style.display = "none";
    document.getElementById("btnAtualizar").style.display = "inline-block";
}

// Atualizar usuário
async function atualizarUsuario() {
    const id = document.getElementById("usuarioId").value;
    const nome = document.getElementById("nome").value;
    const email = document.getElementById("email").value;
    const telefone = document.getElementById("telefone").value;
    const endereco = document.getElementById("endereco").value;

    if (!nome || !email) {
        alert("Preencha os campos obrigatórios (Nome e Email)!");
        return;
    }

    const usuarioAtualizado = {
        nome: nome,
        email: email,
        telefone: telefone || null,
        endereco: endereco || null
    };

    const resultado = await PUT(`/usuarios/${id}`, usuarioAtualizado);
    
    if (resultado) {
        alert("Usuário atualizado com sucesso!");
        limparFormulario();
        listarUsuarios();
    }
}

// Deletar usuário
async function deletarUsuario(id) {
    if (!confirm("Deseja realmente deletar este usuário?")) {
        return;
    }

    const resultado = await DELETE(`/usuarios/${id}`);
    
    if (resultado !== undefined) {
        alert("Usuário deletado com sucesso!");
        listarUsuarios();
    }
}

// Limpar formulário
function limparFormulario() {
    document.getElementById("usuarioId").value = "";
    document.getElementById("nome").value = "";
    document.getElementById("email").value = "";
    document.getElementById("telefone").value = "";
    document.getElementById("endereco").value = "";
    
    document.getElementById("btnSalvar").style.display = "inline-block";
    document.getElementById("btnAtualizar").style.display = "none";
}
