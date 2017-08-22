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
	<div class="error_page">
    	<img src="<c:url value="/img/error.gif" />" alt="404 page not found error - 요청하신 페이지를 찾을 수 없습니다."/>
    </div>
	<div class="error_txt">
			<h1>404 page not found error - 요청하신 페이지를 찾을 수 없습니다.</h1>
			<p class="mt10"><a href="javascript:fn_home();"><img src="<c:url value="/img/btn_home.gif" />" alt=""/></a></p>
		</div>	
</form>
</body>

</html>
