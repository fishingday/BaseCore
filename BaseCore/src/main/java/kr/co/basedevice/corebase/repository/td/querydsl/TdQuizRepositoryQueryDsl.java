package kr.co.basedevice.corebase.repository.td.querydsl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.basedevice.corebase.dto.todo.QuizInfoDto;
import kr.co.basedevice.corebase.dto.todo.QuizUserInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkQuizInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchQuiz;

public interface TdQuizRepositoryQueryDsl {

	/**
	 * 퀴즈 목록 정보
	 * 
	 * @param searchQuiz
	 * @param page
	 * @return
	 */
	Page<QuizInfoDto> findByQuizInfo(SearchQuiz searchQuiz, Pageable page);
	
	/**
	 * 퀴즈별 사용자퀴즈대답 정보
	 * 
	 * @param quizSeq
	 * @return
	 */
	List<QuizUserInfoDto> findByQuizUserInfoList(Long quizSeq);

	/**
	 * 퀴즈별 작업퀴즈 사용 정보
	 * 
	 * @param quizSeq
	 * @return
	 */
	List<WorkQuizInfoDto> findByWorkQuizInfoList(Long quizSeq);

}
