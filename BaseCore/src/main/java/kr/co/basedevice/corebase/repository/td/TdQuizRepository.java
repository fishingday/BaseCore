package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.td.TdQuiz;
import kr.co.basedevice.corebase.repository.td.querydsl.TdQuizRepositoryQueryDsl;

public interface TdQuizRepository extends JpaRepository<TdQuiz, Long>, TdQuizRepositoryQueryDsl {


}
