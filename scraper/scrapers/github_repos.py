import requests
import re


def scrape_githubrepo():
    results = []

    content = requests.get("https://raw.githubusercontent.com/SimplifyJobs/Summer2026-Internships/refs/heads/dev/README.md").text

    for line in content.split("\n"):
        try:
            if not line.startswith("|") or "---" in line:
                continue

            columns = line.split("|")
            company = columns[1].strip()
            role = columns[2].strip()
            location = columns[3].strip()
            link_column = columns[4].strip()
            match = re.search(r'https?://[^\)]+', link_column)
            if match:
                url = match.group()
                results.append({
                        "title": role,
                        "company": company,
                        "location": location,
                        "applicationUrl": url
                    })
        except Exception as e:
            print(f"Simplify Github Repo  scrape failed: {e}")
    print(f"Github: fetched {len(results)} jobs")
    return results

