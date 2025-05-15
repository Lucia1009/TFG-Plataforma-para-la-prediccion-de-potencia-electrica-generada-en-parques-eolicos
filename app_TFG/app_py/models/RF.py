from models.modelo import Modelo


class RF(Modelo):
    def __init__(self):
        super().__init__()
        # valores por defecto
        self.numTrees = 100
        self.maxDepth = 10
        self.split_axis = 'SPARSE_OBLIQUE'
        self.categorical_algorithm = 'RANDOM'
        self.missing_value_policy = 'RANDOM_LOCAL_IMPUTATION'
        self.sparse_oblique_normalization = 'SPARSE_OBLIQUE'
        self.compute_oob_variable_importances = True
        self.winner_take_all = False
        

    def train(self):
        for param in self.__dict__:
            print(f"{param}: {self.__dict__[param]}", flush=True)
        
        print("RF entrenada con éxito", flush=True)
