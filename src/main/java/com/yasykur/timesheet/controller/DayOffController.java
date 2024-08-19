package com.yasykur.timesheet.controller;

import com.yasykur.timesheet.model.DayOff;
import com.yasykur.timesheet.repository.DayOffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DayOffController {


    private final DayOffRepository dayOffRepository;

    @Scheduled(cron = "59 59 23 31 12 ?")
    public void fetchDayOffData() {
        dayOffRepository.deleteAll();

        String url = "https://dayoffapi.vercel.app/api?year=" + Year.now();

        RestTemplate restTemplate = new RestTemplate();

        DayOff[] response = restTemplate.getForObject(url, DayOff[].class);

        if (response != null) {
            List<DayOff> list = Arrays.asList(response);
            dayOffRepository.saveAll(list);
        }
        System.out.println("Successfully save dayOff");
    }
}
