from flask import Flask, jsonify
from app.config import Config
from app.database import init_app as init_db

def create_app():
    app = Flask(__name__)

    app.config.from_object(Config)
    init_db(app)
    
    app.config['JSON_AS_ASCII'] = False

    @app.route('/', methods=['GET'])
    def index():
        return jsonify({
            "service": "Biblioteca - Serviço de Avaliações e Sugestões",
            "status": "online",
            "version": "1.0.0",
            "port": 8084,
            "database": "MongoDB",
            "endpoints": {
                "avaliacoes": {
                    "list": "GET /api/avaliacoes",
                    "get": "GET /api/avaliacoes/{id}",
                    "create": "POST /api/avaliacoes",
                    "update": "PUT /api/avaliacoes/{id}",
                    "delete": "DELETE /api/avaliacoes/{id}"
                },
                "sugestoes": {
                    "list": "GET /api/sugestoes",
                    "get": "GET /api/sugestoes/{id}",
                    "create": "POST /api/sugestoes",
                    "update": "PUT /api/sugestoes/{id}",
                    "delete": "DELETE /api/sugestoes/{id}"
                }
            }
        }), 200
    
    try:
        from app.routes.avaliacaoRoutes import avaliacoes_bp
        from app.routes.sugestaoRoutes import sugestoes_bp
        
        app.register_blueprint(avaliacoes_bp, url_prefix='/api')
        app.register_blueprint(sugestoes_bp, url_prefix='/api')
        
        print("http://localhost:8084/")
        print("Avaliações: http://localhost:8084/api/avaliacoes")
        print("Sugestões: http://localhost:8084/api/sugestoes")
        
    except Exception as e:
        print(f"Erro ao registrar rotas: {e}")
    
    return app