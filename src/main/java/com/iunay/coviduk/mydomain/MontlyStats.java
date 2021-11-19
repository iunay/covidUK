package com.iunay.coviduk.mydomain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MontlyStats {

    //List<Map<String, MontlyData>> montlyStats;
    List<MontlyData> montlyData;
}
