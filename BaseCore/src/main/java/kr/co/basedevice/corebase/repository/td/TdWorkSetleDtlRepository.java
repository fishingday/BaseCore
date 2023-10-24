package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.td.TdWorkSetleMap;
import kr.co.basedevice.corebase.domain.td.TdWorkSetleMapId;

public interface TdWorkSetleDtlRepository extends JpaRepository<TdWorkSetleMap, TdWorkSetleMapId> {
}
