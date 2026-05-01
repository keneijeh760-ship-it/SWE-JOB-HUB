from bs4 import BeautifulSoup
import requests


def jobberman_scaper():
    result = []
    content = requests.get("https://www.jobberman.com/jobs?q=software")

    soup = BeautifulSoup(content.text, "html.parser")

    jobs = soup.find_all("div", {"class": "flex-1 flex items-center justify-between rounded-tr-md w-full py-3 px-4"})

    for job in jobs:
        try:
            title_tag = job.find("p", class_="text-lg font-medium break-words text-link-500")
            title = title_tag.text.strip() if title_tag else "Unknown"
            company_tag = job.find("p", class_="text-sm text-blue-700 text-loading-animate inline-block mt-3")
            company = company_tag.text.strip() if company_tag else "Unknown"

            location_tag = job.find("div", class_="flex flex-wrap mt-3 text-sm text-gray-500 md:py-0")
            location = location_tag.text.strip() if location_tag else "Unknown"

            url_tag = job.find("a", attrs={"data-cy": "listing-title-link"})
            url = url_tag["href"] if url_tag else None
            if url:
                result.append({
                "title": title,
                "company": company,
                "location": location,
                "applicationUrl": url
                })
        except Exception as e:
            print(f"Jobberman  scrape failed: {e}")
    print(f"Jobberman: fetched {len(result)} jobs")

    return result





