# main.py
from flask import Flask

app = Flask(__name__)

# Importa el blueprint y regístralo
from archivos.upload import upload_bp
app.register_blueprint(upload_bp)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
