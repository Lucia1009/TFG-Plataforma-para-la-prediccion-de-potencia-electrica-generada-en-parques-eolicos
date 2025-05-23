# models/recibir_model.py
import traceback
from flask import Blueprint, jsonify, request
from archivos.upload import data
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
    params_modelo = request.get_json(force=True)
    model_type = params_modelo.get('modelType')

    # 1) instancia la clase correcta
    mod = MODEL_FACTORY.get(model_type)
    if mod is None:
        return jsonify({'error': f'ModelType desconocido: {model_type}'}), 400

    modelo = mod()

    # 2) obtiene el DataFrame subido y las columnas especiales (target, wd, time)
    modelo.set_data(data)

    # 3) asigna SOLO los atributos que existan
    for key, val in params_modelo.items():
        if hasattr(modelo, key):
            if key == 'optimizer':
                modelo.set_optimizer(val)
            else:
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
        tb = traceback.format_exc()
        # loggea tb si quieres: app.logger.error(tb)
        return jsonify({
            'error':   str(e),
            'traceback': tb
        }), 500

    return jsonify({'mensaje': f'Modelo {model_type} entrenado correctamente', 'evaluacion': eval}), 200
