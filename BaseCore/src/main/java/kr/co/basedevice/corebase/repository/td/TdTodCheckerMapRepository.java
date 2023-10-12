package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.td.TdTodCheckerMap;
import kr.co.basedevice.corebase.domain.td.TdTodCheckerMapId;

public interface TdTodCheckerMapRepository extends JpaRepository<TdTodCheckerMap, TdTodCheckerMapId> {

}
