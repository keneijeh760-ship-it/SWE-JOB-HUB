package com.swelist.swelistnaija.controller;

import com.swelist.swelistnaija.domian.Job;
import com.swelist.swelistnaija.dto.BatchIngestResponse;
import com.swelist.swelistnaija.dto.JobIngestRequest;
import com.swelist.swelistnaija.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingest/batch")
public class IngestController {

    private final JobService jobService;

    @Value("${ingest.api.key}")
    private String expectedApiKey;

    @PostMapping
    public ResponseEntity<BatchIngestResponse> getBatch (@RequestBody List<JobIngestRequest> requests,
                                                         @RequestHeader("X-Api-Key") String apiKey){

        if (!apiKey.equals(expectedApiKey)) {
            return ResponseEntity.status(401).build();
        }

        BatchIngestResponse batch = jobService.ingestBatch(requests);

        return ResponseEntity.ok(batch);

    }

}
