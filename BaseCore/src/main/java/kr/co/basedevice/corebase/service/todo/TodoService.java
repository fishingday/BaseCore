package kr.co.basedevice.corebase.service.todo;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TodoService {
	
	final private TodoService todoService;

}
