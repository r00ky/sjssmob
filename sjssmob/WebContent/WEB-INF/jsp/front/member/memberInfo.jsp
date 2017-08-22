<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />


<%@ include file="/WEB-INF/jsp/comm/include.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/sub.css" />" />

<script language="JavaScript">
	/* ready */
	$(document).ready(function() {
	
		
	});
		
	/* 주문등록 */
	function fn_orderRegister(){
		var frm = document.form;

		var cusGubun = "${user.cusGubun}";
		if(cusGubun=="C"){
			frm.action = "<c:url value="/orderRegister.do" />";
		}else{
			frm.action = "<c:url value="/selectBussNo.do" />";
		}
		
	    frm.submit();		
	}
		
</script>

</head>

<body>
<form name="form" id="form">

<div id="header">
	<div class="head">
    <a class="m_btn" href="#none"><img src="<c:url value="/img/btn_gnb.png" />" alt="gnb버튼"></a>
    <h1>회원정보</h1>
    <a class="btn_back" href="javascript:fn_home();">[뒤로가기]</a>
    
<%@ include file="/WEB-INF/jsp/front/main/menu.jsp"%>


<div id="main">
	<div class="member_info">
        <table class="list_board" summay="회원정보">
        <caption>회원정보</caption>
            <colgroup>
                <col style="width:90px;">
                <col>
            </colgroup>
            <c:if test="${userInfo != null}">
	            <tr>
	                <th class="info_code">거래처코드</th>
	                <td class="info_code_an">${userInfo.cusCode}</td>
	            </tr>
	            <tr>
	                <th class="info_name">거래처명</th>
	                <td class="info_name_an">${userInfo.bussName}</td>
	            </tr>
	            <tr>
	                <th class="info_rep">대표자명</th>
	                <td class="info_rep_an">${userInfo.reprName}</td>
	            </tr>
	            <tr>
	                <th class="info_no">사업자번호</th>
	                <td class="info_no_an">${userInfo.bussNo}</td>
	            </tr>
	            <tr>
	                <th class="info_add">소재지</th>
	                <td class="info_add_an">${userInfo.address}</td>
	            </tr>
	            <tr>
	                <th class="info_email">e-mail</th>
	                <td class="info_email_an">${userInfo.email}</td>
	            </tr>
	            <c:if test="${user.cusGubun !='A'}">
		            <tr>
		                <th class="info_bank">은행명</th>
		                <td class="info_bank_an">${userInfo.bankName}</td>
		            </tr>
		            <tr>
		                <th class="info_bank_no">계좌번호</th>
		                <td class="info_bank_no_an">${userInfo.vracctNo}</td>
		            </tr>
		            <tr>
		                <th class="info_limit">주문가능금액</th>
		                <td class="info_limit_an"><fmt:formatNumber value="${userInfo.creditLimit}" pattern="#,###" /></td>
		            </tr>
		    	</c:if>
	    	</c:if>
        </table>
    </div>
    
    
    <c:if test="${userInfo == null}">

		<table class="noData_wrap">
			<tr>
				<th class="noData">해당 데이터가 없습니다</th>
			</tr>
		</table>
	</c:if>
		    
    <c:if test="${user.userDivide =='1'}">
		<div class="btn_orderwrap">
	    	<span class="btn_order">
	        	<button id="ibtn_order01" class="btn_order01" type="button" onclick="javascript:fn_orderRegister();">주문등록</button>
			</span>
		</div>
	</c:if>
</div>	

<a href="" id="btnTop" class="btn_top" style="z-index: 100;"><img src="<c:url value="/img/btn_top.png" />" alt="상단으로 이동"></a>

</form>
</body>
</html>