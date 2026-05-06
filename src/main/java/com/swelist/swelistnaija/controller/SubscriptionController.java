package com.swelist.swelistnaija.controller;

import com.swelist.swelistnaija.service.DigestScheduler;
import java.net.URI;
import com.swelist.swelistnaija.domian.Subscriber;
import com.swelist.swelistnaija.dto.SubscribeRequest;
import com.swelist.swelistnaija.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribers")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriberService subscriberService;
    private final DigestScheduler digestScheduler;

    @PostMapping("/subscribe")
    public ResponseEntity<Subscriber> subscribe(@RequestBody SubscribeRequest request) {
        Subscriber subscriber = subscriberService.subscribe(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriber);
    }

    @GetMapping("/verify")
    public ResponseEntity<Void> verify(@RequestParam String token){
        subscriberService.verifyEmail(token);
        URI location = URI.create("https://swe-hub-frontend.vercel.app");
    return ResponseEntity.status(HttpStatus.FOUND).location(location).build();
    }

    @GetMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam String token){
        subscriberService.unsubscribe(token);
        return ResponseEntity.ok("You have been unsubscribed successfully");
    }

    @PostMapping("/trigger-digest")
    public ResponseEntity<String> triggerDigest() {
       digestScheduler.runDailyDigest();
       return ResponseEntity.ok("Digest triggered!");
}

}
