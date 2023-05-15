<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 				prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" 				prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" 			prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" 	prefix="sec"  %>
<!--  p_IM header 공통영역 -->
	<sec:authentication var="user" property="principal" />
    <div class="headerWrap">
        <div class="header">
			<a href="<c:url value='${user.firstMenu.menuUrl}'/>" target="_self"><h1 class="headerLogo"><s:message code="tiles.header.title" text="CIMS-EVM"/></h1></a>
            <div style="margin-left: 210px; padding-top: 20px; color: #000;">남은 세션 시간<b><span id="sessionTimer" style="margin-left: 10px;">00:00</span></b><span style="margin-left: 10px; text-decoration: underline; cursor:pointer;" onclick="extendSession()">세션 연장</span></div>
            <ul class="gnb">
                <li>ㄴㅇㄻㄴㄻㄴㄹㄴㅁ
                    <button class="AXButton Green" onclick="location.href='https://cims.mnd.mil/bigfile/filedown/CIMS_installer.ls';">다운로드(전체)</button>
                    <button class="AXButton Blue" onclick="location.href='https://cims.mnd.mil/bigfile/filedown/CIMS_patcher.ls';">다운로드(패치)</button>
                </li>
                <li class=""><fmt:formatDate value="${user.comUserMngt.loginDt}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></li>
                <li class=""><i class="fa fa-star" id="bookmarkBtn" aria-hidden="true"></i></li>
                <li class="user_select_wrap">
                	<a href="#" title="user" class="userSelect">
                		<span class="user">${user.comUserMngt.userNm}</span>
                        <span class="badge1" id="notiBadge" style="display:none"></span>
                	</a>
                    <div class="user_select_box" style="display:none">
                        <ul class="user_select_box_inner">
                            <c:forEach var="authGrp" items="${user.comAuthGrpMngts}">
                            <c:if test="${user.currAuthGrpMngt.authGrpMngtSeq eq authGrp.authGrpMngtSeq}"><li><a href="#" class="manager"><b>${authGrp.authGrpNm}*</b></a></li></c:if>
                            <c:if test="${user.currAuthGrpMngt.authGrpMngtSeq ne authGrp.authGrpMngtSeq}"><li><a href="javascript:goChangeAuth(${authGrp.authGrpMngtSeq});" class="operator">${authGrp.authGrpNm}</a></li></c:if>
                            </c:forEach>
                            <li style="border-top:1px solid #cccccc;"><a href="<c:url value="/common/NotiReceivedMngt/init.ls"/>" title="업무 알림" class="notice"><s:message code="tiles.header.notiInfo" text="업무 알림"/><span class="badge2" id="notiBadge" style="display:none"></span></a></li>
                            <%-- <li style="border-top:1px solid #cccccc;"><a href="<c:url value="/common/UserInfo/init.ls"/>" title="개인정보 변경" class="privacy"><s:message code="tiles.header.editUserInfo" text="개인정보 변경"/></a></li> --%>
                            <%-- <li style="border-top:1px solid #cccccc;"><a href="javascript:popupChgPwd();" title="패스워드변경" class="password"><s:message code="tiles.header.changePasswd" text="패스워드변경"/></a></li> --%>
                            <li style="border-top:1px solid #cccccc;"><a href="<c:url value="/login/logout.ls"/>" title="로그아웃" class="logout"><s:message code="tiles.header.logout" text="로그아웃"/></a>
                        </ul>
                    </div>
                </li>
                <!-- 
                <li class="lang_select_wrap"><a href="#" title="language" class="userSelect"><span class="">Language(<c:choose>
                	<c:when test="${locale.language eq 'en'}"><s:message code="tiles.locale.language.en" text="ENGLISH"/></c:when>
                	<c:when test="${locale.language eq 'zh'}"><s:message code="tiles.locale.language.zh" text="CHINESE"/></c:when>
                	<c:when test="${locale.language eq 'ko'}"><s:message code="tiles.locale.language.ko" text="KOREAN"/></c:when>
                	<c:otherwise><s:message code="tiles.locale.language.ko" text="KOREAN"/></c:otherwise>
                	</c:choose>)</span></a>
                    <div class="lang_select_box" style="display: none">
                        <ul class="lang_select_box_inner">
                           <li class="" style=""><a onclick="changeLocale('ko')" title="<s:message code="tiles.locale.language.ko" text="KOREAN"/>" class="ko"><s:message code="tiles.locale.language.ko" text="KOREAN"/></a></li>
                           <li class="" style="border-top:1px solid #cccccc;"><a onclick="changeLocale('zh')" title="<s:message code="tiles.locale.language.zh" text="CHINESE"/>" class="zh"><s:message code="tiles.locale.language.zh" text="CHINESE"/></a></li>
                           <li class="" style="border-top:1px solid #cccccc;"><a onclick="changeLocale('en')" title="<s:message code="tiles.locale.language.en" text="ENGLISH"/>" class="en"><s:message code="tiles.locale.language.en" text="ENGLISH"/></a></li>                           
                        </ul>
                    </div>
                </li>
                 -->
                <li class=""><a href="<c:url value="${user.firstMenu.menuUrl}"/>" title="HOME" class="home">HOME</a></li>
            </ul>
            
            <!-- 즐겨찾기 팝업 -->
            <div class="bookmarkDiv">
                <h2>즐겨찾기 메뉴 추가</h2>
                <div class="headerNaviWrap">
	                <div class="headerNavi" style="float: left;"> 
						<c:if test="${!empty user.selectedCurrMenu.upMenuMngt}">
							<c:if test="${!empty user.selectedCurrMenu.upMenuMngt.upMenuMngt}">
								<s:message code="${user.selectedCurrMenu.upMenuMngt.upMenuMngt.mlangCdNm}" text="${user.selectedCurrMenu.upMenuMngt.upMenuMngt.menuNm}" />&gt; 
							</c:if>
							<s:message code="${user.selectedCurrMenu.upMenuMngt.mlangCdNm}" text="${user.selectedCurrMenu.upMenuMngt.menuNm}" />&gt;
						</c:if> 
						<s:message code="${user.selectedCurrMenu.mlangCdNm}" text="${user.selectedCurrMenu.menuNm}" /> 
		            </div>		            
		            <div class="addBtnNavi">
		                <a class="AXButton Blue" id="addBookmarkBtn" href="javascript:bookmark.add(${user.selectedCurrMenu.menuMngtSeq})"><i class="fa fa-plus" aria-hidden="true"></i>추가</a>
		            </div>
	            </div>
	            <div class="contentNaviWrap">
	            	<ul></ul>
	            </div>
            </div>
		<!-- 즐겨찾기 팝업 -->
        </div>     
        <div class="lnbWrap">
            <h2 class="headerName">Omni-VM</h2>
			  <ul>
			  	<c:forEach var="menu" items="${user.topMenuList}">
			  	  	<c:if test="${menu.screnViewYn eq 'Y' and menu.menuNm ne 'DashBoard'}">
					<li onclick="$.fn.clearLocalStorageItemThatBySubKey('-search--')" <c:if test="${(empty param.bbsPatnCd && menu.menuMngtSeq eq user.selectedCurrMenu.upMenuMngtSeq) 
                        || (empty param.bbsPatnCd &&  menu.menuMngtSeq eq user.selectedCurrMenu.upMenuMngt.upMenuMngtSeq) 
                        || (param.bbsPatnCd eq 'NOTICE' && menu.menuMngtSeq eq 6001)
                        || (param.bbsPatnCd eq 'QNA' && menu.menuMngtSeq eq 6002)
                        || (param.bbsPatnCd eq 'DATAROOM' && menu.menuMngtSeq eq 6003)                        
                        }">class="on"</c:if>>
						<a href="<c:choose>
							<c:when test="${!empty menu.menuUrl}"><c:url value="${menu.menuUrl}"/></c:when>
							<c:when test="${!empty menu.childMenuMngts[0].menuUrl}"><c:url value="${menu.childMenuMngts[0].menuUrl}"/></c:when>
							<c:when test="${!empty menu.childMenuMngts[0].childMenuMngts[0].menuUrl}"><c:url value="${menu.childMenuMngts[0].childMenuMngts[0].menuUrl}"/></c:when>
						</c:choose>" class="${menu.treeIconVal}"><s:message code='${menu.mlangCdNm}' text="${menu.menuNm }"/></a>
					</li>
					</c:if>
				</c:forEach>
			  </ul>			      
        </div>
    </div>
    
