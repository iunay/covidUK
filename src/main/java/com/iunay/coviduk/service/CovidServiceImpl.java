package com.iunay.coviduk.service;

import com.iunay.coviduk.provider.CoronavirusUKProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CovidServiceImpl implements CovidService{


    CoronavirusUKProvider coronavirusUkProvider;

    @Autowired
    public CovidServiceImpl(CoronavirusUKProvider coronavirusUkProvider) {
        this.coronavirusUkProvider = coronavirusUkProvider;
    }

    @Override
    public Object getCases() {
       return coronavirusUkProvider.getCovidCases();
    }
}
