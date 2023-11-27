package kr.co.basedevice.corebase.service.todo;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.td.TdTodo;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.dto.todo.WorkDetailInfoDto;
import kr.co.basedevice.corebase.repository.td.TdTodoRepository;
import kr.co.basedevice.corebase.repository.td.TdWorkRepository;
import kr.co.basedevice.corebase.search.todo.SearchWork;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class WorkService {
	
	final private TdTodoRepository tdTodoRepository;
	final private TdWorkRepository tdWorkRepository;
	
	/**
	 * 작업이력
	 * 
	 * @param searchWork
	 * @param page
	 * @return
	 */
	public Page<WorkDetailInfoDto> pageWorkHistory(SearchWork searchWork, Pageable page) {

		Page<WorkDetailInfoDto> pageWorkInfo = tdWorkRepository.pageWorkHistory(searchWork, page);
		
		if(!pageWorkInfo.isEmpty() && !pageWorkInfo.getContent().isEmpty()) {
			
			int num = 1 + (pageWorkInfo.getNumber() * pageWorkInfo.getSize());
			for(int i = 0; i < pageWorkInfo.getContent().size(); i++) {
				WorkDetailInfoDto workDetailInfoDto = pageWorkInfo.getContent().get(i);
				workDetailInfoDto.setNum(num + i);
			}
		}
		 
		
		return pageWorkInfo;
	}
	
	/**
	 * 작업자의 작업상세 조회
	 * 
	 * @param workSeq
	 * @param userSeq
	 * @return
	 */
	public WorkDetailInfoDto getWorkInfo(Long workSeq, Long userSeq) {

		Optional<TdWork> tdWork = tdWorkRepository.findById(workSeq);
		
		if(!tdWork.isPresent() 
				|| userSeq.longValue() != tdWork.get().getWorkerSeq().longValue()){
			throw new IllegalArgumentException("올바른 작업 정보가 아닙니다.");
		}
		
		Optional<TdTodo> tdTodo = tdTodoRepository.findById(tdWork.get().getTodoSeq());
		
		WorkDetailInfoDto workDetailInfoDto = new WorkDetailInfoDto();
		workDetailInfoDto.setTdWork(tdWork.get());
		workDetailInfoDto.setTdTodo(tdTodo.get());
		
		return workDetailInfoDto;
	}

	/**
	 * 확인 작업 저장
	 * 
	 * @param confirmWork
	 * @return
	 */
	public boolean confirmWork(TdWork confirmWork) {
		Optional<TdWork> optWork = tdWorkRepository.findById(confirmWork.getWorkSeq());
		
		if(!optWork.isPresent()) {
			throw new IllegalArgumentException("올바른 작업 정보가 아닙니다.");
		}
		TdWork tdWork = optWork.get();
		
		tdWork.setWorkStatCd(confirmWork.getWorkStatCd());
		tdWork.setWorkCont(confirmWork.getWorkCont());
		tdWork.setConfmDt(LocalDateTime.now());
		tdWork.setConfmCont(confirmWork.getConfmCont());
		
		tdWorkRepository.save(tdWork);
		
		return true;
	}	
}
