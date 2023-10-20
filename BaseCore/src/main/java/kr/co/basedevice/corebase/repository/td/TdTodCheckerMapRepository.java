package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.td.TdCheckerMap;
import kr.co.basedevice.corebase.domain.td.TdCheckerMapId;

public interface TdTodCheckerMapRepository extends JpaRepository<TdCheckerMap, TdCheckerMapId> {

}
