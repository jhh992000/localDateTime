package com.ktds.date.today;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TodayController {

    @RequestMapping("/today")
    public ResponseEntity<?> getToday() {
        return ResponseEntity.ok(new TodayResponse());
    }
}
