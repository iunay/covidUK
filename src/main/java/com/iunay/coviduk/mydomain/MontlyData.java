package com.iunay.coviduk.mydomain;

import lombok.Data;

@Data
public class MontlyData {

    private String monthName;
    private long cumulativeDeaths;
    private long cumulativeCases;
    private double avgDeaths;
    private double percentageCasesOverDeaths;


}
