package kr.co.basedevice.corebase.restController.system;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmOrg;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.dto.system.ChooseUsersOrg;
import kr.co.basedevice.corebase.dto.system.OrgInfoDto;
import kr.co.basedevice.corebase.service.common.OrgService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/system/org_mgt")
@RequiredArgsConstructor
public class OrgMgtRestController {
	
	final private OrgService orgService;	

	/**
	 * 전체 조직 조회
	 * 
	 * @return
	 */
	@GetMapping("/get_org_list.json")
	public ResponseEntity<List<OrgInfoDto>> findByOrgList(){
		List<OrgInfoDto> orgInfoList = orgService.findAllOrg();		
		
		return ResponseEntity.ok(orgInfoList);
	}	
	
	/** 
	 * 부모 조직만 조회
	 * 
	 * @return
	 */
	@GetMapping("/get_parent_org_list.json")
	public ResponseEntity<List<OrgInfoDto>> findByParnetOrgList(){
		List<OrgInfoDto> orgInfoList = orgService.findByParentOrg();
		
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
		OrgInfoDto orgInfo = orgService.findOrg(orgSeq);
		
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
		CmOrg saveCmOrg = orgService.saveCmOrg(cmOrg);
		
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
		boolean isRemove = orgService.removeCmOrg(orgSeq);
		
		return ResponseEntity.ok(isRemove);
	}
	

	
	/**
	 * 소속 사용자 목록
	 * 
	 * @param orgSeq
	 * @return
	 */
	@GetMapping("/org_user_list.json")
	public ResponseEntity<List<CmUser>> findByUserList(Long orgSeq){		
		List<CmUser> cmUserList = null;
		
		if(orgSeq != null) {
			cmUserList = orgService.findByCmUser(orgSeq);
		}
		return ResponseEntity.ok(cmUserList);
	}
		
	/**
	 * 특정 소속 제외 사용자 목록
	 * 
	 * @param orgSeq
	 * @return
	 */
	@GetMapping("/exclude_user_list.json")
	public ResponseEntity<List<CmUser>> findByExcludeUserList(Long orgSeq){
		List<CmUser> cmUserList = null;
		
		if(orgSeq != null) {
			cmUserList = orgService.findByExcludeCmUser(orgSeq);
		}
		
		return ResponseEntity.ok(cmUserList);
	}
	
	/** 
	 * 소속 사용자 추가
	 * 
	 * @param chooseUsers
	 * @return
	 */
	@PutMapping("/add_users.json")
	public ResponseEntity<Integer> addUsers(ChooseUsersOrg chooseUsers){
		int applyCnt = orgService.addUsers(chooseUsers);
		
		return ResponseEntity.ok(applyCnt);
	}
	
	/** 
	 * 소속 사용자 제거
	 * 
	 * @param chooseUsers
	 * @return
	 */
	@DeleteMapping("/remove_users.json")
	public ResponseEntity<Integer> removeUsers(ChooseUsersOrg chooseUsers){
		int applyCnt = orgService.removeUsers(chooseUsers);
		
		return ResponseEntity.ok(applyCnt);
		
	}	
}
