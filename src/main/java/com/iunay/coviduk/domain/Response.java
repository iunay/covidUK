package com.iunay.coviduk.domain;


import lombok.Data;

import java.util.List;

@Data
public class Response {

    private long length;
    private long maxPageLimit;
    private long totalRecords;
    private List<DailyDeath> data;
    private RequestPayload requestPayload;
    private Pagination pagination;


}
