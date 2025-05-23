FROM python:3.9-slim
WORKDIR /app

COPY requirements.txt .
RUN pip install --default-timeout=100 --retries=5 -r requirements.txt

# Para que no intente usar las implementaciones de oneDNN
ENV TF_ENABLE_ONEDNN_OPTS=0

COPY . .
EXPOSE 5000
CMD ["python", "main.py"]
