package com.swelist.swelistnaija.service;

import com.swelist.swelistnaija.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class CleanUpService {
    private final JobRepository  jobRepository;

    @Scheduled(cron =  "0 0 2 * * *", zone = "Africa/Lagos")
    public void deactivateExpiredJobs() {
        int count = jobRepository.deactivateExpiredJobs(Instant.now());
        log.info("Deactivated {} expired jobs", count);
    }

    @Scheduled(cron = "0 0 3 * * SUN", zone = "Africa/Lagos")
    public void purgeOldJobs() {
        Instant cutoff = Instant.now().minus(1, ChronoUnit.DAYS);
        int count = jobRepository.deleteByIsActiveFalseAndExpiresAtBefore(cutoff);
        log.info("Purged {} old jobs", count);
    }


}
