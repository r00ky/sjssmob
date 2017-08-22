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
import com.apcmob.front.dao.CusMasterDAO;
import com.apcmob.front.service.LoginService;
import com.apcmob.object.BranchCodeBO;
import com.apcmob.object.CommCodeBO;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.object.CustListBO;
import com.apcmob.util.Common;
import com.apcmob.util.SeedCBC;

import sun.misc.BASE64Encoder;

/**
 * ExampleServiceImpl
 * 
 * @author 김일범
 * @since 2013-12-05
 * @version $Revision$
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	@Resource(name = "cusMasterDAO")
	private CusMasterDAO cusMasterDAO;


	private final Log log = LogFactory.getLog(getClass());
	
	@Override
	public Map<String, Object> pageLogin (HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {

		//공통코드[사용자구분]
		param.put("GRP_CODE", "USRKND");
		List<CommCodeBO> userKindList = commonDAO.selectCommCode(param);
		param.put("userKindList", userKindList);
		
		return param;
	}
	
	@Override
	public Map<String, Object> selectBranchCode (HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//브런치 목록
		String user_divide = (String) request.getParameter("user_divide");
		if (user_divide == null || user_divide.equals("")) {
			
			user_divide = (String) param.get("user_divide"); 
			if (user_divide == null || user_divide.equals("")) {
				user_divide = "1";	
			}
			
		}
		
		log.info("=============================================");
		log.info("user_divide      : " + user_divide);
		log.info("=============================================");
		
		param.put("USRKND", user_divide);
		
		List<BranchCodeBO> branchCodeList = cusMasterDAO.selectBranchCode(param);
		param.put("branchCodeList", branchCodeList);
		
		return param;
	}
	
	@Override
	public Map<String, Object> selectCustList (HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//거래처 목록
		String BRANCH_CODE = (String) param.get("BRANCH_CODE"); 
		if (BRANCH_CODE == null || BRANCH_CODE.equals("")) {
			BRANCH_CODE =  (String) request.getParameter("BRANCH_CODE");
			param.put("BRANCH_CODE", BRANCH_CODE);
		}
		
		log.info("=============================================");
		log.info("BRANCH_CODE      : " + BRANCH_CODE);
		log.info("=============================================");
		
		List<CustListBO> custList = cusMasterDAO.selectCustList(param);
		param.put("custList", custList);
		
		return param;
	}
	
	@Override
	public Map<String, Object> chkLogin(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		BASE64Encoder base64Encoder = new BASE64Encoder();
		SeedCBC s = new SeedCBC();
		
		//paramter
		log.info("=============================================");
		log.info("user_divide   : " + request.getParameter("user_divide"));
		log.info("branch_divide : " + request.getParameter("branch_divide"));
		log.info("user_id       : " + request.getParameter("user_id"));
		log.info("user_password : " + request.getParameter("user_password"));
		log.info("user_password : " + base64Encoder.encode(s.Encryption(Common.nvl(request.getParameter("user_password"), "").getBytes())));
		log.info("=============================================");
		
		param.put("USRKND",       Common.nvl(request.getParameter("user_divide"), ""));
		param.put("BRANCH_CODE",  Common.nvl(request.getParameter("branch_divide"), ""));
		param.put("BUSS_NO",      Common.nvl(request.getParameter("user_id"), ""));
		param.put("WEB_PASSWORD", Common.nvl(request.getParameter("user_password"), ""));
		param.put("SEED_WEB_PASSWORD",   base64Encoder.encode(s.Encryption(Common.nvl(request.getParameter("user_password"), "").getBytes())));
		
		//기존세션 삭제
		session.removeAttribute("ACCOUNT");
		
		//로그인 성공여부
		String passYn = cusMasterDAO.chkLogin(param);
		
		log.info("=============================================");
		log.info("passYn       : " + passYn                     );
		log.info("=============================================");		
		param.put("passYn",       passYn);
		
		//로그인 성공시 세션정보 생성
		if(passYn.equals("Y")){
			CusMasterMstBO user = cusMasterDAO.selectCusMasterDetail(param);
			
			if(Common.nvl(request.getParameter("user_divide"),"").equals("3")){
				//로그인ID와 비밀번호가 동일하면 최초 로그인으로 본다
				if(base64Encoder.encode(s.Encryption(user.getCusCode().getBytes())).equals(user.getWebPassword())){
						user.setFirstloginYn("Y");
				}else if(user.getCusCode().equals(user.getWebPassword())){
						user.setFirstloginYn("Y");
				}else{
					user.setFirstloginYn("N");
				}
				
			}else{
				//사업자번호와 비밀번호가 동일하면 최초 로그인으로 본다
				if(base64Encoder.encode(s.Encryption(user.getBussNo().getBytes())).equals(user.getWebPassword())){
					user.setFirstloginYn("Y");
				}else if(user.getBussNo().equals(user.getWebPassword())){
					user.setFirstloginYn("Y");
				}else{
					user.setFirstloginYn("N");
				}					
			}

			user.setUserDivide(Common.nvl(request.getParameter("user_divide"), ""));
			session.setAttribute("ACCOUNT", user);
		}
		
		return param;
		
	}		
	
	
	@Override
	public Map<String, Object> updateNewPass(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		BASE64Encoder base64Encoder = new BASE64Encoder();
		SeedCBC s = new SeedCBC();
		
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//paramter
		log.info("=============================================");
		log.info("USRKND           : " + user.getUsrknd());
		log.info("BRANCH_CODE      : " + user.getBranchCode());
		log.info("BUSS_NO          : " + user.getBussNo());
		log.info("NEW_WEB_PASSWORD : " + request.getParameter("NEW_WEB_PASSWORD"));
		log.info("=============================================");
		
		//비밀번호를 암호화 하여 저장
		param.put("USRKND",             user.getUsrknd());
		param.put("BRANCH_CODE",        user.getBranchCode());
		param.put("BUSS_NO",            user.getBussNo());
		param.put("NEW_WEB_PASSWORD",   base64Encoder.encode(s.Encryption(Common.nvl(request.getParameter("NEW_WEB_PASSWORD"), "").getBytes())));
		
		cusMasterDAO.updateNewPass(param);
		
		user.setFirstloginYn("N");
		session.setAttribute("ACCOUNT",user);
		
		return param;
		
	}		

}
