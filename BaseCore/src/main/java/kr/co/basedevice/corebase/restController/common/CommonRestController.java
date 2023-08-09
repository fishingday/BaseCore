package kr.co.basedevice.corebase.restController.common;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmCdDtl;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.service.common.CommonService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonRestController {
	
	final private CommonService commonService;
	
	/**
	 * 역할 목록 조회
	 * 
	 * @return
	 */
	@GetMapping("/role_list.json")
	public ResponseEntity<List<CmRole>> getRoleList(){
		List<CmRole> cmRoleList = commonService.getCmRoleList();
		
		return ResponseEntity.ok(cmRoleList);
	}
	
	
	@GetMapping("/code_dtl_list.json")
	public ResponseEntity<List<CmCdDtl>> getCodeDtlList(String grpCd){
		List<CmCdDtl> cmCdDtlList = commonService.getCmCdDtlList(grpCd);
		
		return ResponseEntity.ok(cmCdDtlList);
	}
}