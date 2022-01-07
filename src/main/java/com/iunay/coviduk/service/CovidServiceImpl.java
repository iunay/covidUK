package com.iunay.coviduk.service;

import com.iunay.coviduk.domain.DailyDeath;
import com.iunay.coviduk.domain.Response;
import com.iunay.coviduk.mydomain.MontlyData;
import com.iunay.coviduk.mydomain.MontlyStats;
import com.iunay.coviduk.provider.CoronavirusUKProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

       for ( Map.Entry<String , List<DailyDeath>> dailyDeath : aggregate.entrySet()){

          String date = dailyDeath.getKey();
          List<DailyDeath> data = dailyDeath.getValue();

          long deathSum = 0;
          long casesSum = 0;

          for (DailyDeath dd : data) {
              deathSum += dd.getDeaths();
              casesSum += dd.getCases();
          }

           double avgDailyDeaths = ((double)deathSum)/data.size();
           avgDailyDeaths = new BigDecimal(avgDailyDeaths).setScale(2, RoundingMode.HALF_UP).doubleValue();

          // 100 : totCases = x : totDeaths
           double percentageDeathOverCases =  (100d * deathSum)/casesSum;
           percentageDeathOverCases = new BigDecimal(percentageDeathOverCases).setScale(2,RoundingMode.HALF_UP).doubleValue();


          MontlyData montlyData = new MontlyData();
          montlyData.setYearMonth(date);
          montlyData.setCumulativeCases(casesSum);
          montlyData.setCumulativeDeaths(deathSum);
          montlyData.setAvgDeaths(avgDailyDeaths);
          montlyData.setPercentageCasesOverDeaths(percentageDeathOverCases);
          montlyDataList.add(montlyData);
       }
        Collections.sort(montlyDataList);


        MontlyStats montlyStats = new MontlyStats();
       montlyStats.setMontlyData(montlyDataList);
       return montlyStats;
    }

    @Override
    public List<MontlyData> getMontlyStatsByMonthAndYear(String year, String month) {

       List<MontlyData> montlyStats = getMontlyStats().getMontlyData();

        List<MontlyData> montlyData =  montlyStats.stream().filter( it -> it.getYearMonth().equals(year+"-"+month)).collect(Collectors.toList());

        return montlyData;
    }

    @Override
    public MontlyStats getMontlyStatsSorted(String sort) {

        MontlyStats m = getMontlyStats();

        List<MontlyData> montlyStats = m.getMontlyData();

        String[] namesList = sort.split(",");
        String sortby = namesList [0];
        String ascdesc = namesList [1];

        Comparator<MontlyData> comparator= null;

        switch (sortby){
            case"yearMonth":
                comparator = new Comparator<MontlyData>() {
                    @Override
                    public int compare(MontlyData o1, MontlyData o2) {
                        return    o1.compareTo(o2);
                    }
                };
               comparator = comparator.reversed();
                break;
            case"cumulativeDeaths":
                comparator = new Comparator<MontlyData>() {
                    @Override
                    public int compare(MontlyData o1, MontlyData o2) {
                        return Long.compare(o1.getCumulativeDeaths(),o2.getCumulativeDeaths());
                    }
                };
                break;
            case"cumulativeCases":
                comparator = new Comparator<MontlyData>() {
                    @Override
                    public int compare(MontlyData o1, MontlyData o2) {
                        return Long.compare(o1.getCumulativeCases(),o2.getCumulativeCases());
                    }
                };
                break;
            case"avgDeaths":
                comparator = new Comparator<MontlyData>() {
                    @Override
                    public int compare(MontlyData o1, MontlyData o2) {
                        return Double.compare(o1.getAvgDeaths(),o2.getAvgDeaths());
                    }
                };
                break;
            case"percentageCasesOverDeaths":
                comparator = new Comparator<MontlyData>() {
                    @Override
                    public int compare(MontlyData o1, MontlyData o2) {
                        return Double.compare(o1.getPercentageCasesOverDeaths(),o2.getPercentageCasesOverDeaths());
                    }
                };
                break;
            default:
                System.out.println("Non si puo' ordinare per "+sortby);
        }

        Collections.sort(montlyStats,comparator);

        if(ascdesc.equals("desc")){
           Collections.reverse(montlyStats);
        }
        return m;
    }

    //sort=field,desc|asc

    //stesso endpoint /stats fare una filter per anno e sort

    //daily stats % vaccinated and not vaccinated ppl

    //rounding percentage and avg and sorting by recent date
    // change variable names
    //implements new url (controller) where the user pass month and year required and returns stats for such year and month!!!!!

}
