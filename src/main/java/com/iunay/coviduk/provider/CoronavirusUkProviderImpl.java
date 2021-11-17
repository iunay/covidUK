package com.iunay.coviduk.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CoronavirusUkProviderImpl implements CoronavirusUKProvider {


    @Autowired
    RestTemplate restTemplate;

    @Override
    public Object getCovidCases(){

        Map<String,String> mappa =  new HashMap<>();
        mappa.put("filters","areaType=nation;areaName=england");
        mappa.put("structure","{\"date\":\"date\",\"newCases\":\"newCasesByPublishDate\"}");

        final String baseUrl = "https://api.coronavirus.data.gov.uk/v1/data?filters={filters}&structure={structure}";
        // create headers
        HttpHeaders headers = new HttpHeaders();
        List<String> lista = new ArrayList<>();
        lista.add("application/json");
        List<String> lista1= new ArrayList<>();
        lista1.add("gzip,deflate,br");
        headers.put("Content-Type",lista);
        headers.put("Accept",lista);
        headers.put("Accept-Encoding",lista1);


        HttpEntity request = new HttpEntity(headers);

       return restTemplate.exchange(baseUrl, HttpMethod.GET, request, String.class,mappa).getBody();
    }

}
