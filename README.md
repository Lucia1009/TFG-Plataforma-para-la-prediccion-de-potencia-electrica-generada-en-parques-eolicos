# TFG-Plataforma para la predicción de potencia eléctrica generada en parques eólicos usando técnicas de machine learning
Repositorio que refleja el desarrollo de mi proyecto de fin de grado

## Modelos
Carpeta que contiene todos los archivos relacionados con la creación de los modelos y el estudio de los datos.

- [Analisis_viento_potencia.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/Analisis_viento_potencia.ipynb "Analisis_viento_potencia.ipynb"): notebook con representaciones gráficas de los datos para una mayor comprensión de los mismos. Contiene gráficas de
	- Potencia del parque frente al viento
	- Potencia de cada turbina frente al viento
	- Ángulo de pitch de cada turbina frente al viento

- [Comparacion_modelos_series_temporales.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/Comparacion_modelos_series_temporales.ipynb "Comparacion_modelos_series_temporales.ipynb"): Notebook que toma datos reales de viento, dirección de viento y temperatura desde enero de 2023 hasta diciembre de 2024 y los compara con 10 modelos de series temporales mediante el cálculo del r2_score, mse y mae. También se hace la comparación con los errores de tomar las variables en la hora anterior.

- [Documento_potencia_sin_saltos.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/Documento_potencia_sin_saltos.ipynb "Documento_potencia_sin_saltos.ipynb"): notebook que contiene un código que permite guardar en un único csv los datos de potencias de varios documentos de datos que mantienen el mismo desalineamiento durante todo el registro. Obtiene las potencias (WF_Power) para velocidades de viento (ws) de 5, 10, 15 y 20 m/s  para los 360 grados de orientación (wd) y las guarda en un fichero. Finalmente representa en una gráfica polar y una plana el resultado para poder ver el efecto sombra de forma aislada.

- [Efecto_sombra_vs_yaw_ref.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/Efecto_sombra_vs_yaw_ref.ipynb "Efecto_sombra_vs_yaw_ref.ipynb"): notebook que contiene varias gráficas dinámicas que permiten comparar la potencia obtenida cuando las turbinas están perfectamente orientadas hacia el viento (yaw_ref=wd) y cuando están ligeramente desorientadas (yaw_ref=wd-10). También compara y representa la diferencia de potencia entre los distintos ángulos de viento y entre los datos orientados y desorientados.

- [Series_temporales.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/Series_temporales.ipynb "Series_temporales.ipynb"): notebook con un modelo LSTM para la predicción de series temporales. Utiliza la velocidad de viento, dirección de viento y setpoint para predecir la potencia del parque completo.

- [YDF_datos_variados.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/YDF_datos_variados.ipynb "YDF_datos_variados.ipynb"): Notebook con un modelo básico de random forest para comprobar si los nuevos datos generan buenos modelos. Utiliza un histograma para la detección de errores. Compara el error obtenido del modelo con el error producido asumiendo que el valor de la potencia del dato de test equivale al valor de la potencia del instante de tiempo anterior.

- [comp_datos_modelos_santiago.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/comp_datos_modelos_santiago.ipynb "comp_datos_modelos_santiago.ipynb"): Notebook que toma las predicciones obtenidas por los modelos de la [página](https://open-meteo.com/en/docs "página") y los compara con datos obtenidos de una estación meteorológica en Santiago de Compostela. Las predicciones han sido tomadas diariamente y predicen hasta 16 días.

- [comp_datos_modelos_santiago.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/comp_datos_modelos_santiago.ipynb "comp_datos_modelos_santiago.ipynb"): Notebook que toma datos de la estación meteorológica en Santiago de Compostla y compara los datos con el histórico de la página de modelos de series temporales para observar si los datos son precisos. También lo compara con las predicciones históricas de la página.

- [comparacion_potencia_individual_y_general.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/comparacion_potencia_individual_y_general.ipynb "comparacion_potencia_individual_y_general.ipynb"): notebook que contiene los modelos de regresión lineal y random forest tanto para la predicción de potencia de todo el parque, como para las potencias individuales. Crea una gráfica para cada modelo que representa los datos reales y los predichos por cada versión del modelo (de las potencias individuales hace la suma). También calcula el r2_score, el error cuadrático medio *(mse)* y el error absoluto medio *(mae)* de ambos modelos y los compara.

- [comparar_reales_pagina.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/comparar_reales_pagina.ipynb "comparar_reales_pagina.ipynb"): Notebook que compara los datos históricos obtenidos en la [página de modelos de climatología mundial](https://open-meteo.com/en/docs "página de modelos de climatología mundial") con [datos históricos reales de Berlín](https://www.dwd.de/DE/klimaumwelt/cdc/cdc_node.html "datos históricos reales de Berlín").

- [datos_reales.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/datos_reales.ipynb "datos_reales.ipynb"): notebook en desarrollo que obtiene los datasets de datos reales y los transforma y visualiza para comprobar si son útiles para crear modelos.

- [datos_variados.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/datos_variados.ipynb "datos_variados.ipynb"): notebook que filtra y representa gráficamente datos del simulador con variaciones de setpoint, direcciones, etc.

- [obtener_forecast_servidor.py](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/obtener_forecast_servidor.py "obtener_forecast_servidor.py"): Programa que se ejecuta cada día para pedir al servidor de predicciones de modelos de datos meteorológicos las predicciones climáticas para los próximos 16 días. Para ejecutarlo diariamente se ha usado el programador de tareas de windows 11.  Para convertirlo en aplicación (exe) para que pueda ejecutarse, deben seguirse estos pasos:
	- Instalar PyInstaller si no se tiene instalado previamente:
	`pip install pyinstaller`
	- Ubícate en el directorio del script y ejecuta:
	`pyinstaller --onefile obtener_forecast_servidor.py`

	El ejecutable estará en la carpeta dist.

- [prediccion_de_potencias_con_regresión.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/prediccion_de_potencias_con_regresi%C3%B3n.ipynb "prediccion_de_potencias_con_regresión.ipynb"): notebook con 2 modelos de regresión para predecir los datos de la potencia general del parque. Los modelos de regresión están hechos con tensorflow y son una regresión polinomial, un random forest y un random forest con la librería ydf. 

- [predicción_potencias_individuales_con_regresión.ipyn](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/predicci%C3%B3n_potencias_individuales_con_regresi%C3%B3n.ipynb "predicción_potencias_individuales_con_regresión.ipyn"): notebook con los dos modelos de regresión adaptados para predecir las potencias individuales de cada turbina en base a sus velocidades. Predicen todas las potencias individuales a la vez y tienen como datos de entrada todas las velociades individuales y el seno y coseno del ángulo del viento.

- [pruebas_hiperparámetros.ipynb](https://github.com/Lucia1009/TFG-Plataforma-para-la-prediccion-de-potencia-electrica-generada-en-parques-eolicos/blob/main/modelos/pruebas_hiperpar%C3%A1metros.ipynb "pruebas_hiperparámetros.ipynb"): notebook en el que se prueban los distintos parámetros de los modelos generales para elegir los más adecuados.

## Fuentes de los datos
- [Página de modelos de series temporales para predicción de datos meteorológicos](https://open-meteo.com/en/docs "Página de modelos de series temporales para predicción de datos meteorológicos")

- [Datos meteorológicos de Alemania](https://www.dwd.de/DE/klimaumwelt/cdc/cdc_node.html "Datos meteorológicos de Alemania")

- [Datos meteorológicos de Galicia](https://www.meteogalicia.gal/web/observacion/rede-meteoroloxica/historico "Datos meteorológicos de Galicia")
