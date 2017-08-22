package com.apcmob.front.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.apcmob.object.BranchCodeBO;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.object.CustListBO;

public interface CusMasterDAO {

	public List<BranchCodeBO> selectBranchCode(Map<String, Object> param) throws SQLException;
	
	public List<CustListBO> selectCustList(Map<String, Object> param) throws SQLException;
	
	public String chkLogin(Map<String, Object> param) throws SQLException;
	
	public CusMasterMstBO selectCusMasterDetail(Map<String, Object> param) throws SQLException;
	
	public CusMasterMstBO selectCusMasterDetail2(Map<String, Object> param) throws SQLException;
	
	public int updateNewPass(Map<String, Object> param) throws SQLException;

}
