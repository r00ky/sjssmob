package com.apcmob.front.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.apcmob.object.TransSettingBO;


public interface CarDAO {

	public String selectTransSettingListCnt(Map<String, Object> param) throws SQLException;
	
	public List<TransSettingBO> selectTransSettingList(Map<String, Object> param) throws SQLException;
	
	public TransSettingBO selectTransSettingDetail(Map<String, Object> param) throws SQLException;
	
	public int updateCarInfo(Map<String, Object> param) throws SQLException;
}
