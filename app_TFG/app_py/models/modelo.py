from archivos.upload import data
import tensorflow as tf
from archivos.upload import data

class Modelo:
    def __init__(self):
        self.data = data
        self.model = None
        self.estratificado = False
        self.evaluacion = None
        self.tr_size = 0.8
        self.test_size = 0.2
        

    def set_data(self, data):
        self.data = data

    def filtrar(self):
        df= self.data.get_data()
        df=df[(df[self.data.get_time()] % 600==540)]
        self.data.set_data(df)

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

    def train_model(self):
        """
        Método genérico: cada subclase DEBE sobrescribirlo.
        """
        raise NotImplementedError("train() debe implementarse en la subclase")
    
    def evaluate(self):
        """
        Método genérico: cada subclase DEBE sobrescribirlo.
        """
        raise NotImplementedError("train() debe implementarse en la subclase")

    
    def getEvaluacion(self):
        return self.evaluacion
    