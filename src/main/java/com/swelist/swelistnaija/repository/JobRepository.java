package com.swelist.swelistnaija.repository;

import com.swelist.swelistnaija.domian.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface JoRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByApplicationUrl(String applicationUrl);
    List<Job> findByIsActiveTrueAndExpiresAtAfter(Instant now);
    @Modifying
    @Query("UPDATE Job j SET j.isActive = false WHERE j.expiresAt < :now AND j.isActive = true")
    int deactivateExpiredJobs(@Param("now") Instant now);
    int deleteByIsActiveFalseAndExpiresAtBefore(Instant cutoff);




}
