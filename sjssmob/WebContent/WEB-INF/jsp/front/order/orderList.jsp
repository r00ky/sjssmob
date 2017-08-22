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
			url           : "<c:url value="/selectOrderList.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	        	
	        	if(option=="success"){
		        	var list = result.orderStatusList;
		        	
		        	//console.log(list);
		        	
		        	$("#search_layer").html("");
		        	
		        	var txt ="";
		        	if(list.length>0){
		        		
						$.each(list, function(key){
			        		var data = list[key];
			        		txt += '<table class="jumun_list" summay="주문현황 출하일자 검색결과">';
			        		txt += '<thead>';
			        		txt += '<tr class="juyu_title">';
			        		txt += '<th class="juyu_name" colspan="3">'+nvl(data.delvCusName,'')+'</th>';
// 			        		txt += '<td class="juyu_condition">'+nvl(data.statName,'')+'</td>';
			        		txt += '<td class="juyu_condition" colspan="2"><a  href="javascript:fn_orderDetail('+nvl(data.orderIdx,'')+');" style="color: white;">[상세 정보]</a></td>';
			        		txt += '</tr>';
			        		txt += '</thead>'
			        		txt += '<tr>';
			        		txt += '<th class="juyu_date_an border_right" style="width:22% !important ;">'+nvl(data.shipRqstDate,'')+'</td>';
			        		txt += '<th class="juyu_type border_right" style="width:20% !important ;">'+nvl(data.goodName,'')+'</td>';
			        		txt += '<th class="juyu_qyt_an border_right" style="width:21% !important ;">'+numericComma(Number(nvl(data.dealQty,'')),0)+'</td>';
			        		txt += '<th class="juyu_store_an" style="width:22% !important ;">'+nvl(data.nabName,'')+'</td>';
			        		txt += '<th class="juyu_date_an border_right" style="width:15% !important ;">'+nvl(data.orderDate,'').substring(5)+'</td>';
			        		txt += '</tr>';
// 			        		txt += '<tr>';
// 			        		txt += '<td class="plus_view" colspan="4"><a class="jumun_board_info" href="javascript:fn_orderDetail('+nvl(data.orderIdx,'')+');">상세 정보 더보기 </a></td>';
// 			        		txt += '</tr>';
							txt += '</table>';
			        	});
						
		        	}else{
		        		txt += '<table class="noData_wrap"><tr><th class="noData">해당 데이터가 없습니다</th></tr></table>';
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
	function fn_orderDetail(orderIdx){
		$("#orderIdx").val(orderIdx);
		
		var frm = document.form;
		
		frm.action = "<c:url value="/orderDetail.do" />";
	    frm.submit();
	}
	
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
<input type="hidden" id="orderIdx" name="orderIdx"  value="" />

<div id="header">
	<div class="head">
    <a class="m_btn" href="#none"><img src="<c:url value="/img/btn_gnb.png" />" alt="gnb버튼"/></a>
    <h1>주문현황</h1>
    <a class="btn_back" href="javascript:fn_home();">[뒤로가기]</a>
    
<%@ include file="/WEB-INF/jsp/front/main/menu.jsp"%>


<div id="main">
	
	<div class="check_jumunwrap">
		<table class="check_jumun">
			<caption>출하일자 조회</caption>
			<colgroup>
                <col width="80"/>
                <col width="*"/>
                <col width="*"/>
			</colgroup>
			<tr>
				<td class="title_text"><label>출하일자</label></td>
				<td colspan="2">
					<span class="data_input mg_l5">
					 	<c:if test="${from1 != null && from1 != ''}">
							<input type="text" id="from1" name="from1" readonly=true value="${from1}"/>
						</c:if>	
						<c:if test="${from1 == null || from1 == ''}">
							<input type="text" id="from1" name="from1" readonly=true value="${TO_DATE_M7}"/>
						</c:if>
					</span> ~ 
					<span class="data_input">
						<c:if test="${to1 != null && to1 != ''}">
							<input type="text" id="to1" name="to1" readonly=true value="${to1}" />
						</c:if>
						<c:if test="${to1 == null || to1 == ''}">
							<input type="text" id="to1" name="to1" readonly=true value="${TO_DATE}" />
						</c:if>
					</span>
				</td>
			</tr>
			<!-- 딜러 -->
			<c:if test="${user.cusGubun =='A'}">
			<tr>
				<td class="title_text"><label>거래처</label></td>
				<td class="pd_t1" colspan="2">
					<select id="S_CUS_CODE" name="S_CUS_CODE"  class="ml_5 wd-100p" >
						<option value="">전 체</option>
						<c:forEach var="val" items="${agentCusList}">
							<option value="${val.commCode}" <c:if test="${S_CUS_CODE == val.commCode}">selected="selected"</c:if>	>
								${val.commName}
							</option>
						</c:forEach>
					</select>				
				</td>
			</tr>
			</c:if>
			<!-- 법인 -->
			<c:if test="${user.cusGubun =='R'}">
			<tr>
				<td class="title_text"><label>거래처</label></td>
				<td class="pd_t1" colspan="2">
					<select id="S_CUS_CODE" name="S_CUS_CODE"  class="ml_5 wd-100p">
						<option value="">전 체</option>
						<c:forEach var="val" items="${reprCusList}">
							<option value="${val.commCode}" <c:if test="${S_CUS_CODE == val.commCode}">selected="selected"</c:if>	>
								${val.commName}
							</option>
						</c:forEach>
					</select>				
				</td>
			</tr>
			</c:if>
			<tr>
				<td class="pd_t1">
					<span class="juyuso_name">
						<input type="text" id="ijuyu_name"  name="ijuyu_name" maxlength="100" value="${ijuyu_name }"/>
					</span>
				</td>
				<td class="pd_t1">
					<span class="mg_l5 wd95 ">
						<select class="jumun_01" id="order_status" name="order_status" title="주문상태">
							<option value="">전 체</option>
							<c:forEach var="val" items="${ordstsList}">
								<option value="${val.commCode}" <c:if test="${order_status == val.commCode}">selected="selected"</c:if>	>
									${val.commName}
								</option>
							</c:forEach>
						</select>
					</span>
				</td>
				<td class="pd_t1">
					<span class="btn_wrap">
						<button class="btn_check_all" onclick="javascript:fn_search();" type="button">조회</button>
					</span>
				</td>
			</tr>
		</table>
		
	</div>
	
	<c:if test="${user.cusGubun =='C'}">
		<div style="margin-top: 76px;position:fixed;width: 100%;">
	</c:if>
	<c:if test="${user.cusGubun !='C'}">
		<div style="margin-top: 108px;position:fixed;width: 100%;">
	</c:if>
		
		<table class="jumun_list" summay="주문현황 주문입력일 검색결과" style="height: 60px;">
			<thead>
				<tr class="juyu_title">
					<th class="juyu_name" colspan="3"><b>거래처명</b></th>
					<td class="juyu_condition" colspan="2">상세 정보</td>
				</tr>
			</thead>
			<tr style="border-bottom:1px solid #ccc;line-height:28px;">
				<td class="juyu_date border_right" style="width:22% !important ;">출하일자</th>
				<td class="juyu_no border_right" style="width:20% !important ;">제품명</th>
				<td class="juyu_no border_right" style="width:21% !important ;">수량(리터)</th>
				<td class="juyu_no border_right" style="width:22% !important ;">출하저유소</th>
				<td class="juyu_date border_right" style="width:15% !important ;">주문일자</th>
			</tr>		        		
		</table>	
	</div>
	
	<c:if test="${user.cusGubun =='C'}">
		<div class="jumun_board" id="search_layer"></div>
	</c:if>
	<c:if test="${user.cusGubun !='C'}">
		<div class="jumun_board2" id="search_layer"></div>
	</c:if>
    
	<div class="btn_orderwrap">
    	<span class="btn_order">
        	<button id="ibtn_order01" class="btn_order01" type="button" onclick="javascript:fn_orderRegister();">주문등록</button>
		</span>
	</div>
	
</div>	

<a href="" id="btnTop" class="btn_top" style="z-index: 100;"><img src="<c:url value="/img/btn_top.png" />" alt="상단으로 이동"></a>

</form>
</body>
</html>