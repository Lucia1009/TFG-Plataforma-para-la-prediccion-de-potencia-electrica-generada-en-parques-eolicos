from models.modelo import Modelo
from sklearn.model_selection import train_test_split
import pandas as pd
import numpy as np
import tensorflow as tf
import ydf




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
        self.train = None
        self.test = None

    
    def train_test_estratificado(self):
        datos_train_rf = []
        datos_test_rf = []
        for i in range(self.df.shape[0]):

            if i%4==0:
                datos_test_rf.append(self.df.iloc[i])
            else:
                datos_train_rf.append(self.df.iloc[i])

        print(len(datos_train_rf), len(datos_test_rf))
        print(self.df.shape[0]/len(datos_test_rf))
        print(self.df.shape[0]/4)

        print(datos_train_rf[0], "\n", datos_train_rf[1])

        self.train = pd.DataFrame(datos_train_rf)
        self.test = pd.DataFrame(datos_test_rf)

    def train_test_aleatorio(self):
        self.train, self.test = train_test_split(self.df, test_size=0.2)

    def train_model(self):
        for param in self.__dict__:
            print(f"{param}: {self.__dict__[param]}", flush=True)


        learner = ydf.RandomForestLearner(label=self.target, task = ydf.Task.REGRESSION,
                                            num_trees=self.numTrees, 
                                            max_depth=self.maxDepth,
                                            split_axis=self.split_axis,
                                            categorical_algorithm=self.categorical_algorithm,
                                            missing_value_policy=self.missing_value_policy,
                                            sparse_oblique_normalization=self.sparse_oblique_normalization,
                                            compute_oob_variable_importances=self.compute_oob_variable_importances,
                                            winner_take_all=self.winner_take_all)
        
        self.model=learner.train(self.train, verbose=True)
        print("RF entrenada con éxito", flush=True)

    def evaluate(self):
        self.evaluacion=self.model.evaluate(self.test)

    def predict(self):
        return self.model.predict(self.test)
