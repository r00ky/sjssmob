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


@Controller
public class MainController {

	private final Log log = LogFactory.getLog(getClass());


	@Resource(name = "commonService")
	private CommonService commonService;
	
	@Resource(name = "orderService")
	private OrderService orderService;
	
	//AJAX
	@Autowired
	MappingJacksonJsonView jsonView;
	
	@RequestMapping("/main.do")
	public String pageMain(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);

		commonService.selectNoticeCost(request, modelMap, session);
		
		commonService.selectNormalNotice(request, modelMap, session);
		
		return "front/main/main";
	}
	
	@RequestMapping("/selectAgentList.do")
	public void selectAgentList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {

		orderService.selectAgentList(request, modelMap, session);

		jsonView.render(modelMap, request, response);
	}
	
	@RequestMapping("/selectReprList.do")
	public void selectReprList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		orderService.selectReprList(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}
	
}
