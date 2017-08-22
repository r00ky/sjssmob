<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<title>Insert title here</title>
<%@ include file="/WEB-INF/jsp/comm/include.jsp"%>
<script src="<c:url value="/daumeditor/js/popup.js" />" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="<c:url value="/daumeditor/css/popup.css" />" type="text/css"  charset="utf-8"/>
<script type="text/javascript">
// <![CDATA[
    /* ready */
	$(document).ready(function(){
		
	});
            	
	function fn_save(){
		var frm = document.form;
		
    	frm.action = contextPath + "/fileUpload.do";
        frm.submit();
		
		
// 		fn_blockUI();
		
// 		jQuery.ajax({
// 			url           : contextPath + "/fileUpload.do",
// 			dataType      : "JSON",
// 			scriptCharset : "UTF-8",
// 			type          : "POST",
// 	        data          : $("#form").serialize(),
// 	        success: function(result, option) {
	        	
// 	        	if(option=="success"){
	        		
// 	        		alert(result.file_name);

// 	        	}else{
// 	        		alert("에러가 발생하였습니다.");
// 	        	}
	        	
// 	        	fn_unBlockUI();
// 	        },
// 	        error: function(result, option) {
// 	        	alert("에러가 발생하였습니다.");
// 	        	fn_unBlockUI();
// 	        }
// 		});
		
	}
	
	
// ]]>
</script>
</head>

<body>
<form name="form" id="form" method="post" enctype="multipart/form-data">
	
	
	<div class="wrapper">
		<div class="header">
			<h1>사진 첨부</h1>
		</div>	
		<div class="body">
			<dl class="alert">
			    <dt>확인</dt>
			    <dd>
			    	등록 버튼을 누르시면 사진첨부 됩니다.<br/> 
				</dd>
				<dd>
					<input type="hidden" id="fileName" readonly="readonly" />
					<input type="file" id="upFile" name="upFile" style="width: 450px;" onchange="javascript:document.getElementById('fileName').value = this.value"/>
				</dd>
			</dl>
		</div>
		<div class="footer">
			<ul>
				<li class="submit"><a href="#" onclick="fn_save();" title="등록" class="btnlink">등록</a> </li>
				<li class="cancel"><a href="#" onclick="closeWindow();" title="취소" class="btnlink">취소</a></li>
			</ul>
		</div>
	</div>
</form>
</body>
</html>