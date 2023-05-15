<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"   prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" 	prefix="sec"  %>
<sec:authentication var="user" property="principal" />
<!-- p_IM left_lnb 공통영역 -->
<div class="snbWrap" id="snb">
    <div class="snbTop">
        <c:set var="top" value="${user.selectedTopMenu}"/>
        <div><a class="${top.treeIconVal}"><s:message code='${top.mlangCdNm}' text="${top.menuNm }"/></a></div>
    </div>
    
    <ul class="snb">
	<c:forEach var="menu" items="${user.selectedTopMenu.childMenuMngts}">
		<c:if test="${menu.screnViewYn eq 'Y'}">
		<li onclick="$.fn.clearLocalStorageItemThatBySubKey('-search--')" <c:if test="${menu.menuMngtSeq eq user.selectedCurrMenu.menuMngtSeq || menu.menuMngtSeq eq user.selectedCurrMenu.upMenuMngtSeq}">class="on"</c:if>>
			<a class="${menu.menuImgUrl}" title="<s:message code="${menu.mlangCdNm}" text="${menu.menuNm}"/>" href="<c:choose><c:when test="${!empty menu.menuUrl}"><c:url value="${menu.menuUrl}"/></c:when><c:when test="${!empty menu.childMenuMngts}"><c:url value="${menu.childMenuMngts[0].menuUrl}"/></c:when></c:choose>">
				<c:if test="${menu.menuMngtSeq ne user.selectedCurrMenu.menuMngtSeq}"><s:message code="${menu.mlangCdNm}"  text="${menu.menuNm}"/></c:if>
				<c:if test="${menu.menuMngtSeq eq user.selectedCurrMenu.menuMngtSeq}"><span style="font-weight:bold;"><s:message code="${menu.mlangCdNm}" text="${menu.menuNm}"/></span></c:if>
			</a>
			<c:if test="${!empty menu.childMenuMngts}">
				<ul class="snb3depWrap">
				<c:forEach var="menu3" items="${menu.childMenuMngts}">
				<li onclick="$.fn.clearLocalStorageItemThatBySubKey('-search--')" <c:if test="${menu3.menuMngtSeq eq user.selectedCurrMenu.menuMngtSeq}">class="on"</c:if>>
					<a class="${menu3.menuImgUrl}" title="<s:message code="${menu3.mlangCdNm}" text="${menu3.menuNm}"/>" href="<c:url value="${menu3.menuUrl}"/>">
					<c:if test="${menu3.menuMngtSeq ne user.selectedCurrMenu.menuMngtSeq}"><s:message code="${menu3.mlangCdNm}"  text="${menu3.menuNm}"/></c:if>
					<c:if test="${menu3.menuMngtSeq eq user.selectedCurrMenu.menuMngtSeq}"><span style="font-weight:bold;"><s:message code="${menu3.mlangCdNm}" text="${menu3.menuNm}"/></span></c:if>
					</a>
				</li>
				</c:forEach>
				</ul>
			</c:if>
		</li>
		</c:if>
	</c:forEach>
	</ul>    
</div>