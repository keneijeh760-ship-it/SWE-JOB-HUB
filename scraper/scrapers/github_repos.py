import requests
from bs4 import BeautifulSoup
import re

def scrape_github():
    url = "https://raw.githubusercontent.com/SimplifyJobs/Summer2026-Internships/refs/heads/dev/README.md"
    response = requests.get(url)
    soup = BeautifulSoup(response.text, "html.parser")

    jobs = []
    last_company = ""

    for row in soup.find_all("tr"):
        cols = row.find_all("td")
        if len(cols) < 4:
            continue

        company_td = cols[0]
        role_td = cols[1]
        location_td = cols[2]
        application_td = cols[3]

        company_text = company_td.get_text(strip=True)
        if company_text == "↳":
            company_text = last_company
        else:
            last_company = company_text

        role = role_td.get_text(strip=True)
        location = location_td.get_text(strip=True)

        # Find application URL - skip simplify links
        app_url = None
        for a_tag in application_td.find_all("a"):
            href = a_tag.get("href", "")
            if href and "simplify.jobs" not in href.lower():
                app_url = href
                break

        if app_url:
            jobs.append({
                "company": company_text,
                "title": role,
                "location": location,
                "applicationUrl": app_url
            })

    print(f"Github: fetched {len(jobs)} jobs")
    return jobs