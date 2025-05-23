from models.modelo import Modelo
import pandas as pd
import numpy as np

import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, LSTM, Dropout
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics import mean_squared_error

from models.ned_neuronal import RedNeuronal


class ST(RedNeuronal):
    def __init__(self):
        super().__init__()
        self.datosPasados = 1
        self.unidades_tiempo_pasadas = "minutos"
        self.datosFuturos = 1
        self.unidades_tiempo_futuras = "minutos"
        self.scaler_x = None
        self.scaler_y = None
      

    def train_test_aleatorio(self):
        self.train_test_estratificado() 

    def train_test_estratificado(self):
           
        N = self.df.shape[0]
        Ntrain = int(self.tr_size*N)  # Número de datos de entrenamiento
        Nval = int(self.vl_size*N)    # Número de datos de validación

        datos_array = self.df.values

        # Realizar partición
        train = datos_array[0:Ntrain]
        val = datos_array[Ntrain:Ntrain+Nval]
        test = datos_array[Ntrain+Nval:]

        # Crear dataset para cada conjunto de datos pasados
        Xtrain, ytrain = self.crear_dataset_supervisado(train)
        Xval, yval = self.crear_dataset_supervisado(val)
        Xtest, ytest = self.crear_dataset_supervisado(test)

        # Escalar los datos
        self.escalar_dataset(Xtrain, ytrain, Xval, yval, Xtest, ytest)

    def crear_dataset_supervisado(self, array):

        # Inicialización
        X, Y = [], []    # Listados que contendrán los datos de entrada y salida del modelo
        shape = array.shape
        if len(shape)==1: # Si tenemos sólo una serie (univariado)
            fils, cols = array.shape[0], 1
            array = array.reshape(fils,cols)
        else: # Multivariado
            fils, cols = array.shape

        # Generar los arreglos
        for i in range(fils-self.datosPasados-self.datosFuturos):
            X.append(array[i:i+self.datosPasados,0:cols])
            Y.append(array[i+self.datosPasados:i+self.datosPasados+self.datosFuturos,-1].reshape(self.datosFuturos,1))

        # Convertir listas a arreglos de NumPy
        X = np.array(X)
        Y = np.array(Y)

        return X, Y


    def escalar_dataset(self, Xtrain, ytrain, Xval, yval, Xtest, ytest):
        NFEATS = self.X_train.shape[2]

        # Create a scaler for input features
        self.scaler_x = MinMaxScaler(feature_range=(-1, 1))

        # Scale the input features
        self.X_train = self.scaler_x.fit_transform(Xtrain.reshape(-1, NFEATS)).reshape(Xtrain.shape)
        self.X_val = self.scaler_x.transform(Xval.reshape(-1, NFEATS)).reshape(Xval.shape)
        self.X_test = self.scaler_x.transform(Xtest.reshape(-1, NFEATS)).reshape(Xtest.shape)

        # Create a scaler for output (target) variable
        self.scaler_y = MinMaxScaler(feature_range=(-1, 1))

        # Scale the output variable
        self.y_train = self.scaler_y.fit_transform(ytrain.reshape(-1, 1)).reshape(ytrain.shape)
        self.y_val = self.scaler_y.transform(yval.reshape(-1, 1)).reshape(yval.shape)
        self.y_test = self.scaler_y.transform(ytest.reshape(-1, 1)).reshape(ytest.shape)
   

    def build_model(self):
        for param in self.__dict__:
            print(f"{param}: {self.__dict__[param]}", flush=True)

        INPUT_SHAPE = (self.X_train.shape[1], self.X_train.shape[2])
        N_UNITS = self.layers[0].units

        self.model = Sequential()
        self.model.add(LSTM(N_UNITS, input_shape=INPUT_SHAPE))
        for capa in self.layers:
            if capa.type == 'LSTM':
                self.model.add(LSTM(capa.units, return_sequences=True))
            elif capa.type == 'Dense':
                self.model.add(Dense(capa.units, activation=capa.activation))
            # elif capa.type == 'Dropout':
            #     modelo.add(Dropout(capa.rate))
        self.model.add(Dense(self.datosFuturos, activation='linear'))
        
        print("ST", flush=True)



    def desescalar(self):
        """
        Desescala los datos de salida
        """
        # Inverse transform the relevant features using scaler_x
        x_descaled = self.scaler_x.inverse_transform(self.x_scaled.reshape(-1, self.X_train.shape[2])).reshape(self.X_test.shape)

        # Inverse transform y_scaled using scaler_y
        y_descaled = self.scaler_y.inverse_transform(self.y_scaled.reshape(-1, 1)).flatten()

        return x_descaled, y_descaled

    def predict(self):

        # Get scaled predictions
        y_pred_s = self.model.predict(self.X_test, verbose=0)

        # Descale predictions and targets
        _, y_pred = self.desescalar(self.X_test, y_pred_s)

        return y_pred

