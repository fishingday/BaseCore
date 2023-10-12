package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMap;
import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMapId;
import kr.co.basedevice.corebase.domain.td.TdTodoSetleDtl;
import kr.co.basedevice.corebase.domain.td.TdTodoSetleDtlId;

public interface TdTodoSetleDtlRepository extends JpaRepository<TdTodoSetleDtl, TdTodoSetleDtlId> {
}
