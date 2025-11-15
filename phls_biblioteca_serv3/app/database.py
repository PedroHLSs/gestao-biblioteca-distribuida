import os
from flask_pymongo import PyMongo

mongo = PyMongo()

def init_app(app):
    app.config.setdefault("MONGO_URI", os.getenv("MONGO_URI", "mongodb://localhost:27017/biblioteca"))
    mongo.init_app(app)