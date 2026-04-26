package com.swelist.swelistnaija.service;

import com.swelist.swelistnaija.domian.*;
import com.swelist.swelistnaija.dto.ResendRequest;
import com.swelist.swelistnaija.dto.ResendResponse;
import com.swelist.swelistnaija.repository.EmailLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final RestClient restClient;
    private final EmailLogRepository emailLogRepository;


    @Value("${resend.api.key}")
    private String resendApiKey;

    public void sendDigest(Subscriber subscriber, List<Job> jobs) {

        List<Job> filteredJobs = jobs.stream()
                .filter(job -> subscriber.getRolePreferences().contains(RolePreference.ALL)
                        || subscriber.getRolePreferences().contains(job.getRoleCategory()))
                .filter(job -> subscriber.getLocationPreference() == LocationPreference.ALL
                        || subscriber.getLocationPreference().name().equalsIgnoreCase(job.getCountry())
                        || (subscriber.getLocationPreference() == LocationPreference.REMOTE && job.isRemote()))
                .toList();

        if (filteredJobs.isEmpty()) {
            return;
        }

        String emailBody = buildEmailBody(filteredJobs, subscriber);

        try {
            ResendResponse response = restClient.post()
                    .uri("/emails")
                    .body(new ResendRequest(
                            "digest@yourclub.com",
                            subscriber.getEmail(),
                            "Your Daily CS Internship Digest",
                            emailBody
                    ))
                    .retrieve()
                    .body(ResendResponse.class);

            emailLogRepository.save(EmailLog.builder()
                    .subscriber(subscriber)
                    .sentAt(Instant.now())
                    .jobCount(filteredJobs.size())
                    .status("sent")
                    .resendMessageId(response.getId())
                    .build());

        } catch (Exception e) {
            log.error("Failed to send digest to: {}", subscriber.getEmail(), e);

            emailLogRepository.save(EmailLog.builder()
                    .subscriber(subscriber)
                    .sentAt(Instant.now())
                    .jobCount(filteredJobs.size())
                    .status("failed")
                    .resendMessageId(null)
                    .build());
        }
    }

    private String buildEmailBody(List<Job> jobs, Subscriber subscriber) {
        StringBuilder body = new StringBuilder();
        body.append("<h2>Your Daily CS Internship Digest</h2>");
        body.append("<p>Here are today's latest postings for you:</p>");

        for (Job job : jobs) {
            body.append("<div style='margin-bottom: 24px;'>")
                    .append("<strong>").append(job.getTitle()).append("</strong>")
                    .append(" — ").append(job.getCompany()).append("<br/>")
                    .append("📍 ").append(job.getLocation()).append("<br/>")
                    .append(job.isRemote() ? "🌐 Remote<br/>" : "")
                    .append("<a href='").append(job.getApplicationUrl()).append("'>Apply Here</a>")
                    .append("</div>");
        }

        body.append("<hr/>")
                .append("<p style='font-size:12px;'>")
                .append("<a href='https://yourclub.com/unsubscribe?token=")
                .append(subscriber.getUnsubscribeToken())
                .append("'>Unsubscribe</a></p>");

        return body.toString();
    }
}