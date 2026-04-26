package com.swelist.swelistnaija.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BatchIngestResponse {
    private int saved;
    private int failed;
}
