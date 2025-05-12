# archivos/upload.py
from flask import Blueprint, request, jsonify
import pandas as pd

# Move the class definition above its usage
class DatosParamsModelo:

    def __init__(self):
        self.df=None
        self.target = ''

    def setTargetAttr(self, target):
        self.target = target
    def setDfAttr(self, df):
        self.df = df

    def __repr__(self):
        return f"DatosParamsModelo(target={self.target}, df={self.df.head()})"

#######################################################################################

datos_params_modelo = DatosParamsModelo()


# Define un blueprint llamado 'upload'
upload_bp = Blueprint('upload', __name__)
set_target_bp=Blueprint('target', __name__)
@upload_bp.route('/upload_f', methods=['POST'])
def process_file():
    print("Entrando en process_file", flush=True)
    if 'file' not in request.files:
        return jsonify({'error': 'No se ha enviado ningún fichero', 'columns': ['']}), 400

    f = request.files['file']
    try:
        df = pd.read_csv(f)
        datos_params_modelo.setDfAttr(df)
        print(df.head(), flush=True)
    except Exception as e:
        return jsonify({'error': str(e), 'columns':['']}), 500

    return jsonify({'mensaje': 'El archivo ha sido leido correctamente', 'columns': list(df.columns)}), 200



@set_target_bp.route('/set_target', methods=['POST'])
def setTarget():
    target = request.form.get('target')
    datos_params_modelo.setTargetAttr(target)
    print("El target es", target , flush=True)
    return jsonify({'mensaje': 'El target ha sido recogido correctamente'}), 200

