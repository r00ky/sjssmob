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

import com.apcmob.front.service.FinishService;
import com.apcmob.front.service.LoginService;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.util.Common;


@Controller
public class FinishController {

	private final Log log = LogFactory.getLog(getClass());


	@Resource(name = "loginService")
	private LoginService loginService;
	
	@Resource(name = "finishService")
	private FinishService finishService;
	
	//AJAX
	@Autowired
	MappingJacksonJsonView jsonView;
	
	@RequestMapping("/finishList.do")
	public String pageFinishList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);

		//브랜치 코드 조회
		modelMap.put("user_divide", "4");
		loginService.selectBranchCode(request, modelMap, session);
		
		//날짜 셋팅
		modelMap.put("TO_DATE"     ,Common.getDate((long)0));
		modelMap.put("TO_DATE_M7"  ,Common.getDate((long)-7));
		
		return "front/finish/finishList";
	}
	
	
	//마감일보 조회
	@RequestMapping("/selectFinishSheet.do")
	public void selectOrderList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		finishService.selectFinishSheet(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}
	
	
}
