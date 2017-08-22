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

		$("#S_CUS_CODE").select2({placeholder: "Select", allowClear: true});
		
		//조회
		fn_search();
	});
	
	/* 조회 */
	function fn_search(){
		var cusGubun = "${user.cusGubun}";
		
		/*딜러일경우 고객목록 조회*/
		if(cusGubun == "A") {
			fn_searchAgentList();				
		}else if(cusGubun == "R"){ /*법인사용자일경우*/
			fn_searchReprList();
		}		
	}
	
	/* 딜러의 거래처 조회 */
	function fn_searchAgentList() {
		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/selectAgentList.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : { S_CUS_CODE        : $("#S_CUS_CODE").val()
	        },
	        success: function(result, option) {
	        	
	        	if(option=="success"){
		        	var list = result.custList;
		        	$("#search_layer").html("");
		        	
		        	var txt ="";
		        	if(list.length>0){
						
						$.each(list, function(key){
			        		var data = list[key];
			        		
			        		txt += '<table class="jumun_list trade_list" summay="거래원장 일자별 조회결과"  >';
			        		txt += '<thead>';
			        		txt += '<tr class="juyu_title">';
			        		txt += '<th class="juyu_name" colspan="4">['+nvl(data.cusCode,'')+'] '+nvl(data.cusName,'')+'</th>';
			        		txt += '</tr>';
			        		txt += '</thead>';
			        		txt += '<tr>';
			        		txt += '<th class="trade_sum">대표자명</th>';
			        		txt += '<td class="trade_divide_an border_right" >'+nvl(data.reprName,'')+'</td>';
			        		txt += '<th class="trade_diposit">사업자번호</th>';
			        		txt += '<td class="trade_divide_an">'+nvl(data.bussNo,'').substring(0,3)+"-"+nvl(data.bussNo,'').substring(3,5)+"-"+nvl(data.bussNo,'').substring(5,10)+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr>';
			        		txt += '<th class="trade_sum">주문가능액</th>';
			        		txt += '<td class="trade_divide_an border_right" >'+numericComma(Number(nvl(data.orderAbleAmt,'')),0)+'</td>';
			        		txt += '<th class="trade_sum">가상계좌번호</th>';
			        		txt += '<td class="trade_divide_an border_right" >'+nvl(data.vracctNo,'')+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr><td class="plus_view" colspan="4"><a class="jumun_board_info" href="javascript:fn_orderDetail(\''+nvl(data.cusCode,'')+'\');">주문입력 &gt;</a></td></tr>';
			        		txt += '</table>';
			        	});
						
		        	}else{
		        		txt += "<tr><td class=\"right-line\" colspan=9'>데이터가 없습니다.</td></tr>";	
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
	};
	
	/* 법인 거래처 조회 */
	function fn_searchReprList() {
		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/selectReprList.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : {S_CUS_CODE        : $("#S_CUS_CODE").val()
	        },
	        success: function(result, option) {
	        	
	        	if(option=="success"){
		        	var list = result.custList;
		        	$("#search_layer").html("");
		        	
		        	var txt ="";
		        	if(list.length>0){
						
						$.each(list, function(key){
			        		var data = list[key];
			        		
			        		txt += '<table class="jumun_list trade_list" summay="거래원장 일자별 조회결과"  style="height:90px;">';
			        		txt += '<thead>';
			        		txt += '<tr class="juyu_title">';
			        		txt += '<th class="juyu_name" colspan="4">['+nvl(data.cusCode,'')+'] '+nvl(data.cusName,'')+'</th>';
			        		txt += '</tr>';
			        		txt += '</thead>';
			        		txt += '<tr>';
			        		txt += '<th class="trade_sum">대표자명</th>';
			        		txt += '<td class="trade_divide_an border_right" >'+nvl(data.reprName,'')+'</td>';
			        		txt += '<th class="trade_diposit">사업자번호</th>';
			        		txt += '<td class="trade_divide_an">'+nvl(data.bussNo,'').substring(0,3)+"-"+nvl(data.bussNo,'').substring(3,5)+"-"+nvl(data.bussNo,'').substring(5,10)+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr><td class="plus_view" colspan="4"><a class="jumun_board_info" href="javascript:fn_orderDetail(\''+nvl(data.cusCode,'')+'\');">주문입력 &gt;</a></td></tr>';
			        		txt += '</table>';
			        	});
						
		        	}else{
		        		txt += "<tr><td class=\"\" colspan=3'>데이터가 없습니다.</td></tr>";	
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
	};	
	
	/* 거래처선택 - 주문등록 */
	function fn_orderDetail(cusCode){
		fn_blockUI();
		var frm = document.form;
		
		$("#CUS_CODE").val(cusCode);
		
		frm.action = "<c:url value="/orderRegister.do" />";
	    frm.submit();
	}

	
</script>

</head>

<body>
<form name="form" id="form">
<input type="hidden" name="CUS_CODE" id="CUS_CODE" value="" />

<div id="header">
	<div class="head">
		<a class="m_btn" href="#none"><img src="img/btn_gnb.png" alt="gnb버튼"></a>
		<h1>거래처 현황</h1>
		<a class="btn_back" href="javascript:window.history.back();">[뒤로가기]</a>
		<div class="over"></div>
		
<%@ include file="/WEB-INF/jsp/front/main/menu.jsp"%>
		
<div id="main">

	<div class="check_jumunwrap">
		<table class="check_jumun">
			<caption>주문입력일 조회</caption>
			<colgroup>
                <col width="80"/>
                <col width="*"/>
                <col width="*"/>
			</colgroup>
			<tr>
				<td class="title_text"><label>거래처</label></td>
				<td colspan="2">
					<select id="S_CUS_CODE" name="S_CUS_CODE" class="wd-90p">
						<option value="">전 체</option>
						<c:forEach var="val" items="${custList}">
							<option value="${val.cusCode}" >
									(${val.cusCode}) ${val.cusName}
							</option>												
						</c:forEach>
					</select>				
				</td>
			</tr>
			<tr>
				<td class="pd_t1" colspan="3">
					<span class="btn_wrap">
						<button class="btn_check_all" onclick="javascript:fn_search();" type="button">조회</button>
					</span>
				</td>
			</tr>
		</table>
	</div>		
	
	<div class="jumun_board trade_board3" id="search_layer"></div>
	
</div>

<a href="" id="btnTop" class="btn_top" style="z-index: 100;"><img src="<c:url value="/img/btn_top.png" />" alt="상단으로 이동"></a>

</form>

</body>
</html>