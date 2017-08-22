package com.apcmob.front.dao;

import java.sql.SQLException;

import com.apcmob.object.ExampleBO;

/**
 * ExampleDAO
 * 
 * @author 김일범
 * @since 2013-12-05
 * @version $Revision$
 */
public interface ExampleDAO {

	/**
	 * 예제 샘플
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public ExampleBO exampleTest(String test) throws SQLException;

}
