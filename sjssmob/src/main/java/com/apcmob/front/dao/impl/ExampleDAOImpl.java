package com.apcmob.front.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.apcmob.front.dao.ExampleDAO;
import com.apcmob.object.ExampleBO;

/**
 * ExampleDAOImpl
 * 
 * @author 김일범
 * @since 2013-12-05
 * @version $Revision$
 */
@Repository("exampleDAO")
public class ExampleDAOImpl extends SqlSessionDaoSupport implements ExampleDAO {

	@Override
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

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
	public ExampleBO exampleTest(String test) throws SQLException {
		return getSqlSession().selectOne("example0.getExample0", test);
	}

}
