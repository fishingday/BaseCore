package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.td.TdCheckerWorkerMap;
import kr.co.basedevice.corebase.domain.td.TdCheckerWorkerMapId;

public interface TdCheckerWorkerMapRepository extends JpaRepository<TdCheckerWorkerMap, TdCheckerWorkerMapId> {

}
