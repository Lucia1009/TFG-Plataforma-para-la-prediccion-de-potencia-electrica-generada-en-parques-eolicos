from models.modelo import Modelo
# aquí podrías importar Keras, etc.

class ST(Modelo):
    def __init__(self):
        super().__init__()
        self.datosPasados = 1
        self.unidades_tiempo_pasadas = "minutos"
        self.datosFuturos = 1
        self.unidades_tiempo_futuras = "minutos"
        self.epochs = 10
        self.batchSize = 32
        self.optimizer = "adam"
        self.loss = "mean_squared_error"
        self.metrics = []
        self.layers = []
        

    def train(self):
        for param in self.__dict__:
            print(f"{param}: {self.__dict__[param]}", flush=True)
        # lógica de series temporales
        print("ST entrenada con éxito", flush=True)
