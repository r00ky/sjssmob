package com.apcmob.front.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.apcmob.front.dao.FinishDAO;
import com.apcmob.front.service.FinishService;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.object.FinishSheet1;
import com.apcmob.object.FinishSheet2;
import com.apcmob.object.FinishSheet3;
import com.apcmob.util.Common;

@Service("finishService")
public class FinishServiceImpl implements FinishService {

	private final Log log = LogFactory.getLog(getClass());
	
	@Resource(name = "finishDAO")
	private FinishDAO finishDAO;

	
	@Override
	public Map<String, Object> selectFinishSheet(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		log.info("=============================================");
		log.info("BRANCH_CODE   : " + request.getParameter("BRANCH_CODE"));
		log.info("FROM_DATE     : " + request.getParameter("from1"));
		log.info("TO_DATE       : " + request.getParameter("to1"));
		log.info("=============================================");

		param.put("BRANCH_CODE",   Common.nvl(request.getParameter("BRANCH_CODE"),""));
		param.put("FROM_DATE",     Common.nvl(request.getParameter("from1"),"").replaceAll("-", ""));
		param.put("TO_DATE",       Common.nvl(request.getParameter("to1"),"").replaceAll("-", ""));
		
		
		List<FinishSheet1> list1 = finishDAO.selectFinishSheet1(param);

		log.info("=============================================");
		log.info("list1   : " + list1.size());
		log.info("=============================================");
		
		
		List<FinishSheet2> list2 = finishDAO.selectFinishSheet2(param);
		
		log.info("=============================================");
		log.info("list2   : " + list2.size());
		log.info("=============================================");
		
		List<FinishSheet3> list3 = finishDAO.selectFinishSheet3(param);
		
		log.info("=============================================");
		log.info("list3   : " + list3.size());
		log.info("=============================================");
		
		param.put("finishSheet1",  list1);
		param.put("finishSheet2",  list2);
		param.put("finishSheet3",  list3);
		
		return param;
	}
	

	

}
