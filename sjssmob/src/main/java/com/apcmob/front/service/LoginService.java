package com.apcmob.front.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ExampleService
 * 
 * @author 김일범
 * @since 2013-12-05
 * @version $Revision$
 */
public interface LoginService {

	public Map<String, Object> pageLogin(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> selectBranchCode(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> selectCustList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> chkLogin(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> updateNewPass(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;

}
