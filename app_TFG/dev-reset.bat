@echo off
echo Limpiando contenedores, volúmenes y redes huérfanas...
docker-compose down -v --remove-orphans
docker volume prune -f
docker-compose up --build
