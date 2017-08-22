<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<%@ include file="/WEB-INF/jsp/comm/include.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/log.css" />" />

<script language="JavaScript">
	/* ready */
	$(document).ready(function() {
		
		//로그인 여부 체크
		var cusCode = "${user.cusCode}";
		if(cusCode!=""){//세션 정보가 존재할경우
			location.href = "<c:url value="/main.do" />";
		}

		//쿠키 정조 조회
		var user_id = GetCookie("user_id");
		if(user_id!=""){
			$("#user_id").val(user_id);
			$("#save_loginfo").attr("checked", true);
		}
		
		$("#user_id").focus();
		
		/*사용자 소속 콤보박스 이벤트 추가*/
		$("#user_divide").bind("change",fn_selectUserDivide);
		
		
		
	});
	
	/* 사용자 소속 콤보박스 변경 */
	function fn_selectUserDivide(){
		
		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/selectBranchCode.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : {user_divide : $("#user_divide").val()},
	        success: function(result, option) {
	
	        	if(option=="success"){
	        	
	        		var list = result.branchCodeList;

					$("#branch_divide").html("");
		        	
		        	var txt ="";
		        	if(list.length>0){
						
						$.each(list, function(key){
			        		var data = list[key];
			        		txt += "<option value="+data.branchCode+">";
			        		txt += data.branchName;
							txt += "</option>";
			        	});
						
						$("#branch_divide").html(txt);
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

	/* 로그인 */
	function fn_login(){
		
		if($("#user_id").val()==""){
			alert("아이디를 입력하세요");
			$("#user_id").focus();
			return;
		}
		if($("#user_password").val()==""){
			alert("비밀번호를 입력하세요");
			$("#user_password").focus();
			return;
		}
		
		fn_blockUI();
		
		if($("#save_loginfo").is(":checked")) //id저장
			SetCookie("user_id", $("#user_id").val());
		else
			DelCookie("user_id");
		
		jQuery.ajax({
			url           : "<c:url value="/chkLogin.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	        	
	        	if(option=="success"){
	        		var passYn = result.passYn;
	        		
	        		if(passYn=="N"){
	        			alert("로그인에 실패했습니다. 사용자 정보를 확인하십시오.");
	        			fn_unBlockUI();
	        		}else{
	        			
	        			//최초 로그인시 비밀번호 변경
	        			if(result.user.firstloginYn=="Y"){
	        				fn_unBlockUI();
	        				layer_open('layer');
	        			}else{
	        				location.href = "<c:url value="/main.do" />";
	        			}
	        			
	        		}
	        		
	        	}else{
	        		alert("에러가 발생하였습니다.");
	        		fn_unBlockUI();
	        	}
	        },
	        error: function(result, option) {
	        	alert("에러가 발생하였습니다.");
	        	fn_unBlockUI();
	        }
		});
		
	}

	//쿠키생성
	function SetCookie(cKey, cValue)  // name,pwd
	{
	    var date = new Date(); // 오늘 날짜
	    // 만료시점 : 오늘날짜+999 설정
	    var validity = 999;
	    date.setDate(date.getDate() + validity);
	    // 쿠키 저장
	    document.cookie = cKey + '=' + escape(cValue) + ';expires=' + date.toGMTString();
	}

	//쿠키 삭제
	function DelCookie(cKey) {
	    var date = new Date(); // 오늘 날짜 
	    var validity = -1;
	    date.setDate(date.getDate() + validity);
	    document.cookie = cKey + "=;expires=" + date.toGMTString();
	}

	//쿠키 가져오기
	function GetCookie(cKey) {
	    var allcookies = document.cookie;
	    var cookies = allcookies.split("; ");
	    for (var i = 0; i < cookies.length; i++) {
	        var keyValues = cookies[i].split("=");
	        if (keyValues[0] == cKey) {
	            return unescape(keyValues[1]);
	        }
	    }
	    return "";
	}


	/* 새로운 비밀번호 저장 */
	function fn_updateNewPass(){
		
		/*등록전 유효성 검사*/
		if(!fn_validation()){
			return;
		}
		
		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/updateNewPass.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	        	
	        	if(option=="success"){
        			alert("비밀번호가 변경되었습니다.");
        			layer_close();
        			fn_unBlockUI();
        			
        			//메인으로 이동
        			fn_home();
    	        	
	        	}else{
	        		alert("에러가 발생하였습니다.");
	        		layer_close();
		        	fn_logout();
	        	}
	        },
	        error: function(result, option) {
	        	alert("에러가 발생하였습니다.");
	        	layer_close();
	        	fn_logout();
	        	
	        }
		});
		
	}	
	

	/* 비밀번호 유효성 체크 */
	function fn_validation(){
		
		if($("#NEW_WEB_PASSWORD").val()==""){
			alert("새 비밀번호를 입력하세요");
			$("#NEW_WEB_PASSWORD").focus();
			return false;
		}
	    if($("#NEW_WEB_PASSWORD").val().length<8){
	        alert("비밀번호는 문자, 숫자, 특수문자의 조합으로 8~16자리로 입력해주세요.");
	        $("#NEW_WEB_PASSWORD").focus();
	        return false;
	    }

	    if(!$("#NEW_WEB_PASSWORD").val().match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/))
	    {
	        alert("비밀번호는 문자, 숫자, 특수문자의 조합으로 8~16자리로 입력해주세요.");
	        $("#NEW_WEB_PASSWORD").focus();
	        return false;
	    }


	    var SamePass_0 = 0; //동일문자 카운트
	    var SamePass_1 = 0; //연속성(+) 카운드
	    var SamePass_2 = 0; //연속성(-) 카운드

	    var chr_pass_0;
	    var chr_pass_1;
	    var chr_pass_2;

	    for(var i=0; i < $("#NEW_WEB_PASSWORD").val().length; i++)
	    {
	        chr_pass_0 = $("#NEW_WEB_PASSWORD").val().charAt(i);
	        chr_pass_1 = $("#NEW_WEB_PASSWORD").val().charAt(i+1);
	        
	        //동일문자 카운트
	        if(chr_pass_0 == chr_pass_1)
	        {
	            SamePass_0 = SamePass_0 + 1
	        }
	        
	       
	        chr_pass_2 = $("#NEW_WEB_PASSWORD").val().charAt(i+2);
	        //연속성(+) 카운드

	        if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == 1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == 1)
	        {
	            SamePass_1 = SamePass_1 + 1
	        }
	        
	        //연속성(-) 카운드
	        if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == -1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == -1)
	        {
	            SamePass_2 = SamePass_2 + 1
	        }
	    }
	    if(SamePass_0 > 1){
	        alert("동일문자를 3번 이상 사용할 수 없습니다.");
	        $("#NEW_WEB_PASSWORD").focus();
	        return false;
	    }
	   
	    if(SamePass_1 > 1 || SamePass_2 > 1 ){
	        alert("연속된 문자열(123 또는 321, abc, cba 등)을\n 3자 이상 사용 할 수 없습니다.");
	        $("#NEW_WEB_PASSWORD").focus();
	        return false;
	    }
	    
		if($("#NEW_WEB_PASSWORD2").val()==""){
			alert("새 비밀번호 확인을 입력하세요");
			$("#NEW_WEB_PASSWORD2").focus();
			return;
		}
		
		if($("#NEW_WEB_PASSWORD").val() != $("#NEW_WEB_PASSWORD2").val()){
			alert("비밀번호가 다릅니다\n비밀번호를 확인하세요");
			$("#NEW_WEB_PASSWORD2").focus();
			return;
		}
		
		return true;
		
	}
		

