const API_GESTOR = "http://localhost:8080/api/gestor";

// Método GET
async function GET(endpoint) {
    try {
        const response = await fetch(`${API_GESTOR}${endpoint}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });
        
        if (!response.ok) {
            const errorText = await response.text();
            console.error("Erro GET:", errorText);
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error("Erro no GET:", error);
        return null;
    }
}

// Método POST
async function POST(endpoint, body) {
    try {
        console.log("Enviando POST para:", `${API_GESTOR}${endpoint}`);
        console.log("Corpo:", JSON.stringify(body, null, 2));
        
        const response = await fetch(`${API_GESTOR}${endpoint}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body),
        });
        
        const responseText = await response.text();
        console.log("Resposta do servidor:", responseText);
        
        if (!response.ok) {
            console.error("Erro do servidor:", responseText);
            throw new Error(responseText || `HTTP error! status: ${response.status}`);
        }
        
        // Tenta parsear como JSON, se falhar retorna o texto
        try {
            return JSON.parse(responseText);
        } catch (e) {
            return responseText;
        }
    } catch (error) {
        console.error("Erro no POST:", error);
        throw error;
    }
}

// Método PUT
async function PUT(endpoint, body) {
    try {
        const response = await fetch(`${API_GESTOR}${endpoint}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body),
        });
        
        const responseText = await response.text();
        
        if (!response.ok) {
            console.error("Erro do servidor:", responseText);
            throw new Error(responseText || `HTTP error! status: ${response.status}`);
        }
        
        try {
            return JSON.parse(responseText);
        } catch (e) {
            return responseText;
        }
    } catch (error) {
        console.error("Erro no PUT:", error);
        throw error;
    }
}

// Método DELETE
async function DELETE(endpoint) {
    try {
        const response = await fetch(`${API_GESTOR}${endpoint}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            },
        });
        
        if (!response.ok) {
            const errorText = await response.text();
            console.error("Erro do servidor:", errorText);
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        return true;
    } catch (error) {
        console.error("Erro no DELETE:", error);
        throw error;
    }
}