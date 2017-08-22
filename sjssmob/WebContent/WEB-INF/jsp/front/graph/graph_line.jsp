<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<%@ include file="/WEB-INF/jsp/comm/include.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/sub.css" />" />

<link rel="stylesheet" type="text/css" href="<c:url value="/css/nv.d3.css" />" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.2/d3.min.js" charset="utf-8"></script>
<script src="https://cdn.rawgit.com/novus/nvd3/v1.8.1/build/nv.d3.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="common/select2-3.4.5/select2.css" />" />
<script src="<c:url value="/common/select2-3.4.5/select2.js" />"></script>

<script language="JavaScript">
	var chart1;
	var chart2;
	var chart3;
	var chart4;
	
	/* ready */
	$(document).ready(function() {
	
		$("#BRANCH_CODE").select2({placeholder: "Select", allowClear: true});
	   
		fn_search();
	});
	


	/* 조회 */
	function fn_search(){
		
		fn_blockUI();
		
		jQuery.ajax({
			url           : "<c:url value="/selectGraph.do" />",
			dataType      : "JSON",
			scriptCharset : "UTF-8",
			type          : "POST",
	        data          : $("#form").serialize(),
	        success: function(result, option) {
	        	
	        	if(option=="success"){
		        	//##############################################################
		        	//유종별 판매량<드럼>
		        	//##############################################################
	        		var list1 = result.graph1[0];
		        	var max1 = Number(list1.totQty);
		        	
		        	var res1 = 
		    			[
		    			{  key: "무연",color: "#669900", values: [{"label" : "무연" ,"value" : list1.ugQty}]},
		    			  {key: "경유",color: "#ff9900", values: [{"label" : "경유" ,"value" : list1.kQty} ]},
		    			  {key: "등유",color: "#555555", values: [{"label" : "등유" ,"value" : list1.hQty} ]}
		    			];
		        	
		        	
		        	 nv.addGraph(function() {
		 		        chart1 = nv.models.multiBarHorizontalChart()
		 		            .x(function(d) { return d.label })
		 		            .y(function(d) { return d.value })
		 		            .duration(1000)
		 		            .height(150)
		 		            .showValues(true)
		 		            .forceY([0, max1])
		 		            .showYAxis(false)
		 		            .margin({top: 10, right: 10, bottom: 45, left: 60})
		 		            .showControls(false);

		 		        chart1.yAxis.tickFormat(d3.format(",0f"));
		 		        chart1.valueFormat(d3.format(",0f"));

		 		         var cause =  d3.select('#chart1 svg')
		 		            .datum(res1)
		 		            .call(chart1);

		 		        nv.utils.windowResize(chart1.update);
				        return chart1;
		 		    });

		        	//##############################################################
		        	//결제수단별판매량<드럼>
		        	//##############################################################		        	
	        		var list2 = result.graph2[0];
		        	var max2 = Number(list2.totQty);
		        	
		        	var res2 = 
		    			[
		    			{  key: "현금판매",color: "#d62728", values: [{"label" : "현금판매" ,"value" : list2.cashQty}]},
		    			  {key: "외상판매",color: "#1f77b4", values: [{"label" : "외상판매" ,"value" : list2.blcQty} ]},
		    			  {key: "상품권"  ,color: "#555555", values: [{"label" : "상품권"   ,"value" : list2.tktQty} ]},
		    			  {key: "신용카드",color: "#6633cc", values: [{"label" : "신용카드" ,"value" : list2.crdtQty} ]}
		    			];
		        	
		        	
		        	 nv.addGraph(function() {
		 		        chart2 = nv.models.multiBarHorizontalChart()
		 		            .x(function(d) { return d.label })
		 		            .y(function(d) { return d.value })
		 		            .duration(1000)
		 		            .height(150)
		 		            .showValues(true)
		 		            .forceY([0, max2])
		 		            .showYAxis(false)
		 		            .margin({top: 10, right: 10, bottom: 20, left: 60})
		 		            .showControls(false);

		 		        chart2.yAxis.tickFormat(d3.format(",0f"));
		 		        chart2.valueFormat(d3.format(",0f"));

		 		        d3.select('#chart2 svg')
		 		            .datum(res2)
		 		            .call(chart2);

		 		        nv.utils.windowResize(chart2.update);
				        return chart2;
		 		    });

		        	//##############################################################
		        	//유종별판매금액
		        	//##############################################################		        	 
	        		var list3 = result.graph3[0];
		        	var max3 = Number(list3.totAmt);
		        	
		        	var res3 = 
		    				[
			    			{  key: "무연",color: "#669900", values: [{"label" : "무연" ,"value" : list3.ugAmt}]},
			    			  {key: "경유",color: "#ff9900", values: [{"label" : "경유" ,"value" : list3.kAmt} ]},
			    			  {key: "등유",color: "#555555", values: [{"label" : "등유" ,"value" : list3.hAmt} ]}
			    			];
		        	
		        	
		        	 nv.addGraph(function() {
		 		        chart3 = nv.models.multiBarHorizontalChart()
		 		            .x(function(d) { return d.label })
		 		            .y(function(d) { return d.value })
		 		            .duration(1000)
		 		            .height(150)
		 		            .showValues(true)
		 		            .forceY([0, max3])
		 		            .showYAxis(false)
		 		            .margin({top: 10, right: 10, bottom: 45, left: 60})
		 		            .showControls(false);

		 		        chart3.yAxis.tickFormat(d3.format(",0f"));
		 		        chart3.valueFormat(d3.format(",0f"));

		 		        d3.select('#chart3 svg')
		 		            .datum(res3)
		 		            .call(chart3);

		 		        nv.utils.windowResize(chart3.update);
				        return chart3;
		 		    });		        	
	        		
	        		
		        	//##############################################################
		        	//결제수단별판매금액
		        	//##############################################################	        		
	        		var list4 = result.graph4[0];
		        	var max4 = Number(list4.totQty);
		        	
		        	var res4 = 
		    				[
			    			{  key: "현금판매",color: "#d62728", values: [{"label" : "현금판매" ,"value" : list4.cashAmt}]},
			    			  {key: "외상판매",color: "#1f77b4", values: [{"label" : "외상판매" ,"value" : list4.blcAmt} ]},
			    			  {key: "상품권"  ,color: "#555555", values: [{"label" : "상품권"   ,"value" : list4.tktAmt} ]},
			    			  {key: "신용카드",color: "#6633cc", values: [{"label" : "신용카드" ,"value" : list4.crdtAmt} ]}
			    			];
		        	
		        	
		        	 nv.addGraph(function() {
		 		        chart4 = nv.models.multiBarHorizontalChart()
		 		            .x(function(d) { return d.label })
		 		            .y(function(d) { return d.value })
		 		            .duration(1000)
		 		            .height(150)
		 		            .showValues(true)
		 		            .forceY([0, max4])
		 		            .showYAxis(false)
		 		            .margin({top: 10, right: 10, bottom: 20, left: 60})
		 		            .showControls(false);

		 		        chart4.yAxis.tickFormat(d3.format(",0f"));
		 		        chart4.valueFormat(d3.format(",0f"));

		 		        d3.select('#chart4 svg')
		 		            .datum(res4)
		 		            .call(chart4);

		 		        nv.utils.windowResize(chart4.update);
				        return chart4;
		 		    });
		      
		        	
	        	}else{
	        		alert("에러가 발생하였습니다1.");
	        	}
	        	fn_unBlockUI();
	        },
	        error: function(result, option) {
	        	alert("에러가 발생하였습니다2.");
	        	fn_unBlockUI();
	        }
	        
		});		
	}
		
		
