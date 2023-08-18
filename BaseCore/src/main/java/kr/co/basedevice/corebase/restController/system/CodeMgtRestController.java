package kr.co.basedevice.corebase.restController.system;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmCdDtl;
import kr.co.basedevice.corebase.domain.cm.CmCdDtlId;
import kr.co.basedevice.corebase.domain.cm.CmCdGrp;
import kr.co.basedevice.corebase.search.common.SearchGrpCd;
import kr.co.basedevice.corebase.search.system.SearchDtlCd;
import kr.co.basedevice.corebase.service.common.CommonService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/system/code_mgt")
@RequiredArgsConstructor
public class CodeMgtRestController {
		
	final private CommonService commonService;	

	/** 
	 * 코드 그룹 목록
	 * 
	 * @param SearchGrpCd 
	 * @return
	 */
	@GetMapping("/get_grpcd_list.json")
	public ResponseEntity<List<CmCdGrp>> findByCdGrpList(SearchGrpCd searchCodeGrp){		
		List<CmCdGrp> cmCdGrpList = commonService.findBySearch(searchCodeGrp);
		
		return ResponseEntity.ok(cmCdGrpList);
	}
	
	/**
	 *  코드 그룹 상세
	 * 
	 * @param grpCd
	 * @return
	 */
	@GetMapping("/get_grpcd.json")
	public ResponseEntity<CmCdGrp> getCmCdGrp(String grpCd){
		CmCdGrp cmCdGrp = commonService.findCmCdGrpById(grpCd);
		
		if(cmCdGrp != null) {
			return ResponseEntity.ok(cmCdGrp);
		}else{
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * 코드 그룹 추가
	 * 
	 * @param cmCdGrp
	 * @return
	 */
	@PostMapping("/save_grpcd.json")
	public ResponseEntity<CmCdGrp> saveCdGrp(CmCdGrp cmCdGrp){
		CmCdGrp saveCdGrp = commonService.saveCmCdGrp(cmCdGrp);
		
		return ResponseEntity.ok(saveCdGrp);
	}
	
	/**
	 * 코드 그룹 삭제
	 * 
	 * @param cdGrp
	 * @return
	 */
	@DeleteMapping("/remove_grpcd.json")
	public ResponseEntity<Boolean> removeCdGrp(String grpCd){
		boolean isRemove = commonService.removeCmCdGrp(grpCd);
		
		return ResponseEntity.ok(isRemove);
	}
		
	/**
	 * 코드 그룹별 코드 목록
	 * 
	 * @param grpCd
	 * @return
	 */
	@GetMapping("/get_code_list.json")
	public ResponseEntity<List<CmCdDtl>> findCmCdDtlByGrpCd(SearchDtlCd searchDtlCd){
		
		List<CmCdDtl> cmCdDtlList = commonService.getCmCdDtlList(searchDtlCd);
		
		return ResponseEntity.ok(cmCdDtlList);
	}
	
	/**
	 * 코드 상세
	 * 
	 * @param grpCd
	 * @param cd
	 * @return
	 */
	@GetMapping("/get_code.json")
	public ResponseEntity<CmCdDtl> getCmCdDtl(CmCdDtlId cmCdDtlId){
		CmCdDtl cmCdDtl = commonService.findCmCdDtlById(cmCdDtlId);
				
		return ResponseEntity.ok(cmCdDtl);
	}
	
	/** 
	 * 코드 추가
	 * 
	 * @param cmCdDtl
	 * @return
	 */
	@PostMapping("/save_code_json")
	public ResponseEntity<CmCdDtl> saveCmCdDtl(CmCdDtl cmCdDtl){		
		CmCdDtl saveCdDtl = commonService.saveCmCdDtl(cmCdDtl);
		
		return ResponseEntity.ok(saveCdDtl);
	}
	
	/** 
	 * 코드 삭제
	 * 
	 * @param grpCd
	 * @param cd
	 * @return
	 */
	@DeleteMapping("/remove_code.json")
	public ResponseEntity<Boolean> removeCmCdDtl(CmCdDtlId cmCdDtlId){
		
		boolean isRemove = commonService.removeCmCdDtl(cmCdDtlId);
		
		return ResponseEntity.ok(isRemove);
	}
}
