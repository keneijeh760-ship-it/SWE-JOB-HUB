#!/bin/sh

echo "Waiting for Spring to be ready..."
until wget -q --spider http://spring:8080 2>/dev/null; do
  echo "Spring not ready, retrying in 10s..."
  sleep 10
done

echo "Spring is ready! Starting scraper..."
while true; do
    python main.py
    sleep 21600
done
