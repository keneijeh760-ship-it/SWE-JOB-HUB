import requests
from bs4 import BeautifulSoup

url = "https://raw.githubusercontent.com/SimplifyJobs/Summer2026-Internships/refs/heads/dev/README.md"
response = requests.get(url)
soup = BeautifulSoup(response.text, "html.parser")

rows = soup.find_all("tr")
print(f"Total rows found: {len(rows)}")

# Check first actual data row
for row in rows[:5]:
    cols = row.find_all("td")
    if len(cols) >= 4:
        print(f"\nCompany: {cols[0].get_text(strip=True)}")
        print(f"Role: {cols[1].get_text(strip=True)}")
        print(f"Location: {cols[2].get_text(strip=True)}")
        # Print all links in application column
        links = cols[3].find_all("a")
        print(f"Links found: {len(links)}")
        for a in links:
            print(f"  href: {a.get('href')}")