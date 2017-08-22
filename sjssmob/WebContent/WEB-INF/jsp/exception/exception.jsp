<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/WEB-INF/jsp/comm/include.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/error.css" />" />
</head>

<body id="error">
<form name="form" id="form">
	<div class="error_page02 clear">
		<div class="error_icon">
			<img src="img/error.gif" alt=""/>
		</div>	
		<div class="error_txt">
			<h1>현재 페이지에 <span>'에러'</span>가 있습니다.</h1>
			<p>불편을 끼쳐드려 죄송합니다. <br/>에러사항에 대해 고객센터 <span>031-973-5154</span>으로 문의하시기 바랍니다. <br/> 감사합니다.</p>
			<p class="mt10"><a href="javascript:fn_home();"><img src="<c:url value="/img/btn_home.gif" />" alt=""/></a></p>
		</div>	
	</div>
</form>
</body>
</html>
