package com.ktds.date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;


//@SpringBootTest
class TimePackage {

    Logger log = LoggerFactory.getLogger(TimePackage.class);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void timePackaget() {
        LocalDate currentDate = LocalDate.now();    // 현재 날짜 정보. 2020-03-01
        LocalDate setDate = LocalDate.of(2020, 3, 1);   // 지정한 날짜 정보

        LocalTime currentTime = LocalTime.now();    // 현재 시간 정보. 09:52:13.906
        LocalTime setTime = LocalTime.of(9, 52, 13); // 지정된 시간 정보

        LocalDateTime currentDateTime = LocalDateTime.now();    // 현재 날짜와 시간 정보. 2020-03-01T09:52:13.906
        LocalDateTime setDateTime = LocalDateTime.of(2020, 3, 1, 9, 52, 13); // 지정된 날짜와 시간 정보

        ZonedDateTime utcDateTime = ZonedDateTime.now(ZoneId.of("UTC")); //2020-03-01T00:52:13.906+09:00[UTC]
        ZonedDateTime seoulDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")); // Zone 정보가 추가된 현재 날짜와 시간 정보. 2020-03-01T09:52:13.906+09:00[Asia/Seoul]

        log.info("{}", seoulDateTime);
    }

    @Test
    public void timeCalc() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime newDateTime = currentDateTime
                .plusYears(1)       // 1년 더하기
                .minusYears(1)      // 1년 빼기
                .plusMonths(1)      // 1개월 더하기
                .minusMonths(1)     // 1갸월 빼기
                .plusDays(1)        // 1일 더하기
                .minusDays(1)       // 1일 빼기
                .plusWeeks(1)       // 1주 더하기
                .minusWeeks(1);     // 1주 빼기
        log.info("current:{}, new:{}", currentDateTime, newDateTime);
    }

    @Test
    public void timeTemporalAdjust() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime newDateTime = currentDateTime
                .with(TemporalAdjusters.firstDayOfMonth())              // 이번 달의 첫 번째 일(1일)
                .with(TemporalAdjusters.lastDayOfMonth())               // 이번 달의 마지막 일
                .with(TemporalAdjusters.firstDayOfNextMonth())          // 다음 달의 첫 번째 일(1일)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)) // 이번 달의 첫 번째 요일
                .with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY))  // 이번 달의 마지막 요일
                .with(TemporalAdjusters.previous(DayOfWeek.FRIDAY))     // 지난주 금요일
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY));// 지난주 금요일(오늘 포함)
    }

    @Test
    public void timeGap() {
        LocalDate currentDate = LocalDate.now();
        LocalDate newDateTime = LocalDate.of(2020,8,15);

        Period period = currentDate.until(newDateTime);
        log.info("Gap - 년:{}, 월:{}, 일:{}", period.getYears(), period.getMonths(), period.getDays());
    }

    @Test
    public void timeFormmater() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 hh시 mm분 ss초");
        String nowString = LocalDateTime.now().format(dateTimeFormatter);
        log.info(nowString);
    }

    @Test
    public void timeParse() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate newDate = LocalDate.parse("2020.03.01", dateTimeFormatter);
    }
}