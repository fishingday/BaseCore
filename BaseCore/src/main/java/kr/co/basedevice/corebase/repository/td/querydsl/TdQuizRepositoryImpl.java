package kr.co.basedevice.corebase.repository.td.querydsl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.basedevice.corebase.domain.cm.QCmUser;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.QTdQuiz;
import kr.co.basedevice.corebase.domain.td.QTdQuizUserMap;
import kr.co.basedevice.corebase.domain.td.QTdQuizWorkUse;
import kr.co.basedevice.corebase.domain.td.QTdWork;
import kr.co.basedevice.corebase.dto.todo.QuizInfoDto;
import kr.co.basedevice.corebase.dto.todo.QuizUserInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkQuizInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchQuiz;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TdQuizRepositoryImpl implements TdQuizRepositoryQueryDsl{
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<QuizInfoDto> findByQuizInfo(SearchQuiz searchQuiz, Pageable page) {
		QTdQuiz tdQuiz = QTdQuiz.tdQuiz;
		QTdQuizUserMap tdQuizUserMap = QTdQuizUserMap.tdQuizUserMap;
		QTdQuizWorkUse tdQuizWorkUse = QTdQuizWorkUse.tdQuizWorkUse;
		
		JPQLQuery<QuizInfoDto> query = jpaQueryFactory.select(
				Projections.bean(QuizInfoDto.class,
					 tdQuiz.quizSeq
					,tdQuiz.quizTypCd
					,tdQuiz.quizTitl
					,tdQuiz.quizCont
					,tdQuiz.quizAnswer
				)
			)
			.from(tdQuiz);
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(tdQuiz.delYn.eq(Yn.N));
		
		if(!ObjectUtils.isEmpty(searchQuiz.getQuizTypCd())) {
			builder.and(tdQuiz.quizTypCd.eq(searchQuiz.getQuizTypCd()));
		}

		if(!ObjectUtils.isEmpty(searchQuiz.getQuizTitl())) {
			builder.and(tdQuiz.quizTitl.contains(searchQuiz.getQuizTitl()));			
		}
		
		if(!ObjectUtils.isEmpty(searchQuiz.getQuizCont())) {
			builder.and(tdQuiz.quizCont.contains(searchQuiz.getQuizCont()));
		}
		
		if(!ObjectUtils.isEmpty(searchQuiz.getQuizAnswer())) {
			builder.and(tdQuiz.quizAnswer.contains(searchQuiz.getQuizAnswer()));			
		}
		
		if(!ObjectUtils.isEmpty(searchQuiz.getQuizUserSeq())) { // 퀴즈 참여자
			JPQLQuery <Long> subQuery = 
		    		  JPAExpressions.select(Projections.bean(Long.class, tdQuizUserMap.quizSeq))
		    	      .from(tdQuizUserMap)
		    	      .where(tdQuizUserMap.delYn.eq(Yn.N), tdQuizUserMap.userSeq.in(searchQuiz.getQuizUserSeq()));			    	      
			builder.and(tdQuiz.quizSeq.in(subQuery));
		}
		
		if(!ObjectUtils.isEmpty(searchQuiz.getUseQuizYn())) {
			if(Yn.Y.equals(searchQuiz.getUseQuizYn())) { // 사용된 퀴즈
				JPQLQuery <Long> subQuery = 
			    		  JPAExpressions.select(Projections.bean(Long.class, tdQuizWorkUse.quizSeq))
			    	      .from(tdQuizWorkUse)
			    	      .where(tdQuizWorkUse.delYn.eq(Yn.N));			    	      
				builder.and(tdQuiz.quizSeq.in(subQuery));
			}else { // 미사용 퀴즈
				JPQLQuery <Long> subQuery = 
			    		  JPAExpressions.select(Projections.bean(Long.class, tdQuizWorkUse.quizSeq))
			    	      .from(tdQuizWorkUse)
			    	      .where(tdQuizWorkUse.delYn.eq(Yn.N));			    	      
				builder.and(tdQuiz.quizSeq.notIn(subQuery));
			}
		}
		
		query.where(builder);
		
		if(!ObjectUtils.isEmpty(searchQuiz.getOrder()) && !ObjectUtils.isEmpty(searchQuiz.getSort())) {
	    	Order direction = Order.valueOf(searchQuiz.getOrder().toUpperCase());	    	
	        if(searchQuiz.getSort().equals("quizTitl")) {
		        query.orderBy(new OrderSpecifier<>(direction, tdQuiz.quizTitl));
	        }else{
	        	query.orderBy(tdQuiz.quizSeq.desc());
	        }
		}else {
        	query.orderBy(tdQuiz.quizSeq.desc());
		}
		
		QueryResults<QuizInfoDto> queryResults = query.limit(page.getPageSize()).offset(page.getOffset()).fetchResults();
		return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
	}

	@Override
	public List<QuizUserInfoDto> findByQuizUserInfoList(Long quizSeq) {
		QTdQuizUserMap quizUserMap = QTdQuizUserMap.tdQuizUserMap;
		QCmUser cmUser = QCmUser.cmUser;
		
		JPQLQuery<QuizUserInfoDto> query = jpaQueryFactory.select(
				Projections.bean(QuizUserInfoDto.class,
					 quizUserMap.quizUserSeq
					,quizUserMap.quizSeq
					,cmUser.userSeq
					,cmUser.loginId
					,cmUser.userNm
					,quizUserMap.userAnswer
					,quizUserMap.answerCnt
					,quizUserMap.sucesYn
				)
			)
			.from(quizUserMap).innerJoin(cmUser).on(quizUserMap.userSeq.eq(cmUser.userSeq));
		
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(quizUserMap.delYn.eq(Yn.N));
		builder.and(cmUser.delYn.eq(Yn.N));
		builder.and(quizUserMap.quizSeq.eq(quizSeq));
		
		return query.where(builder).fetch();
	}

	@Override
	public List<WorkQuizInfoDto> findByWorkQuizInfoList(Long quizSeq) {
		QTdQuizWorkUse quizWorkUse = QTdQuizWorkUse.tdQuizWorkUse;
		QTdWork tdWork = QTdWork.tdWork;
		QCmUser cmUser = QCmUser.cmUser;
				
		JPQLQuery<WorkQuizInfoDto> query = jpaQueryFactory.select(
				Projections.bean(WorkQuizInfoDto.class,
					 quizWorkUse.quizSeq
					,quizWorkUse.workSeq
					
					,tdWork.workSeq
					,tdWork.workTitl
					,tdWork.workCont
					,tdWork.workDt
					,tdWork.workStatCd
					,tdWork.gainPoint
										
					,tdWork.workerSeq
					,cmUser.loginId
					,cmUser.userNm
				)
			)
			.from(quizWorkUse)
			.innerJoin(tdWork).on(quizWorkUse.workSeq.eq(tdWork.workSeq))
			.innerJoin(cmUser).on(tdWork.workerSeq.eq(cmUser.userSeq));

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(quizWorkUse.delYn.eq(Yn.N));
		builder.and(tdWork.delYn.eq(Yn.N));
		builder.and(cmUser.delYn.eq(Yn.N));
		builder.and(quizWorkUse.quizSeq.eq(quizSeq));
		
		return query.where(builder).fetch();
	}
}