<%@ include file="/resources/js/sessionTimeout.jsp" %>
<script type="text/javascript">
	var pwdModal = new AXModal();

	<%-- 패스워드를 변경한다. --%>
	function popupChgPwd(){
		pwdModal.open({
			url: '<c:url value="/common/UserInfo/popupUserPasswdChg.ls"/>',
			pars: { <c:out value="${_csrf.parameterName}"/> : '<c:out value="${_csrf.token}"/>' },
			top:20,
			width:400,
			height:300
		});	
	}

	<%-- 권한을 변경한다. --%>
	function goChangeAuth(vAuthGrpMngtSeq){
		location.href = '<c:url value="/changeAuthGrp.ls"/>?authGrpMngtSeq=' + vAuthGrpMngtSeq;
	}
	
	<%-- 언어를 변경한다. --%>
	function changeLocale(language) {
		location.href = '<c:url value="/login/changeLocale.ls"/>?language=' +language;
	}
	
	<%-- 공지를 닫는다. --%>
	function noticeComfirm(vNotiMngtSeq){
		$.post('<c:url value="/common/NotiReceivedMngt/closeNotiUserMap.json"/>',
			{ 
				notiMngtSeq				: vNotiMngtSeq,
				<c:out value="${_csrf.parameterName}"/> : '<c:out value="${_csrf.token}"/>'
			},
			function(data){
				<%-- 읽기 처리는 했지만 할 일은 없다. --%>
			}
		);	
	}
	
	<%-- 공통 오류 표시기 --%>
	function commonAjaxErrorDialog(data){
		if(data.isError != undefined && data.isError == 'true' ){
			dialog.push({body:'<b><s:message code="framework.common.error.defaultMsg" text="장애가 발견 되었습니다.\n 관리자에게 문의하세요."/></b>\nMessage : ' + data.errorMsg, type:'Caution'});
			return true;
		}else{
			return false;
		}	
	}
	
	<%-- ajax 요청 실패시 경고 메세지 --%>
	function commonAjaxRequestFailDialog(data){
		if(data.isError != undefined && data.isError =='true'){
			// 객체 잘못 지정으로 수정 text -> data
			dialog.push({body:'<b><s:message code="framework.common.error.defaultMsg" text="장애가 발견 되었습니다.\n 관리자에게 문의하세요."/></b>\nMessage : ' + data.errorMsg, type:'Caution'});
			return true;
		}else{
			try{
				var text = $.parseJSON(data);
				if(text.isError != undefined && text.isError == 'true' ){
					dialog.push({body:'<b><s:message code="framework.common.error.defaultMsg" text="장애가 발견 되었습니다.\n 관리자에게 문의하세요."/></b>\nMessage : ' + text.errorMsg, type:'Caution'});
					return;
				}else{
					dialog.push({body:'<b><s:message code="framework.common.error.defaultMsg" text="장애가 발견 되었습니다.\n 관리자에게 문의하세요."/></b>', type:'Caution'});
					return false;
				}
			}catch(e){
				console.log("response data is not json.");
				dialog.push({body:'<b><s:message code="framework.common.error.defaultMsg" text="장애가 발견 되었습니다.\n 관리자에게 문의하세요."/></b>', type:'Caution'});
				return;
			}	
		} 
	}
	
	function notificationCallback(notification){
    	if(notification.notificationDTO == '' || notification.notificationDTO.cnt == -1){    		
    		if($("#basicDialog div").length == 0){
        		dialog.push({
    			    body:'<s:message code="tiles.header.sessionout.forceLogout" text="<b>접속이 종료되었습니다.<b>창을 닫습니다."/>'
    			    , top:0, type:'Caution', buttons:[
                        {buttonValue:'<s:message code="tiles.header.sessionout.closeWindow.y" text="예"/>', buttonClass:'Red', 
                        	onClick:function(){
                        		if(navigator.appVersion.indexOf("MSIE 6.0")>=0) { 
    		        		        parent.window.close(); 
    		        		    }else { 
    		        		        parent.window.open('about:blank','_self').close(); 	    
    		        		    }
    						}
                        	
                        },
                        {buttonValue:'<s:message code="tiles.header.sessionout.closeWindow.n" text="아니오"/>', buttonClass:'Blue', 
                        	onClick:function(){
                        		location.href = '<c:url value="/login/loginForm.ls"/>';
    						}                                	
                        }
    			    ]});    			
    		}
    		clearInterval(noticeIntervalId);
    	}else{
    		if(notification.notificationDTO.cnt > 0){
    			$("span[id=notiBadge]").html(notification.notificationDTO.cnt);
    			$("span[id=notiBadge]").show();
    		}else{
    			$("span[id=notiBadge]").hide();
    		}
    		
    		if(Object.keys(notification.notificationDTO.noticeMap).length > 0){
                $.getJSON(
                        url="<c:url value='/notification/toastNotice.json'/>",
                        {notiMngtSeq : -1023},
                        function(notce){
                            var vTitle = '<b>새로운 알림</b>\n';
                            var vBody = '새로운 알림이 있습니다.';
                            var vType = 'Complete';
                            var vData = -1023;                                            
                            toast.push(
                                    {
                                        body:"읽지않은 알림이 " + notification.notificationDTO.cnt + "건 있습니다. <a style='text-decoration: underline; color: blue;' href='<c:url value="/common/NotiReceivedMngt/init.ls"/>'><b>바로가기</b></a>", 
                                        type:vType,
                                        onConfirm:function(){
                                        }, 
                                        data:vData
                                    }
                                );
                        }
                    );
                
                for(var i = 0; i < Object.keys(notification.notificationDTO.noticeMap).length; i++){
                    $.getJSON(
                        url="<c:url value='/notification/toastNotice.json'/>",
                        {notiMngtSeq : Object.keys(notification.notificationDTO.noticeMap)[i]},
                        function(notce){
                            if(notce.noticeDTO){
                                //noticeComfirm(notce.noticeDTO.notiMngtSeq);
                            }
                        }
                    );
                }
            }
    		
    	}
    };    
    var bookmark = {
    	add : function(menuMngtSeq){
    		$.getJSON(
                url="<c:url value='/common/bookmark/add.json'/>",
                {"menuMngtSeq" : menuMngtSeq},
                function(data){
                    if(data){
                    	bookmark.loading();
                    }
                }
            );
    	},
    	remove : function(menuMngtSeq){
    		$.getJSON(
                    url="<c:url value='/common/bookmark/remove.json'/>",
                    {"menuMngtSeq" : menuMngtSeq},
                    function(data){
                        if(data){
                            bookmark.loading();
                        }
                    }
                );
    	},
    	loading: function(){
    		$.getJSON(
                url="<c:url value='/common/bookmark/loading.json'/>",
                {},
                function(data){
                	$(".contentNaviWrap ul li").remove();                	
                    if(data.comMenuMngtList.length){
                    	for(var i = 0; i < data.comMenuMngtList.length; i++){
                    		// <li><i class="fa fa-align-justify" aria-hidden="true"></i><span>체계등록</span><a href="javascript:bookmark.remove(1)"><i class="fa fa-minus-square floatRight" aria-hidden="true" style="font-size: 20px;"></i></a></li>
                    		$(".contentNaviWrap ul").append('<li><a href="'+ data.comMenuMngtList[i].menuUrl +'"><i class="fa fa-bookmark" aria-hidden="true"></i><span>' + data.comMenuMngtList[i].menuNm + '</span></a><a href="javascript:bookmark.remove(' + data.comMenuMngtList[i].menuMngtSeq + ')"><i class="fa fa-minus-square floatRight" aria-hidden="true" style="font-size: 20px;"></i></a></li>');
                    	}
                    }else{
                    	$(".contentNaviWrap ul").append('<li>즐겨찾기 목록이 비었습니다.</li>')
                    }
                }
            );
    	}
    }
	
	$(document).ready(function(){
		
		// header_gnb 권한설정
		$(".user_select_wrap").click(function(){
			$(".user_select_box").slideToggle(100);
		});

		// header_lang 다국어 설정
		$(".lang_select_wrap").click(function(){
			$(".lang_select_box").slideToggle(100);
		});
		
		$("#bookmarkBtn").click(function(){
			$(".bookmarkDiv").toggle();
		})
				
		bookmark.loading();
		
		// ajax headr 설정 - ajax 요청시 csrf을 http 헤더에 항상 넣는다
		var header = $("meta[name='_csrf_header']").attr("content");
		var token =	$("meta[name='_csrf']").attr("content");
		var data = {};
		data[header] = token
		$.ajaxSetup({
			headers : data
		}); 
		
		<%-- 처음 한 번은 새 메세지를 확인한다. --%>
		$.getJSON(url="<c:url value='/notification/checkSessionAndNotify.json'/>", notificationCallback ); 
		
		<%-- 이후 세션에 새로운 메시지가 있는지 확인한다. --%>
		var noticeIntervalId = setInterval(function(){
			$.getJSON(
		        url="<c:url value='/notification/checkSessionAndNotify.json'/>",
		        notificationCallback
		    )}, 60*1000);
	});
</script>
<!--  //p_IM header 공통영역 -->