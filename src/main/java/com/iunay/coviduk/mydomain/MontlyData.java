package com.iunay.coviduk.mydomain;

import lombok.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Data
public class MontlyData implements  Comparable<MontlyData> {

    private String yearMonth;
    private long cumulativeDeaths;
    private long cumulativeCases;
    private double avgDeaths;
    private double percentageCasesOverDeaths;

    @Override
    public int compareTo(MontlyData o) {

         SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
        try {
            return sdf.parse(o.getYearMonth()).compareTo(sdf.parse(this.yearMonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
