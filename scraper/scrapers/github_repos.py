import requests
from bs4 import BeautifulSoup


def scrape_github():
    url = "https://raw.githubusercontent.com/SimplifyJobs/Summer2026-Internships/refs/heads/dev/README.md"
    response = requests.get(url)
    soup = BeautifulSoup(response.text, "html.parser")

    jobs = []

    for row in soup.find_all("tr"):
        cols = row.find_all("td")
        if len(cols) < 3:
            continue

        company_td = cols[0]
        role_td = cols[1]
        location_td = cols[2]
        application_td = cols[3] if len(cols) > 3 else None

        # Skip continuation rows (↳)
        company_text = company_td.get_text(strip=True)
        if company_text == "↳":
            # Use last known company
            company_text = last_company
        else:
            last_company = company_text

        role = role_td.get_text(strip=True)
        location = location_td.get_text(strip=True)

        # Get application URL
        url_tag = application_td.find("a") if application_td else None
        app_url = url_tag["href"] if url_tag else None

        if app_url and "simplify" not in app_url.lower():
            jobs.append({
                "company": company_text,
                "role": role,
                "location": location,
                "applicationUrl": app_url,
            })

    print(f"Github: fetched {len(jobs)} jobs")
    return jobs