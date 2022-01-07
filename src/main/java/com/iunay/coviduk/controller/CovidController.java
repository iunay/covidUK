package com.iunay.coviduk.controller;
import com.iunay.coviduk.mydomain.MontlyData;
import com.iunay.coviduk.mydomain.MontlyStats;
import com.iunay.coviduk.service.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CovidController {


    CovidService covidService;

    @Autowired
    public CovidController(CovidService covidService) {
        this.covidService = covidService;
    }

    @GetMapping("/stats")
    MontlyStats getStats(@RequestParam(name = "sort",required = false) String sort) {

        if(sort != null){
          return  covidService.getMontlyStatsSorted(sort);
        }
        return covidService.getMontlyStats();
    }

    @GetMapping("/stats/by")
    List<MontlyData> getStatsByMonthAndYear (@RequestParam String year, @RequestParam String month) {

        List<MontlyData> montlyData = covidService.getMontlyStatsByMonthAndYear(year, month);
        if (!montlyData.isEmpty()) {
            return montlyData;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
