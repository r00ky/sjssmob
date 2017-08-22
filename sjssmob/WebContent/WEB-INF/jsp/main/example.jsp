<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/WEB-INF/jsp/comm/include.jsp"%>
<script language="JavaScript">

</script>

</head>
<body id="login">
	테스트 페이지<br/>
	dbtest   : ${exampleKey}<br/>
	aes256   : ${aes256}<br/>
	ipaddr   : ${ipaddr}<br/>
	seedCBC  : ${seedCBC}<br/>
	사용자ID : ${user.bussNo}  <br/>
	관리자ID : ${adm_user.adminid} <c:if test="${adm_user.adminid !=null}"> &nbsp;&nbsp;&nbsp; <a href="javascript:fn_admLogout();" class="bt">[로그아웃]</a> </c:if> 

<p><h3>일반사용자</h3></p>
<a href="<c:url value="/main.do" />">메인페이지</a><br/>
<a href="<c:url value="/login.do" />">로그인</a><br/>

<br/>[일반 사용자]<br/>
<a href="<c:url value="/boardList.do" />">회원정보 > 회원정보</a><br/>

<a href="<c:url value="/boardList.do" />">유류주문 > 유류주문 </a><br/>
<a href="<c:url value="/boardList.do" />">유류주문 > 주문현황 </a><br/>
<a href="<c:url value="/boardList.do" />">유류주문 > 거래원장 </a><br/>

<br/>[딜러 사용자]<br/>
<a href="<c:url value="/boardList.do" />">회원정보 > 회원정보</a><br/>

<a href="<c:url value="/boardList.do" />">유류주문 > 유류주문 </a><br/>
<a href="<c:url value="/boardList.do" />">유류주문 > 주문현황 </a><br/>
<a href="<c:url value="/boardList.do" />">유류주문 > 거래원장 </a><br/>
<br/>[통합 사용자]<br/>
<a href="<c:url value="/boardList.do" />">회원정보 > 회원정보</a><br/>

<a href="<c:url value="/boardList.do" />">유류주문 > 유류주문 </a><br/>
<a href="<c:url value="/boardList.do" />">유류주문 > 주문현황 </a><br/>
<a href="<c:url value="/boardList.do" />">유류주문 > 거래원장 </a><br/>

<br/>[수송배차 사용자]<br/>
<a href="<c:url value="/boardList.do" />">차량배차 > 차량배차 </a><br/>
<a href="<c:url value="/boardList.do" />">차량배차 > 수송차량현황 </a><br/>

<br/>[공통]<br/>
<a href="<c:url value="/noticeList.do" />">공지사항 목록 (페이징) *완료*</a><br/>
<a href="<c:url value="/noticeDetail.do?IDX=1" />">공지사항 상세 *완료*</a><br/><br/>

<a href="<c:url value="/qnaList.do" />">Q&amp;A목록(페이징) *완료*</a><br/>
<a href="<c:url value="/qnaDetail.do" />">Q&amp;A등록, 수정 *완료*</a><br/>
<a href="<c:url value="/qnaView.do" />">Q&amp;A상세 *완료*</a><br/><br/>

<a href="<c:url value="/faqList.do" />">FAQ조회(페이징) *완료*</a><br/><br/>

<a href="<c:url value="/contactDetail.do" />">제휴문의 - 등록  *완료*</a><br/>

<p><h3>관리자</h3></p>
<a href="<c:url value="/admin/admLogin.do" />">로그인 *완료*</a><br/><br/>

<a href="<c:url value="/admin/adminList.do" />">사용자관리목록(페이징) *완료*</a><br/>
<!-- 
<a href="<c:url value="/admin/adminDetail.do?IDX=5" />">사용자관리 등록, 수정 *완료*</a><br/><br/>
 -->
<a href="<c:url value="/admin/noticeList.do" />">공지사항목록(페이징) *완료*</a><br/>
<!--
<a href="<c:url value="/admin/noticeDetal.do?IDX=1" />">공지사항등록,수정 *완료*</a><br/><br/>
-->
<a href="<c:url value="/admin/qnaList.do" />">Q&amp;A목록(페이징) - 삭제 *완료*</a><br/>
<!--
<a href="<c:url value="/admin/qnaDetail.do?IDX=36" />">Q&amp;A상세,수정(답하기)[상태변경] *완료*</a><br/><br/>
-->
<a href="<c:url value="/admin/faqList.do" />">FAQ목록(페이징) - 삭제 *완료*</a><br/>
<!--
<a href="<c:url value="/admin/faqDetail.do?IDX=1" />">FAQ등록, 수정 *완료*</a><br/><br/>
-->
<a href="<c:url value="/admin/contactList.do" />">제휴문의목록(페이징) - 삭제 *완료*</a><br/>
<!--
<a href="<c:url value="/admin/contactDetail.do?IDX=137" />">제휴문의상세 *완료*</a><br/><br/>
-->
</body>
</html>
