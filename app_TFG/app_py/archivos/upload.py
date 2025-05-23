import pandas as pd
from flask import Blueprint, request, jsonify

from archivos.Data import Data

upload_bp = Blueprint('upload', __name__)
get_columns_bp = Blueprint('get_columns', __name__)
create_dataset_bp = Blueprint('create_dataset', __name__)

# almacenamos aquí el DataFrame subido
data=Data()

@upload_bp.route('/upload_f', methods=['POST'])
def process_file():
    if 'file' not in request.files:
        return jsonify({'error': 'No se ha enviado ningún fichero'}), 400

    f = request.files['file']
    global data
    try:
        data.set_data(pd.read_csv(f, delimiter=';', decimal=".", skiprows=[1]))
        print("DataFrame subido:\n", data.get_data().head(), flush=True)
    except Exception as e:
        return jsonify({'error': str(e)}), 500

    return jsonify({'mensaje': 'El archivo ha sido leído correctamente'}), 200

@get_columns_bp.route('/get_columns', methods=['GET'])
def get_columns():
    global data
    if data.get_data() is None:
        return jsonify({'error': 'No se ha subido ningún fichero', 'columns': []}), 400

    cols = list(data.get_data().columns)
    print("Columnas disponibles:", cols, flush=True)
    return jsonify({'mensaje': 'Columnas recogidas', 'columns': cols}), 200


@create_dataset_bp.route('/createDataset', methods=['POST'])
def createDataset():
    global data
    if data.get_data() is None:
        return jsonify({'error': 'No se pueden seleccionar las columnas antes de haber subido el dataset', 'dataset': []}), 400

    df = data.get_data()
    columns= request.json['columns']
    columns.append(request.json['wd'])
    columns.append(request.json['time'])
    columns.append(request.json['target'])

    data.set_data(df[columns])
    data.set_target(request.json['target'])
    data.set_direccion(request.json['wd'])
    data.set_time(request.json['time'])

    return jsonify({'mensaje': 'Dataset creado correctamente'}), 200
