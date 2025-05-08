# archivos/upload.py
from flask import Blueprint, request, jsonify
import pandas as pd

# Define un blueprint llamado 'upload'
upload_bp = Blueprint('upload', __name__)

@upload_bp.route('/upload_f', methods=['POST'])
def process_file():
    print("Entrando en process_file", flush=True)
    if 'file' not in request.files:
        return jsonify({'error': 'No se ha enviado ningún fichero'}), 400

    f = request.files['file']
    try:
        df = pd.read_csv(f)
        print(df.head(), flush=True)
    except Exception as e:
        return jsonify({'error': str(e)}), 500

    return jsonify({'mensaje': 'El archivo ha sido leido correctamente'}), 200
