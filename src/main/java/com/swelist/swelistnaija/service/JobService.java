package com.swelist.swelistnaija.service;

import com.swelist.swelistnaija.domian.Job;
import com.swelist.swelistnaija.dto.BatchIngestResponse;
import com.swelist.swelistnaija.dto.JobIngestRequest;
import com.swelist.swelistnaija.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobService {
    private JobRepository jobRepository;

    public Job ingestJob(JobIngestRequest request){
        Job url = jobRepository.findByApplicationUrl(request.getApplicationUrl())
                .orElseThrow(()-> new RuntimeException("Job Not Found"));

        if (!url.getApplicationUrl().isEmpty()){
            url.setExpiresAt(Instant.now().plus(14, ChronoUnit.DAYS));
            url.setActive(true);
            jobRepository.save(url);
            return url;
        }

            Job newjob = Job.builder()
                    .applicationUrl(request.getApplicationUrl())
                    .company(request.getCompany())
                    .country(request.getCountry())
                    .title(request.getTitle())
                    .experienceLevel(request.getExperienceLevel())
                    .createdAt(Instant.now())
                    .expiresAt(Instant.now().plus(14, ChronoUnit.DAYS))
                    .isRemote(request.isRemote())
                    .location(request.getLocation())
                    .postedAt(request.getPostedAt())
                    .fetchedAt(Instant.now())
                    .isActive(true)
                    .build();

            jobRepository.save(newjob);
            return newjob;


    }

    public BatchIngestResponse ingestBatch(List<JobIngestRequest> requests) {
        int saved = 0;
        int failed = 0;

        for (JobIngestRequest request : requests) {
            try {
                ingestJob(request);
                saved++;
            } catch (Exception e) {
                failed++;
                log.error("Failed to ingest job: {}", request.getApplicationUrl(), e);
            }
        }

        return new BatchIngestResponse(saved, failed);
    }




}
