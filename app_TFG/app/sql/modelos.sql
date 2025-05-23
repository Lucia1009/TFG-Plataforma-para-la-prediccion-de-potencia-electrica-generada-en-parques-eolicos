-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS tfgdb;

-- Switch to the tfgdb database
USE tfgdb;


CREATE TABLE IF NOT EXISTS modelos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(160) UNIQUE NOT NULL ,
    dir_modelo VARCHAR(560) NOT NULL
);


INSERT INTO modelos (nombre, dir_modelo) VALUES('model_nn.keras',
    'C:\\Users\\garla\\OneDrive\\Escritorio\\TFG\\codigo\\model_nn.keras')