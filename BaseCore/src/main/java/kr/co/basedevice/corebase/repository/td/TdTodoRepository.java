package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.td.TdTodo;

public interface TdTodoRepository extends JpaRepository<TdTodo, Long> {

}
