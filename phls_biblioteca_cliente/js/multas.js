// Listar todas as multas
async function listarMultas() {
    const multas = await GET("/multas");
    if (!multas) return;

    const tbody = document.querySelector("#tabelaMultas tbody");
    tbody.innerHTML = "";

    multas.forEach(m => {
        const tr = document.createElement("tr");
        const idMulta = m.idMultas || m.id_multa || m.id;
        const idEmprestimo = m.emprestimo?.idEmprestimo || m.emprestimo?.id_emprestimo || m.id_emprestimo || "—";
        const valor = m.valorMulta ? parseFloat(m.valorMulta).toFixed(2) : (m.valor ? parseFloat(m.valor).toFixed(2) : "0.00");
        
        tr.innerHTML = `
            <td>${idMulta}</td>
            <td>${idEmprestimo}</td>
            <td>R$ ${valor}</td>
            <td>${m.descricao || "—"}</td>
            <td>${m.status || "—"}</td>
            <td>
                <button onclick="editarMulta(${idMulta})">Editar</button>
                <button onclick="deletarMulta(${idMulta})">Deletar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Criar nova multa
async function criarMulta() {
    const emprestimoId = document.getElementById("emprestimoId").value;
    const valor = document.getElementById("valor").value;
    const descricao = document.getElementById("descricao").value;
    const status = document.getElementById("status").value;

    if (!emprestimoId || !valor || !status) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

    const novaMulta = {
        emprestimo: { idEmprestimo: parseInt(emprestimoId) },
        valorMulta: parseFloat(valor),
        descricao: descricao || null,
        status: status
    };

    const resultado = await POST("/multas", novaMulta);
    
    if (resultado) {
        alert("Multa criada com sucesso!");
        limparFormulario();
        listarMultas();
    }
}

// Editar multa existente
async function editarMulta(id) {
    const multa = await GET(`/multas/${id}`);
    
    if (multa) {
        const idMulta = multa.idMultas || multa.id_multa || multa.id;
        const idEmprestimo = multa.emprestimo?.idEmprestimo || multa.emprestimo?.id_emprestimo || multa.id_emprestimo || "";
        
        document.getElementById("multaId").value = idMulta;
        document.getElementById("emprestimoId").value = idEmprestimo;
        document.getElementById("valor").value = multa.valorMulta || multa.valor || "";
        document.getElementById("descricao").value = multa.descricao || "";
        document.getElementById("status").value = multa.status || "";
        
        document.getElementById("btnSalvar").style.display = "none";
        document.getElementById("btnAtualizar").style.display = "inline-block";
    }
}

// Atualizar multa
async function atualizarMulta() {
    const id = document.getElementById("multaId").value;
    const emprestimoId = document.getElementById("emprestimoId").value;
    const valor = document.getElementById("valor").value;
    const descricao = document.getElementById("descricao").value;
    const status = document.getElementById("status").value;

    if (!emprestimoId || !valor || !status) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

    const multaAtualizada = {
        emprestimo: { idEmprestimo: parseInt(emprestimoId) },
        valorMulta: parseFloat(valor),
        descricao: descricao || null,
        status: status
    };

    const resultado = await PUT(`/multas/${id}`, multaAtualizada);
    
    if (resultado) {
        alert("Multa atualizada com sucesso!");
        limparFormulario();
        listarMultas();
    }
}

// Deletar multa
async function deletarMulta(id) {
    if (!confirm("Deseja realmente deletar esta multa?")) {
        return;
    }

    const resultado = await DELETE(`/multas/${id}`);
    
    if (resultado !== undefined) {
        alert("Multa deletada com sucesso!");
        listarMultas();
    }
}

// Limpar formulário
function limparFormulario() {
    document.getElementById("multaId").value = "";
    document.getElementById("emprestimoId").value = "";
    document.getElementById("valor").value = "";
    document.getElementById("descricao").value = "";
    document.getElementById("status").value = "";
    
    document.getElementById("btnSalvar").style.display = "inline-block";
    document.getElementById("btnAtualizar").style.display = "none";
}
