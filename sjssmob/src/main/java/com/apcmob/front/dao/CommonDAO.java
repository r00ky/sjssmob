package com.apcmob.front.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.apcmob.object.CommCodeBO;


public interface CommonDAO {

	public String selectLastIdx(Map<String, Object> param) throws SQLException;
	
	public List<CommCodeBO> selectCommCode(Map<String, Object> param) throws SQLException;
	
	public String selectNoticeCost(Map<String, Object> param) throws SQLException;
	
	public String selectNormalNotice(Map<String, Object> param) throws SQLException;
	
	
}