</script>

</head>

<body>
<form name="form" id="form">


<div id="header">
	<div class="head"> <a class="m_btn" href="#none"><img src="<c:url value="/img/btn_gnb.png"/>" alt="gnb버튼"></a>
	<h1>실시간 매출현황</h1>
	<a class="btn_back" href="javascript:fn_home();">[뒤로가기]</a>
	<div class="over"></div>
<%@ include file="/WEB-INF/jsp/front/main/menu.jsp"%>    	


<div id="main">
	<div class="check_jumunwrap">
		<table class="check_jumun">
			<caption>
			실시간 매출현황 조회
			</caption>
			<colgroup>
                <col width="80"/>
                <col width="*"/>
                <col width="*"/>
			</colgroup>
			<tr>
				<td class="title_text"><label>영업일자</label></td>
				<td colspan="2"><span class="data_input wd_985 mg_l5"><input type="text" id="from1" name="from1"  value="${TO_DATE}"  /></span></td>
			</tr>
			<tr>
				<td class="pd_t1 title_text">소속지점</td>
				<td class="pd_t1">
                	<p class="mg_l5 wd95">
						<select class="jumun_01" title="소속지점" id="BRANCH_CODE" name="BRANCH_CODE">
							<c:forEach var="val" items="${branchCodeList}">
								<option value="${val.branchCode}">
									${val.branchName}
								</option>
							</c:forEach>      
						</select>
					</p>
                </td>
				<td class="pd_t1">
					<span class="btn_wrap">
						<button class="btn_check_all btn_check_all2" type="button" onclick="javascript:fn_search();" >조회</button>
					</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="graph_wrap">
		<div class="graph">
			<h4>1.유종별 판매량&lt;드럼&gt;</h4>
			<ul class="graph_list graph_list01">
				<div id="chart1" class='with-3d-shadow with-transitions'>
				    <svg style="height:120px;"> </svg>
				</div>				
			</ul>
		</div>
		<div class="graph">
			<h4>2.결제수단별 판매량&lt;드럼&gt;</h4>
			<ul class="graph_list graph_list02">
				<div id="chart2" class='with-3d-shadow with-transitions'>
				    <svg style="height:150px;"> </svg>
				</div>				
			</ul>
	  </div>
		<div class="graph">
			<h4>3.유종별 판매금액&lt;천원&gt;</h4>
			<ul class="graph_list graph_list01">
				<div id="chart3" class='with-3d-shadow with-transitions'>
				    <svg style="height:120px;"> </svg>
				</div>				
			</ul>
		</div>
		<div class="graph">
			<h4>4.결제수단별 판매금액&lt;천원&gt;</h4>
			<ul class="graph_list graph_list02">
				<div id="chart4" class='with-3d-shadow with-transitions'>
				    <svg style="height:150px;"> </svg>
				</div>				
			</ul>			
		</div>
	</div>
</div>
<a href="#" id="btnTop" class="btn_top" style="z-index: 100;"><img src="<c:url value="/img/btn_top.png"/>" alt="상단으로 이동"></a>


</form>

</body>
</html>