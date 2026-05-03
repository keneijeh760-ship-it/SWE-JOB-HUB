import requests
from bs4 import BeautifulSoup

SEARCH_URLS = [
    "https://www.myjobmag.com/jobs-by-title/software-engineer",
    "https://www.myjobmag.com/jobs-by-title/software-developer",
    "https://www.myjobmag.com/jobs-by-title/data-scientist",
    "https://www.myjobmag.com/jobs-by-title/backend-developer",
    "https://www.myjobmag.com/jobs-by-title/frontend-developer",
    "https://www.myjobmag.com/jobs-by-title/cybersecurity",
    "https://www.myjobmag.com/jobs-by-title/devops-engineer",
    "https://www.myjobmag.com/jobs-by-title/mobile-developer",
]

HEADERS = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
}

def scrape_myjobmag():
    jobs = []
    seen_urls = set()

    for url in SEARCH_URLS:
        try:
            response = requests.get(url, headers=HEADERS, timeout=10)
            soup = BeautifulSoup(response.text, "html.parser")


            for item in soup.select("ul.job-list li, li"):
                try:
                    title_tag = item.find("h2")
                    if not title_tag:
                        continue

                    link_tag = title_tag.find("a")
                    if not link_tag:
                        continue

                    title = link_tag.get_text(strip=True)
                    job_url = link_tag.get("href", "")

                    if not job_url or job_url in seen_urls:
                        continue

                    # Extract company — it's usually the text after "at" in the title
                    company = ""
                    if " at " in title:
                        parts = title.split(" at ", 1)
                        title = parts[0].strip()
                        company = parts[1].strip()

                    # Extract location
                    location_tag = item.find("a", href=lambda h: h and "/jobs-location/" in h)
                    location = location_tag.get_text(strip=True) if location_tag else "Nigeria"

                    seen_urls.add(job_url)
                    jobs.append({
                        "company": company,
                        "role": title,
                        "location": location,
                        "country": "Nigeria",
                        "applicationUrl": job_url,
                    })
                except Exception as e:
                    continue

        except Exception as e:
            print(f"MyJobMag failed for {url}: {e}")

    print(f"MyJobMag: fetched {len(jobs)} jobs")
    return jobs