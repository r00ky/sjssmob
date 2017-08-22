package com.apcmob.front.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.apcmob.front.dao.GraphDAO;
import com.apcmob.front.service.GraphService;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.object.GraphBO1;
import com.apcmob.object.GraphBO2;
import com.apcmob.object.GraphBO3;
import com.apcmob.object.GraphBO4;
import com.apcmob.util.Common;

@Service("graphService")
public class GraphServiceImpl implements GraphService {

	private final Log log = LogFactory.getLog(getClass());
	
	@Resource(name = "graphDAO")
	private GraphDAO graphDAO;

	
	@Override
	public Map<String, Object> selectGraph(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		log.info("=============================================");
		log.info("BRANCH_CODE   : " + request.getParameter("BRANCH_CODE"));
		log.info("DEAL_DATE     : " + request.getParameter("from1"));
		log.info("=============================================");

		param.put("BRANCH_CODE",   Common.nvl(request.getParameter("BRANCH_CODE"),""));
		param.put("DEAL_DATE",     Common.nvl(request.getParameter("from1"),"").replaceAll("-", ""));
		
		
		List<GraphBO1> list1 = graphDAO.selectGraph1(param);

		log.info("=============================================");
		log.info("list1   : " + list1.size());
		log.info("=============================================");
		
		List<GraphBO2> list2 = graphDAO.selectGraph2(param);
		
		log.info("=============================================");
		log.info("list2   : " + list2.size());
		log.info("=============================================");
		
		List<GraphBO3> list3 = graphDAO.selectGraph3(param);
		
		log.info("=============================================");
		log.info("list3   : " + list3.size());
		log.info("=============================================");
		
		List<GraphBO4> list4 = graphDAO.selectGraph4(param);
		
		log.info("=============================================");
		log.info("list4   : " + list4.size());
		log.info("=============================================");
		
		param.put("graph1",  list1);
		param.put("graph2",  list2);
		param.put("graph3",  list3);
		param.put("graph4",  list4);
		
		return param;
	}
	

	

}
