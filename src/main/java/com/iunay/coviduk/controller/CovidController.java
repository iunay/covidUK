package com.iunay.coviduk.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CovidController {

    @GetMapping("/covid")
    String test() {
        return "Covid time";
    }

}
