package com.swelist.swelistnaija.dto;

import com.swelist.swelistnaija.domian.ExperienceLevel;
import com.swelist.swelistnaija.domian.LocationPreference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobIngestRequest {
    private String title;
    private String company;
    private String location;
    private LocationPreference country;
    private boolean isRemote;
    private String roleCategory;
    private ExperienceLevel experienceLevel;
    private String applicationUrl;
    private Instant postedAt;
}
