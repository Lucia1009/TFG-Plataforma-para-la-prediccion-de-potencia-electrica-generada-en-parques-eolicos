# models/recibir_model.py
from flask import Blueprint, jsonify, request
import archivos.upload as upload
from models.RF import RF
from models.PR import PR
from models.ST import ST

train_bp = Blueprint('train', __name__)

# fábrica de clases
MODEL_FACTORY = {
    'rf': RF,
    'pr': PR,
    'st': ST
}

@train_bp.route('/train_model', methods=['POST'])
def train_model():
    data       = request.get_json(force=True)
    model_type = data.get('modelType')
    target     = data.get('target')

    # 1) obtiene el DataFrame subido
    df = upload.df
    if df is None:
        return jsonify({'error': 'Primero sube un CSV'}), 400

    # 2) instancia la clase correcta
    mod = MODEL_FACTORY.get(model_type)
    if mod is None:
        return jsonify({'error': f'ModelType desconocido: {model_type}'}), 400

    modelo = mod()
    modelo.setDf(df)
    modelo.setTarget(target)

    # 3) asigna SOLO los atributos que existan
    for key, val in data.items():
        if hasattr(modelo, key):
            setattr(modelo, key, val)

    # 4) entrena
    try:
        if modelo.estratificado:
            modelo.train_test_estratificado()
        else:
            modelo.train_test_aleatorio()
        modelo.train_model()
        eval=modelo.evaluate()
        
    except Exception as e:
        return jsonify({'error': str(e), '\ntraza del error': e.__traceback__}), 500

    return jsonify({'mensaje': f'Modelo {model_type} entrenado correctamente', 'evaluacion': eval}), 200
