
from scrapers.remotive import scrape_remotive
from scrapers.adzuna import adzuna_scaper
from scrapers.github_repos import scrape_github
from scrapers.jobberman import jobberman_scaper
from core.normalizer import normalizer
from core.poster import poster
from scrapers.my_jobmag import scrape_myjobmag
from scrapers.greenhouse import scrape_reed

from dotenv import load_dotenv


load_dotenv()

def main():
    raw_jobs = []
    raw_jobs.extend(scrape_reed())
    raw_jobs.extend(scrape_github())
    raw_jobs.extend(scrape_remotive())


    normalized_jobs = [normalizer(job) for job in raw_jobs]

    poster(normalized_jobs)


main()




