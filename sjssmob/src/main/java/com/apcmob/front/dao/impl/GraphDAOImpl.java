package com.apcmob.front.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.apcmob.front.dao.GraphDAO;
import com.apcmob.object.GraphBO1;
import com.apcmob.object.GraphBO2;
import com.apcmob.object.GraphBO3;
import com.apcmob.object.GraphBO4;


@Repository("graphDAO")
public class GraphDAOImpl extends SqlSessionDaoSupport implements GraphDAO {

	@Override
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public List<GraphBO1> selectGraph1(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("graph.selectGraph1", param);
	}
	
	@Override
	public List<GraphBO2> selectGraph2(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("graph.selectGraph2", param);
	}
	
	@Override
	public List<GraphBO3> selectGraph3(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("graph.selectGraph3", param);
	}
	
	@Override
	public List<GraphBO4> selectGraph4(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("graph.selectGraph4", param);
	}
	
	
}
