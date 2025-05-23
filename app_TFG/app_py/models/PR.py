import tensorflow as tf
from tensorflow import keras
from keras import layers
from models.modelo import Modelo
from sklearn.model_selection import train_test_split
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler
from sklearn.preprocessing import PolynomialFeatures
import pandas as pd
import numpy as np


class PR(Modelo):
    def __init__(self):
        super().__init__()
        self.degree = 15
        self.optimizer = 'adam'
        self.loss = 'mean_squared_error'
        self.epochs = 10
        self.metrics = []
        self.layers = []

    def train_test_estratificado(self):
        datos_train = []
        datos_test = []
        for i in range(self.df.shape[0]):

            if i%4==0:
                datos_test.append(self.df.iloc[i])
            else:
                datos_train.append(self.df.iloc[i])

        datos_train = pd.DataFrame(datos_train)
        datos_test = pd.DataFrame(datos_test)

        Xtrain = datos_train.drop('WF_Power', axis=1)
        self.y_train = datos_train['WF_Power']

        Xtest = datos_test.drop('WF_Power', axis=1)
        self.y_test = datos_test['WF_Power']

        self.transform(Xtrain, Xtest)
    
    def train_test_aleatorio(self):

        datos_x = self.df.drop('WF_Power', axis=1)
        datos_y = self.df['WF_Power']

        Xtrain, Xtest, self.y_train, self.y_test = train_test_split(datos_x, datos_y, test_size=0.2 )
        self.transform(Xtrain, Xtest)

    def transform(self, Xtrain, Xtest):
        transformador = Pipeline([('scaler', StandardScaler()),  # normaliza los datos
                  ('poly', PolynomialFeatures(degree=15))]) ################ grado


        self.X_train  = transformador.fit_transform(Xtrain)
        self.X_test = transformador.transform(Xtest)


    def train_model(self):
        for param in self.__dict__:
            print(f"{param}: {self.__dict__[param]}", flush=True)

        layers_list = [keras.layers.Input(shape=(self.X_train.shape[1],))]
        for capa in self.layers:
            if capa.type == 'Dense':
                layers_list.append(layers.Dense(capa.units, activation=capa.activation))
            # elif capa.type == 'Dropout':
            #     layers_list.append(layers.Dropout(capa.rate))
        layers_list.append(layers.Dense(1))

        self.model = keras.Sequential(layers_list)

        optimizer = tf.keras.optimizers.Adam(learning_rate=1e-3)

        self.model.compile(loss='mse',
                        optimizer=optimizer,
                    metrics=['mae', 'mse', 'R2Score'])

        self.model.fit(self.X_train, self.y_train, epochs=self.epochs, batch_size=self.batch_size, verbose=1)

        print("PR entrenada con éxito", flush=True)
