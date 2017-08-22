package com.apcmob.front.service;

import com.apcmob.object.ExampleBO;

/**
 * ExampleService
 * 
 * @author 김일범
 * @since 2013-12-05
 * @version $Revision$
 */
public interface ExampleService {

	/**
	 * 예제 샘플
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public ExampleBO exampleTest(String test) throws Exception;

}
