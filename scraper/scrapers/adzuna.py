import os

import requests
from dotenv import load_dotenv

load_dotenv()

id = os.getenv("ADZUNA_APP_ID")
key = os.getenv("ADZUNA_APP_KEY")

def adzuna_scaper():
    results = []

    countries = ["ng", "gb", "us", "ca"]

    for country in countries:
        try:
            response = requests.get(f"https://api.adzuna.com/v1/api/jobs/{country}/search/1?app_id={id}&app_key={key}&what=software+engineer&results_per_page=50")
            data = response.json()
            jobs = data["jobs"]

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