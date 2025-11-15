// Listar todos os empréstimos
async function listarEmprestimos() {
    const emprestimos = await GET("/emprestimos");
    if (!emprestimos) return;

    const tbody = document.querySelector("#tabelaEmprestimos tbody");
    tbody.innerHTML = "";

    emprestimos.forEach(e => {
        const tr = document.createElement("tr");
        const idEmprestimo = e.idEmprestimo || e.id_emprestimo || e.id;
        const idUsuario = e.idUsuario || e.id_usuario || e.usuario?.id || "—";
        const idLivro = e.idLivro || e.id_livro || e.livro?.id || "—";
        
        tr.innerHTML = `
            <td>${idEmprestimo}</td>
            <td>${idUsuario}</td>
            <td>${idLivro}</td>
            <td>${e.dataEmprestimo || e.data_emprestimo || "—"}</td>
            <td>${e.dataPrevistaDevolucao || e.data_prevista_devolucao || "—"}</td>
            <td>${e.dataDevolucao || e.data_devolucao || "—"}</td>
            <td>${e.status || "—"}</td>
            <td>
                <button onclick="editarEmprestimo(${idEmprestimo})">Editar</button>
                <button onclick="deletarEmprestimo(${idEmprestimo})">Deletar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Criar novo empréstimo
async function criarEmprestimo() {
    const usuarioId = document.getElementById("usuarioId").value;
    const livroId = document.getElementById("livroId").value;
    const dataEmprestimo = document.getElementById("dataEmprestimo").value;
    const dataPrevistaDevolucao = document.getElementById("dataPrevistaDevolucao").value;
    const dataDevolucao = document.getElementById("dataDevolucao").value;
    const status = document.getElementById("status").value;

    if (!usuarioId || !livroId || !dataEmprestimo || !dataPrevistaDevolucao || !status) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

    const novoEmprestimo = {
        idUsuario: parseInt(usuarioId),
        idLivro: parseInt(livroId),
        dataEmprestimo: dataEmprestimo,
        dataPrevistaDevolucao: dataPrevistaDevolucao,
        dataDevolucao: dataDevolucao || null,
        status: status
    };

    const resultado = await POST("/emprestimos", novoEmprestimo);
    
    if (resultado) {
        alert("Empréstimo criado com sucesso!");
        limparFormulario();
        listarEmprestimos();
    }
}

// Editar empréstimo existente
async function editarEmprestimo(id) {
    const emprestimo = await GET(`/emprestimos/${id}`);
    
    if (emprestimo) {
        const idEmprestimo = emprestimo.idEmprestimo || emprestimo.id_emprestimo || emprestimo.id;
        const idUsuario = emprestimo.idUsuario || emprestimo.id_usuario || emprestimo.usuario?.id || "";
        const idLivro = emprestimo.idLivro || emprestimo.id_livro || emprestimo.livro?.id || "";
        
        document.getElementById("emprestimoId").value = idEmprestimo;
        document.getElementById("usuarioId").value = idUsuario;
        document.getElementById("livroId").value = idLivro;
        document.getElementById("dataEmprestimo").value = emprestimo.dataEmprestimo || emprestimo.data_emprestimo || "";
        document.getElementById("dataPrevistaDevolucao").value = emprestimo.dataPrevistaDevolucao || emprestimo.data_prevista_devolucao || "";
        document.getElementById("dataDevolucao").value = emprestimo.dataDevolucao || emprestimo.data_devolucao || "";
        document.getElementById("status").value = emprestimo.status || "";
        
        document.getElementById("btnSalvar").style.display = "none";
        document.getElementById("btnAtualizar").style.display = "inline-block";
    }
}

// Atualizar empréstimo
async function atualizarEmprestimo() {
    const id = document.getElementById("emprestimoId").value;
    const usuarioId = document.getElementById("usuarioId").value;
    const livroId = document.getElementById("livroId").value;
    const dataEmprestimo = document.getElementById("dataEmprestimo").value;
    const dataPrevistaDevolucao = document.getElementById("dataPrevistaDevolucao").value;
    const dataDevolucao = document.getElementById("dataDevolucao").value;
    const status = document.getElementById("status").value;

    if (!usuarioId || !livroId || !dataEmprestimo || !dataPrevistaDevolucao || !status) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

    const emprestimoAtualizado = {
        idUsuario: parseInt(usuarioId),
        idLivro: parseInt(livroId),
        dataEmprestimo: dataEmprestimo,
        dataPrevistaDevolucao: dataPrevistaDevolucao,
        dataDevolucao: dataDevolucao || null,
        status: status
    };

    const resultado = await PUT(`/emprestimos/${id}`, emprestimoAtualizado);
    
    if (resultado) {
        alert("Empréstimo atualizado com sucesso!");
        limparFormulario();
        listarEmprestimos();
    }
}

// Deletar empréstimo
async function deletarEmprestimo(id) {
    if (!confirm("Deseja realmente deletar este empréstimo?")) {
        return;
    }

    const resultado = await DELETE(`/emprestimos/${id}`);
    
    if (resultado !== undefined) {
        alert("Empréstimo deletado com sucesso!");
        listarEmprestimos();
    }
}

// Limpar formulário
function limparFormulario() {
    document.getElementById("emprestimoId").value = "";
    document.getElementById("usuarioId").value = "";
    document.getElementById("livroId").value = "";
    document.getElementById("dataEmprestimo").value = "";
    document.getElementById("dataPrevistaDevolucao").value = "";
    document.getElementById("dataDevolucao").value = "";
    document.getElementById("status").value = "";
    
    document.getElementById("btnSalvar").style.display = "inline-block";
    document.getElementById("btnAtualizar").style.display = "none";
}
