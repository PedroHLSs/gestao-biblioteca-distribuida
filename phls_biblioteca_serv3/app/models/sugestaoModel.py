from app.database import mongo

class SugestaoLivroModel:
    @staticmethod
    def to_dict(sugestao):
        return {
            "id": str(sugestao["_id"]) if "_id" in sugestao else None,
            "id_usuario": sugestao.get("id_usuario"),
            "titulo_livro": sugestao.get("titulo_livro", ""),
            "autor": sugestao.get("autor", ""),
            "comentario": sugestao.get("comentario", ""),
            "data_sugestao": sugestao.get("data_sugestao")
        }

    @staticmethod
    def from_dict(data):
        return {
            "id_usuario": data.get("id_usuario"),
            "titulo_livro": data.get("titulo_livro", ""),
            "autor": data.get("autor", ""),
            "comentario": data.get("comentario", ""),
            "data_sugestao": data.get("data_sugestao")
        }
