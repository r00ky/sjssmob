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

import com.apcmob.front.service.CarService;
import com.apcmob.front.service.CommonService;
import com.apcmob.object.CarNoBO;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.util.Common;


@Controller
public class CarController {

	private final Log log = LogFactory.getLog(getClass());

	@Resource(name = "commonService")
	private CommonService commonService;


	@Resource(name = "carService")
	private CarService carService;
	
	
	//AJAX
	@Autowired
	MappingJacksonJsonView jsonView;
	
	@RequestMapping("/carList.do")
	public String pageCarList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);

		//공통코드(배차상태)
		modelMap.put("GRP_CODE","ORDSTS");
		commonService.selectCommCode(request, modelMap, session);
		modelMap.put("orderStatusList", modelMap.get("commCode"));
		
		//공통코드(유종)
		modelMap.put("GRP_CODE","GOODCD");
		commonService.selectCommCode(request, modelMap, session);
		modelMap.put("goodsList", modelMap.get("commCode"));
		
		
		//날짜 셋팅
		modelMap.put("TO_DATE"     ,Common.getDate((long)0));
		modelMap.put("TO_DATE_M7"  ,Common.getDate((long)-7));
		
		//조회조건
		modelMap.put("from1",        request.getParameter("from3"));
		modelMap.put("to1",          request.getParameter("to3"));
		modelMap.put("from2",        request.getParameter("from4"));
		modelMap.put("to2",          request.getParameter("to4"));
		modelMap.put("order_status", request.getParameter("order_status"));
		modelMap.put("goods_code",   request.getParameter("goods_code"));
		
		return "front/car/carList";
	}
	
	//차량배차 목록 조회
	@RequestMapping("/selectCarList.do")
	public void selectCarList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		carService.selectTransSettingList(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}
	
	@RequestMapping("/carDetail.do")
	public String pageCarDetail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		modelMap.addAttribute("user", user);

		carService.selectTransSettingDetail(request, modelMap, session);

		//조회조건
		modelMap.put("from1",        request.getParameter("from1"));
		modelMap.put("to1",          request.getParameter("to1"));
		modelMap.put("from2",        request.getParameter("from2"));
		modelMap.put("to2",          request.getParameter("to2"));
		modelMap.put("order_status", request.getParameter("order_status"));
		modelMap.put("goods_code",   request.getParameter("goods_code"));
		
		return "front/car/carDetail";
	}
	
	@RequestMapping("/updateCarInfo.do")
	public void updateCarInfo(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) throws Exception {
		
		carService.updateCarInfo(request, modelMap, session);
		
		jsonView.render(modelMap, request, response);
	}
	
}
