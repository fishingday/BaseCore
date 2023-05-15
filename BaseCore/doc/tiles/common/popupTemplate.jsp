<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"   prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"  prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" 	prefix="sec"  %>
<sec:authentication var="user" property="principal" />
<!DOCTYPE html>
<html>
	<head>
		<title>CIMS-EVM</title>	
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1.0, minimum-scale=1" />
		<meta http-equiv="Expires" content="-1"> 
		<meta http-equiv="Pragma" content="no-cache"> 
		<meta http-equiv="Cache-Control" content="No-Cache">
        <meta name="format-detection" content="telephone=no" />
        
        <!-- csrf meta data -->
        <meta name="_csrf_header" content="<c:out value="${_csrf.headerName}"/>" />
        <meta name="_csrf" content="<c:out value="${_csrf.token}"/>"/>
        	
		<link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico"/>"/>
		
		<!-- css block -->
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/js/axisj/ui/arongi/AXJ.min.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/sumoselect.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/opnetDeco.css"/>" />
		
		<!-- js block -->
		<!-- script type="text/javascript" src="<c:url value="/resources/js/axisj/jquery/jquery.min.js"/>"></script -->
		<script type="text/javascript" src="<c:url value="/resources/js/jquery-2.2.4.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/timer/jquery.timer.js"/>"></script> 
		<script type="text/javascript" src="<c:url value="/resources/js/axisj/dist/AXJ.all.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/validator-min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/framework.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/json2.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/jquery.form.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/jquery.mask.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/jquery.placeholder.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/jquery.sumoselect.min.js"/>"></script>	<%-- sumoSelect관련 --%>
		<script type="text/javascript" src="<c:url value="/resources/js/Highcharts/js/highcharts.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/Highcharts/js/themes/grid-light.js"/>"></script><%-- Chart Color --%>
		<script type="text/javascript" src="<c:url value="/resources/js/Highcharts/js/modules/exporting.js"/>"></script>	
		<script type="text/javascript" src="<c:url value="/resources/js/Highcharts/js/highcharts-more.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/js/Highcharts/js/modules/solid-gauge.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/axisj/lib/AXProgress.js"/>"></script>

		<script type="text/javascript">
			var pageID = "LSWareWebPage";
			var globalPageInLoading = "<s:message code='tiles.axisj.globalPageInLoading' text="페이지를 로딩 중입니다. 잠시만 기다려 주세요."/>";
			
			$(document).ready(function(){
				Date.prototype.yyyymmddhhmi = function() {
					var yyyy = this.getFullYear().toString();
					var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
					var dd  = this.getDate().toString();
					var hh = this.getHours().toString();
					var mi = this.getMinutes().toString();
					return yyyy + "-" + (mm[1]?mm:"0"+mm[0]) + "-" + (dd[1]?dd:"0"+dd[0]) + " " + (hh[1]?hh:"0"+hh[0]) + ":" + (mi[1]?mi:"0"+mi[0]); // padding
				};
				
				Date.prototype.yyyymmdd = function() {
					var yyyy = this.getFullYear().toString();
					var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
					var dd  = this.getDate().toString();
					return yyyy + "-" + (mm[1]?mm:"0"+mm[0]) + "-" + (dd[1]?dd:"0"+dd[0]); // padding
				};	
			});
		</script>	
	</head>
	<body>
		<div class="bodyHeightDiv">
			<tiles:insertAttribute name="content" />
		</div>
		<script type="text/javascript">
			var pageID = "AXModal";
			var globalPageInLoading = "<s:message code='tiles.axisj.globalPageInLoading' text="페이지를 로딩 중입니다. 잠시만 기다려 주세요."/>";
			$(document).ready(function(){
				Date.prototype.yyyymmddhhmi = function() {
					var yyyy = this.getFullYear().toString();
					var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
					var dd  = this.getDate().toString();
					var hh = this.getHours().toString();
					var mi = this.getMinutes().toString();
					return yyyy + "-" + (mm[1]?mm:"0"+mm[0]) + "-" + (dd[1]?dd:"0"+dd[0]) + " " + (hh[1]?hh:"0"+hh[0]) + ":" + (mi[1]?mi:"0"+mi[0]); // padding
				};
				
				Date.prototype.yyyymmdd = function() {
					var yyyy = this.getFullYear().toString();
					var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
					var dd  = this.getDate().toString();
					return yyyy + "-" + (mm[1]?mm:"0"+mm[0]) + "-" + (dd[1]?dd:"0"+dd[0]); // padding
				};	
			});
			
			<%-- 공통 오류 표시기 --%>
			function commonAjaxErrorDialog(data){
				if(data.isError == 'true' ){
					dialog.push({body:'<b><s:message code="framework.common.error.defaultMsg" text="오류가 발견 되었습니다.\n 관리자에게 문의하세요."/></b>\nMessage : ' + data.errorMsg, type:'Caution'});
					return true;
				}else{
					return false;
				}
			}
		</script>
	</body>
</html>