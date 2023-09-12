package kr.co.basedevice.corebase.restController.system;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmOrg;
import kr.co.basedevice.corebase.dto.system.OrgInfoDto;
import kr.co.basedevice.corebase.service.common.CommonService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/system/org_mgt")
@RequiredArgsConstructor
public class OrgMgtRestController {
	
	final private CommonService commonService;	

	/**
	 * 전체 조직 조회
	 * 
	 * @return
	 */
	@GetMapping("/get_org_list.json")
	public ResponseEntity<List<OrgInfoDto>> findByOrgList(){
		List<OrgInfoDto> orgInfoList = commonService.findAllOrg();		
		
		return ResponseEntity.ok(orgInfoList);
	}	
	
	/** 
	 * 부모 조직만 조회
	 * 
	 * @return
	 */
	@GetMapping("/get_parent_org_list.json")
	public ResponseEntity<List<OrgInfoDto>> findByParnetOrgList(){
		List<OrgInfoDto> orgInfoList = commonService.findByParentOrg();
		
		return ResponseEntity.ok(orgInfoList);
	}
	
	/**
	 * 조직 조회
	 * 
	 * @param orgSeq
	 * @return
	 */
	@GetMapping("/get_org.json")
	public ResponseEntity<OrgInfoDto> findByOrg(Long orgSeq){
		OrgInfoDto orgInfo = commonService.findOrg(orgSeq);
		
		return ResponseEntity.ok(orgInfo);
	}
	
	/** 
	 * 조직 저장	
	 * 
	 * @param cmOrg
	 * @return
	 */
	@PostMapping("/save_org.json")
	public ResponseEntity<CmOrg> saveCdGrp(CmOrg cmOrg){
		CmOrg saveCmOrg = commonService.saveCmOrg(cmOrg);
		
		return ResponseEntity.ok(saveCmOrg);
	}
	
	/** 
	 * 조직 삭제
	 * 
	 * @param orgSeq
	 * @return
	 */
	@DeleteMapping("/remove_org.json")
	public ResponseEntity<Boolean> removeOrg(Long orgSeq){
		boolean isRemove = commonService.removeCmOrg(orgSeq);
		
		return ResponseEntity.ok(isRemove);
	}
}
