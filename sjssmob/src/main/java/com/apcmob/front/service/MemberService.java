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
public interface MemberService {

	public Map<String, Object> selectMemberInfo(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	

}
