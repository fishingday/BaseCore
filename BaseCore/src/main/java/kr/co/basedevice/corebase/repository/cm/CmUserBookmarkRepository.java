package kr.co.basedevice.corebase.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmUserBookmark;
import kr.co.basedevice.corebase.domain.cm.CmUserBookmarkId;


public interface CmUserBookmarkRepository extends JpaRepository<CmUserBookmark, CmUserBookmarkId>{

}
