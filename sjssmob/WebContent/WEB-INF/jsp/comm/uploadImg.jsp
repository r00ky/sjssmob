<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<%@ include file="/WEB-INF/jsp/comm/include.jsp"%>
<script src="<c:url value="/daumeditor/js/popup.js" />" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="<c:url value="/daumeditor/css/popup.css" />" type="text/css"  charset="utf-8"/>
<script type="text/javascript">
// <![CDATA[
    /* ready */
	$(document).ready(function(){
		//alert("ready!!  " + contextPath);
		
		//조회
		//fn_search();
		initUploader();
	});
            	
	function done() {
		alert("2");
		if (typeof(execAttach) == 'undefined') { //Virtual Function
	        return;
	    }
		
		var _mockdata = {
			'imageurl': 'http://cfile284.uf.daum.net/image/116E89154AA4F4E2838948',
			'filename': 'editor_bi.gif',
			'filesize': 640,
			'imagealign': 'C',
			'originalurl': 'http://cfile284.uf.daum.net/original/116E89154AA4F4E2838948',
			'thumburl': 'http://cfile284.uf.daum.net/P150x100/116E89154AA4F4E2838948'
		};
		alert("3");
		execAttach(_mockdata);
		closeWindow();
	}

	function initUploader(){
	    var _opener = PopupUtil.getOpener();
	    if (!_opener) {
	        alert('잘못된 경로로 접근하셨습니다.');
	        return;
	    }
	    
	    var _attacher = getAttacher('image', _opener);
	    registerAction(_attacher);
	    done();
	}	
</script>
</head>

<body>
</body>
</html>