</script>

</head>

<body>
<form name="form" id="form">
<div id="log_wrap">
  <div class="icon_login"> <img src="<c:url value="/img/bg_login_img.png" />" alt="로그인아이콘BG"> </div>
  <div class="log">
    <ul class="area_type">
      <li>
        <label for="user_divide"></label>
        <select id="user_divide" name="user_divide">
			<c:forEach var="val" items="${userKindList}">
				<option value="${val.commCode}">
					${val.commName}
				</option>
			</c:forEach>
        </select>
      </li>
      <li>
        <label for="branch_divide"></label>
        <select id="branch_divide" name="branch_divide">
			<c:forEach var="val" items="${branchCodeList}">
				<option value="${val.branchCode}">
					${val.branchName}
				</option>
			</c:forEach>        
        
        </select>
      </li>
    </ul>
    <ul class="area_login">
      <li>
        <label for="user_id" id="user_idLabel" style="position:absolute;left:8px;width:200px;"></label>
        <input type="text" id="user_id" name="user_id"  style="lineheight:35px;" />
      </li>
      <li class="btn_loginwrap"> 
        <button  class="btn_login" type="button" id="btn_login"  onclick="javascript:fn_login();" ><img src="<c:url value="/img/btn_login.png" />" alt="로그인버튼이미지"></button>
      <</li>
      <li>
        <label for="user_password" id="user_passwordLabel" style="position:absolute;left:8px;width:200px;"></label>
        <input type="password" id="user_password" name="user_password" style="lineheight:35px;"/>
      </li>
    </ul>
    <ul class="area_logcheck">
      <li>
        <input type="checkbox" id="save_loginfo"/>
      </li>
      <li>
        <label for="save_loginfo"><span>로그인 정보 저장</span></label>
    </ul>
    <div class="info_company">
      <ul>
        <li class="logo"><img src="<c:url value="/img/logo_apc.png" />" alt="(주)승진상사 로고"></li>
        <li>회원가입은 담당 영업사원에게 문의해 주시기 바랍니다.</li>
      </ul>
      <div class="copylight">
          <p>
           <strong>대표이사 :</strong>&nbsp;송경호 
           <strong>&nbsp;&nbsp;사업자번호 :</strong>&nbsp;314-81-34126 
          </p>
          <p>
          <strong>대표번호 :</strong>&nbsp;(042)523-5466 <strong>&nbsp;&nbsp;FAX :</strong>&nbsp;042)522-4104 </p>
          <p><strong>주소 :</strong>&nbsp;대전광역시 중구 계백로 1625, 602호(유천동, 벽산빌딩)</p>
          <p>Copyright ⓒ Seung Jin Co.,Ltd All right reserved.</p>
      </div>
      <em>프로그램 도입 문의 : 우리소프트 HP.010-3739-8901</em> 
    </div>
  </div>
