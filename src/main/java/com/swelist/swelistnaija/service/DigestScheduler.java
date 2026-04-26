package com.swelist.swelistnaija.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DigestScheduler {

    private final JobService jobService;
    private final SubscriberService subscriberService;
    private final EmailService emailService;

    public void runDailyDigest() {
        log.info("Running daily digest...");

        List<Job> jobs = jobService.getActiveJobsForDigest();
        List<Subscriber> subscribers = subscriberService.getActiveVerifiedSubscribers();

        for (Subscriber subscriber : subscribers) {
            emailService.sendDigest(subscriber, jobs);
        }

        log.info("Daily digest complete. {} subscribers processed.", subscribers.size());
    }


}
