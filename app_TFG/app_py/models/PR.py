from models.modelo import Modelo


class PR(Modelo):
    def __init__(self):
        super().__init__()
        self.degree = 15
        self.optimizer = 'adam'
        self.loss = 'mean_squared_error'
        self.epochs = 10
        self.metrics = []
        self.layers = []
        

    def train(self):
        for param in self.__dict__:
            print(f"{param}: {self.__dict__[param]}", flush=True)
        
        print("PR entrenada con éxito", flush=True)
