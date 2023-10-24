package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.td.TdSetle;
import kr.co.basedevice.corebase.repository.td.querydsl.TdSetleRepositoryQueryDsl;

public interface TdSetleRepository extends JpaRepository<TdSetle, Long>, TdSetleRepositoryQueryDsl{

}
