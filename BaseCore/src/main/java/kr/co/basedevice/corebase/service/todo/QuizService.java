package kr.co.basedevice.corebase.service.todo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdQuiz;
import kr.co.basedevice.corebase.dto.todo.QuizInfoDto;
import kr.co.basedevice.corebase.dto.todo.QuizUserInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkQuizInfoDto;
import kr.co.basedevice.corebase.repository.td.TdQuizRepository;
import kr.co.basedevice.corebase.search.todo.SearchQuiz;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class QuizService {
	
	private final TdQuizRepository quizRepository;
	
	/**
	 * 퀴즈 목록 정보 조회
	 * 
	 * @param searchQuiz
	 * @param page
	 * @return
	 */
	public Page<QuizInfoDto> findByQuizInfo(SearchQuiz searchQuiz, Pageable page) {
		Page<QuizInfoDto> pageQuizInfoDto = quizRepository.findByQuizInfo(searchQuiz, page);
				
		return pageQuizInfoDto;
	}


	/**
	 * 퀴즈 정보 조회
	 * 
	 * @param quizSeq
	 * @return
	 */
	public TdQuiz getQuiz(Long quizSeq) {
		Optional<TdQuiz> tdQuiz = quizRepository.findById(quizSeq);
		
		return tdQuiz.get();
	}


	/**
	 * 퀴증별 사용자 대답 정보
	 * 
	 * @param quizSeq
	 * @return
	 */
	public List<QuizUserInfoDto> getQuizUserInfoList(Long quizSeq) {
		List<QuizUserInfoDto> listQuizUserInfoDto = quizRepository.findByQuizUserInfoList(quizSeq);

		return listQuizUserInfoDto;
	}


	/**
	 * 퀴즈별 작업 정보
	 * 
	 * @param quizSeq
	 * @return
	 */
	public List<WorkQuizInfoDto> getWorkQuizInfoList(Long quizSeq) {
		List<WorkQuizInfoDto> listWorkQuizInfoDto = quizRepository.findByWorkQuizInfoList(quizSeq);
		
		return listWorkQuizInfoDto;
	}


	/**
	 * 퀴즈 정보 저장
	 * 
	 * @param tdQuiz
	 * @return
	 */
	public boolean saveQuiz(TdQuiz tdQuiz) {
		
		tdQuiz.setDelYn(Yn.N);
		quizRepository.save(tdQuiz);
		
		return true;
	}
	
	
}
