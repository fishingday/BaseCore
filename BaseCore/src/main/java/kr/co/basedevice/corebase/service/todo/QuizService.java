package kr.co.basedevice.corebase.service.todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.code.QuizTypCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdQuiz;
import kr.co.basedevice.corebase.dto.todo.QuizInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkQuizInfoDto;
import kr.co.basedevice.corebase.repository.td.TdQuizRepository;
import kr.co.basedevice.corebase.search.todo.SearchQuiz;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class QuizService {
	
	private final TdQuizRepository quizRepository;
	
	final private JdbcTemplate jdbcTemplate;
	
	/**
	 * 퀴즈 목록 정보 조회
	 * 
	 * @param searchQuiz
	 * @param page
	 * @return
	 */
	public Page<QuizInfoDto> findByQuizInfo(SearchQuiz searchQuiz, Pageable page) {
		Page<QuizInfoDto> pageQuizInfoDto = quizRepository.findByQuizInfo(searchQuiz, page);
		
		if(!pageQuizInfoDto.isEmpty() && !pageQuizInfoDto.getContent().isEmpty()) {
			
			int num = 1 + (pageQuizInfoDto.getNumber() * pageQuizInfoDto.getSize());
			for(int i = 0; i < pageQuizInfoDto.getContent().size(); i++) {
				QuizInfoDto quizInfoDto = pageQuizInfoDto.getContent().get(i);
				quizInfoDto.setNum(num + i);
			}
		}
		
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

	/**
	 * 미 사용 퀴즈 조회
	 * 
	 * @param userSeq
	 * @param quizTypCd
	 * @return
	 */
	public TdQuiz getTodayQuiz(Long userSeq, QuizTypCd quizTypCd) {
		
		// 사용자가 풀지 않은 퀴즈 수
		int cnt = 0;
		int incre = 0;
		while(true) {
			String strSql = "SELECT COUNT(*) cnt"
					+ "  FROM TD_QUIZ A "
					+ " WHERE A.DEL_YN = 'N' "
					+ "   AND A.QUIZ_TYP_CD = ? "
					+ "   AND A.QUIZ_SEQ NOT IN ( "
					+ "       SELECT X.QUIZ_SEQ "
					+ "         FROM TD_QUIZ_WORK_USE X "
					+ "        WHERE X.DEL_YN = 'N' "
					+ "          AND X.USER_SEQ = ? "
					+ "        GROUP BY X.QUIZ_SEQ "
					+ "       HAVING COUNT(*) > ? "
					+ "   )";
			
		    cnt = jdbcTemplate.queryForObject(strSql, Integer.class, quizTypCd.name(), userSeq, incre);
		    
		    if(cnt > 0) {
		    	break;
		    }		    
			incre++; // 만약에 없다면... 한번씩 더 풀어 보게...
		}
			
		// 랜덤하게 하나...
		int pickedNum = (int)(Math.random() * cnt);
		
		// 하나 조회
		String sqlstr ="SELECT v.* "
				+ "  FROM (SELECT ROW_NUMBER() OVER(ORDER BY A.QUIZ_SEQ) NUM "
				+ "               ,A.QUIZ_SEQ, A.QUIZ_TYP_CD, A.QUIZ_TITL, A.QUIZ_CONT, A.QUIZ_ANSWER "
				+ "          FROM td_quiz A "
				+ "         WHERE A.DEL_YN = 'N' "
				+ "           AND A.QUIZ_TYP_CD = ? "
				+ "           AND A.QUIZ_SEQ NOT IN ( "
				+ "               SELECT X.QUIZ_SEQ "
				+ "                 FROM td_quiz_work_use X "
				+ "                WHERE X.DEL_YN = 'N' "
				+ "                  AND X.USER_SEQ = ? "
				+ "                GROUP BY X.QUIZ_SEQ "
				+ "               HAVING COUNT(*) > ? "
				+ "           ) "
				+ "       ) as v "
				+ " WHERE v.NUM = ?";
		
		TdQuiz tdQuiz = jdbcTemplate.queryForObject(sqlstr, 
				new RowMapper<TdQuiz>() {
						@Override
						public TdQuiz mapRow(ResultSet rs, int rowNum) throws SQLException{
							TdQuiz tdQuiz
							= new TdQuiz(
								 rs.getLong("QUIZ_SEQ")
								,QuizTypCd.valueOf(rs.getString("QUIZ_TYP_CD"))
								,rs.getString("QUIZ_TITL")
								,rs.getString("QUIZ_CONT")
								,rs.getString("QUIZ_ANSWER")
							);						
							return tdQuiz;
						}
					}, quizTypCd.name(), userSeq, incre, pickedNum);
		
		return tdQuiz;
	}
}
