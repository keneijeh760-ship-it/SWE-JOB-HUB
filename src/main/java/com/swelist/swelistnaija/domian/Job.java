package com.swelist.swelistnaija.domian;

import jakarta.persistence.*;
import lombok.*;

import javax.xml.stream.Location;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {
    @Id
    @SequenceGenerator(name = "job_Id",
            sequenceName = "job_Id",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "job_Id")
    private long id;
    @Column(nullable = false, name = "title")
    private String title;
    @Column(nullable = false, name = "company")
    private String company;
    @Column(nullable = false, name = "location")
    private String location;
    @Column(nullable = false, name = "country")
    @Enumerated(EnumType.STRING)
    private LocationPreference country;
    @Column(name = "is_remote", nullable = false)
    private boolean isRemote;
    @Column(name = "experience_level", nullable = false)
    private ExperienceLevel  experienceLevel;
    @Column(name = "application_url", nullable = false)
    private String applicationUrl;
    @Column(name = "posted_at")
    private Instant postedAt;
    @Column(name = "fetched_at", nullable = false)
    private Instant fetchedAt;
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;



}
