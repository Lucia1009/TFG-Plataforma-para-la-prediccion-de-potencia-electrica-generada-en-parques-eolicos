import os
import pandas as pd
import tensorflow as tf
from models.modelo import Modelo



class RedNeuronal(Modelo):
    def __init__(self):
        super().__init__()
        self.optimizer = tf.keras.optimizers.Adam(learning_rate=1e-3)
        self.loss = 'mean_squared_error'
        self.epochs = 10
        self.batchSize = 32
        self.metrics = []
        self.layers = []
        self.X_train = None
        self.y_train = None
        self.X_test = None
        self.y_test = None
        self.X_val = None
        self.y_val = None
        self.vl_size = 0.1
        

    def set_optimizer(self, optimizer):
        if optimizer == 'ADAM':
            self.optimizer = tf.keras.optimizers.Adam(learning_rate=1e-3)
        elif optimizer == 'SGD':
            self.optimizer = tf.keras.optimizers.SGD(learning_rate=1e-3)
        elif optimizer == 'RMSprop':
            self.optimizer = tf.keras.optimizers.RMSprop(learning_rate=1e-3)

    def train_test_aleatorio(self):
        """
        Método genérico: cada subclase DEBE sobrescribirlo.
        """
        raise NotImplementedError("train() debe implementarse en la subclase")

    def train_test_estratificado(self):
        """
        Método genérico: cada subclase DEBE sobrescribirlo.
        """
        raise NotImplementedError("train() debe implementarse en la subclase")

    def build_model(self):
        """
        Método genérico: cada subclase DEBE sobrescribirlo.
        """
        raise NotImplementedError("train() debe implementarse en la subclase")

    def train_model(self):
        self.build_model()
        self.model.compile(optimizer=self.optimizer, loss=self.loss, metrics=self.metrics)
        if self.X_val is None:
            self.model.fit(self.X_train, self.y_train, epochs=self.epochs, batch_size=self.batchSize)
        else:
            self.model.fit(self.X_train, self.y_train, epochs=self.epochs, batch_size=self.batchSize, validation_data=(self.X_val, self.y_val))


        print(self.model.summary(), flush=True)
        print("Modelo entrenado con éxito", flush=True)

    def predict(self):
        """
        Método genérico: cada subclase DEBE sobrescribirlo.
        """
        raise NotImplementedError("train() debe implementarse en la subclase")
        
                
    def evaluate(self):
        """
        Obtiene la evaluación del modelo.
        """

        eval = self.model.evaluate(self.X_test, self.y_test)
        self.evaluacion = {metric: value for metric, value in zip(self.metrics, eval)}

        return self.evaluacion
    
    def guardar(self, name):
        """
        Guarda el modelo con el nombre indicado.
        """

        carpeta = os.path.normpath(self.path)
    
        try:
            os.makedirs(carpeta, exist_ok=True)
        except Exception as e:
            return f"No se pudo crear la carpeta de modelos: {e}", 500

        # Si name no vino (None o cadena vacía), generamos un nombre basado en timestamp
        if not name or not isinstance(name, str) or name.strip() == "":
            timestamp = pd.Timestamp.now().strftime("%Y%m%d_%H%M%S")
            archivo = f"rn_model_{timestamp}.keras"
        else:
            archivo = f"{name.strip()}.keras"

        ruta_completa = os.path.join(carpeta, archivo)

        try:
            self.model.save(ruta_completa)
        except Exception as e:
            return f"Error al guardar el modelo: {e}", 500

        return f"Modelo guardado en {ruta_completa}", 200