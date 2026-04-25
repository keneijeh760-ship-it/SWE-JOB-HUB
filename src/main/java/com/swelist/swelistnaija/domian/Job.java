package com.swelist.swelistnaija.domian;

import jakarta.persistence.*;
import lombok.*;

import javax.xml.stream.Location;

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


}
