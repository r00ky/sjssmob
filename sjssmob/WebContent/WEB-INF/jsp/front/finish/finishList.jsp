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
	
		
		$("#BRANCH_CODE").select2({placeholder: "Select", allowClear: true});
		
		//조회
		fn_search();
		
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
			url           : "<c:url value="/selectFinishSheet.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	        	
	        	if(option=="success"){
		        	//##############################################################
		        	//상품수불
		        	//##############################################################
	        		var list1 = result.finishSheet1;
		        	
		        	//console.log(list);
		        	
		        	$("#tab1").html("");
		        	
		        	var txt ="";
		        	if(list1.length>0){
						
						$.each(list1, function(key){
			        		var data = list1[key];
			        		
			        		txt += '<table class="jumun_list" summay="상품수불 현황">';
			        		txt += '<thead>';
			        		txt += '<tr class="juyu_title">';
			        		txt += '<th class="juyu_name" colspan="4">'+nvl(data.goodName,'')+'</th>';
			        		txt += '</tr>';
			        		txt += '</thead>';
			        		txt += '<tr>';
			        		txt += '<th class="bstock">전일재고</th>';
			        		txt += '<td class="bstock_an border_right">'+numericComma(Number(nvl(data.openQty,'')),0)+'</td>';
			        		txt += '<th class="goods">입고</th>';
			        		txt += '<td class="goods_an">'+numericComma(Number(nvl(data.inQty,'')),0)+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr>';
			        		txt += '<th class="delivery">출고</th>';
			        		txt += '<td class="delivery_an border_right">'+numericComma(Number(nvl(data.outQty,'')),0)+'</td>';
			        		txt += '<th class="plus">증감량</th>';
			        		txt += '<td class="plus_an">'+numericComma(Number(nvl(data.lossQty,'')),0)+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr>';
			        		txt += '<th class="astock">장부재고</th>';
			        		txt += '<td colspan="3" class="astock_an">'+numericComma(Number(nvl(data.closeQty,'')),0)+'</td>';
			        		txt += '</tr>';
			        		txt += '</table>';			        		
			        	
			        	});
						
		        	}else{
		        		txt += '<table class="noData_wrap"><tr><th class="noData">해당 데이터가 없습니다</th></tr></table>';
		        	}		        	

		        	$("#tab1").append(txt);

		        	//##############################################################
		        	//출고현황
		        	//##############################################################
		        	var list2 = result.finishSheet2;
		        	
		        	//console.log(list);
		        	
		        	$("#tab2").html("");
		        	
		        	txt ="";
		        	if(list2.length>0){
						
						$.each(list2, function(key){
			        		var data = list2[key];
			        	
			        		txt += '<table class="jumun_list" summay="출고현황">';
			        		txt += '<thead>';
			        		txt += '<tr class="juyu_title">';
			        		txt += '<th class="juyu_name" colspan="3">'+nvl(data.goodName,'')+'</th>';
			        		txt += '</tr>';
			        		txt += '</thead>';
			        		txt += '<tr>';
			        		txt += '<th class="cash">현금판매</th>';
			        		txt += '<td class="cash_q_an border_right align_r">'+numericComma(Number(nvl(data.cashQty,'')),0)+'(리터)</td>';
			        		txt += '<td class="cash_sum_an align_r">'+numericComma(Number(nvl(data.cashAmt,'')),0)+'(원)</td>';
			        		txt += '</tr>';
			        		txt += '<tr>';
			        		txt += '<th class="credit">외상판매</th>';
			        		txt += '<td class="credit_q_an border_right align_r">'+numericComma(Number(nvl(data.blcQty,'')),0)+'(리터)</td>';
			        		txt += '<td class="credit_sum_an align_r">'+numericComma(Number(nvl(data.blcAmt,'')),0)+'(원)</td>';
			        		txt += '</tr>';
			        		txt += '<tr>';
			        		txt += '<th class="gift">상품권</th>';
			        		txt += '<td class="gift_q_an border_right align_r">'+numericComma(Number(nvl(data.tckQty,'')),0)+'(리터)</td>';
			        		txt += '<td class="gift_sum_an align_r">'+numericComma(Number(nvl(data.tckAmt,'')),0)+'(원)</td>';
			        		txt += '</tr>';
			        		txt += '<tr>';
			        		txt += '<th class="card">신용카드</th>';
			        		txt += '<td class="card_q_an border_right align_r">'+numericComma(Number(nvl(data.crdtQty,'')),0)+'(리터)</td>';
			        		txt += '<td class="card_sum_an align_r">'+numericComma(Number(nvl(data.crdtAmt,'')),0)+'(원)</td>';
			        		txt += '</tr>';
			        		txt += '<tr class="bg_sumpoint">';
			        		txt += '<th class="sum">판매합계</th>';
			        		txt += '<td class="sum_q_an border_right align_r">'+numericComma(Number(nvl(data.sumQty,'')),0)+'(리터)</td>';
			        		txt += '<td class="sum_an align_r">'+numericComma(Number(nvl(data.sumAmt,'')),0)+'(원)</td>';
			        		txt += '</tr>';
			        		txt += '</table>';			        		
			        	});
						
		        	}else{
		        		txt += '<table class="noData_wrap"><tr><th class="noData">해당 데이터가 없습니다</th></tr></table>';
		        	}		        	

		        	$("#tab2").append(txt);
		        	
		        	
		        	
		        	//##############################################################
		        	//외상현황
		        	//##############################################################
		        	var list3 = result.finishSheet3;
		        	
		        	//console.log(list);
		        	
		        	$("#tab3").html("");
		        	
		        	txt ="";
		        	if(list3.length>0){
						
						$.each(list3, function(key){
			        		var data = list3[key];
			        	
			        		txt += '<table class="jumun_list" summay="외상현황">';
			        		txt += '<thead>';
			        		txt += '<tr class="juyu_title">';
			        		txt += '<th class="juyu_name" colspan="2">'+nvl(data.dataName,'')+'</th>';
			        		txt += '</tr>';
			        		txt += '</thead>';
			        		txt += '<tr>';
			        		txt += '<th class="b_balance">전일잔액</th>';
			        		txt += '<td class="b_balance_an align_r">'+numericComma(Number(nvl(data.openAmt,'')),0)+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr>';
			        		txt += '<th class="t_new">금일발생</th>';
			        		txt += '<td class="t_new_an align_r">'+numericComma(Number(nvl(data.inAmt,'')),0)+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr>';
			        		txt += '<th class="t_deposit">금일입금</th>';
			        		txt += '<td class="t_deposit_an align_r">'+numericComma(Number(nvl(data.outAmt,'')),0)+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr class="bg_sumpoint">';
			        		txt += '<th class="t_sum">당일잔액</th>';
			        		txt += '<td class="t_sum_an align_r">'+numericComma(Number(nvl(data.closeAmt,'')),0)+'</td>';
			        		txt += '</tr>';
			        		txt += '</table>';			        		
			        	});
						
		        	}else{
		        		txt += '<table class="noData_wrap"><tr><th class="noData">해당 데이터가 없습니다</th></tr></table>';
		        	}		        	

		        	$("#tab3").append(txt);
		        	
		        	
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
	
</script>

</head>

<body>
<form name="form" id="form">

<div id="header">
	<div class="head"> <a class="m_btn" href="#none"><img src="<c:url value="/img/btn_gnb.png"/>" alt="gnb버튼"></a>
	<h1>마감일보</h1>
	<a class="btn_back" href="javascript:fn_home();">[뒤로가기]</a>
	<div class="over"></div>
<%@ include file="/WEB-INF/jsp/front/main/menu.jsp"%>    	

<div id="main">
	<div id="main_top">
		<div class="check_jumunwrap ">
			<table class="check_jumun">
				<caption>
				마감일보 조회
				</caption>
				<colgroup>
					<col width="90"/>
					<col width="*"/>
					<col width="*"/>
				</colgroup>
				<tr>
					<td class="title_text title_text2"><label>영업일자</label></td>
					<td colspan="2">
						<span class="data_input mg_l5">
							<input type="text" id="from1" name="from1" value="${TO_DATE_M7}"  />
						</span> ~ 
						<span class="data_input">
							<input type="text" id="to1" name="to1" value="${TO_DATE}" />
						</span></td>
				</tr>
				<tr>
					<td class="title_text title_text2 pd_t1"><label>소속지점</label></td>
					<td class="pd_t1">
						<span class="mg_l5 wd95 ">
							<select class="jumun_01" title="소속지점" id="BRANCH_CODE" name="BRANCH_CODE">
								<c:forEach var="val" items="${branchCodeList}">
									<option value="${val.branchCode}">
										${val.branchName}
									</option>
								</c:forEach>      
							</select>
						</span>
					</td>
					<td class="pd_t1">
						<span class="btn_wrap">
							<button class="btn_check_all" type="button" onclick="javascript:fn_search();" >조회</button>
						</span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="container">
		<div class="contents3">
			<ul class="tabs">
				<li class="active" rel="tab1">상품수불</li>
				<li rel="tab2">출고현황</li>
				<li rel="tab3">외상현황</li>
			</ul>
			<div class="tab_container">
				<div id="tab1" class="tab_content jumun_board" >
                    <table class="noData_wrap"><tr><th class="noData">해당 데이터가 없습니다</th></tr></table>
				</div>
				<div id="tab2" class="tab_content jumun_board" >
                    <table class="noData_wrap"><tr><th class="noData">해당 데이터가 없습니다</th></tr></table>
				</div>
				<div id="tab3" class="tab_content jumun_board" >
                    <table class="noData_wrap"><tr><th class="noData">해당 데이터가 없습니다</th></tr></table>
				</div>
			</div>
		</div>
	</div>
</div>
<a href="#" id="btnTop" class="btn_top" style="z-index: 100;"><img src="<c:url value="/img/btn_top.png"/>" alt="상단으로 이동"></a>


</form>

</body>
</html>