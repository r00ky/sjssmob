package com.apcmob.front.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.apcmob.front.dao.CommonDAO;
import com.apcmob.object.CommCodeBO;


@Repository("commonDAO")
public class CommonDAOImpl extends SqlSessionDaoSupport implements CommonDAO {

	@Override
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public String selectLastIdx(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("common.selectLastIdx", param);
	}
	
	@Override
	public List<CommCodeBO> selectCommCode(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("common.selectCommCode", param);
	}
	
	
	@Override
	public String selectNoticeCost(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("common.selectNoticeCost", param);
	}
	
	@Override
	public String selectNormalNotice(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("common.selectNormalNotice", param);
	}
	
}
