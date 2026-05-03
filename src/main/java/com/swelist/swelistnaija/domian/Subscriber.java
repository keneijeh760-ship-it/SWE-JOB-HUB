package com.swelist.swelistnaija.domian;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "subscribers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "role_preferences", columnDefinition = "text[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<RolePreference> rolePreferences;
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
