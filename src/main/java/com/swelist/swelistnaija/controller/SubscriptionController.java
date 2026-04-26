package com.swelist.swelistnaija.controller;

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

    @PostMapping("/subscribe")
    public ResponseEntity<Subscriber> subscribe(@RequestBody SubscribeRequest request) {
        Subscriber subscriber = subscriberService.subscribe(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriber);
    }

    @GetMapping("/verify")
    public ResponseEntity<Subscriber> verify (@RequestParam String token){

        Subscriber verification = subscriberService.verifyEmail(token);
        return ResponseEntity.ok(verification);
    }

    @GetMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam String token){
        subscriberService.unsubscribe(token);
        return ResponseEntity.ok("You have been unsubscribed successfully");
    }


}
