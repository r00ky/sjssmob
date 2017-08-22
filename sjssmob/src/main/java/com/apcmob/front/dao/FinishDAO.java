package com.apcmob.front.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.apcmob.object.FinishSheet1;
import com.apcmob.object.FinishSheet2;
import com.apcmob.object.FinishSheet3;


public interface FinishDAO {

	public List<FinishSheet1> selectFinishSheet1(Map<String, Object> param) throws SQLException;
	
	public List<FinishSheet2> selectFinishSheet2(Map<String, Object> param) throws SQLException;
	
	public List<FinishSheet3> selectFinishSheet3(Map<String, Object> param) throws SQLException;
	
}
