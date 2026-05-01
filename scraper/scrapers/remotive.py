import requests

def scrape_remotive():
    results = []

    try:
        response = requests.get("https://remotive.com/api/remote-jobs?category=software-dev")
        data = response.json()
        jobs = data['jobs']
        for job in jobs:

            results.append({
                "title": job["title"],
                "company": job["company_name"],
                "location": job["candidate_required_location"],
                "applicationUrl": job["url"]
            })
    except Exception as e:
        print(f"Remotive scrape failed: {e}")
    print(f"Remotive: fetched {len(results)} jobs")
    return results





