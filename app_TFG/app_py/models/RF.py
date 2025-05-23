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
        self.df_train = None
        self.df_test = None
       

    def train_test_aleatorio(self):
        df=self.data.get_data()
        self.df_train, self.df_test = train_test_split(df, test_size=self.test_size)
    
    def train_test_estratificado(self):
        datos_train_rf = []
        datos_test_rf = []
        df=self.data.get_data()
        for i in range(df.shape[0]):

            if i%4==0:
                datos_test_rf.append(df.iloc[i])
            else:
                datos_train_rf.append(df.iloc[i])

        self.df_train = pd.DataFrame(datos_train_rf)
        self.df_test = pd.DataFrame(datos_test_rf)
       

    def train_model(self):
        for param in self.__dict__:
            print(f"{param}: {self.__dict__[param]}", flush=True)


        learner = ydf.RandomForestLearner(label=self.data.get_target(), task = ydf.Task.REGRESSION,
                                            num_trees=self.numTrees, 
                                            max_depth=self.maxDepth,
                                            split_axis=self.split_axis,
                                            categorical_algorithm=self.categorical_algorithm,
                                            missing_value_policy=self.missing_value_policy,
                                            sparse_oblique_normalization=self.sparse_oblique_normalization,
                                            compute_oob_variable_importances=self.compute_oob_variable_importances,
                                            winner_take_all=self.winner_take_all)

        self.model=learner.train(self.df_train, verbose=True)
        print("RF entrenada con éxito", flush=True)

    def evaluate(self):
        self.evaluacion=self.model.evaluate(self.df_test)

    def predict(self):
        return self.model.predict(self.df_test)
