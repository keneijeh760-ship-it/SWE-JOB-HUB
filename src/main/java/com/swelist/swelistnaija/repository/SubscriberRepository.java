package com.swelist.swelistnaija.repository;

import com.swelist.swelistnaija.domian.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {


    Optional<Subscriber> findByEmail(String email);
    Optional<Subscriber> findByVerificationToken(String token);


    Optional<Subscriber> findByUnsubscribeToken(String token);


    List<Subscriber> findByIsVerifiedTrueAndIsActiveTrue();
}
