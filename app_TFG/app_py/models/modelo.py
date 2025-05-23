class Modelo:
    def __init__(self):
        self.df = None
        self.target = ''
        self.model = None
        self.estratificado = False
        self.X_train = None
        self.y_train = None
        self.X_test = None
        self.y_test = None
        self.evaluacion = None
        self.tr_size = 0.8
        self.test_size = 0.2

    def setTarget(self, target):
        self.target = target

    def setDf(self, df):
        self.df = df

    def filtrar(self):
        self.df=self.df[(self.df['TIME'] % 600==540)]
        return self.df
    
    def train_test_estratificado(self):
        """
        Método genérico: cada subclase DEBE sobrescribirlo.
        """
        raise NotImplementedError("train() debe implementarse en la subclase")

    def train_test_aleatorio(self):
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
        
        self.evaluacion = self.model.evaluate(self.X_test, self.y_test)
        return self.evaluacion


    def predict(self):
        return self.model.predict(self.X_test)
    
    def getEvaluacion(self):
        return self.evaluacion