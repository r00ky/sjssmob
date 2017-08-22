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

import com.apcmob.front.service.CommonService;
import com.apcmob.front.service.OrderService;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.util.Common;


@Controller
public class OrderController {


	@Resource(name = "commonService")
	private CommonService commonService;
	
	@Resource(name = "orderService")
	private OrderService orderService;
	
	private final Log log = LogFactory.getLog(getClass());

	//AJAX
	@Autowired
	MappingJacksonJsonView jsonView;
	
	//주문현황 페이지 이동
	@RequestMapping("/orderList.do")
	public String pageOrderList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		//거래처 콤보박스 조회
		orderService.pageOrderList(request, modelMap, session);
		
		//공통코드(주문상태)
		modelMap.put("GRP_CODE","ORDSTS");
		commonService.selectCommCode(request, modelMap, session);
		modelMap.put("ordstsList", modelMap.get("commCode"));
		
		//조회조건
		modelMap.put("from1",        request.getParameter("from1"));
		modelMap.put("to1",          request.getParameter("to1"));
		modelMap.put("ijuyu_name",   request.getParameter("ijuyu_name"));
		modelMap.put("order_status", request.getParameter("order_status"));
		modelMap.put("S_CUS_CODE",   request.getParameter("S_CUS_CODE"));
		
		return "front/order/orderList";
	}
	
	
	//주문현황 목록 조회
	@RequestMapping("/selectOrderList.do")
	public void selectOrderList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		orderService.selectOrderStatusList(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}
	
	
	//주문현황 상세 페이지 이동
	@RequestMapping("/orderDetail.do")
	public String pageOrderDetail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);
		
		orderService.selectOrderStatusDetail(request, modelMap, session);
		
		//조회조건
		modelMap.put("from1",        request.getParameter("from1"));
		modelMap.put("to1",          request.getParameter("to1"));
		modelMap.put("ijuyu_name",   request.getParameter("ijuyu_name"));
		modelMap.put("order_status", request.getParameter("order_status"));
		modelMap.put("S_CUS_CODE",   request.getParameter("S_CUS_CODE"));
		
		return "front/order/orderDetail";
	}

	
	//주문현황 상세 주문 취소
	@RequestMapping("/updateOrderCancel.do")
	public void updateOrderCancel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		orderService.updateOrderCancel(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}
	
	//주문 페이지 이동
	@RequestMapping("/orderRegister.do")
	public String pageOrderRegister(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);
		
		orderService.selectCusCredit(request, modelMap, session);
		
		//딜러 거래처 정보
		if(user.getCusGubun().equals("A")){
			orderService.selectAgentList(request, modelMap, session);
		}
		//법인 거래처 정보
		else if(user.getCusGubun().equals("R")){
			orderService.selectReprList(request, modelMap, session);	
		}
		
		//조회조건
		modelMap.put("from1",        request.getParameter("from1"));
		modelMap.put("to1",          request.getParameter("to1"));
		modelMap.put("ijuyu_name",   request.getParameter("ijuyu_name"));
		modelMap.put("order_status", request.getParameter("order_status"));
		modelMap.put("S_CUS_CODE",   request.getParameter("S_CUS_CODE"));
		
		return "front/order/orderRegister";
	}

	
	//단가 조회
	@RequestMapping("/selectOrderCost.do")
	public void selectOrderCost(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {

		orderService.selectOrderCost(request, modelMap, session);

		jsonView.render(modelMap, request, response);
	}
	
	//한도 초과 조회
	@RequestMapping("/checkOrderLimit.do")
	public void checkOrderLimit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		orderService.checkOrderLimit(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}	

	//오더 입력
	@RequestMapping("/insertOrderDetailList.do")
	public void insertOrderDetailList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		orderService.insertOrderDetailList(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}
	
	//거래처 선택 페이지 이동
	@RequestMapping("/selectBussNo.do")
	public String pageSelectBussNo(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);
		
		//딜러 거래처 정보
		if(user.getCusGubun().equals("A")){
			orderService.selectAgentList(request, modelMap, session);
		}
		//법인 거래처 정보
		else if(user.getCusGubun().equals("R")){
			orderService.selectReprList(request, modelMap, session);	
		}
		
		//조회조건
		modelMap.put("from1",        request.getParameter("from1"));
		modelMap.put("to1",          request.getParameter("to1"));
		modelMap.put("ijuyu_name",   request.getParameter("ijuyu_name"));
		modelMap.put("order_status", request.getParameter("order_status"));
		modelMap.put("S_CUS_CODE",   request.getParameter("S_CUS_CODE"));
		
		return "front/order/selectBussNo";
	}	

}
