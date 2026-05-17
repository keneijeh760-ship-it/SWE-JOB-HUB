import requests
from dotenv import load_dotenv
import os

load_dotenv()

API_KEY = os.getenv("REED_API_KEY")
# Add this temporarily at the top of reed.py
print(f"API KEY: {API_KEY}")
KEYWORDS = [
    "software engineer",
    "developer",
    "data scientist",
    "cybersecurity",
    "backend engineer",
    "frontend developer"
]


def scrape_reed():
    results = []

    for keyword in KEYWORDS:
        try:
            # your code here
            # use requests.get() with:
            response = requests.get(
                "https://www.reed.co.uk/api/1.0/search",
                params={
                    "keywords": keyword,
                    "locationName": "United Kingdom",
                    "resultsToTake": 100
                },
                auth=(API_KEY, ""),
                timeout = 30
            )
            data = response.json()
            jobs = data["results"]
            seen_urls = set()
            for job in jobs:
                url = job["jobUrl"]
                if url not in seen_urls:
                    seen_urls.add(url)
                    results.append({
            "title": job["jobTitle"],
            "company": job["employerName"],
            "location": job["locationName"],
            "applicationUrl": job["jobUrl"]
        })


        except Exception as e:
            print(f"Reed {keyword} failed: {e}")

    print(f"Reed: fetched {len(results)} jobs")
    return results