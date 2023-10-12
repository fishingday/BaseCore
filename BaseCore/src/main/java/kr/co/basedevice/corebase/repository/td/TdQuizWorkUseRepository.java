package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.td.TdQuizWorkUse;
import kr.co.basedevice.corebase.domain.td.TdQuizWorkUseId;

public interface TdQuizWorkUseRepository  extends JpaRepository<TdQuizWorkUse, TdQuizWorkUseId>{

}
