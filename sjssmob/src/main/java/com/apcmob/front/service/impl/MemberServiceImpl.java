package com.apcmob.front.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.apcmob.front.dao.CusMasterDAO;
import com.apcmob.front.service.MemberService;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.util.Common;

/**
 * ExampleServiceImpl
 * 
 * @author 김일범
 * @since 2013-12-05
 * @version $Revision$
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {

	
	@Resource(name = "cusMasterDAO")
	private CusMasterDAO cusMasterDAO;


	private final Log log = LogFactory.getLog(getClass());
	
	
	@Override
	public Map<String, Object> selectMemberInfo(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		CusMasterMstBO s_user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//paramter
		log.info("=============================================");
		log.info("USRKND        : " + s_user.getUsrknd());
		log.info("BRANCH_CODE   : " + s_user.getBranchCode());
		log.info("BUSS_NO       : " + s_user.getBussNo());
		log.info("=============================================");
		
		param.put("USRKND",       Common.nvl(s_user.getUsrknd(), ""));
		param.put("BRANCH_CODE",  Common.nvl(s_user.getBranchCode(), ""));
		param.put("BUSS_NO",      Common.nvl(s_user.getBussNo(), ""));

		CusMasterMstBO user = cusMasterDAO.selectCusMasterDetail(param);
		param.put("userInfo", user);
		
		return param;
		
	}		
	

}
