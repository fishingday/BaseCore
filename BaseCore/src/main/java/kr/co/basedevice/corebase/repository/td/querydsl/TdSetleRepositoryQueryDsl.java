package kr.co.basedevice.corebase.repository.td.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.basedevice.corebase.dto.todo.GetSettelDto;
import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchSettle;

public interface TdSetleRepositoryQueryDsl {
	
	/**
	 * 정상 목록 페이지
	 * 
	 * @param searchSettle
	 * @param page
	 * @return
	 */
	Page<SettleInfoDto> pageSettleInfo(SearchSettle searchSettle, Pageable page);
	
	/**
	 * 정산 정보
	 * 
	 * @param getSettelDto
	 * @return
	 */
	SettleInfoDto getSettleInfo(GetSettelDto getSettelDto);
	
}
