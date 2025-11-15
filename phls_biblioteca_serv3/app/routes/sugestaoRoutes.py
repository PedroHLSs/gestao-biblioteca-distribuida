from flask import Blueprint, request, jsonify
from bson.errors import InvalidId
from bson.objectid import ObjectId
from app.database import mongo
from app.models.sugestaoModel import SugestaoLivroModel

sugestoes_bp = Blueprint("sugestoes", __name__)

@sugestoes_bp.route("/sugestoes", methods=["POST"])
def create_sugestao():
    data = request.json
    sugestao = SugestaoLivroModel.from_dict(data)
    result = mongo.db.sugestoes_livros.insert_one(sugestao)
    return jsonify({"id": str(result.inserted_id)}), 201

@sugestoes_bp.route("/sugestoes", methods=["GET"])
def get_sugestoes():
    sugestoes = mongo.db.sugestoes_livros.find()
    return jsonify([SugestaoLivroModel.to_dict(s) for s in sugestoes]), 200

@sugestoes_bp.route("/sugestoes/<string:sugestao_id>", methods=["GET"])
def get_sugestao(sugestao_id):
    sugestao = mongo.db.sugestoes_livros.find_one({"_id": ObjectId(sugestao_id)})
    if sugestao:
        return jsonify(SugestaoLivroModel.to_dict(sugestao)), 200
    return jsonify({"error": "sugestão não encontrada"}), 404

@sugestoes_bp.route("/sugestoes/<string:sugestao_id>", methods=["PUT"])
def update_sugestao(sugestao_id):
    data = request.json

    try:
        object_id = ObjectId(sugestao_id)
    except InvalidId:
        return jsonify({"error": "ID inválido"}), 400

    update_data = {"$set": SugestaoLivroModel.from_dict(data)}
    result = mongo.db.sugestoes_livros.update_one({"_id": object_id}, update_data)

    if result.matched_count:
        updated = mongo.db.sugestoes_livros.find_one({"_id": object_id})
        return jsonify(SugestaoLivroModel.to_dict(updated)), 200
    return jsonify({"error": "Sugestão não encontrada"}), 404


@sugestoes_bp.route("/sugestoes/<string:sugestao_id>", methods=["DELETE"])
def delete_sugestao(sugestao_id):
    try:
        object_id = ObjectId(sugestao_id)
    except InvalidId:
        return jsonify({"error": "ID inválido"}), 400

    result = mongo.db.sugestoes_livros.delete_one({"_id": object_id})
    if result.deleted_count:
        return jsonify({"message": "Sugestão removida com sucesso"}), 200
    return jsonify({"error": "Sugestão não encontrada"}), 404
