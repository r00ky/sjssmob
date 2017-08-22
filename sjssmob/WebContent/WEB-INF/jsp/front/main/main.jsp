<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<%@ include file="/WEB-INF/jsp/comm/include.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css" />" />

<script language="JavaScript">
	/* ready */
	$(document).ready(function() {
	
		
		
		
	});
	
</script>

</head>

<body>
<form name="form" id="form">

<div id="header">
	<div class="head">
    <a class="m_btn" href="#"><img src="<c:url value="/img/btn_gnb.png"/>" alt="gnb버튼"></a>
    <h1><a class="main_logo" href="javascript:fn_home();"><img src="<c:url value="/img/logo.png"/>" width="190" alt="e-Biz로고"></a></h1>
    <a class="btn_logout" href="javascript:fn_logout();"><img src="<c:url value="/img/btn_logout.png"/>" alt="로그아웃버튼"></a>
    <div class="over"></div>
<%@ include file="/WEB-INF/jsp/front/main/menu.jsp"%>


<div id="login">
	<span class="log_info">${user.bussName}님 환영합니다.</span>

	</div>
</div>
<div id="main">
	<ul class="banner_ad">
    	<li class="banner_ad_01"><a href="#none"><marquee scrolldelay="200">${priceNoti}</marquee></a></li>
        <li class="banner_ad_02"><a href="#none"><marquee scrolldelay="200">${normalNoti}</marquee></a></li>
    </ul>
    <ul class="content">
    	<c:if test="${user.userDivide =='1'}">
    		<c:if test="${user.cusGubun !='T'}">
				<li><a class="cont_01" href="javascript:fn_member();">회원정보버튼</a><span>회원정보</span></li>
		        <li><a class="cont_02" href="javascript:fn_orderList();">주문버튼</a><span>주 문</span></li>
		        <li><a class="cont_03" href="javascript:fn_tradeList();">거래원장버튼</a><span>거래원장</span></li>
    		</c:if>
    		<c:if test="${user.cusGubun =='T'}">
    			<li><a class="cont_04" href="javascript:fn_carList();">배차버튼</a><span>배 차</span></li>
    		</c:if>
    	</c:if>
    	
    	<c:if test="${user.userDivide =='2'}">
    		<li><a class="cont_01" href="javascript:fn_member();">회원정보버튼</a><span>회원정보</span></li>
    		<li><a class="cont_03" href="javascript:fn_tradeList();">거래원장버튼</a><span>거래원장</span></li>
    	</c:if>
    	
    	<c:if test="${user.userDivide =='3'}">
	        <li><a class="cont_0402" href="javascript:fn_graph();">실시간매출현황</a><span>실시간매출현황</span></li>
	        <li><a class="cont_05" href="javascript:fn_finish();">마감일보버튼</a><span>마감일보</span></li>
	        <li><a class="cont_06" href="javascript:fn_selltradeList();">매출처거래원장버튼</a><span>매출처거래원장</span></li>    	
    	</c:if>
    </ul>
</div>	

</form>

</body>
</html>