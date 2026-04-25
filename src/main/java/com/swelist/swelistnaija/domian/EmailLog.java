package com.swelist.swelistnaija.domian;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "email_logs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailLog {
    @Id
    @SequenceGenerator(name = "emaillog_Id",
            sequenceName = "emaillog_Id",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "emaillog_Id")
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id", nullable = false)
    private Subscriber subscriber;
    @Column(name = "sent_at", nullable = false, updatable = false)
    private Instant sentAt;
    @Column(name = "job_count", nullable = false)
    private int jobCount;
    @Column(nullable = false)
    private String status;
    @Column(name = "resend_message_id")
    private String resendMessageId;


}
