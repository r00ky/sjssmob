<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%
 pageContext.setAttribute("cr", "\r");
 pageContext.setAttribute("lf", "\n");
 pageContext.setAttribute("crlf", "\r\n");
%>
<script language="JavaScript">
	var contextPath = "${pageContext.servletContext.contextPath}";
</script>
<meta charset="UTF-8" />
<title>(주)승진상사</title>
<meta name="Description" content="(주)승진상사" />
<meta name="Keywords" content="주(주)승진상사" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width">

<link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui-1.10.3.custom.css" />" />


<script type="text/javascript" src="<c:url value="/js/jquery-1.11.2.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-ui.min.js" />"></script>
<script src="<c:url value="/js/jquery-ui-1.10.3.custom.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/ui.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.blockUI.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/common2.js" />"></script>

<script language="JavaScript">
	function fn_blockUI(){
		$.blockUI({ message: null }); 
	};
	
	function fn_unBlockUI(){
		$.unblockUI();
	};

	function ajaxSessionTimeout()
	{
		alert("세션이 종료되어 로그인 페이지로 이동합니다.");
		fn_home();
	}

	!function( $ )
	{
	    $.ajaxSetup({
	        statusCode: 
	        {
	            901: ajaxSessionTimeout
	        }
	    });
	    
		var userAgent = navigator.userAgent.toLowerCase(); // 접속 핸드폰 정보    
		
		// 모바일 홈페이지 바로가기 링크 생성 
		if(userAgent.match('iphone')) {     
			document.write('<link rel="apple-touch-icon" href="img/apple-touch-icon.png " />') 
		} else if(userAgent.match('ipad')) {
			document.write('<link rel="apple-touch-icon" sizes="72*72" href="img/apple-touch-icon.png "/>') 
		} else if(userAgent.match('ipod')) {
			document.write('<link rel="apple-touch-icon" href="img/apple-touch-icon.png " />') 
		} else if(userAgent.match('android')) {
			document.write('<link rel="shortcut icon" href="img/android-touch-icon.png " />') 
		} 
		
	}(window.jQuery);

	
	/* 로그아웃 */
	function fn_logout(){
		
		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/logout.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	
	        	if(option=="success"){
	        		var frm = document.form;
	        		
	        		frm.action = "<c:url value="/login.do" />";
	        	    frm.submit();	        		
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

	var dateOption = {
		showOn: "button",
		buttonImage: "<c:url value="/images/btn_cal.png" />",
		buttonImageOnly: true,
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		dateFormat: 'yy-mm-dd',
		showMonthAfterYear: true,
		yearSuffix: '년',
		onSelect: function( selectedDate ) {
		}           
	};



	var bg = "";
	var temp = "";
	
	/* 레이어 팝업 출력 */
	function layer_open(el){

		temp = $('#' + el);
		bg = temp.prev().hasClass('bg');	//dimmed 레이어를 감지하기 위한 boolean 변수

		if(bg){
			$('.layer').fadeIn();	//'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다. 
		}else{
			temp.fadeIn();
		}

		// 화면의 중앙에 레이어를 띄운다.
		if (temp.outerHeight() < $(document).height() ) temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
		else temp.css('top', '0px');
		if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
		else temp.css('left', '0px');


		$('.layer .bg').click(function(e){	//배경을 클릭하면 레이어를 사라지게 하는 이벤트 핸들러
			$('.layer').fadeOut();
		});

	}				

	/* 레이어 팝업 닫기 */
	function layer_close(){
		if(bg){
			$('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다. 
		}else{
			temp.fadeOut();
		}
	}	

	
	/* 홈 버튼 */
	function fn_home(){
		var frm = document.form;
		
		frm.action = "<c:url value="/main.do" />";
	    frm.submit();
	}
	
	/* 회원정보 버튼 */
	function fn_member(){
		var frm = document.form;
		
		frm.action = "<c:url value="/memberInfo.do" />";
	    frm.submit();
	}

	/* 주문목록 버튼 */
	function fn_orderList(){
		var frm = document.form;
		
		frm.action = "<c:url value="/orderList.do" />";
	    frm.submit();
	}

	/* 거래원장목록 버튼 */
	function fn_tradeList(){
		var frm = document.form;
		
		frm.action = "<c:url value="/tradeList.do" />";
	    frm.submit();
	}

	/* 배차목록 버튼 */
	function fn_carList(){
		var frm = document.form;
		
		frm.action = "<c:url value="/carList.do" />";
	    frm.submit();
	}
	
	/* 실시간매출현황 버튼 */
	function fn_graph(){
		var frm = document.form;
		
		frm.action = "<c:url value="/graph.do" />";
	    frm.submit();
	}	
	
	/* 마감일보 버튼 */
	function fn_finish(){
		var frm = document.form;
		
		frm.action = "<c:url value="/finishList.do" />";
	    frm.submit();
	}
	
	/* 매출처거래원장목록 버튼 */
	function fn_selltradeList(){
		var frm = document.form;
		
		frm.action = "<c:url value="/selltradeList.do" />";
	    frm.submit();
	}
	
	
	
</script>
