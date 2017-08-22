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
	
</script>

</head>

<body>
<form name="form" id="form">
<input type="hidden" id="11" name="from1"  value="${from1}" />
<input type="hidden" id="22"   name="to1"  value="${to1}" />
<input type="hidden" id="S_CUS_CODE" name="S_CUS_CODE"  value="${S_CUS_CODE}" />

<div id="header">
	<div class="head">
    <a class="m_btn" href="#none"><img src="<c:url value="/img/btn_gnb.png" />" alt="gnb버튼"></a>
    <h1>거래원장상세</h1>
    <c:if test="${SELL_TRADE_YN == null}">
    	<a class="btn_back" href="javascript:fn_tradeList()">[뒤로가기]</a>
    </c:if>
    <c:if test="${SELL_TRADE_YN != null}">
    	<a class="btn_back" href="javascript:fn_selltradeList()">[뒤로가기]</a>
    </c:if>
    <div class="over"></div>    
<%@ include file="/WEB-INF/jsp/front/main/menu.jsp"%>
    
<div id="main">
	<div class="sub_title">
    	<h2>거래정보</h2>
    </div>
	<div class="member_info">
        <table class="list_board" summay="거래원장 중 상세정보">
        <caption>거래정보</caption>
            <colgroup>
                <col width="90"/>
				<col width="*"/>
            </colgroup>
            <tr>
                <th class="info_date02">거래일자</th>
                <td class="info_date02_an">${tradeDetail.dealDate}</td>
            </tr>
            <tr>
                <th class="info_division02">구분</th>
                <td class="info_division02_an">${tradeDetail.goodName}</td>
            </tr>
            <tr>
                <th class="info_qty02">수량(리터)</th>
                <td class="info_qty02_an"><fmt:formatNumber value="${tradeDetail.dealQty}" pattern="#,###" /></td>
            </tr>
            <tr>
                <th class="info_a02">단가</th>
                <td class="info_a02_an"><fmt:formatNumber value="${tradeDetail.dealPrice}" pattern="#,###" /></td>
            </tr>
            <tr>
                <th class="info_sum02">매출금액</th>
                <td class="info_sum02_an"><fmt:formatNumber value="${tradeDetail.saleAmt}" pattern="#,###" /></td>
            </tr>
            <tr>
                <th class="info_cash02">현금입금액</th>
                <td class="info_cash02_an"><fmt:formatNumber value="${tradeDetail.creditRecvAmt}" pattern="#,###" /></td>
            </tr>
            <tr>
                <th class="info_card02">카드입금액</th>
                <td class="info_card02_an"><fmt:formatNumber value="${tradeDetail.cardRecvAmt}" pattern="#,###" /></td>
            </tr>
            <tr>
                <th class="info_bank02">통장입금액</th>
                <td class="info_bank02_an"><fmt:formatNumber value="${tradeDetail.bankRecvAmt}" pattern="#,###" /></td>
            </tr>
            <tr>
                <th class="info_bond02">채권잔액</th>
                <td class="info_bond02_an"><fmt:formatNumber value="${tradeDetail.blcAmt}" pattern="#,###" /></td>
            </tr>
        </table>
    </div>
</div>	
<a href="#" id="btnTop" class="btn_top" style="z-index: 100;"><img src="<c:url value="/img/btn_top.png" />" alt="상단으로 이동"></a>

</form>

</body>
</html>