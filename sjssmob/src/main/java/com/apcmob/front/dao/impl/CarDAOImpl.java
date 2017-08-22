package com.apcmob.front.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.apcmob.front.dao.CarDAO;
import com.apcmob.object.TransSettingBO;


@Repository("carDAO")
public class CarDAOImpl extends SqlSessionDaoSupport implements CarDAO {

	@Override
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public String selectTransSettingListCnt(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("trans.selectTransSettingListCnt", param);
	}
	
	@Override
	public List<TransSettingBO> selectTransSettingList(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("trans.selectTransSettingList", param);
	}
	
	@Override
	public TransSettingBO selectTransSettingDetail(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("trans.selectTransSettingList", param);
	}
	
	@Override
	public int updateCarInfo(Map<String, Object> param) throws SQLException {
		return getSqlSession().update("trans.updateCarInfo", param);
	}
}
