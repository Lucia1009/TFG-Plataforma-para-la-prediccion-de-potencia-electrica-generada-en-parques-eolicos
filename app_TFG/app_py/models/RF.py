import os
import traceback
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
        eval=self.model.evaluate(self.df_test).to_dict()
        self.evaluacion = {'rmse': eval['rmse']}

    def predict(self):
        return self.model.predict(self.df_test)


    # def guardar(self, name):
    #     """
    #     Guarda el modelo con el nombre indicado.
    #     """
    #     carpeta = os.path.normpath(self.path)
    #     try:
    #         os.makedirs(carpeta, exist_ok=True)
    #     except Exception as e:
    #         return f"No se pudo crear la carpeta de modelos: {e}", 500

    #     # Si name no vino (None o cadena vacía), generamos un nombre basado en timestamp
    #     if not name or not isinstance(name, str) or name.strip() == "":
    #         timestamp = pd.Timestamp.now().strftime("%Y%m%d_%H%M%S")
    #         archivo = f"rf_model_{timestamp}"
    #     else:
    #         archivo = name.strip()

    #     ruta_completa = os.path.join(carpeta, archivo)

    #     try:
    #         self.model.save(ruta_completa)
    #     except Exception as e:
    #         return f"Error al guardar el modelo: {e}", 500

    #     return f"Modelo guardado en {ruta_completa}", 200
    




    def guardar(self, name):
        """
        Guarda el modelo en disco dentro de la carpeta self.path, usando 'name' como nombre de archivo.
        Devuelve mensaje y código HTTP (200 o 400 o 500).
        """
        if self.model is None:
            msg = "No se ha entrenado ningún modelo."
            print(f"RF.guardar: {msg}", flush=True)
            return msg, 400

        # 1) Normalizar y crear carpeta si no existe
        carpeta = os.path.normpath(self.path)  # Normaliza algo como "../../modelos"
        print(f"RF.guardar: Carpeta deseada -> {carpeta}", flush=True)

        try:
            os.makedirs(carpeta, exist_ok=True)
            print(f"RF.guardar: Carpeta creada o ya existía.", flush=True)
        except Exception as e:
            msg = f"No se pudo crear la carpeta de modelos: {e}"
            print(f"RF.guardar: ERROR al crear carpeta: {traceback.format_exc()}", flush=True)
            return msg, 500

        # 2) Decidir nombre de archivo
        if not name or not isinstance(name, str) or name.strip() == "":
            timestamp = pd.Timestamp.now().strftime("%Y%m%d_%H%M%S")
            archivo = f"rf_model_{timestamp}"
        else:
            archivo = name.strip()

        print(f"RF.guardar: Nombre de archivo para el modelo -> '{archivo}'", flush=True)

        # 3) Ruta completa en la que salvar
        ruta_completa = os.path.join(carpeta, archivo)
        ruta_completa = os.path.abspath(ruta_completa)  # convertir a ruta absoluta para mayor claridad
        print(f"RF.guardar: Ruta absoluta final -> {ruta_completa}", flush=True)

        # 4) Intentar guardar el modelo
        try:
            print("RF.guardar: Llamando a self.model.save()...", flush=True)
            self.model.save(ruta_completa)
            print("RF.guardar: self.model.save() terminó sin excepción.", flush=True)
        except Exception as e:
            msg_error = f"Error al guardar el modelo en disco: {e}"
            print(f"RF.guardar: EXCEPCIÓN en save(): {traceback.format_exc()}", flush=True)
            return msg_error, 500

        # 5) Éxito
        mensaje_exito = f"Modelo guardado en {ruta_completa}"
        print(f"RF.guardar: ÉXITO -> {mensaje_exito}", flush=True)
        return mensaje_exito, 200

