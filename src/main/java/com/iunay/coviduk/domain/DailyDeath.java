package com.iunay.coviduk.domain;

import lombok.Data;

@Data
public class DailyDeath {

    private String date;
    private long cases;
    private long deaths;

}
