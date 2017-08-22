<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<%@ include file="/WEB-INF/jsp/comm/include.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/sub.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="common/select2-3.4.5/select2.css" />" />
<script src="<c:url value="/common/select2-3.4.5/select2.js" />"></script>

<script language="JavaScript">
	/* ready */
	$(document).ready(function() {
		//조회
		fn_search();
		
		var cusGubun = "${user.cusGubun}";
		if(cusGubun!="C"){
			$("#S_CUS_CODE").select2({placeholder: "Select", allowClear: true});
		}
		
	});
	
	
	/* 조회 */
	function fn_search(){
		
		var term = 31; //날짜 범위 31일로 세팅

		var sDate = $("#from1").val(); //검색 시작날짜
		var eDate = $("#to1").val();  //검색 마지막날짜

		var strSDT = new Date(sDate.substring(0,4), sDate.substring(5,7)-1, sDate.substring(8,10));
		var strEDT = new Date(eDate.substring(0,4), eDate.substring(5,7)-1, eDate.substring(8,10));
		
		if ((strEDT.getTime() - strSDT.getTime()) / (1000*60*60*24) >= term) {
		    alert('1달(31일) 이하의 기간을 검색하실 수 있습니다.');
		    return;
		}

		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/selectTradeList.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	        	
	        	if(option=="success"){
		        	var list = result.tradeList;
		        	
		        	//console.log(list);
		        	
		        	$("#search_layer").html("");
		        	
		        	var txt ="";
		        	if(list.length>0){
						
						$.each(list, function(key){
			        		var data = list[key];
			        		txt += '<table class="jumun_list trade_list" summay="거래원장 일자별 조회결과">';
			        		txt += '<tr>';
			        		txt += '<th class="trade_date_an border_right"><a class="jumun_board_info" href="javascript:fn_tradeDetail('+nvl(data.dataKey,'')+');" style="color: black;text-decoration:underline">'+nvl(data.dealDate,'')+'</a></td>';
			        		txt += '<th class="trade_sum_an border_right">'+nvl(numericComma(Number(data.saleAmt),''),'0')+'</td>';
			        		txt += '<th class="trade_diposit_an border_right">'+nvl(numericComma(Number(data.totalRecvAmt),''),'0')+'</td>';
			        		txt += '<th colspan="3" class="trade_bl_an">'+nvl(numericComma(Number(data.blcAmt),''),'0')+'</td>';
// 			        		txt += '<tr><td class="plus_view" colspan="4"><a class="jumun_board_info" href="javascript:fn_tradeDetail('+nvl(data.dataKey,'')+');">상세 정보 더보기 &gt;</a></td></tr>';
			        		txt += '</table>';
			        	});
						
						txt += '<table class="jumun_list trade_list trade_list_sum" summay="총합계">';
						txt += '<tr class="juyu_title">';
						txt += '<th colspan="3">총 합 계</th>';
						txt += '</tr>';
						txt += '<tr class="juyu_title">';
						txt += '<th class="trade_date">구분</th>';
						txt += '<th class="trade_qty">수량</td>';
						txt += '<th class="trade_sum">금액</th>';
						txt += '</tr>';
						var list2 = result.sumList;
						
						$.each(list2, function(key){
							var data = list2[key];
							
							txt += '<tr>';
							
							txt += '<th class="trade_oil001">'+nvl(data.goodName,'')+'</th>';

							if(data.dataKey != "4000" && data.dataKey != "5000" && data.dataKey != "9999"){
								txt += '<td class="border_right">'+nvl(numericComma(Number(data.dealQty),''),'0')+'</td>';
								txt += '<td>'+nvl(numericComma(Number(data.saleAmt),''),'0')+'</td>';
							}else if(data.dataKey == "4000"){
								//매출액
								txt += '<td colspan="2" >'+nvl(numericComma(Number(data.saleAmt),''),'0')+'</td>';								
							}else if(data.dataKey == "5000"){
								//입금액
								txt += '<td colspan="2" >'+nvl(numericComma(Number(data.recvAmt),''),'0')+'</td>';
							}else if(data.dataKey == "9999"){
								//채권잔액
								txt += '<td colspan="2" >'+nvl(numericComma(Number(data.blcAmt),''),'0')+'</td>';							
							}

							txt += '</tr>';
						});

						txt += '</table>';
						
						
		        	}else{
		        		txt += '<table class="jumun_list noData_wrap"><tr><th class="noData">해당 데이터가 없습니다</th></tr></table>';
		        	}		        	

		        	$("#search_layer").append(txt);

		        	
	        	}else{
	        		alert("에러가 발생하였습니다.");
	        	}
	        	fn_unBlockUI();
	        },
	        error: function(result, option) {
	        	alert("에러가 발생하였습니다.");
	        	fn_unBlockUI();
	        }
	        
		});		
	}
	
	
	/* 상세조회 */
	function fn_tradeDetail(tradeIdx){
		$("#tradeKey").val(tradeIdx);
		
		var frm = document.form;
		
		frm.action = "<c:url value="/tradeDetail.do" />";
	    frm.submit();
	}
	

