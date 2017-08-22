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

import com.apcmob.front.service.MemberService;
import com.apcmob.object.CusMasterMstBO;


@Controller
public class MemberController {

	@Resource(name = "memberService")
	private MemberService memberService;
	
	private final Log log = LogFactory.getLog(getClass());

	//AJAX
	@Autowired
	MappingJacksonJsonView jsonView;
	
	@RequestMapping("/memberInfo.do")
	public String pageMember(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);

		//회원정보 조회
		memberService.selectMemberInfo(request, modelMap, session);
		
		return "front/member/memberInfo";
	}
	
}
