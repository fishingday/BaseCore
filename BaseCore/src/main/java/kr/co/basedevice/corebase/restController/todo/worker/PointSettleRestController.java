package kr.co.basedevice.corebase.restController.todo.worker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

/**
 * 포인트 정산 조회
 * 
 * @author fishingday
 *
 */
@RestController
@RequestMapping("/todo/worker/point_settle")
@RequiredArgsConstructor
public class PointSettleRestController {
	
	final private TodoService todoService;

	// 포인트 정산 목록 조회
	
	
	// 정산 별 상세 조회
	
	
	// 이의 신청 ? (이런 것은 하지 말자)
}
