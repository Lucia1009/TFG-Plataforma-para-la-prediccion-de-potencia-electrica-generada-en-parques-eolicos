# TFG-Plataforma para la predicción de potencia eléctrica generada en parques eólicos usando técnicas de machine learning
Repositorio que refleja el desarrollo de mi proyecto de fin de grado

# Modelos
Carpeta que contiene todos los archivos relacionados con la creación de los modelos y el estudio de los datos.

- [Analisis_viento_potencia.ipynb](https://github.com/Lucia1009/TFG-Red-neuronal-turbinas-eolicas/blob/main/Analisis_viento_potencia.ipynb "Analisis_viento_potencia.ipynb"): notebook con representaciones gráficas de los datos para una mayor comprensión de los mismos. Contiene gráficas de
	- Potencia del parque frente al viento
	- Potencia de cada turbina frente al viento
	- Ángulo de pitch de cada turbina frente al viento

- [prediccion_de_potencias_con_regresión.ipynb](https://github.com/Lucia1009/TFG-Red-neuronal-turbinas-eolicas/blob/main/prediccion_de_potencias_con_regresi%C3%B3n.ipynb "prediccion_de_potencias_con_regresión.ipynb"): notebook con 2 modelos de regresión para predecir los datos de la potencia general del parque. Los modelos de regresión están hechos con tensorflow y son una regresión polinomial, un random forest y un random forest con la librería ydf. 

- [pruebas_hiperparámetros.ipynb](https://github.com/Lucia1009/TFG-Red-neuronal-turbinas-eolicas/blob/main/pruebas_hiperpar%C3%A1metros.ipynb "pruebas_hiperparámetros.ipynb"): notebook en el que se prueban los distintos parámetros de los modelos generales para elegir los más adecuados.

- [prediccion_poencias_individuales_con_regresión.ipynb](https://github.com/Lucia1009/TFG-Red-neuronal-turbinas-eolicas/blob/main/predicci%C3%B3n_potencias_individuales_con_regresi%C3%B3n.ipynb "prediccion_poencias_individuales_con_regresión.ipynb"): notebook con los dos modelos de regresión adaptados para predecir las potencias individuales de cada turbina en base a sus velocidades. Predicen todas las potencias individuales a la vez y tienen como datos de entrada todas las velociades individuales y el seno y coseno del ángulo del viento.

- [comparacion_potencia_individual_y_general.ipynb](https://github.com/Lucia1009/TFG-Red-neuronal-turbinas-eolicas/blob/main/comparacion_potencia_individual_y_general.ipynb "comparacion_potencia_individual_y_general"): notebook que contiene los modelos de regresión lineal y random forest tanto para la predicción de potencia de todo el parque, como para las potencias individuales. Crea una gráfica para cada modelo que representa los datos reales y los predichos por cada versión del modelo (de las potencias individuales hace la suma). También calcula el r2_score, el error cuadrático medio *(mse)* y el error absoluto medio *(mae)* de ambos modelos y los compara.

- [Efecto_sombra_vs_yaw_ref.ipynb](https://github.com/Lucia1009/TFG-Red-neuronal-turbinas-eolicas/blob/desarrollo/Efecto_sombra_vs_yaw_ref.ipynb "Efecto_sombra_vs_yaw_ref.ipynb"): notebook que contiene varias gráficas dinámicas que permiten comparar la potencia obtenida cuando las turbinas están perfectamente orientadas hacia el viento (yaw_ref=wd) y cuando están ligeramente desorientadas (yaw_ref=wd-10). También compara y representa la diferencia de potencia entre los distintos ángulos de viento y entre los datos orientados y desorientados.

- [Documento_potencia_sin_saltos.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/Documento_potencia_sin_saltos.ipynb "Documento_potencia_sin_saltos.ipynb"): notebook que contiene un código que permite guardar en un único csv los datos de potencias de varios documentos de datos que mantienen el mismo desalineamiento durante todo el registro. Obtiene las potencias (WF_Power) para velocidades de viento (ws) de 5, 10, 15 y 20 m/s  para los 360 grados de orientación (wd) y las guarda en un fichero. Finalmente representa en una gráfica polar y una plana el resultado para poder ver el efecto sombra de forma aislada.
