package com.swelist.swelistnaija.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResendRequest {
    private String from;
    private String to;
    private String subject;
    private String html;
}
