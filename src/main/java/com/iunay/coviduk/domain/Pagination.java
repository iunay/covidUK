package com.iunay.coviduk.domain;

import lombok.Data;

@Data
public class Pagination {
    private String current;
    private String next;
    private String previous;
    private String first;
    private String last;
}
