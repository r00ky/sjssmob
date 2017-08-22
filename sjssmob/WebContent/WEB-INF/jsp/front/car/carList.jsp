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
	
		//조회
		fn_search();
		
	});
	
	/* 조회 */
	function fn_search() {
		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/selectCarList.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	        	
	        	if(option=="success"){
		        	var list = result.transSettingList;
		        	$("#search_layer").html("");
		        	
		        	var txt ="";
		        	if(list.length>0){
						
						$.each(list, function(key){
			        		var data = list[key];

			        		txt += '<table class="jumun_list car_list" summay="배차현황 조회결과">';
			        		txt += '<thead>';
			        		txt += '<tr class="juyu_title">';
			        		txt += '<th class="juyu_name" colspan="3">'+nvl(data.delvCusName,'')+'</th>';
			        		txt += '<td class="juyu_condition"><span class="ft_red">'+nvl(data.statName,'')+'</span></td>';
			        		txt += '</tr>';
			        		txt += '</thead>';
			        		txt += '<tr>';
			        		txt += '<th class="juyu_date">주문일자</th>';
			        		txt += '<td class="juyu_date_an border_right">'+nvl(data.orderDate,'')+'</td>';
			        		txt += '<th class="juyu_no">주문번호</th>';
			        		txt += '<td class="juyu_no_an">'+nvl(data.orderIdx,'')+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr>';
			        		txt += '<th class="juyu_call_date">출하요청일</th>';
			        		txt += '<td class="juyu_call_date_an border_right">'+nvl(data.shipRqstDate,'')+'</td>';
			        		txt += '<th class="juyu_name">제품명</th>';
			        		txt += '<td class="juyu_type">'+nvl(data.goodName,'')+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr>';
			        		txt += '<th class="juyu_qty">수량(리터)</th>';
			        		txt += '<td class="juyu_qyt_an border_right">'+nvl(data.dealQty,'')+'</td>';
			        		txt += '<th class="juyu_store">출하저유소</th>';
			        		txt += '<td class="juyu_store_an">'+nvl(data.nabName,'')+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr>';
			        		txt += '<th class="juyu_carno">차량번호</th>';
			        		txt += '<td class="juyu_carno_an border_right">'+nvl(data.carName,'')+'</td>';
			        		txt += '<th class="juyu_carname">기사명</th>';
			        		txt += '<td class="juyu_carname_an">'+nvl(data.driverName,'')+'</td>';
			        		txt += '</tr>';
			        		txt += '<tr><td class="plus_view" colspan="4"><a class="jumun_board_info" href="javascript:fn_carDetail('+nvl(data.orderIdx,'')+');">상세 정보 더보기 &gt;</a></td></tr>';
			        		txt += '</table>';
			        	});
						
		        	}else{
		        		txt += '<table class="noData_wrap">';
		        		txt += '<tr>';
		        		txt += '<th class="noData">해당 데이터가 없습니다</th>';
		        		txt += '</tr>';
		        		txt += '</table>';	
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

	/* 상세조회 */
	function fn_carDetail(orderIdx){
		$("#orderIdx").val(orderIdx);
		
		var frm = document.form;
		
		frm.action = "<c:url value="/carDetail.do" />";
	    frm.submit();
	}	
	
</script>

</head>

<body>
<form name="form" id="form">
<input type="hidden" id="orderIdx" name="orderIdx"  value="" />

<div id="header">
	<div class="head">
    <a class="m_btn" href="#none"><img src="<c:url value="/img/btn_gnb.png"/>" alt="gnb버튼"/></a>
    <h1>배차현황</h1>
    <a class="btn_back" href="javascript:fn_home();">[뒤로가기]</a>
    <div class="over"></div>
<%@ include file="/WEB-INF/jsp/front/main/menu.jsp"%>    

<div id="main">
	<div class="check_jumunwrap">
        <table class="check_jumun">
			<caption>주문입력일 조회</caption>
			<colgroup>
            
                <col width="90"/>
                <col width="*"/>
                <col width="*"/>
			</colgroup>
            <tr>
                <td class="title_text title_text2"><label>출하요청일</label></td>
                <td colspan="2">
                	<span class="data_input mg_l5">
					 	<c:if test="${from1 != null}">
							<input type="text" id="from1" name="from1" readonly=true value="${from1}"/>
						</c:if>
						<c:if test="${from1 == null}">	                	
                			<input type="text" id="from1" name="from1" readonly="readonly" value="${TO_DATE_M7}"/>
                		</c:if>
                	</span> ~ 
                	<span class="data_input">
                		<c:if test="${to1 != null}">
                			<input type="text" id="to1" name="to1" readonly="readonly" value="${to1}"/>
                		</c:if>
                		<c:if test="${to1 == null}">
                			<input type="text" id="to1" name="to1" readonly="readonly" value="${TO_DATE}"/>
                		</c:if>
                	</span>
                </td>
            </tr>
             <tr>
                <td class="title_text title_text2 pd_t1"><label>주문일자</label></td>
                <td colspan="2" class="pd_t1">
                	<span class="data_input mg_l5">
                		<c:if test="${from2 != null}">
                			<input type="text" id="from2" name="from2" readonly="readonly" value="${from2}"/>
                		</c:if>
                		<c:if test="${from2 == null}">
                			<input type="text" id="from2" name="from2" readonly="readonly" value="${TO_DATE_M7}"/>
                		</c:if>
                	</span> ~ 
                	<span class="data_input">
                		<c:if test="${to2 != null}">
                			<input type="text" id="to2" name="to2" readonly="readonly" value="${to2}"/>
                		</c:if>
                		<c:if test="${to2 == null}">
                			<input type="text" id="to2" name="to2" readonly="readonly" value="${TO_DATE}"/>
                		</c:if>
                	</span> 
                </td>
            </tr>
            <tr>
                <td class="pd_t1 title_text title_text2">배차상태</td>
				<td colspan="2" class="pd_t1">
					<p class="mg_l5">
						<select class="jumun_01 car_01" id="order_status" name="order_status" title="주문상태">
							<option value="">전 체</option>
							<c:forEach var="val" items="${orderStatusList}">
								<option value="${val.commCode}" <c:if test="${order_status == val.commCode}">selected="selected"</c:if>	>
									${val.commName}
								</option>
							</c:forEach>
						</select>	                	
                 	</p>
            	</td>
			</tr>
            <tr>
                <td class="pd_t1 title_text title_text2">유종</td>
				<td class="pd_t1">
					<p class="mg_l5 wd95">
						<select class="jumun_01 car_01" id="goods_code" name="goods_code" title="유종">
							<option value="">전 체</option>
							<c:forEach var="val" items="${goodsList}">
								<option value="${val.commCode}" <c:if test="${goods_code == val.commCode}">selected="selected"</c:if>	>
									${val.commName}
								</option>
							</c:forEach>
						</select>                 		
                 	</p>
                 </td>
                 <td class="pd_t1"><span class="btn_wrap"><button class="btn_check_all btn_check_all2"  onclick="javascript:fn_search();" type="button">조회</button></span></td>
           </tr>
         </table>
    </div>
    
	<div class="jumun_board car" id="search_layer">

	</div>
	
</div>	
<a href="#" id="btnTop" class="btn_top" style="z-index: 100;"><img src="<c:url value="/img/btn_top.png"/>" alt="상단으로 이동"/></a>

</form>

</body>
</html>