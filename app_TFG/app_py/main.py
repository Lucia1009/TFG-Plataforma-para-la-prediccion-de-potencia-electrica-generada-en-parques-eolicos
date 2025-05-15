# main.py
from flask import Flask

# import blueprints
from archivos.upload import upload_bp, get_columns_bp, df
from models.recibir_model import train_bp

app = Flask(__name__)

# registramos blueprints
app.register_blueprint(upload_bp)
app.register_blueprint(get_columns_bp)
app.register_blueprint(train_bp)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
