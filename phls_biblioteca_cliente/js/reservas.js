// Listar todas as reservas
async function listarReservas() {
    const reservas = await GET("/reservas");
    if (!reservas) return;

    const tbody = document.querySelector("#tabelaReservas tbody");
    tbody.innerHTML = "";

    reservas.forEach(r => {
        const tr = document.createElement("tr");
        const idReserva = r.idReserva || r.id_reserva || r.id;
        const idUsuario = r.idUsuario || r.id_usuario || r.usuario?.id || "—";
        const idLivro = r.idLivro || r.id_livro || r.livro?.id || "—";
        
        tr.innerHTML = `
            <td>${idReserva}</td>
            <td>${idUsuario}</td>
            <td>${idLivro}</td>
            <td>${r.dataReserva || r.data_reserva || "—"}</td>
            <td>${r.status || "—"}</td>
            <td>
                <button onclick="editarReserva(${idReserva})">Editar</button>
                <button onclick="deletarReserva(${idReserva})">Deletar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Criar nova reserva
async function criarReserva() {
    const usuarioId = document.getElementById("usuarioId").value;
    const livroId = document.getElementById("livroId").value;
    const dataReserva = document.getElementById("dataReserva").value;
    const status = document.getElementById("status").value;

    if (!usuarioId || !livroId || !dataReserva || !status) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

    const novaReserva = {
        idUsuario: parseInt(usuarioId),
        idLivro: parseInt(livroId),
        dataReserva: dataReserva,
        status: status
    };

    const resultado = await POST("/reservas", novaReserva);
    
    if (resultado) {
        alert("Reserva criada com sucesso!");
        limparFormulario();
        listarReservas();
    }
}

// Editar reserva existente
async function editarReserva(id) {
    const reserva = await GET(`/reservas/${id}`);
    
    if (reserva) {
        const idReserva = reserva.idReserva || reserva.id_reserva || reserva.id;
        const idUsuario = reserva.idUsuario || reserva.id_usuario || reserva.usuario?.id || "";
        const idLivro = reserva.idLivro || reserva.id_livro || reserva.livro?.id || "";
        
        document.getElementById("reservaId").value = idReserva;
        document.getElementById("usuarioId").value = idUsuario;
        document.getElementById("livroId").value = idLivro;
        document.getElementById("dataReserva").value = reserva.dataReserva || reserva.data_reserva || "";
        document.getElementById("status").value = reserva.status || "";
        
        document.getElementById("btnSalvar").style.display = "none";
        document.getElementById("btnAtualizar").style.display = "inline-block";
    }
}

// Atualizar reserva
async function atualizarReserva() {
    const id = document.getElementById("reservaId").value;
    const usuarioId = document.getElementById("usuarioId").value;
    const livroId = document.getElementById("livroId").value;
    const dataReserva = document.getElementById("dataReserva").value;
    const status = document.getElementById("status").value;

    if (!usuarioId || !livroId || !dataReserva || !status) {
        alert("Preencha os campos obrigatórios!");
        return;
    }

    const reservaAtualizada = {
        idUsuario: parseInt(usuarioId),
        idLivro: parseInt(livroId),
        dataReserva: dataReserva,
        status: status
    };

    const resultado = await PUT(`/reservas/${id}`, reservaAtualizada);
    
    if (resultado) {
        alert("Reserva atualizada com sucesso!");
        limparFormulario();
        listarReservas();
    }
}

// Deletar reserva
async function deletarReserva(id) {
    if (!confirm("Deseja realmente deletar esta reserva?")) {
        return;
    }

    const resultado = await DELETE(`/reservas/${id}`);
    
    if (resultado !== undefined) {
        alert("Reserva deletada com sucesso!");
        listarReservas();
    }
}

// Limpar formulário
function limparFormulario() {
    document.getElementById("reservaId").value = "";
    document.getElementById("usuarioId").value = "";
    document.getElementById("livroId").value = "";
    document.getElementById("dataReserva").value = "";
    document.getElementById("status").value = "";
    
    document.getElementById("btnSalvar").style.display = "inline-block";
    document.getElementById("btnAtualizar").style.display = "none";
}
