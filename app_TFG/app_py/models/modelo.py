class Modelo:
    def __init__(self):
        self.df = None
        self.target = ''
        # opcional: un atributo común para almacenar el modelo entrenado
        self.model = None

    def setTargetAttr(self, target):
        self.target = target

    def setDfAttr(self, df):
        self.df = df

    def train(self):
        """
        Método genérico: cada subclase DEBE sobrescribirlo.
        """
        raise NotImplementedError("train() debe implementarse en la subclase")