</div>


<div class="layer">
	<div class="bg"></div>
	<div id="layer" class="pop-layer">
		<div class="pop-container">
			<div class="pop-conts">
				<!--content //-->

				<p class="ctxt mb20">
				
					<div class="pop-head">
						<h1>비밀번호변경</h1>
					</div>
					
					<p class="pop-txt">최초로 로그인 하셨습니다. <br/>보안을 위해 비밀번호를 변경해주세요.</p>
					<p>* 문자, 숫자, 특수문자의 조합으로 <span style="color: red;"><b>8~16자리</b></span> 이상 사용</p>
					<p>* 동일문자 <span style="color: red;"><b>3번 이상</b></span> 사용 금지</p>
					<p>* 연속된 문자, 숫자 <span style="color: red;"><b>3자 이상</b></span> 사용 금지</p>
					
					<div class="boardtypeA_viewbox mt_5">
						<table width="100%">
							<colgroup>
								<col width="25%">
								<col width="75%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row"><span class="must">새비밀번호</span></th>
									<td class="line_top pdl_10">
										<input type="password" id="NEW_WEB_PASSWORD" name="NEW_WEB_PASSWORD" style="ime-mode:disabled;" class="text01 wd-90p" maxlength="16"/>
									</td>
								</tr>
								<tr>
									<th scope="row"><span class="must">새비밀번호 확인</span></th>
									<td class="pdl_10">
										<input type="password" id="NEW_WEB_PASSWORD2" name="NEW_WEB_PASSWORD2" style="ime-mode:disabled;" class="text01 wd-90p" maxlength="16"/>
									</td>
								</tr>
								
							</tbody>
					  	</table>
					</div>
				</p>

				<div class="mt_20 text-c">
					<a href="javascript:fn_updateNewPass();" id="btn_save" class="btn btn-primary">저장</a>
					<a href="javascript:layer_close();fn_logout();" class="btn btn-gray">닫기</a>
					
				</div>
       
        
				<!--// content-->
			</div>
		</div>
	</div>
</div>



</form>

</body>
</html>