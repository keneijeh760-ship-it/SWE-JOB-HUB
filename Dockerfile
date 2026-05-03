FROM python:3.11-slim
WORKDIR /scraper

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .
COPY run.sh .
RUN chmod +x run.sh

CMD ["./run.sh"]