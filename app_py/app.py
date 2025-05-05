from flask import Flask, request, jsonify
import pandas as pd

app = Flask(__name__)

@app.route('/upload', methods=['POST'])
def process_file():
    # 1. Comprobar que llega un fichero
    if 'file' not in request.files:
        return jsonify({'error': 'No se ha enviado ningún fichero'}), 400

    f = request.files['file']
    try:
        # 2. Leer CSV directamente desde el stream
        df = pd.read_csv(f)
        print(df.head())  # Imprimir las primeras filas del DataFrame para depuración
    except Exception as e:
        return jsonify({'error': str(e)}), 500

    # 3. Construir respuesta JSON
    return jsonify({
        'mensaje': 'El archivo ha sido leido correctamente',
    })

if __name__ == '__main__':
    # Arranca en el puerto 5000
    app.run()
