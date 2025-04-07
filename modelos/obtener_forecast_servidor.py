import openmeteo_requests
import requests_cache
import pandas as pd
import numpy as np
from retry_requests import retry
import pickle

def main():
    lista_modelos = ["best_match", "ecmwf_ifs04", "gfs_global", "jma_gsm", "icon_global", "icon_eu", "icon_d2", 
                     "gem_global", "meteofrance_arpege_europe", "meteofrance_arome_france_hd", "ukmo_global_deterministic_10km"]

    # Setup Open-Meteo API client with cache and retry on error
    cache_session = requests_cache.CachedSession('.cache', expire_after=3600)
    retry_session = retry(cache_session, retries=5, backoff_factor=0.2)
    openmeteo = openmeteo_requests.Client(session=retry_session)

    # Parámetros para el forecast
    url = "https://api.open-meteo.com/v1/forecast"
    params = {
        "latitude": 42.87596,
        "longitude": -8.559434,
        "hourly": ["temperature_2m", "wind_speed_10m", "wind_direction_10m"],
        "past_days": 0,
        "forecast_days": 16,
        "models": lista_modelos
    }

    # Obtener datos
    forecast = obtener_datos(url, params, lista_modelos, openmeteo)

    # Guardar modelos
    hoy = pd.Timestamp.now().date()
    ruta = f'C:\\Users\\garla\\OneDrive\\Escritorio\\TFG\\modelos_serie_temporal\\forecast_{hoy}.pkl'
    guardar_datos(forecast, ruta)

def obtener_datos(url, params, lista_modelos, openmeteo):
    responses = openmeteo.weather_api(url, params=params)
    modelos = {}

    for response, mod in zip(responses, lista_modelos):
        hourly = response.Hourly()
        hourly_temperature_2m = hourly.Variables(0).ValuesAsNumpy()
        hourly_wind_speed_10m = hourly.Variables(1).ValuesAsNumpy()
        hourly_wind_direction_10m = hourly.Variables(2).ValuesAsNumpy()

        hourly_data = {
            "date": pd.date_range(
                start=pd.to_datetime(hourly.Time(), unit="s", utc=True),
                end=pd.to_datetime(hourly.TimeEnd(), unit="s", utc=True),
                freq=pd.Timedelta(seconds=hourly.Interval()),
                inclusive="left"
            ),
            "temperature_2m": hourly_temperature_2m,
            "wind_speed_10m": hourly_wind_speed_10m,
            "wind_direction_10m": hourly_wind_direction_10m
        }

        hourly_dataframe = pd.DataFrame(data=hourly_data)
        hourly_dataframe.dropna(inplace=True)

        hourly_dataframe['wd_sin_10m'] = np.sin(hourly_dataframe['wind_direction_10m'] * np.pi / 180)
        hourly_dataframe['wd_cos_10m'] = np.cos(hourly_dataframe['wind_direction_10m'] * np.pi / 180)

        modelos[mod] = hourly_dataframe.to_dict(orient='list')

    return modelos 

def guardar_datos(modelo, ruta):
    with open(ruta, 'wb') as f:
        pickle.dump(modelo, f)

if __name__ == "__main__":
    main()
