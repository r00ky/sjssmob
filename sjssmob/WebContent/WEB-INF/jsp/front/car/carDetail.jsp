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
	
		/*차량번호 SELECTBOX 이벤트 추가*/
		$("#CAR_INFO").bind("change",fn_carInfo);
		
		/*차량 검색*/
		$("#CAR_INFO").select2({placeholder: "Select", allowClear: true}); 
		
	});
	


	/* 차량번호 선택시 이벤트 */
	function fn_carInfo(){
		var vals = $("#CAR_INFO").val().split("<<@>>");
		
		if(vals.length>1){
			//$("#DRIVER_NAME").html(vals[1]);
			//$("#HP_NO").html(vals[2]);
			$("#DRIVER_NAME").val(vals[1]);
			$("#HP_NO").val(vals[2]);
		}
	}
	

	/* 배차등록 */
	function fn_updateCarInfo(){
		
		if(!fn_validation()){
			return;
		}
		
		if(!confirm("배차 정보를 저장 하시겠습니까?")){
			return;
		}
		
		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/updateCarInfo.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	        	
	        	if(option=="success"){
		        
	        		if(result.updateCnt == 1){
	        				
						alert("배차 정보가 저장 되었습니다");
						
		        		var frm = document.form;
		        		
		        		frm.action = "<c:url value="/carDetail.do" />";
		        	    frm.submit();
	        		}else{
	        			alert("저장에 실패 하였습니다");
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


	/* 저장전 유효성 체크 */
	function fn_validation(){

		var carVals = $("#CAR_INFO").val().split("<<@>>");
		
		if(carVals[0].trim()==""){
			alert("차량번호를 선택 하세요");
			$("#CAR_INFO").focus();
			return false;
		}
		
		if($("#DRIVER_NAME").val()==""){
			alert("운전자명을 입력 하세요");
			$("#DRIVER_NAME").focus();
			return false;			
		}
		
		if($("#HP_NO").val()==""){
			alert("핸드폰 번호를 입력 하세요");
			$("#HP_NO").focus();
			return false;			
		}
		
		
			
		return true;
		
	}
	
</script>

</head>

<body>
<form name="form" id="form">
<input type="hidden" id="orderIdx" name="orderIdx"  value="${carDetail.orderIdx}" />
<input type="hidden" id="from3" name="from3"  value="${from1}" />
<input type="hidden" id="to3" name="to3"  value="${to1}" />
<input type="hidden" id="from4" name="from4"  value="${from2}" />
<input type="hidden" id="to4" name="to4"  value="${to2}" />
<input type="hidden" id="order_status" name="order_status"  value="${order_status}" />
<input type="hidden" id="goods_code" name="goods_code"  value="${goods_code}" />

<div id="header">
	<div class="head">
    <a class="m_btn" href="#none"><img src="img/btn_gnb.png" alt="gnb버튼"></a>
    <h1>배차상세</h1>
    <a class="btn_back" href="javascript:fn_carList();">[뒤로가기]</a>
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
                <col width="*"/>
            </colgroup>
            <tr>
                <th class="info_date02">주문일자</th>
                <td class="info_date02_an">${carDetail.orderDate}</td>
            </tr>
            <tr>
                <th class="info_no02">주문번호</th>
                <td class="info_no02_an">${carDetail.orderIdx}</td>
            </tr>
            <tr>
                <th class="info_condition02">주문상태</th>
                <td class="info_condition02_an">${carDetail.statName}</td>
            </tr>
             <tr>
                <th class="info_supply03 ">납품거래처<br>(정산거래처)</th>
                <td class="info_supply03_an">${carDetail.delvCusName}</td>
            </tr>
            <tr>
                <th class="info_calldate02">출하요청일</th>
                <td class="info_calldate02_an">${carDetail.shipRqstDate}</td>
            </tr>
            <tr>
                <th class="info_prodname02">제품명</th>
                <td class="info_prodname02_an">${carDetail.goodName}</td>
            </tr>
            <tr>
                <th class="info_qty02">수량(리터)</th>
                <td class="info_qty02_an">${carDetail.dealQty}</td>
            </tr>
			<tr>
                <th class="info_trance02">수송방법</th>
                <td class="info_trance02_an">${carDetail.arriveName}</td>
            </tr>
            <tr>
                <th class="info_shiptype02">출하처구분</th>
                <td class="info_shiptype02_an">${carDetail.shipName}</td>
            </tr>
            <tr>
                <th class="info_shipplace02">출하저유소</th>
                <td class="info_shipplace02_an">${carDetail.nabName}</td>
            </tr>
			<tr>
                <th class="info_car02">수송차량명</th>
                <td class="info_car02_an">
					
					<select id="CAR_INFO" name="CAR_INFO"  style="width:220px;" <c:if test="${carDetail.carNo != ''}"> disabled="disabled" </c:if>   >
						<option value=" <<@>> <<@>> <<@>> <<@>> ">선 택</option>
						<c:forEach var="val" items="${carNoList}">
							<option value="${val.carNo}<<@>>${val.driverName}<<@>>${val.driverHpNo}<<@>>${val.placeCode}<<@>>${val.carryQty}"   
								<c:if test="${carDetail.carNo == val.carNo}"> selected="selected"   </c:if> 
							>
								${val.carName}
							</option>
						</c:forEach>
					</select>
				</td>
            </tr>
			<tr>
                <th class="info_carname02">운전자명</th>
                <td class="info_carname02_an">
                	<c:if test="${carDetail.carNo == ''}">
                		<input type="text" name="DRIVER_NAME"      id="DRIVER_NAME" style="ime-mode:active; width:220px;" maxlength="5"  value=""/>
                	</c:if>
                	<c:if test="${carDetail.carNo != ''}">
                		<span id="DRIVER_NAME">${carDetail.driverName}</span>
                	</c:if>
                </td>
            </tr>
            <tr>
                <th class="info_carcall02 info_supply03">운전자<br>전화번호</th>
                <td class="info_carcall02_an">
                	<c:if test="${carDetail.carNo == ''}">
                		<input type="text" id="HP_NO" name="HP_NO"  style="width:220px; ime-mode:disabled;" maxlength="12" onkeydown="javascript:keypressNumber2();" />
                	</c:if>
                	<c:if test="${carDetail.carNo != ''}">
                		<span id="HP_NO">${carDetail.hpNo}</span>
                	</c:if>
                </td>
            </tr>
        </table>
     </div>   
     
	
	<c:if test="${carDetail.carNo == ''}">
		<div class="btn_orderwrap">
			<span class="btn_order">
	        	<button class="btn_order01" type="button" onclick="javascript:fn_updateCarInfo();" >배차등록</button>
			</span>
		</div>	
	</c:if>

</div>	
<a href="#" id="btnTop" class="btn_top" style="z-index: 100;"><img src="img/btn_top.png" alt="상단으로 이동"></a>

</form>

</body>
</html>