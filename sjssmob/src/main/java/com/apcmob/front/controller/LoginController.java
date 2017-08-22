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

import com.apcmob.front.service.LoginService;
import com.apcmob.object.CusMasterMstBO;


@Controller
public class LoginController {

	private final Log log = LogFactory.getLog(getClass());

	@Resource(name = "loginService")
	private LoginService loginService;

	//AJAX
	@Autowired
	MappingJacksonJsonView jsonView;


	@RequestMapping({"/login.do"})
	public String pageLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);
				
		//기본 페이지 정보 조회
		loginService.pageLogin(request, modelMap, session);
		
		//브랜치 코드 조회
		loginService.selectBranchCode(request, modelMap, session);
		
		return "front/login/login";	
	}

	@RequestMapping("/selectBranchCode.do")
	public void selectBranchCode(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		
		loginService.selectBranchCode(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}
	
	@RequestMapping("/chkLogin.do")
	public void chkLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {

		loginService.chkLogin(request, modelMap, session);

		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);
		
		jsonView.render(modelMap, request, response);
	}
		

	@RequestMapping("/logout.do")
	public void logout(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		session.removeAttribute("ACCOUNT");
		
		jsonView.render(modelMap, request, response);
	}	


	@RequestMapping("/updateNewPass.do")
	public void updateNewPass(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		loginService.updateNewPass(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}		
}
