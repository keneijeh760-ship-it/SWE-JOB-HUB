import requests
from dotenv import load_dotenv
import os

load_dotenv()


def poster(jobs):
    api_url = os.getenv("SPRING_API_URL")
    api_key = os.getenv("INGEST_API_KEY")

    headers = {
        "X-Api-Key": api_key
    }

    response = requests.post(f"{api_url}/api/ingest/batch", json=jobs, headers=headers)

    print(response.status_code)







