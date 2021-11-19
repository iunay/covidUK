package com.iunay.coviduk.service;

import com.iunay.coviduk.domain.DailyDeath;
import com.iunay.coviduk.domain.Response;
import com.iunay.coviduk.mydomain.MontlyData;
import com.iunay.coviduk.mydomain.MontlyStats;
import com.iunay.coviduk.provider.CoronavirusUKProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CovidServiceImpl implements CovidService{


    CoronavirusUKProvider coronavirusUkProvider;

    @Autowired
    public CovidServiceImpl(CoronavirusUKProvider coronavirusUkProvider) {
        this.coronavirusUkProvider = coronavirusUkProvider;
    }

    @Override
    public MontlyStats getMontlyStats() {

       Response response = coronavirusUkProvider.getCovidCases();

       List<DailyDeath> dailyData  = response.getData();

        Map<String, List<DailyDeath>> aggregate = new HashMap<>();

       for(DailyDeath data : dailyData ) {
           LocalDate localDate = LocalDate.parse(data.getDate());

           String yearMonth = ""+localDate.getYear()+"-"+localDate.getMonth().getValue();

           if(aggregate.containsKey(yearMonth)){
               aggregate.get(yearMonth).add(data);
           }else{
               List<DailyDeath> dailyDeaths = new ArrayList<>();
               dailyDeaths.add(data);
               aggregate.put(yearMonth,dailyDeaths);
           }
       }

       List<MontlyData> montlyDataList = new ArrayList<>();

       for ( Map.Entry<String , List<DailyDeath>> x : aggregate.entrySet()){

          String date = x.getKey();
          List<DailyDeath> data = x.getValue();

          long deathSum = 0;
          long casesSum = 0;

          for (DailyDeath y : data) {
              deathSum += y.getDeaths();
              casesSum += y.getCases();
          }
           double avgDailyDeaths = ((double)deathSum)/data.size();
          // 100 : totCases = x : totDeaths
           double percentage =  (100d * deathSum)/casesSum;

          MontlyData montlyData = new MontlyData();
          montlyData.setMonthName(date);
          montlyData.setCumulativeCases(casesSum);
          montlyData.setCumulativeDeaths(deathSum);
          montlyData.setAvgDeaths(avgDailyDeaths);
          montlyData.setPercentageCasesOverDeaths(percentage);
          montlyDataList.add(montlyData);
       }
       MontlyStats montlyStats = new MontlyStats();
       montlyStats.setMontlyData(montlyDataList);
       return montlyStats;
    }

    //rounding percentage and avg and sorting by  recent date
    // change variable names




}
