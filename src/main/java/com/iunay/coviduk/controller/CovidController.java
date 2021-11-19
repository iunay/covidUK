package com.iunay.coviduk.controller;
import com.iunay.coviduk.domain.Response;
import com.iunay.coviduk.mydomain.MontlyStats;
import com.iunay.coviduk.service.CovidService;
import com.iunay.coviduk.service.CovidServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CovidController {


    CovidService covidService;

    @Autowired
    public CovidController(CovidService covidService) {
        this.covidService = covidService;
    }

    @GetMapping("/stats")
    MontlyStats getStats() {
        return covidService.getMontlyStats();
    }

}
