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

from models.ned_neuronal import RedNeuronal


class PR(RedNeuronal):
    def __init__(self):
        super().__init__()
        self.degree = 15

    def transform(self, Xtrain, Xtest):
            transformador = Pipeline([('scaler', StandardScaler()),  # normaliza los datos
                    ('poly', PolynomialFeatures(degree=self.degree))]) ################ grado


            self.X_train  = transformador.fit_transform(Xtrain)
            self.X_test = transformador.transform(Xtest)

    def train_test_aleatorio(self):
        df=self.data.get_data()
        datos_x = df.drop(self.data.get_target(), axis=1)
        datos_y = df[self.data.get_target()]

        Xtrain, Xtest, self.y_train, self.y_test = train_test_split(datos_x, datos_y, test_size=0.2 )
        self.transform(Xtrain, Xtest)

    def train_test_estratificado(self):
        datos_train = []
        datos_test = []
        df=self.data.get_data()
        for i in range(df.shape[0]):

            if i%4==0:
                datos_test.append(df.iloc[i])
            else:
                datos_train.append(df.iloc[i])

        datos_train = pd.DataFrame(datos_train)
        datos_test = pd.DataFrame(datos_test)

        Xtrain = datos_train.drop(self.data.get_target(), axis=1)
        self.y_train = datos_train[self.data.get_target()]

        Xtest = datos_test.drop(self.data.get_target(), axis=1)
        self.y_test = datos_test[self.data.get_target()]

        self.transform(Xtrain, Xtest)


    def build_model(self):
        for param in self.__dict__:
            print(f"{param}: {self.__dict__[param]}", flush=True)

        layers_list = [keras.layers.Input(shape=(self.X_train.shape[1],))]
        for capa in self.layers:
            if capa["name"] == 'Dense':
                layers_list.append(layers.Dense(units=capa["units"], activation=capa["activation"]))
            elif capa["name"] == 'Dropout':
                layers_list.append(layers.Dropout(rate=capa["rate"]))
        layers_list.append(layers.Dense(1))

        self.model = keras.Sequential(layers_list)

        print("PR", flush=True)

    def predict(self):
        return self.model.predict(self.X_test)
    
            
       
