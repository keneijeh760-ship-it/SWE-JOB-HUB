package com.swelist.swelistnaija.dto;

import com.swelist.swelistnaija.domian.ExperienceLevel;
import com.swelist.swelistnaija.domian.LocationPreference;
import com.swelist.swelistnaija.domian.RolePreference;
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
    private String country;
    private boolean isRemote;
    private RolePreference roleCategory;
    private ExperienceLevel experienceLevel;
    private String applicationUrl;
    private Instant postedAt;
}
