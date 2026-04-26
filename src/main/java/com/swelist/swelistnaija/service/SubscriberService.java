package com.swelist.swelistnaija.service;

import com.swelist.swelistnaija.domian.Subscriber;
import com.swelist.swelistnaija.dto.SubscribeRequest;
import com.swelist.swelistnaija.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    public Subscriber subscribe(SubscribeRequest request){

       Optional<Subscriber> email = subscriberRepository.findByEmail(request.getEmail());

       if (email.isPresent()){
           throw new RuntimeException("Email already subscribed");
       }

       Subscriber newSubscriber = Subscriber.builder()
               .email(request.getEmail())
               .isVerified(false)
               .isActive(true)
               .rolePreferences(request.getRolePreferences())
               .locationPreference(request.getLocationPreference())
               .verificationToken(UUID.randomUUID().toString())
               .unsubscribeToken(UUID.randomUUID().toString())
               .subscribedAt(Instant.now())
               .unsubscribedAt(null)
               .build();
       return subscriberRepository.save(newSubscriber);
    }

    public Subscriber verifyEmail(String token){
        Subscriber verify = subscriberRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Verification token not found"));

        verify.setVerified(true);
        verify.setVerificationToken(null);
        return subscriberRepository.save(verify);
    }

    public Subscriber unsubscribe(String token){
        Subscriber subject = subscriberRepository.findByUnsubscribeToken(token)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        subject.setActive(false);
        subject.setUnsubscribedAt(Instant.now());
       return  subscriberRepository.save(subject);
    }

    public List<Subscriber> getActiveVerifiedSubscribers(){
        List<Subscriber> active = subscriberRepository.findByIsVerifiedTrueAndIsActiveTrue();
        return active;


    }


}
