package kr.co.basedevice.corebase.domain.code;

public enum TodoCreCd {
	DAILY, // empty
	WEEK,  // MON, TUE, WED, THU, FRI, SAT, SUN
	MONTH, // 1, 2, 3, 4 ... LAST
	BATCH,  // 크론 배치 실행 ... cron expression
	DIRECT  // 직접 생성
}
