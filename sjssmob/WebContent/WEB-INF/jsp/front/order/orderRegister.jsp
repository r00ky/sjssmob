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
	
		
		$('.btn_act').click(function(){
			$('.loan_info').fadeIn(100);
			$('.over').fadeIn(100);
		});
		$('.btn_closed2').click(function(){
			$('.loan_info').fadeOut(100);
			$('.over').fadeOut(100);
		});
		
		
		var cusGubun = "${user.cusGubun}";
		var bussName = "${user.bussName}";

		/*납품거래처 SELECTBOX 이벤트 추가*/
		$("#DELV_CUS_CODE").bind("change",fn_selectDelvCust);

		/*운송방법 SELECTBOX 이벤트 추가*/
		$("#ARRIVE_SEC").bind("change",fn_selectArriveSec);

		/*출하구분 SELECTBOX 이벤트 추가*/
		$("#SHIP_FLAG").bind("change",fn_selectShipFlag);

		/*출하지 SELECTBOX 이벤트 추가*/
		$("#NAB_CODE").bind("change",fn_selectCost);

		/*저장소 SELECTBOX 이벤트 추가*/
		$("#SPO_CODE").bind("change",fn_selectCost);

		/*제품명 SELECTBOX 이벤트 추가*/
		$("#GOOD_CODE").bind("change",fn_selectGoodCode);

		/*차량번호 SELECTBOX 이벤트 추가*/
		$("#CAR_INFO").bind("change",fn_carInfo);

		/*수량 INPUT 이벤트 추가*/
		$("#DEAL_QTY").bind("keyup",fn_dealQty);

		/*차량 검색*/
		$("#CAR_INFO").select2({placeholder: "Select", allowClear: true}); 

		/*상표구분의 초기값은 "상표없음"*/
		$("#BRAND_DIVISION").val("99");

		/*출하구분의 초기값은 "저유소"*/
		$("#SHIP_FLAG").val("1");

		/*수송방법 초기값은 "공급자직배"*/
		$("#ARRIVE_SEC").val("ZS8");

		/*수송방법 초기값은 "공급자직배" 차량번호 선택 못함*/
		$("#CAR_INFO").select2("enable", false);

// 		/*남품처의 코드를 거래처코드로 기본셋팅 */
// 		var intSize = $("#DELV_CUS_CODE option").size();
		
//  	    for (var i = 0; i < intSize; i++)
//  	    {
//  			//$("#DELV_CUS_CODE option:eq("+ i +")").attr("selected", "selected");
			
