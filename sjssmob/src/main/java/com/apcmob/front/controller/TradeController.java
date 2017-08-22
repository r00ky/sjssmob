package com.apcmob.front.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.apcmob.front.service.OrderService;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.util.Common;


@Controller
public class TradeController {

	@Resource(name = "orderService")
	private OrderService orderService;
	
	private final Log log = LogFactory.getLog(getClass());

	//AJAX
	@Autowired
	MappingJacksonJsonView jsonView;
	
	@RequestMapping("/tradeList.do")
	public String pageTradeList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		//거래처 콤보박스 조회
		orderService.pageOrderList(request, modelMap, session);
		
		modelMap.put("TO_DATE"     ,Common.getDate((long)-1));
		
		//조회조건
		modelMap.put("from1",        request.getParameter("from1"));
		modelMap.put("to1",          request.getParameter("to1"));
		modelMap.put("S_CUS_CODE",    request.getParameter("S_CUS_CODE"));
		
		return "front/trade/tradeList";
	}
	
	//거래원장 목록 조회
	@RequestMapping("/selectTradeList.do")
	public void selectTradeList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		orderService.selectTradeList(request, modelMap, session);
		orderService.selectTradeSum(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}		
	
	@RequestMapping("/tradeDetail.do")
	public String pageTradeDetail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);
		
		orderService.selectTradeDetail(request, modelMap, session);
		
		//조회조건
		modelMap.put("from1",        request.getParameter("from1"));
		modelMap.put("to1",          request.getParameter("to1"));
		modelMap.put("S_CUS_CODE",    request.getParameter("S_CUS_CODE"));
		
//		modelMap.put("SELL_TRADE_YN",request.getParameter("SELL_TRADE_YN"));
//		modelMap.put("BRANCH_CODE",  request.getParameter("BRANCH_CODE"));
//		modelMap.put("CUST_CODE",    request.getParameter("CUST_CODE"));
		
		return "front/trade/tradeDetail";
	}
	
}
