import pandas as pd
from flask import Blueprint, request, jsonify

upload_bp = Blueprint('upload', __name__)
get_columns_bp = Blueprint('get_columns', __name__)

# almacenamos aquí el DataFrame subido
df = None

@upload_bp.route('/upload_f', methods=['POST'])
def process_file():
    if 'file' not in request.files:
        return jsonify({'error': 'No se ha enviado ningún fichero'}), 400

    f = request.files['file']
    global df
    try:
        df = pd.read_csv(f)
        print("DataFrame subido:\n", df.head(), flush=True)
    except Exception as e:
        return jsonify({'error': str(e)}), 500

    return jsonify({'mensaje': 'El archivo ha sido leído correctamente'}), 200

@get_columns_bp.route('/get_columns', methods=['GET'])
def get_columns():
    global df
    if df is None:
        return jsonify({'error': 'No se ha subido ningún fichero', 'columns': []}), 400

    cols = list(df.columns)
    print("Columnas disponibles:", cols, flush=True)
    return jsonify({'mensaje': 'Columnas recogidas', 'columns': cols}), 200
