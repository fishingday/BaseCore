package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.td.TdWorkerMap;
import kr.co.basedevice.corebase.domain.td.TdWorkerMapId;

public interface TdWorkerMapRepository extends JpaRepository<TdWorkerMap, TdWorkerMapId> {

}
