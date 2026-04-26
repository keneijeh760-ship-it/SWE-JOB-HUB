package com.swelist.swelistnaija.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
@Configuration
public class ResendConfig {

    @Value("${resend.api.key}")
    private String resendApiKey;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://api.resend.com")
                .defaultHeader("Authorization", "Bearer " + resendApiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
