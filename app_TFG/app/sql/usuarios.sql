-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS tfgdb;

-- Switch to the tfgdb database
USE tfgdb;


-- Creo la tabla Role
-- DROP TABLE IF EXISTS role;
CREATE TABLE IF NOT EXISTS role (
                                    rol VARCHAR(50) NOT NULL PRIMARY KEY
);
-- Inserto los roles
INSERT INTO role (rol) VALUES
                           ('normal'),
                           ('premium'),
                           ('admin');

-- Creo Usuario
-- DROP TABLE IF EXISTS usuario;
CREATE TABLE IF NOT EXISTS usuario (
                                       nombre      VARCHAR(160) UNIQUE NOT NULL,
                                       email       VARCHAR(160) PRIMARY KEY,
                                       password    VARCHAR(190) NOT NULL,
                                       rol         VARCHAR(50),
                                       CONSTRAINT FK_usuario_role
                                           FOREIGN KEY (rol) REFERENCES role(rol)
);

