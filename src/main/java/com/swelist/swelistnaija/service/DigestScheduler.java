package com.swelist.swelistnaija.service;

import com.swelist.swelistnaija.domian.Job;
import com.swelist.swelistnaija.domian.Subscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DigestScheduler {

    private final JobService jobService;
    private final SubscriberService subscriberService;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 8 * * *", zone = "Africa/Lagos")
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
