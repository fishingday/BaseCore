package kr.co.basedevice.corebase.restController.system;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmCdGrp;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.search.common.SearchCodeGrp;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.CommonService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/system/code_mgt")
@RequiredArgsConstructor
public class CodeMgtRestController {
		
	private CommonService commonService;
	

	/** 
	 * 코드 그룹 목록
	 * 
	 * @param SearchCodeGrp 
	 * @return
	 */
	@GetMapping("/cd_grp_list.json")
	public ResponseEntity<List<CmCdGrp>> findByCdGrpList(SearchCodeGrp searchCodeGrp){		
		List<CmCdGrp> cmCdGrpList = commonService.findBySearch(searchCodeGrp);		
		
		return ResponseEntity.ok(cmCdGrpList);
	}
	
	// 코드 그룹 상세
	public ResponseEntity<CmCdGrp> getCdGrp(String grpCd){
		CmCdGrp cmCdGrp = commonService.findCmCdGrpById(grpCd);
		
		if(cmCdGrp != null) {
			return ResponseEntity.ok(cmCdGrp);
		}else{
			return ResponseEntity.notFound().build();
		}
	}
	
	// 코드 그룹 추가
	@PostMapping("/save_cd_grp.json")
	public ResponseEntity<CmCdGrp> saveCdGrp(CmCdGrp cmCdGrp){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();

		CmCdGrp saveCdGrp = commonService.saveCmCdGrp(cmCdGrp, cmUser.getUserSeq());
		
		return ResponseEntity.ok(saveCdGrp);
	}
	
	// 코드 그룹 삭제
	@DeleteMapping("/remove_cd_grp.json")
	public ResponseEntity<Boolean> removeCdGrp(String cdGrp){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();

		boolean isRemove = commonService.removeCmCdGrp(cdGrp, cmUser.getUserSeq());
		
		return ResponseEntity.ok(isRemove);
	}
		
	// 코드 그룹별 코드 목록
	
	// 코드 상세
	
	// 코드 추가
	
	// 코드 삭제
	
	
}
