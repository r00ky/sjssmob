package com.apcmob.front.controller;

import java.util.List;

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
import com.apcmob.object.BranchCodeBO;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.util.Common;


@Controller
public class SelltradeController {

	private final Log log = LogFactory.getLog(getClass());

	//AJAX
	@Autowired
	MappingJacksonJsonView jsonView;
	
	@Resource(name = "loginService")
	private LoginService loginService;
	
	@RequestMapping("/selltradeList.do")
	public String pageSelltradeList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);

		//날짜 셋팅
		modelMap.put("TO_DATE"     ,Common.getDate((long)0));
		modelMap.put("TO_DATE_M7"  ,Common.getDate((long)-7));
		
		//브랜치 코드 조회
		modelMap.put("user_divide", "4");
		loginService.selectBranchCode(request, modelMap, session);
		
		String BRANCH_CODE = Common.nvl(request.getParameter("BRANCH_CODE"),"");
		if(BRANCH_CODE==null || BRANCH_CODE.equals("")){
			List<BranchCodeBO> branchCodeList = (List<BranchCodeBO>) modelMap.get("branchCodeList");
			BranchCodeBO bo = branchCodeList.get(0);
			modelMap.put("BRANCH_CODE", bo.getbranchCode());			
		}else{
			modelMap.put("BRANCH_CODE", BRANCH_CODE);
		}

		//거래처 코드 조회
		loginService.selectCustList(request, modelMap, session);

		//조회조건
		modelMap.put("from1",        request.getParameter("from1"));
		modelMap.put("to1",          request.getParameter("to1"));
		modelMap.put("BRANCH_CODE",  request.getParameter("BRANCH_CODE"));
		modelMap.put("CUST_CODE",    request.getParameter("CUST_CODE"));
		
		return "front/selltrade/selltradeList";
	}
	
	@RequestMapping("/selectCustCodeList.do")
	public void selectCustCodeList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		loginService.selectCustList(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}
	
}
