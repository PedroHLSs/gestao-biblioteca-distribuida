from flask import Blueprint, request, jsonify
from bson.objectid import ObjectId
from app.database import mongo
from app.models.avaliacaoModel import AvaliacaoModel

avaliacoes_bp = Blueprint("avaliacoes", __name__)

@avaliacoes_bp.route("/avaliacoes", methods=["POST"])
def create_avaliacao():
    data = request.json
    avaliacao = AvaliacaoModel.from_dict(data)
    result = mongo.db.avaliacoes.insert_one(avaliacao)
    return jsonify({"id": str(result.inserted_id)}), 201

@avaliacoes_bp.route("/avaliacoes", methods=["GET"])
def get_avaliacoes():
    # Fail fast if DB is not initialized
    if getattr(mongo, 'db', None) is None:
        return jsonify({"error": "Banco de dados não inicializado. Verifique a configuração do MongoDB."}), 500
    avaliacoes = mongo.db.avaliacoes.find()
    return jsonify([AvaliacaoModel.to_dict(a) for a in avaliacoes]), 200

@avaliacoes_bp.route("/avaliacoes/<string:avaliacao_id>", methods=["GET"])
def get_avaliacao(avaliacao_id):
    avaliacao = mongo.db.avaliacoes.find_one({"_id": ObjectId(avaliacao_id)})
    if avaliacao:
        return jsonify(AvaliacaoModel.to_dict(avaliacao)), 200
    return jsonify({"error": "avaliação não encontrada"}), 404

@avaliacoes_bp.route("/avaliacoes/<string:avaliacao_id>", methods=["PUT"])
def update_avaliacao(avaliacao_id):
    data = request.json
    update_data = {"$set": AvaliacaoModel.from_dict(data)}
    result = mongo.db.avaliacoes.update_one({"_id": ObjectId(avaliacao_id)}, update_data)

    if result.matched_count:
        updated = mongo.db.avaliacoes.find_one({"_id": ObjectId(avaliacao_id)})
        return jsonify(AvaliacaoModel.to_dict(updated)), 200
    return jsonify({"error": "avaliação não encontrada"}), 404

@avaliacoes_bp.route("/avaliacoes/<string:avaliacao_id>", methods=["DELETE"])
def delete_avaliacao(avaliacao_id):
    result = mongo.db.avaliacoes.delete_one({"_id": ObjectId(avaliacao_id)})
    if result.deleted_count:
        return jsonify({"message": "avaliação removida"}), 200
    return jsonify({"error": "avaliação não encontrada"}), 404
