package com.iunay.coviduk.domain;

import lombok.Data;

@Data
public class Filter {
    private String identifier;
    private String operator;
    private String value;
}
