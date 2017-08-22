package com.apcmob.front.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.apcmob.front.dao.CusMasterDAO;
import com.apcmob.object.BranchCodeBO;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.object.CustListBO;

@Repository("cusMasterDAO")
public class CusMasterDAOImpl extends SqlSessionDaoSupport implements CusMasterDAO {

	@Override
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public List<BranchCodeBO> selectBranchCode(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("cusMaster.selectBranchCode", param);
	}
	
	
	@Override
	public List<CustListBO> selectCustList(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("cusMaster.selectCustList", param);
	}
	
	@Override
	public String chkLogin(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("cusMaster.chkLogin", param);
	}
	
	@Override
	public CusMasterMstBO selectCusMasterDetail(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("cusMaster.selectCusMasterDetail", param);
	}	

	@Override
	public CusMasterMstBO selectCusMasterDetail2(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("cusMaster.selectCusMasterDetail2", param);
	}

	
	@Override
	public int updateNewPass(Map<String, Object> param) throws SQLException {
		return getSqlSession().update("cusMaster.updateNewPass", param);
	}
}