</script>

</head>

<body>
<form name="form" id="form">
<input type="hidden" id="tradeKey" name="tradeKey"  value="" />

<div id="header">
	<div class="head">
    <a class="m_btn" href="#none"><img src="<c:url value="/img/btn_gnb.png" />" alt="gnb버튼"/></a>
    <h1>거래원장</h1>
    <a class="btn_back" href="javascript:fn_home();">[뒤로가기]</a>
    <div class="over"></div>
<%@ include file="/WEB-INF/jsp/front/main/menu.jsp"%>

<div id="main">
	<div class="check_jumunwrap">
    	<table class="check_jumun">
			<caption>
				거래일자 조회
			</caption>
			<colgroup>
			<col width="90"/>
			<col width="*"/>
			</colgroup>
			<tr>
				<td class="title_text title_text2"><label>거래일자</label></td>
				<td >
					<span class="data_input mg_l5">
						<c:if test="${from1 != null}">
							<input type="text" id="from1" name="from1"  value="${from1}"/>
						</c:if>	
						<c:if test="${from1 == null}">
							<input type="text" id="from1" name="from1"  value="${TO_DATE_M7}"/>
						</c:if>
					</span>
						 ~ 
					<span class="data_input">
						<c:if test="${to1 != null}">
							<input type="text" id="to1" name="to1"  value="${to1}" />
						</c:if>
						<c:if test="${to1 == null}">
							<input type="text" id="to1" name="to1"  value="${TO_DATE}" />
						</c:if>
					</span>
				</td>
			</tr>
			<!-- 딜러 -->
			<c:if test="${user.cusGubun =='A' && user.userDivide =='1'}">
			<tr>
				<td class="title_text"><label>거래처</label></td>
				<td class="pd_t1" colspan="2">
					<select id="S_CUS_CODE" name="S_CUS_CODE"  class="ml_5 wd-100p" >
						<c:forEach var="val" items="${agentCusList}" >
							<option value="${val.commCode}" <c:if test="${S_CUS_CODE == val.commCode}">selected="selected"</c:if>>
								${val.commName}
							</option>
						</c:forEach>
					</select>				
				</td>
			</tr>
			</c:if>
			<!-- 법인 -->
			<c:if test="${user.cusGubun =='R' && user.userDivide =='1'}">
			<tr>
				<td class="title_text"><label>거래처</label></td>
				<td class="pd_t1" colspan="2">
					<select id="S_CUS_CODE" name="S_CUS_CODE"  class="ml_5 wd-100p">
						<c:forEach var="val" items="${reprCusList}">
							<option value="${val.commCode}" <c:if test="${S_CUS_CODE == val.commCode}">selected="selected"</c:if>>
								${val.commName}
							</option>
						</c:forEach>
					</select>				
				</td>
			</tr>
			</c:if>			
			<tr>
				<td colspan="2" class="pd_t1">
					<span class="btn_wrap">
						<button class="btn_check_all" onclick="javascript:fn_search();"  type="button">조회</button>
					</span>
				</td>
			</tr>
		</table>
        
    </div>
    
    
	<c:if test="${user.cusGubun =='C' && user.userDivide =='1'}">
		<div style="margin-top: 76px;position:fixed;width: 100%;">
	</c:if>
	<c:if test="${user.cusGubun !='C' && user.userDivide =='1'}">
		<div style="margin-top: 108px;position:fixed;width: 100%;">
	</c:if>
	<c:if test="${user.userDivide =='2'}">
		<div style="margin-top: 76px;position:fixed;width: 100%;">
	</c:if>		
		<table class="jumun_list" summay="주문현황 주문입력일 검색결과" style="height: 30px;">
			<tr style="border-bottom:1px solid #ccc;line-height:28px;background:#96b2e9;">
				<td class="juyu_date border_right" style="color:white;">거래일자</th>
				<td class="juyu_no border_right" style="color:white;">매출액</th>
				<td class="juyu_no border_right" style="color:white;">입금액</th>
				<td class="juyu_no border_right" style="color:white;">채권잔액</th>
			</tr>		        		
		</table>	
	</div>
	
	    
    <c:if test="${user.cusGubun =='C' && user.userDivide =='1'}">
		<div class="jumun_board trade_board" id="search_layer"></div>
	</c:if>			
    <c:if test="${user.cusGubun !='C' && user.userDivide =='1'}">
		<div class="jumun_board trade_board2" id="search_layer"></div>
	</c:if>
    <c:if test="${user.userDivide =='2'}">
		<div class="jumun_board trade_board" id="search_layer"></div>
	</c:if>							
</div>	

<a href="#" id="btnTop" class="btn_top" style="z-index: 100;"><img src="<c:url value="/img/btn_top.png"/>" alt="상단으로 이동"/></a>

</form>

</body>
</html>