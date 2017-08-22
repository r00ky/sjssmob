package com.apcmob.front.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.apcmob.front.dao.FinishDAO;
import com.apcmob.object.FinishSheet1;
import com.apcmob.object.FinishSheet2;
import com.apcmob.object.FinishSheet3;


@Repository("finishDAO")
public class FinishDAOImpl extends SqlSessionDaoSupport implements FinishDAO {

	@Override
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public List<FinishSheet1> selectFinishSheet1(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("finish.selectFinishSheet1", param);
	}
	@Override
	public List<FinishSheet2> selectFinishSheet2(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("finish.selectFinishSheet2", param);
	}
	@Override
	public List<FinishSheet3> selectFinishSheet3(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("finish.selectFinishSheet3", param);
	}
	
	
}
