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
	
	
	/* 주문취소 */
	function fn_orderCancel(){
		
		if(!confirm("주문을 취소하시겠습니까?")){
			return;
		}
		
		
		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/updateOrderCancel.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	        	
	        	if(option=="success"){
		        
	        		if(result.updateCnt == 1){
	        				
						alert("주문이 취소되었습니다.");
						
		        		var frm = document.form;
		        		
		        		frm.action = "<c:url value="/orderDetail.do" />";
		        	    frm.submit();
	        		}else{
	        			alert("취소에 실패 하였습니다");
	        		}
	        		fn_unBlockUI();
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
	
	function callNumber(num){
		location.href= "tel:"+num;
	}	
</script>

</head>

<body>
<form name="form" id="form">
<input type="hidden" id="orderIdx" name="orderIdx"  value="${orderStatusDetail.orderIdx}" />
<input type="hidden" id="orderIdx" name="from1"  value="${from1}" />
<input type="hidden" id="orderIdx" name="to1"  value="${to1}" />
<input type="hidden" id="orderIdx" name="ijuyu_name"  value="${ijuyu_name}" />
<input type="hidden" id="orderIdx" name="order_status"  value="${order_status}" />
<input type="hidden" id="S_CUS_CODE" name="S_CUS_CODE"  value="${S_CUS_CODE}" />

<div id="header">
	<div class="head">
    <a class="m_btn" href="#none"><img src="<c:url value="/img/btn_gnb.png" />" alt="gnb버튼"/></a>
    <h1>주문상세</h1>
    <c:if test="${from1 != null}">
    	<a class="btn_back" href="javascript:fn_orderList()">[뒤로가기]</a>
    </c:if>
    <c:if test="${from1 == null}">
    	<a class="btn_back" href="javascript:fn_member()">[뒤로가기]</a>
    </c:if>
    <div class="over"></div>
<%@ include file="/WEB-INF/jsp/front/main/menu.jsp"%>


<div id="main">
	<div class="sub_title">
    	<h2>주문정보</h2>
    </div>
	<div class="member_info">
        <table class="list_board" summay="주문상세 중 주문정보">
        <caption>주문정보</caption>
            <colgroup>
                <col style="width:90px;"/>
                <col>
            </colgroup>
            
            <c:if test="${orderStatusDetail != null}">
	            <tr>
	                <th class="info_date02">주문일자</th>
	                <td class="info_date02_an">${orderStatusDetail.orderDate}</td>
	            </tr>
	            <tr>
	                <th class="info_no02">주문번호</th>
	                <td class="info_no02_an">${orderStatusDetail.orderIdx}</td>
	            </tr>
	            <tr>
	                <th class="info_condition02">주문상태</th>
	                <td class="info_condition02_an">${orderStatusDetail.statName}</td>
	            </tr>
	            <tr>
	                <th class="info_calldate02">출하요청일</th>
	                <td class="info_calldate02_an">${orderStatusDetail.shipRqstDate}</td>
	            </tr>
	            <tr>
	                <th class="info_type02">정유사(상표)</th>
	                <td class="info_type02_an">${orderStatusDetail.brandDivisionName}</td>
	            </tr>
	            <tr>
	                <th class="info_prodname02">제품명</th>
	                <td class="info_prodname02_an">${orderStatusDetail.goodName}</td>
	            </tr>
	            <tr>
	                <th class="info_qty02">수량(리터)</th>
	                <td class="info_qty02_an"><fmt:formatNumber value="${orderStatusDetail.dealQty}" pattern="#,###.#" /></td>
	            </tr>
	            <tr>
	                <th class="info_a02">제품단가</th>
	                <td class="info_a02_an"><fmt:formatNumber value="${orderStatusDetail.salePrice}" pattern="#,###.#" /></td>
	            </tr>
	            <tr>
	                <th class="info_total02">제품금액</th>
	                <td class="info_total02_an"><fmt:formatNumber value="${orderStatusDetail.saleAmt}" pattern="#,###.#" /></td>
	            </tr>
				<tr>
	                <th class="info_shipplace02">수송방법</th>
	                <td class="info_shipplace02_an">${orderStatusDetail.arriveName}</td>
	            </tr>
				<tr>
	                <th class="info_shiptype02">출하처구분</th>
	                <td class="info_shiptype02_an">${orderStatusDetail.shipName}</td>
	            </tr>
	            
				<tr>
	                <th class="info_shipplace02">출하저유소</th>
	                <td class="info_shipplace02_an">${orderStatusDetail.nabName}</td>
	            </tr>
	             <tr>
	                <th class="info_ta02">수송단가</th>
	                <td class="info_ta02_an"><fmt:formatNumber value="${orderStatusDetail.saleTransPri}" pattern="#,###.#" /></td>
	            </tr>
	            <tr>
	                <th class="info_ttotal02">수송금액</th>
	                <td class="info_ttotal02_an"><fmt:formatNumber value="${orderStatusDetail.saleTransAmt}" pattern="#,###.#" /></td>
	            </tr>
	            <tr>
	                <th class="info_ttotal02">합계단가<br>(수송비포함)</th>
	                <td class="info_ttotal02_an"><fmt:formatNumber value="${orderStatusDetail.dealPrice}" pattern="#,###.#" /></td>
	            </tr>
	            <tr>
	                <th class="info_ttotal02">합계금액<br>(수송비포함)</th>
	                <td class="info_ttotal02_an"><fmt:formatNumber value="${orderStatusDetail.dealAmt}" pattern="#,###.#" /></td>
	            </tr>
				<tr>
	                <th class="info_car02">수송차량명</th>
	                <td class="info_car02_an">${orderStatusDetail.carName}
	                	<c:if test="${orderStatusDetail.driverName != ''}"> 
	                		/ ${orderStatusDetail.driverName} 
	                	</c:if>
	                	<c:if test="${orderStatusDetail.hpNo != ''}">
	                		/ <a onclick="javascript:callNumber('${orderStatusDetail.hpNo}')"> ${orderStatusDetail.hpNo} </a>
	                	</c:if>
	                </td>
	            </tr>
				<tr>
	                <th class="info_etc02">비고</th>
	                <td class="info_etc02_an"><textarea class="text-write02" readonly=true >${orderStatusDetail.remark}</textarea></td>
	            </tr>
	           </c:if>
        </table>
        
        
        <c:if test="${orderStatusDetail == null}">
	        <table class="jumun_list noData_wrap">
				<tr>
					<th class="noData">해당 데이터가 없습니다</th>
				</tr>
			</table>
		</c:if>
		
    </div>
    
    
    <!-- 주문중일경우에만 취소가 그낭함 -->
    <c:if test="${orderStatusDetail.orderStat eq '1'}">
		<div class="btn_orderwrap">
			<span class="btn_order">
	        	<button class="btn_order02" type="button" onclick="javascript:fn_orderCancel();" >주문취소</button>
			</span>
		</div>
	</c:if>
</div>	



</form>

</body>
</html>