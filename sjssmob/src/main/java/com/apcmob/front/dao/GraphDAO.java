package com.apcmob.front.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.apcmob.object.GraphBO1;
import com.apcmob.object.GraphBO2;
import com.apcmob.object.GraphBO3;
import com.apcmob.object.GraphBO4;


public interface GraphDAO {

	public List<GraphBO1> selectGraph1(Map<String, Object> param) throws SQLException;
	
	public List<GraphBO2> selectGraph2(Map<String, Object> param) throws SQLException;
	
	public List<GraphBO3> selectGraph3(Map<String, Object> param) throws SQLException;
	
	public List<GraphBO4> selectGraph4(Map<String, Object> param) throws SQLException;
	
}
