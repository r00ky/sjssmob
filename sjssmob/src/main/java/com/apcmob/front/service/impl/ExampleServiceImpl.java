package com.apcmob.front.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.apcmob.front.dao.ExampleDAO;
import com.apcmob.front.service.ExampleService;
import com.apcmob.object.ExampleBO;

/**
 * ExampleServiceImpl
 * 
 * @author 김일범
 * @since 2013-12-05
 * @version $Revision$
 */
@Service("exampleService")
public class ExampleServiceImpl implements ExampleService {

	@Resource(name = "exampleDAO")
	private ExampleDAO exampleDAO;

	/**
	 * 예제 샘플
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public ExampleBO exampleTest(String test) throws Exception {

		return exampleDAO.exampleTest(test);

	}

}
