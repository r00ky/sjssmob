<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

    <div class="gnb_wrap">
    	<ul class="gnb_top">
        	<li class="btn_home"><a href="javascript:fn_home();">홈으로가기버튼</a></li>
            <li class="gnb_logo"><span>e-Biz MOBILE SERVICE</span></li>
        	<li class="btn_closed"><a href="#none">메뉴닫기버튼</a></li>
        </ul>
        <ul class="gnb">
	    	<c:if test="${user.userDivide =='1'}">
	    		<c:if test="${user.cusGubun !='T'}">
		        	<li class="g_cont_01"><a href="javascript:fn_member();"><img src="<c:url value="/img/icon_con01.png"/>" alt="회원정보아이콘"><span>회원정보</span></a></li>
		            <li class="g_cont_02"><a href="<c:url value="/orderList.do" />"><img src="<c:url value="/img/icon_con02.png"/>" alt="주문아이콘"><span>주  문</span></a></li>
		            <li class="g_cont_03"><a href="<c:url value="/tradeList.do" />"><img src="<c:url value="/img/icon_con03.png"/>" alt="거래원장아이콘"><span>거래원장</span></a></li>	    	
	    		</c:if>
	    		<c:if test="${user.cusGubun =='T'}">
	    			<li class="g_cont_04"><a href="javascript:fn_carList();"><img src="<c:url value="/img/icon_con04.png"/>" alt="배차아이콘"><span>배  차</span></a></li>	
	    		</c:if>
	    	</c:if> 
	    	<c:if test="${user.userDivide =='2'}">
	        	<li class="g_cont_01"><a href="javascript:fn_member();"><img src="<c:url value="/img/icon_con01.png"/>" alt="회원정보아이콘"><span>회원정보</span></a></li>
	            <li class="g_cont_03"><a href="javascript:fn_tradeList();"><img src="<c:url value="/img/icon_con03.png"/>" alt="거래원장아이콘"><span>거래원장</span></a></li>	    	    	
	    	</c:if>       
        	<c:if test="${user.userDivide =='3'}">
	            <li class="g_cont_0402"><a href="javascript:fn_graph();"><img src="<c:url value="/img/icon_con0402.png"/>" alt="실시간매출현황"><span>실시간 매출현황</span></a></li>
	            <li class="g_cont_05"><a href="javascript:fn_finish();"><img src="<c:url value="/img/icon_con05.png"/>" alt="마감일보아이콘"><span>마감일보</span></a></li>
	            <li class="g_cont_06"><a href="javascript:fn_selltradeList();"><img src="<c:url value="/img/icon_con06.png"/>" alt="매출처거래원장아이콘"><span>매출처 거래원장</span></a></li>        	
        	</c:if>
        	<li><a href="javascript:fn_logout();"><img src="<c:url value="/img/icon_con_logout.png"/>" alt="로그아웃"><span>로그아웃</span></a></li>
        </ul>
     </div>   
    </div>
</div>
