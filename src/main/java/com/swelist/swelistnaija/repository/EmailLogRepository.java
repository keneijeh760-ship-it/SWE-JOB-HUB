package com.swelist.swelistnaija.repository;

import com.swelist.swelistnaija.domian.EmailLog;
import com.swelist.swelistnaija.domian.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

    boolean existsBySubscriberAndSentAtAfter(Subscriber subscriber, Instant since);
}
