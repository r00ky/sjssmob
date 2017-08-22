package com.apcmob.front.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.apcmob.front.dao.CommonDAO;
import com.apcmob.front.service.CommonService;
import com.apcmob.object.CommCodeBO;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.util.Common;

/**
 * ExampleServiceImpl
 * 
 * @author 김일범
 * @since 2013-12-05
 * @version $Revision$
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {

	
	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;


	private final Log log = LogFactory.getLog(getClass());
	
	
	@Override
	public Map<String, Object> selectCommCode(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//paramter
		List<CommCodeBO> commCode = commonDAO.selectCommCode(param);
		param.put("commCode", commCode);
		
		return param;
		
	}		
	
	@Override
	public Map<String, Object> selectNoticeCost(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		log.info("=============================================");
		log.info("BRANCH_CODE   : " + user.getBranchCode());
		log.info("CUS_CODE      : " + user.getCusCode());
		log.info("BRAND_CODE    : 0" );
		log.info("=============================================");
		
		param.put("BRANCH_CODE", Common.nvl(user.getBranchCode(),""));
		param.put("CUS_CODE",    Common.nvl(user.getCusCode(),""));
		param.put("BRAND_CODE",  Common.nvl("0",""));
		
		//paramter
		String priceNoti = commonDAO.selectNoticeCost(param);
		param.put("priceNoti", priceNoti);
		
		return param;
		
	}		
	
	@Override
	public Map<String, Object> selectNormalNotice(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//paramter
		String contents = commonDAO.selectNormalNotice(param);
		param.put("normalNoti", contents);
		
		return param;
		
	}		
	

}
