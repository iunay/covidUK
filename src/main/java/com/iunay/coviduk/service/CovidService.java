package com.iunay.coviduk.service;

import com.iunay.coviduk.domain.Response;
import com.iunay.coviduk.mydomain.MontlyData;
import com.iunay.coviduk.mydomain.MontlyStats;

import java.util.List;

public interface CovidService{

     MontlyStats getMontlyStats();

     List<MontlyData> getMontlyStatsByMonthAndYear(String month, String year);
}
