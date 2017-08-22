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

import com.apcmob.front.service.GraphService;
import com.apcmob.front.service.LoginService;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.util.Common;


@Controller
public class GraphController {

	private final Log log = LogFactory.getLog(getClass());

	@Resource(name = "loginService")
	private LoginService loginService;
	
	@Resource(name = "graphService")
	private GraphService graphService;
	
	//AJAX
	@Autowired
	MappingJacksonJsonView jsonView;
	
	@RequestMapping("/graph.do")
	public String pageGraph(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);

		//브랜치 코드 조회
		modelMap.put("user_divide", "4");
		loginService.selectBranchCode(request, modelMap, session);
		
		//날짜 셋팅
		modelMap.put("TO_DATE"     ,Common.getDate((long)0));
		
		return "front/graph/graph";
	}
	
	
	//실시간 매출현황 조회
	@RequestMapping("/selectGraph.do")
	public void selectGraph(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		graphService.selectGraph(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}
		

	
}
