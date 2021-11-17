package com.iunay.coviduk.domain;

import lombok.Data;

import java.util.List;

@Data
public class RequestPayload {

    private Structure structure;
    private List<Filter> filters;
    private int page;

}
