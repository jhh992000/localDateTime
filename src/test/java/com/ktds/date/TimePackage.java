package com.ktds.date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;


//@SpringBootTest
@DisplayName("java8 - 날짜 객체 사용하기")
class TimePackage {

	Logger log = LoggerFactory.getLogger(TimePackage.class);

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	@DisplayName("LocalDate 사용 예시")
	public void test_localDate() {
		//LocalDate 예제
		LocalDate currentDate = LocalDate.now();    // 현재 날짜 정보. 2020-03-01
		LocalDate setDate = LocalDate.of(2020, 3, 1);   // 지정한 날짜 정보

		LocalDate christmas = LocalDate.parse("2020-12-25");
		log.info("{}, {}, {}", christmas.getYear(), christmas.getMonthValue(), christmas.getDayOfMonth());
	}

	@Test
	@DisplayName("LocalTime 사용 예시")
	public void test_localTime() {
		//LocalTime 예제
		LocalTime currentTime = LocalTime.now();    // 현재 시간 정보. 09:52:13.906
		LocalTime setTime = LocalTime.of(9, 52, 13); // 지정된 시간 정보
		log.info("{}", setTime);
	}

	@Test
	@DisplayName("LocalDateTime 사용 예시")
	public void test_localDateTime() {
		//LocalDateTime 예제
		LocalDateTime currentDateTime = LocalDateTime.now();    // 현재 날짜와 시간 정보. 2020-03-01T09:52:13.906
		LocalDateTime setDateTime = LocalDateTime.of(2020, 3, 1, 9, 52, 13); // 지정된 날짜와 시간 정보
		log.info("{}", setDateTime);
	}

	@Test
	@DisplayName("ZonedDateTime 사용 예시")
	void test_zonedDateTime() {

		//ZonedDateTime 은 타임존 또는 시차개념이 필요한 경우 사용한다. LocalDateTime + 타임존/시차
		ZonedDateTime utcDateTime = ZonedDateTime.now(ZoneId.of("UTC")); //2020-03-01T00:52:13.906+09:00[UTC]
		ZonedDateTime seoulDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")); // Zone 정보가 추가된 현재 날짜와 시간 정보. 2020-03-01T09:52:13.906+09:00[Asia/Seoul]

		log.info("UTC : {}", utcDateTime);
		log.info("Seoul : {}", seoulDateTime);

	}

	@Test
	@DisplayName("타임존 목록 확인")
	void test_zonedId() {

		Map<String, String> result = new HashMap<>();
		LocalDateTime localDateTime = LocalDateTime.now();

		for (String zoneId : ZoneId.getAvailableZoneIds()) {

			ZoneId id = ZoneId.of(zoneId);

			// LocalDateTime -> ZonedDateTime
			ZonedDateTime zonedDateTime = localDateTime.atZone(id);

			// ZonedDateTime -> ZoneOffset
			ZoneOffset zoneOffset = zonedDateTime.getOffset();

			//replace Z to +00:00
			String offset = zoneOffset.getId().replaceAll("Z", "+00:00");

			log.info("{} : {}", id.toString(), offset);

		}

	}

	@Test
	@DisplayName("날짜계산하기")
	public void timeCalc() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime newDateTime = currentDateTime
				.plusYears(1)       // 1년 더하기
				.minusYears(1)      // 1년 빼기
				.plusMonths(1)      // 1개월 더하기
				.minusMonths(1)     // 1개월 빼기
				.plusDays(1)        // 1일 더하기
				.minusDays(1)       // 1일 빼기
				.plusWeeks(1)       // 1주 더하기
				.minusWeeks(1);     // 1주 빼기
		log.info("current:{}, new:{}", currentDateTime, newDateTime);
	}

	@Test
	@DisplayName("날짜조정기(TemporalAdjust)를 이용하여 시간조정하기")
	public void timeTemporalAdjust() {
		//LocalDate 객체의 with() 메서드에 TemporalAdjust 객체를 전달하는 방식으로 조정
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime newDateTime = currentDateTime
				.with(TemporalAdjusters.firstDayOfMonth())              // 이번 달의 첫 번째 일(1일)
				.with(TemporalAdjusters.lastDayOfMonth())               // 이번 달의 마지막 일
				.with(TemporalAdjusters.firstDayOfNextMonth())          // 다음 달의 첫 번째 일(1일)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)) // 이번 달의 첫 번째 요일
				.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY))  // 이번 달의 마지막 요일
				.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY))     // 지난주 금요일
				.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY));// 지난주 금요일(오늘 포함)
		log.info("currentDateTime : {}", currentDateTime);
		log.info("newDateTime : {}", newDateTime);

		LocalDateTime lastDayOfYear = LocalDateTime.now()
				.with(TemporalAdjusters.lastDayOfYear());
		log.info("lastDayOfYear : {}", lastDayOfYear);
	}

	@Test
	@DisplayName("시간차이 구하기 - Period 객체로 반환")
	public void timeGap() {
		LocalDate currentDate = LocalDate.now();
		LocalDate newDateTime = LocalDate.of(2020, 1, 4);

		Period period = currentDate.until(newDateTime);
		log.info("Gap - 년:{}, 월:{}, 일:{}", period.getYears(), period.getMonths(), period.getDays());

	}

	@Test
	@DisplayName("시간차이 구하기 - localDate")
	void test_localDate_differenceBetweenTwoDates() {
		LocalDate currentDate = LocalDate.now();
		LocalDate newDateTime = LocalDate.of(2020, 6, 4);

		long diffMonths = ChronoUnit.MONTHS.between(currentDate, newDateTime);
		log.info("diffMonths : {}", diffMonths);

		long diffDays = ChronoUnit.DAYS.between(currentDate, newDateTime);
		log.info("diffDays : {}", diffDays);
	}

	@Test
	@DisplayName("시간차이 구하기 - (초, 밀리초 단위까지)")
	void test_localDateTime_differenceBetweenTwoDates() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime beforeDateTime = LocalDateTime.of(2021, 5, 3, 0, 0, 0);

		long diffSeconds = SECONDS.between(currentDateTime, beforeDateTime);
		log.info("diffSeconds : {}", diffSeconds);

		long diffMillis = MILLIS.between(currentDateTime, beforeDateTime);
		log.info("diffMillis : {}", diffMillis);
	}

	@Test
	@DisplayName("날짜 포맷팅하기")
	public void timeFormmater() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 hh시 mm분 ss초");
		String nowString = LocalDateTime.now().format(dateTimeFormatter);
		log.info(nowString);

		ZonedDateTime hangulnal = Year.of(2020).atMonth(10).atDay(9).atTime(0, 0).atZone(ZoneId.of("Asia/Seoul"));
		String koreanString = hangulnal.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.KOREA));
		log.info(koreanString);

	}

	@Test
	@DisplayName("문자열 날짜 변환하기 (파싱)")
	public void timeParse() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		LocalDate newDate = LocalDate.parse("2020.03.01", dateTimeFormatter);
		log.info("{}", newDate);
	}
}