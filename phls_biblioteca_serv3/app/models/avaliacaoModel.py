from app.database import mongo

class AvaliacaoModel:
    @staticmethod
    def to_dict(avaliacao):
        return {
            "id": str(avaliacao["_id"]) if "_id" in avaliacao else None,
            "id_livro": avaliacao.get("id_livro"),
            "id_usuario": avaliacao.get("id_usuario"),
            "nota": avaliacao.get("nota"),
            "titulo": avaliacao.get("titulo", ""),
            "descricao": avaliacao.get("descricao", ""),
            "data_avaliacao": avaliacao.get("data_avaliacao")
        }

    @staticmethod
    def from_dict(data):
        return {
            "id_livro": data.get("id_livro"),
            "id_usuario": data.get("id_usuario"),
            "nota": data.get("nota"),
            "titulo": data.get("titulo", ""),
            "descricao": data.get("descricao", ""),
            "data_avaliacao": data.get("data_avaliacao")
        }