//  			var vals = $("#DELV_CUS_CODE").val().split("<<@>>");
//  			$("#BUY_CUS_CODE").val(vals[2]);
//  			$("#BRAND_CODE").val(vals[3]);
//  			if(vals[0]=="${CUS_CODE}"){
//  				/* alert("로그인거래처 : " + "${CUS_CODE}" + "  "+"납품거래처 : " + vals[0]); */
//  				break;
//  			}
//  	    }

		/*딜러일경우 고객목록 조회*/
		if(cusGubun == "A") { /*사용자 정보를 조회한다*/
			/*거래처 미선택시 거래처 목록을 보여준다*/
			if("${cusUser.cusCode}"==""){
				$("#main").hide();
				fn_showCustList();
				$("#S_CUS_CODE").select2({placeholder: "Select", allowClear: true});
				
				/* 딜러 거래처 조회 */
				fn_searchAgentList();				
			}
		}else if(cusGubun == "R"){ /*법인사용자일경우*/
			/*거래처 미선택시 거래처 목록을 보여준다*/
			if("${RCUS_CODE}"==""){
				$("#main").hide();
				fn_showCustList();
				$("#S_CUS_CODE").select2({placeholder: "Select", allowClear: true});
				
				//법인 사업장 조회
				fn_searchReprList();
			}else{
				$("#CUS_CODE").val("${RCUS_CODE}");	
			}
		}

	});
	
	/* 거래처선택 - 주문등록 */
	function fn_orderDetail(cusCode){
		fn_blockUI();
		var frm = document.form;
		
		$("#CUS_CODE").val(cusCode);
		
		frm.action = "<c:url value="/orderRegister.do" />";
	    frm.submit();
	}

	
	/* 거래처 조회 */
	function fn_search(){
		var cusGubun = "${user.cusGubun}";
		
		//딜러 거래처 조회
		if(cusGubun == "A") {
			fn_searchAgentList();
		}
		//법인 소속 거래처 조회
		else if(cusGubun == "R"){
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
	        data          : {selectPageNo      : $("#selectPageNo").val()
	        	            ,S_CUS_CODE        : $("#S_CUS_CODE").val()
	        },
	        success: function(result, option) {
	        	
	        	if(option=="success"){
		        	var list = result.custList;
		        	$("#search_layer").html("");
		        	
		        	var txt ="";
		        	if(list.length>0){
		        		txt += "<table class='list-table01'><tbody>";
						txt += "<colgroup>";
						txt += "<col style='width:20%;'/>";
						txt += "<col style='width:40%;'/>";
						txt += "<col style='width:20%;'/>";
						txt += "<col style='width:20%;'/>";
						txt += "</colgroup>";
						
						$.each(list, function(key){
			        		var data = list[key];
			        		txt += "<tr>";
			        		txt += "<td>"+nvl(data.cusCode,'')+"</td>";
			        		txt += "<td>"+nvl(data.cusName,'')+"</td>";
			        		txt += "<td class=\"num\">"+numericComma(Number(nvl(data.orderAbleAmt,'')),0)+"</td>";
			        		txt += "<td class=\"right-line\"><button type=\"button\" class=\"btn00\" onclick=\"javascript:fn_orderDetail('"+nvl(data.cusCode,'')+"');fn_blockUI();\">주문선택</button></td>";
							txt += "</tr>";
			        	});
						
		        	}else{
		        		txt += "<tr><td class=\"right-line\" colspan=9'>데이터가 없습니다.</td></tr>";	
		        	}		        	

		        	txt += "</tbody></table>";
		        	$("#search_layer").append(txt);

		        	$("#search_layer").height($("#layer1_bg").height()-180);
		        	
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

	/* 페이지 이동 */
	function fn_agetnPageMove(selectPageNo)
	{	
		$("#selectPageNo").val(selectPageNo);
		fn_searchAgentList();		
	}
	

	/* 법인 거래처 조회 */
	function fn_searchReprList() {
		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/selectReprList.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : {selectPageNo      : $("#selectPageNo").val()
	        	            ,S_CUS_CODE        : $("#S_CUS_CODE").val()
	        },
	        success: function(result, option) {
	        	
	        	if(option=="success"){
		        	var list = result.custList;
		        	$("#search_layer").html("");
		        	
		        	var txt ="";
		        	if(list.length>0){
						txt += "<table class='list-table01'><tbody>";
						txt += "<colgroup>";
						txt += "<col style='width:25%;'/>";
						txt += "<col style='width:55%;'/>";
						txt += "<col style='width:20%;'/>";
						txt += "</colgroup>";				
						
						$.each(list, function(key){
			        		var data = list[key];
			        		txt += "<tr>";
			        		txt += "<td>"+nvl(data.cusCode,'')+"</td>";
			        		txt += "<td >"+nvl(data.cusName,'')+"</td>";
			        		txt += "<td ><button type=\"button\" class=\"btn00\" onclick=\"javascript:fn_orderDetail('"+nvl(data.cusCode,'')+"');fn_blockUI();\">주문선택</button></td>";
							txt += "</tr>";
			        	});
						
		        	}else{
		        		txt += "<tr><td class=\"\" colspan=3'>데이터가 없습니다.</td></tr>";	
		        	}		        	

		        	txt += "</tbody></table>";
		        	$("#search_layer").append(txt);
		        	
		        	$("#search_layer").height($("#layer1_bg").height()-180);

		        	
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

	/* 페이지 이동 */
	function fn_reprPageMove(selectPageNo)
	{	
		$("#selectPageNo").val(selectPageNo);
		fn_searchReprList();		
	}
	
	/* 거래처 조회 팝업 */
	function fn_showCustList(){
		layer_open('layer1');	
	}
	
	/*-- 인자 textarea, byte표시 text필드, 제한byte*/
	function fn_textCounter(theField,theCharCounter,maxChars)
	{
	    var strCharCounter = 0;
	    var intLength = theField.value.length;
	
	    for (var i = 0; i < intLength; i++)
	    {
	        var charCode = theField.value.charCodeAt(i);
	        if (charCode > 128)        {
	                strCharCounter += 2;
	        } else {
	                strCharCounter++;
	        }
	
	        if(strCharCounter < (maxChars+1)) {
	                theCharCounter.value = strCharCounter;
	        } else {
	            eval("alert('한글" + maxChars/2 + ", 영문" + maxChars+ "자 제한입니다. 초과된 문자는 잘립니다.')");
	            if(!fn_cutStr(theField, i, theCharCounter, maxChars)) {
	                    alert("문자열 커트 함수가 작동되지 않습니다.");
	            }
	            break;
	        }
	    }
	}	
	

	
	function fn_cutStr(theField, i, theCharCounter, maxChars)
	{
        var intLength = theField.value.length;

        var strChar = theField.value.substring(0,i);
        
        theField.value = strChar;
        textCounter(theField,theCharCounter,maxChars);
        return true;
	}
	

	/* 제품명 선택시 단가 조회  */
	function fn_selectGoodCode(){
		fn_selectCost(); /*단가조회시도*/
	}
	
	/* 단가 조회 */
	function fn_selectCost(){
		
		if($("#BRAND_CODE").val() ==""){
			var vals = $("#DELV_CUS_CODE").val().split("<<@>>");
			
			if(vals.length>1){
				$("#BUY_CUS_CODE").val(vals[2]);
				$("#BRAND_CODE").val(vals[3]);
			}else{
				$("#BUY_CUS_CODE").val("");
				$("#BRAND_CODE").val("");
			}
		}
		
		var brandCode = $("#BRAND_CODE").val();    /* 브랜드코드 */
		var buyCusCode = $("#BUY_CUS_CODE").val(); /* 매입거래처 */		
		
		
		var goodCode = $("#GOOD_CODE").val(); /* 제품명 */

		var shipFlag = $("#SHIP_FLAG").val(); /* 출하구분 */
		var nabCode  = shipFlag.trim() == "1" ? $("#NAB_CODE").val() : $("#SPO_CODE").val();  /* 출하지 or 저장소*/

		if(goodCode!=""){
			/* alert(brandCode + " " + buyCusCode + " " + goodCode); */
			
			fn_blockUI();
			
			jQuery.ajax({
				url           : "<c:url value="/selectOrderCost.do" />",
				dataType      : "JSON",
				scriptCharset : "UTF-8",
				type          : "POST",
		        data          : $("#form").serialize(),
		        success: function(result, option) {
		
		        	if(option=="success"){
		        		$("#BASE_PRICE").val(result.orderCost.basePrice);            /*기준단가*/
		        		$("#BUY_PRICE").val(result.orderCost.buyPrice);              /*매입단가*/
		        		$("#PRICE_BASE_DATE").val(result.orderCost.priceBaseDate);   /*단가기준일자*/
		        		$("#DEAL_PRICE").val(result.orderCost.salePrice);            /*거래단가*/
		        		
		        		var salePrice = Number(result.orderCost.salePrice);
		        		var saleTransPri = Number(result.orderCost.saleTransPri);
		        		
		        		$("#SALE_PRICE").text("");                        /*판매단가 초기화*/
		        		$("#SALE_PRICE").text(commify(salePrice));        /*판매단가 넣기*/
		        		$("#SALE_TRANS_AMT").text("");                    /*수송단가 초기화*/
		        		$("#SALE_TRANS_AMT").text(commify(saleTransPri)); /*수송단가 넣기*/
		        		
		        		fn_dealQty(); /*금액 계산 (단가 * 수량)*/
		        		
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
	}
	
	/* 수량으로 금액을 구함 (수량 * 단가) */
	function fn_dealQty(){
		var dealQty = $("#DEAL_QTY").val();
		var salePrice = $("#SALE_PRICE").text();
		var arriveSec = $("#ARRIVE_SEC").val();
		var saleTransAmt = $("#SALE_TRANS_AMT").text();
		
		
		if(dealQty!="" && salePrice!=""){
			$("#SALE_AMT").text(commify(Number(dealQty.replace(/,/gi,"")) * Number(salePrice.replace(/,/gi,""))));
			$("#DEAL_AMT").text(commify(Number(dealQty.replace(/,/gi,"")) * Number(salePrice.replace(/,/gi,""))));
		}else if(dealQty ==""){
			$("#SALE_AMT").text("");
			$("#DEAL_AMT").text("");
		}

		var saleAmt = $("#SALE_AMT").text();

		/* 수송방법이 공급자직배(ZS8)인 경우만 수송단가와 수송비 계산 */
		if(arriveSec == "ZS8"){
			/* 수송비 계산 */		
			if(dealQty!="" && saleTransAmt!=""){
				$("#SALE_TRANS_PRI").text("0");
				$("#SALE_TRANS_PRI").text(commify2(Number(saleTransAmt.replace(/,/gi,"")) / Number(dealQty.replace(/,/gi,""))));
			}else if(dealQty ==""){
				$("#SALE_TRANS_PRI").text("0");
			}

			/* 수송단가=매출단가+수송단가 계산 */
			var saleTransPri = $("#SALE_TRANS_PRI").text();
			
			if(salePrice!="" && saleTransPri!=""){
				$("#DEAL_PRICE").val("");
				$("#DEAL_PRICE").val(commify(Number(salePrice.replace(/,/gi,"")) + Number(saleTransPri.replace(/,/gi,""))));
			}else if(salePrice ==""){
				$("#DEAL_PRICE").val("");
			}
			
			/* 거래금액=매출금액+수송금액 계산 */
			if(saleAmt!="" && saleTransAmt!=""){
				$("#DEAL_AMT").text("");
				$("#DEAL_AMT").text(commify(Number(saleAmt.replace(/,/gi,"")) + Number(saleTransAmt.replace(/,/gi,""))));
			}else if(saleAmt ==""){
				$("#DEAL_AMT").text("");
			}

		/* 수송방법이 거래처자차(ZS7)인 경우 수송단가와 수송비 초기화 */
		}else if(arriveSec == "ZS7"){
			$("#DEAL_PRICE").val("");
			$("#DEAL_PRICE").val(commify(Number(salePrice.replace(/,/gi,""))));
			$("#DEAL_AMT").text(commify(Number(saleAmt.replace(/,/gi,""))));
			$("#SALE_TRANS_PRI").text("0");
			$("#SALE_TRANS_AMT").text("0");			
		}

	}
	
	/* 납품거래처 선택시 이벤트 */
	function fn_selectDelvCust(){
		var vals = $("#DELV_CUS_CODE").val().split("<<@>>");
		
		if(vals.length>1){
			$("#BUY_CUS_CODE").val(vals[2]);
			$("#BRAND_CODE").val(vals[3]);
		}else{
			$("#BUY_CUS_CODE").val("");
			$("#BRAND_CODE").val("");			
		}

		fn_selectCost();
	}

	/* 차량번호 선택시 이벤트 */
	function fn_carInfo(){
		var vals = $("#CAR_INFO").val().split("<<@>>");
		
		if(vals.length>1){
		}
		
		/* 수송비계산 */
		fn_dealQty();
		
	}
	

	/* 출하구분 선택시 출하지 SELECTBOX 이벤트 */
	function fn_selectShipFlag(){
		$('#SPO_CODE').val("");
		$('#NAB_CODE').val("");
		$("#SALE_TRANS_PRI").text("0");
		$("#CARRY_QTY").val("");
		$("#SALE_TRANS_AMT").text("0");
		
		if($("#SHIP_FLAG").val()==1){
			$('#SPO_CODE').css('display', 'none');
			$('#NAB_CODE').css('display', 'block');
		}else if($("#SHIP_FLAG").val()==2){
			$('#NAB_CODE').css('display', 'none');
			$('#SPO_CODE').css('display', 'block');
		}		
	}
	
	/* 출하구분 선택시 출하지 SELECTBOX 이벤트 */
	function fn_selectArriveSec(){

		/* 선택 타량번호 초기화 */
// 		$("#CAR_INFO").select2("data")[0];
		$("#CAR_INFO").select2("val", " <<@>> <<@>> <<@>> <<@>> ");
		
		if($("#ARRIVE_SEC").val()=="ZS7"){
			$("#CAR_INFO").select2("enable", true);
		}else{
			$("#CAR_INFO").select2("enable", false);
		}
		
		/* 수송비 조회 */
		fn_selectCost();
		
	}
	

	/* 숫자 세 자리마다 콤마 찍기 함수 */
    function commify(n) {
        var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
        n += '';                                       // 숫자를 문자열로 변환

        while (reg.test(n))
           n = n.replace(reg, '$1' + ',' + '$2');
        return n;
    }

	/* 숫자 세 자리마다 콤마 찍기 함수(소숫점 1자리에서 반올림) */
    function commify2(n) {
        var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
        n += '';                                       // 숫자를 문자열로 변환
        
        var m = Math.round(n*10)/10;
        var p = m.toString();
        
        while (reg.test(p))
            p = p.replace(reg, '$1' + ',' + '$2');
        return p;
    }
	

    /* 주문전 한도 체크 */
	function fn_checkLimit(){
	
		if(!fn_validation()){
			return;
		}
		
		if(!confirm("저장 하시겠습니까?")){
			return;
		}
		
		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/checkOrderLimit.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	
	        	if(option=="success"){
	        		if(result.limitYn == "N"){ /*한도가능*/
	        			$("#CREDIT_LIMIT").val(result.CREDIT_LIMIT) /*이전 여신한도 설정*/
	        			fn_save();
	        		}else {
	        			alert("한도 초과 입니다\n주문가능금액 : " + numericComma(result.ableAmt,0) +"\n주문금액 : "+numericComma(result.totalAmt,0)+"\n초과금액 : "+numericComma(result.overAmt,0));
	        		}
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
	
	/* 유류주문 저장 */
	function fn_save(){

		jQuery.ajax({
			url           : "<c:url value="/insertOrderDetailList.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	
	        	if(option=="success"){
        			
        			alert("저장이 완료되었습니다");
        			
	        		fn_unBlockUI();
	        		
	        		//주문목록 조회
	        		fn_orderList();
	        		
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
	

	/* 상품등록시 유효성 체크 */
	function fn_validation(){
		
		if($("#DELV_CUS_CODE").val()==""){
			alert("납품거래처를 선택 하세요");
			$("#DELV_CUS_CODE").focus();
			return false;
		}
		
		if($("#SHIP_RQST_DATE").val()==""){
			alert("출하요청일을 입력 하세요");
			$("#SHIP_RQST_DATE").focus();
			return false;
		}

		if(chkDate2($("#SHIP_RQST_DATE").val())!=1){
			alert("출하요청일이 날짜 형식이 아닙니다");
			$("#SHIP_RQST_DATE").focus();
			return false;
		}

		if($("#BRAND_DIVISION").val()==""){
			alert("정유사(상표)를  선택 하세요");
			$("#BRAND_DIVISION").focus();
			return false;
		}

		if($("#GOOD_CODE").val()==""){
			alert("제품명을 선택 하세요");
			$("#GOOD_CODE").focus();
			return false;
		}

		if($("#DEAL_QTY").val()=="" || $("#DEAL_QTY").val()=="0"){
			alert("수량을 입력 하세요");
			$("#DEAL_QTY").focus();
			return false;
		}
		

		if($("#ARRIVE_SEC").val()==""){
			alert("수송방법을 선택 하세요");
			$("#ARRIVE_SEC").focus();
			return false;
		}

		if($("#ARRIVE_SEC").val()=="ZS7"){
			var carVals = $("#CAR_INFO").val().split("<<@>>");
			
			if(carVals[0].trim()==""){
				alert("차량번호를 선택 하세요");
				$("#CAR_INFO").focus();
				return false;
			}			
		}

		if($("#SHIP_FLAG").val()==""){
			alert("출하구분을 선택 하세요");
			$("#SHIP_FLAG").focus();
			return false;
		}

		if($("#SHIP_FLAG").val()==1){
			if($("#ARRIVE_SEC").val()=="ZS8" && $("#NAB_CODE").val()==""){
				alert("출하지를 선택 하세요");
				$("#NAB_CODE").focus();
				return false;
			}
		}else if($("#SHIP_FLAG").val()==2){
			if($("#ARRIVE_SEC").val()=="ZS8" && $("#SPO_CODE").val()==""){
				alert("출하지를 선택 하세요");
				$("#SPO_CODE").focus();
				return false;
			}
		}	

		if($("#SALE_PRICE").text()=="0" || $("#SALE_PRICE").text()=="" ){
			alert("제품단가가 없으면 주문을 할수가 없습니다.");
			return false;
		}
		
		if($("#DEAL_AMT").text()=="0" || $("#DEAL_AMT").text()=="" ){
			alert("주문 금액이 없으면 주문을 할수가 없습니다.");
			return false;
		}
		
		$("#T_DEAL_AMT").val($("#DEAL_AMT").text());
		$("#T_SALE_PRICE").val($("#SALE_PRICE").text());
		$("#T_SALE_TRANS_PRI").val($("#SALE_TRANS_PRI").text());
		$("#T_SALE_TRANS_AMT").val($("#SALE_TRANS_AMT").text());
		$("#T_SALE_AMT").val($("#SALE_AMT").text());
				
		return true;
		
	}
	
	
	function fn_next(){
		$("#ARRIVE_SEC").focus();
		return;
	}
</script>

</head>

<body>
<form name="form" id="form">
<input type="hidden" name="CREDIT_LIMIT" id="CREDIT_LIMIT" value="0"/>
<input type="hidden" name="DEAL_AMT" id="T_DEAL_AMT" value="0"/>
<input type="hidden" name="SALE_PRICE" id="T_SALE_PRICE" value="0"/>
<input type="hidden" name="SALE_TRANS_PRI" id="T_SALE_TRANS_PRI" value="0"/>
<input type="hidden" name="SALE_TRANS_AMT" id="T_SALE_TRANS_AMT" value="0"/>
<input type="hidden" name="SALE_AMT" id="T_SALE_AMT" value="0"/>
<input type="hidden" name="CUS_CODE" id="CUS_CODE" value="${CUS_CODE}" readonly="readonly"/>

<input type="hidden" id="orderIdx" name="from1"  value="${from1}" />
<input type="hidden" id="orderIdx" name="to1"  value="${to1}" />
<input type="hidden" id="orderIdx" name="ijuyu_name"  value="${ijuyu_name}" />
<input type="hidden" id="orderIdx" name="order_status"  value="${order_status}" />

<div id="header">
	<div class="head">
		<a class="m_btn" href="#none"><img src="img/btn_gnb.png" alt="gnb버튼"></a>
		<h1>주문등록</h1>
		<c:if test="${user.cusGubun =='C'}">
			<a class="btn_back" href="javascript:fn_orderList();">[뒤로가기]</a>
		</c:if>
		<c:if test="${user.cusGubun !='C'}">
			<a class="btn_back" href="javascript:window.history.back();">[뒤로가기]</a>
		</c:if>
		<div class="over"></div>
		
<%@ include file="/WEB-INF/jsp/front/main/menu.jsp"%>		
		
<div id="main">
	<div class="sub_title">
		<h2>주문등록정보</h2>
		<span class="btn_act">
		<button class="btn_cancel btn_loan" type="button">여신현황</button>
		</span>
		<div class="loan_info">
			<div class="sub_title sub_title2">
				<h2>여신현황</h2>
				<span class="btn_closed2"><a href="#none">&nbsp;</a></span> </div>
			<table class="list_board list_board2" summay="여신현황팝업창">
				<caption>
				여신현황팝업창
				</caption>
				<colgroup>
				<col style="width:90px">
				<col>
				</colgroup>
				<tr>
					<th class="info_lonesum03">여신한도액</th>
					<td class="info_lonesum03_an"><fmt:formatNumber value="${cusCreditBO.creditLimit}" pattern="#,###" /></td>
				</tr>
				<tr>
					<th class="info_lm_sum03">전월채권잔액</th>
					<td class="info_lm_sum03_an"><fmt:formatNumber value="${cusCreditBO.prevCreditBlc}" pattern="#,###" /></td>
				</tr>
				<tr>
					<th class="info_tm_sum03">당월입금액</th>
					<td class="info_tm_sum03_an"><fmt:formatNumber value="${cusCreditBO.currRecvAmt}" pattern="#,###" /></td>
				</tr>
				<tr>
					<th class="info_tm_sum03">당월매출액</th>
					<td class="info_tm_sum03_an"><fmt:formatNumber value="${cusCreditBO.currSaleAmt}" pattern="#,###" /></td>
				</tr>
				<tr>
					<th class="info_order_sum03">주문가능액</th>
					<td class="info_order_sum03_an"><fmt:formatNumber value="${cusCreditBO.orderAbleAmt}" pattern="#,###" /></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="member_info oder_info">
		<table class="list_board" summay="주문등록">
			<caption>
			주문등록
			</caption>
			<colgroup>
			<col width="90"/>
			<col width="*"/>
			</colgroup>
			<tr>
				<th class="info_name03">거래처명</th>
				<td class="info_name03_an">
					<c:if test="${user.cusGubun =='C'}">${user.bussName}</c:if>
					<c:if test="${user.cusGubun =='A' || user.cusGubun =='R' }">${cusUser.bussName}</c:if>
				</td>
			</tr>
			<tr>
				<th class="info_supply03">납품거래처<br>(정산거래처)</th>
				<td class="info_supply03_an">
					<select title="납품거래처" style="width:220px" id="DELV_CUS_CODE" name="DELV_CUS_CODE"  >
						<option value="">선 택</option>
						<c:forEach var="val" items="${delvCustList}">
							<option value="${val.delvCusCode}<<@>>${val.transCusCode}<<@>>${val.buyCusCode}<<@>>${val.brandCode}"  <c:if test="${CUS_CODE == val.delvCusCode}">selected="selected"</c:if> >
								${val.delvCusName}
							</option>
						</c:forEach>					
					</select>
					<input type="hidden" name="BUY_CUS_CODE" id="BUY_CUS_CODE" value=""/>
					<input type="hidden" name="BRAND_CODE"   id="BRAND_CODE"   value=""/>					
					</td>
			</tr>
			<tr>
				<th class="info_calldate03">주문상태</th>
				<td class="info_calldate03_an">주문중</td>
			</tr>
			<tr>
				<th class="info_calldate02"><label>출하요청일</label></th>
				<td class="info_calldate02_an">
					<span class="data_input">
						<input type="text" id="SHIP_RQST_DATE" name="SHIP_RQST_DATE" readonly=true  style="width:220px;" maxlength="10" value="${TO_DATE}" />
					</span>
				</td>
			</tr>
			<tr>
				<th class="info_type03">정유사(상표)</th>
				<td class="info_type03_an">
					<select class="info_type_s" title="정유사(상표)구분"  style="width:220px;" id="BRAND_DIVISION" name="BRAND_DIVISION">
						<option value="">선 택</option>
						<c:forEach var="val" items="${brandDivisionList}">
							<option value="${val.commCode}">
								${val.commName}
							</option>
						</c:forEach>					
					</select>
				</td>
			</tr>
			<tr>
				<th class="info_prodname03">제품명</th>
				<td class="info_prodname03_an"><select title="제품명" style="width:220px;" id="GOOD_CODE" name="GOOD_CODE" >
						<option value=" <<@>> ">선 택</option>
						<c:forEach var="val" items="${goodCodeList}">
							<option value="${val.commCode}<<@>>${val.commName}">
								${val.commName}
							</option>
						</c:forEach>						
					</select>				
				</td>
			</tr>
			<tr>
				<th class="info_qty03">수량(리터)</th>
				<td class="info_qty03_an"><input type="text" id="DEAL_QTY" name="DEAL_QTY"  style="width:220px;" style="ime-mode:disabled;" maxlength="9" onkeyUp="if(this.value==''){this.value=''}else{this.value=num_format(this.value);}" onkeydown="javascript:if(event.keyCode==13){fn_next();}"  /></td>
			</tr>
			<tr>
				<th class="info_a03">제품단가</th>
				<td class="info_a03_an">
					<span id="SALE_PRICE"></span>
					<input type="hidden" name="BASE_PRICE"      id="BASE_PRICE"      value=""/>
					<input type="hidden" name="BUY_PRICE"       id="BUY_PRICE"       value=""/>
					<input type="hidden" name="PRICE_BASE_DATE" id="PRICE_BASE_DATE" value=""/>				
				</td>
			</tr>
			<tr>
				<th class="info_total03">제품금액</th>
				<td class="info_total03_an">
					<span id="SALE_AMT" class="wd-90p"></span>
					<input type="hidden" name="DEAL_PRICE"      id="DEAL_PRICE"      value=""/>
				</td>
			</tr>
			<tr>
				<th class="info_trance3">수송방법</th>
				<td class="info_trance03_an">
					<select title="수송방법" style="width:220px;" id="ARRIVE_SEC" name="ARRIVE_SEC">
					<option value="">선 택</option>
					<c:forEach var="val" items="${arvsecList}">
						<option value="${val.commCode}">
							${val.commName}
						</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="info_shiptype03">출하처구분</th>
				<td class="info_shiptype03_an">
					<select title="출하처구분" style="width:220px;" id="SHIP_FLAG" name="SHIP_FLAG">
						<option value="">선 택</option>
						<c:forEach var="val" items="${shpflgList}">
							<option value="${val.commCode}">
								${val.commName}
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="info_shipplace03">출하저유(장)소</th>
				<td class="info_shipplace03_an">
					<!-- 저유소 -->
					<select title="출하저유소" style="width:220px;" id="NAB_CODE" name="NAB_CODE" >
						<option value=" <<@>> ">선 택</option>
						<c:forEach var="val" items="${nabcodList}">
							<option value="${val.commCode}<<@>>${val.commName}">
								${val.commName}
							</option>
						</c:forEach>
					</select>
					<!-- 저장소 -->
					<select id="SPO_CODE" name="SPO_CODE"  style="width:220px; display: none;">
						<option value=" <<@>> ">선 택</option>
						<c:forEach var="val" items="${spocodList}">
							<option value="${val.commCode}<<@>>${val.commName}">
								${val.commName}
							</option>
						</c:forEach>
					</select>			
				</td>
			</tr>
             <tr>
				<th class="info_ta03">수송단가</th>
				<td class="info_ta03_an">
					<span id="SALE_TRANS_PRI"></span>
					<input type="hidden" name="CARRY_QTY"   id="CARRY_QTY"   value=""/>				
				</td>
			</tr>
			<tr>
				<th class="info_ttotal03">수송금액</th>
				<td class="info_ttotal03_an">
					<span id="SALE_TRANS_AMT" class="wd-90p"></span>
				</td>
			</tr>
			<tr>
				<th class="info_supply03">합계금액<br>(수송비포함)</th>
				<td class="info_ttotal03_an">
					<span id="DEAL_AMT"  class="wd-90p"></span>
					<input type="hidden" name="DEAL_PRICE"      id="DEAL_PRICE"      value=""/>	
				</td>
			</tr>
			<tr>
				<th class="info_car03">수송차량명</th>
				<td class="info_car03_an">
					<select id="CAR_INFO" name="CAR_INFO"  style="width:220px;">
						<option value=" <<@>> <<@>> <<@>> <<@>> <<@>> ">선 택</option>
						<c:forEach var="val" items="${carNoList}">
							<option value="${val.carNo}<<@>>${val.driverName}<<@>>${val.driverHpNo}<<@>>${val.placeCode}<<@>>${val.carryQty}<<@>>${val.carName}">
								${val.carName}
							</option>
						</c:forEach>
					</select>							
				</td>
			</tr>
			<tr>
				<th class="info_etc03">비고</th>
				<td class="info_etc03_an">
					<textarea class="text-write03" id="REMARK" name="REMARK"  onKeyUp="fn_textCounter(REMARK, remChars, 100);" ></textarea>
					<input type="text" name="remChars" id="remChars" value="0" style="background-color:transparent;border:0; width: 30px; text-align: right; color: red;padding-right: 3px;" readonly>Bytes / 100Bytes
				</td>
			</tr>
		</table>
	</div>
	<div class="btn_orderwrap"> <span class="btn_order">
		<button class="btn_order01" type="button" onclick="javascript:fn_checkLimit();">주문등록</button>
		</span> </div>
</div>



<!-- 고객목록 -->
<div class="layer" id="layer1_bg" style=" z-index:501;">
	<div class="bg"></div>
	<div id="layer1" class="pop-layer wd-98p"  style="height:98%;" >
		<div class="pop-container2">
			<div class="pop-conts">
				<!--content //-->
				<p class="ctxt mb20">
				
					<div class="boardtypeA_viewbox mt_5">
						<table width="100%">
							<colgroup>
								<col width="25%">
								<col width="75%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row"><span class="must text_red">거래처</span></th>
									<td>
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
									<td class="" colspan="2"><span class="btn_wrap"><button class="btn_check_all btn_check_all2"  onclick="javascript:fn_search();" type="button">조회</button></span></td>
								</tr>
							</tbody>
					  	</table>
					</div>
					
					<!-- 딜러 -->
					<c:if test="${user.cusGubun =='A'}">
						<div class="table-wrap mt_5">
							<table class="list-table" >
								<colgroup>
									<col style="width:20%;"/>
									<col style="width:40%;"/>
									<col style="width:20%;"/>
									<col style="width:20%;"/>
								</colgroup>
								<thead>
									<tr>
										<th>거래처코드</th>
										<th>거래처명</th>
										<th>주문가능금액</th>
										<th class="right-line">선택</th>
									</tr>
								</thead>
							</table>
							<div id="search_layer" style="overflow: scroll; height: 100%;" ></div>
						</div>
					</c:if>
					
					
					<!-- 법인 -->
					<c:if test="${user.cusGubun =='R'}">
						<div class="table-wrap mt_5">
							<table class="list-table" >
								<colgroup>
									<col style="width:25%;"/>
									<col style="width:55%;"/>
									<col style="width:20%;"/>
								</colgroup>
								<thead>
									<tr>
										<th>거래처코드</th>
										<th>거래처명</th>
										<th class="right-line">선택</th>
									</tr>
								</thead>
							</table>
							<div id="search_layer" style="overflow: scroll; height: 100%;" ></div>
						</div>					
					</c:if>										
					
				</p>
				<!--// content-->
			</div>
		</div>
	</div>
</div>


</form>

</body>
</html>