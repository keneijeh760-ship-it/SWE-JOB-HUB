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

    public void sendVerificationEmail(Subscriber subscriber) {
    String verifyUrl = "https://swehub.name.ng/api/subscribers/verify?token=" 
        + subscriber.getVerificationToken();
    String body = "<h2>Verify your email</h2>"
        + "<p>Click the link below to start receiving daily SWE jobs:</p>"
        + "<a href='" + verifyUrl + "'>Verify Email</a>";
    try {
        restClient.post()
            .uri("/emails")
            .body(new ResendRequest(
                "noreply@swehub.name.ng",
                subscriber.getEmail(),
                "Verify your SWE Hub subscription",
                body
            ))
            .retrieve()
            .body(ResendResponse.class);
        log.info("Verification email sent to {}", subscriber.getEmail());
    } catch (Exception e) {
        log.error("Failed to send verification email to {}", subscriber.getEmail(), e);
    }
}

    public void sendDigest(Subscriber subscriber, List<Job> jobs) {

        List<Job> filteredJobs = jobs.stream()
                .filter(job -> subscriber.getRolePreferences().contains("ALL")
                        || job.getRoleCategory() == null
                        || subscriber.getRolePreferences().contains(job.getRoleCategory().name())) 
                .filter(job -> subscriber.getLocationPreference() == LocationPreference.ALL
                        || subscriber.getLocationPreference().name().equalsIgnoreCase(job.getCountry())
                        || (subscriber.getLocationPreference() == LocationPreference.REMOTE && job.isRemote()))
                .limit(80)
                .toList();

        if (filteredJobs.isEmpty()) {
            return;
        }

        String emailBody = buildEmailBody(filteredJobs, subscriber);

        try {
            ResendResponse response = restClient.post()
                    .uri("/emails")
                    .body(new ResendRequest(
                            "noreply@swehub.name.ng",
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
                .append("<a href='https://swehub.name.ng/api/subscribers/unsubscribe?token=")
                .append(subscriber.getUnsubscribeToken())
                .append("'>Unsubscribe</a></p>");

        return body.toString();
    }
}
