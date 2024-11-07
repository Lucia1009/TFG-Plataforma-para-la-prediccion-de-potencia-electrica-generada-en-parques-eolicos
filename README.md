# TFG-Red-neuronal-turbinas-eolicas
Repositorio que refleja el desarrollo de mi proyecto de fin de grado

# Archivos

- [Analisis_viento_potencia.ipynb](https://github.com/Lucia1009/TFG-Red-neuronal-turbinas-eolicas/blob/main/Analisis_viento_potencia.ipynb "Analisis_viento_potencia.ipynb"): notebook con representaciones gráficas de los datos para una mayor comprensión de los mismos. Contiene gráficas de
	- Potencia del parque frente al viento
	- Potencia de cada turbina frente al viento
	- Ángulo de pitch de cada turbina frente al viento

- [prediccion_de_potencias_con_regresión.ipynb](https://github.com/Lucia1009/TFG-Red-neuronal-turbinas-eolicas/blob/main/prediccion_de_potencias_con_regresi%C3%B3n.ipynb "prediccion_de_potencias_con_regresión.ipynb"): notebook con 2 modelos de regresión para predecir los datos de la potencia general del parque. Los modelos de regresión están hechos con tensorflow y son una regresión polinomial, un random forest y un random forest con la librería ydf. 

- [pruebas_hiperparámetros.ipynb](https://github.com/Lucia1009/TFG-Red-neuronal-turbinas-eolicas/blob/main/pruebas_hiperpar%C3%A1metros.ipynb "pruebas_hiperparámetros.ipynb"): notebook en el que se prueban los distintos parámetros de los modelos generales para elegir los más adecuados.

- [prediccion_poencias_individuales_con_regresión.ipynb](https://github.com/Lucia1009/TFG-Red-neuronal-turbinas-eolicas/blob/main/predicci%C3%B3n_potencias_individuales_con_regresi%C3%B3n.ipynb "prediccion_poencias_individuales_con_regresión.ipynb"): notebook con los dos modelos de regresión adaptados para predecir las potencias individuales de cada turbina en base a sus velocidades. Predicen todas las potencias individuales a la vez y tienen como datos de entrada todas las velociades individuales y el seno y coseno del ángulo del viento.

- [comparacion_potencia_individual_y_general.ipynb](https://github.com/Lucia1009/TFG-Red-neuronal-turbinas-eolicas/blob/main/comparacion_potencia_individual_y_general.ipynb "comparacion_potencia_individual_y_general"): notebook que contiene los modelos de regresión lineal y random forest tanto para la predicción de potencia de todo el parque, como para las potencias individuales. Crea una gráfica para cada modelo que representa los datos reales y los predichos por cada versión del modelo (de las potencias individuales hace la suma). También calcula el r2_score, el error cuadrático medio *(mse)* y el error absoluto medio *(mae)* de ambos modelos y los compara.
