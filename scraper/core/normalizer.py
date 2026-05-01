
def normalizer(raw_job):
    title = raw_job.get("title", "").lower()
    location = raw_job.get("location", "")
    application_url = raw_job.get("applicationUrl", "")
    experience_level = raw_job.get("experience_level", "")



    is_remote = "remote" in location.lower()

    if "nigeria" in location.lower():
        country = "NIGERIA"
    elif "united kingdom" in location.lower() or "uk" in location.lower():
        country = "UK"
    elif "canada" in location.lower():
        country = "CANADA"

    else:
        country = "UNKNOWN"

    if "frontend" in title:
        role_category = "FRONTEND"
    elif "backend" in title:
        role_category = "BACKEND"
    elif "fullstack" in title:
        role_category = "FULL_STACK"
    elif "datascience" in title:
        role_category = "DATA_SCIENCE"
    elif "cybersecurity" in title:
        role_category = "CYBERSECURITY"
    elif "game" in title:
        role_category = "GAME_DEV"
    elif "devops" in title:
        role_category = "DEVOPS"
    elif "mobile" in title:
        role_category = "MOBILE"
    elif "research" in title:
        role_category = "RESEARCH"
    else:
        role_category = "ALL"

    if "intern" in title:
        experience_level = "INTERN"
    else:
        experience_level = "NEWGRADUATE"

    return {
        "title": raw_job.get("title"),
        "company": raw_job.get("company"),
        "location": raw_job.get("location"),
        "country": country,
        "isRemote": is_remote,
        "roleCategory": role_category,
        "experienceLevel": experience_level,
        "applicationUrl": application_url,
        "postedAt": None
    }




