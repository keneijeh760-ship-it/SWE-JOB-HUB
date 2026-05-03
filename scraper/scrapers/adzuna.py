import os

import requests
from dotenv import load_dotenv

load_dotenv()

app_id = os.getenv("ADZUNA_APP_ID")
app_key = os.getenv("ADZUNA_APP_KEY")

def adzuna_scaper():
    results = []

    countries = ["gb", "us", "ca"]

    for country in countries:
        try:
            response = requests.get(f"https://api.adzuna.com/v1/api/jobs/{country}/search/1?app_id={app_id}&app_key={app_key}&what=software+engineer&results_per_page=50")
            data = response.json()
            jobs = data["results"]

            for job in jobs:
                results.append({
                    "title": job["title"],
                    "company": job["company"]["display_name"],
                    "location": job["location"]["display_name"],
                    "applicationUrl": job["redirect_url"]
            })
        except Exception as e:
            print(f"Adzuna scrape failed for {country}: {e}")

    print(f"Adzuna: fetched {len(results)} jobs")
    return results