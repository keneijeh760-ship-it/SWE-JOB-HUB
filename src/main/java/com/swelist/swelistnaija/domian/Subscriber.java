package com.swelist.swelistnaija.domian;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@Getter
@Setter
@Table(name = "subscribers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscriber {
    @Id
    @SequenceGenerator(name = "subscriber_Id",
            sequenceName = "subscriber_Id",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "subscriber_Id")
    private long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "role_preferences", columnDefinition = "text[]")
    @Enumerated(EnumType.STRING)
    private RolePrefernce rolePreferences;
    @Enumerated(EnumType.STRING)
    @Column(name = "location_preference", nullable = false)
    private LocationPreference locationPreference;
    @Column(name = "verification_token", nullable = false, unique = true)
    private String verificationToken;
    @Column(name = "unsubscribe_token", nullable = false, unique = true)
    private String unsubscribeToken;
    @Column(name = "subscribed_at", nullable = false, updatable = false)
    private Instant subscribedAt;
    @Column(name = "unsubscribed_at")
    private Instant unsubscribedAt;

}
