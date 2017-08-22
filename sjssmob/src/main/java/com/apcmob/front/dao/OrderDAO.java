package com.apcmob.front.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.apcmob.object.AgentCustBO;
import com.apcmob.object.CarNoBO;
import com.apcmob.object.CommCodeBO;
import com.apcmob.object.CusCreditBO;
import com.apcmob.object.DelvCustBO;
import com.apcmob.object.OrderCostBO;
import com.apcmob.object.OrderSheetBO;
import com.apcmob.object.OrderSheetSumBO;
import com.apcmob.object.OrderStatusBO;
import com.apcmob.object.ReprCustBO;
import com.apcmob.object.SmsBO;


public interface OrderDAO {

	public String selectOrderStatusListCnt(Map<String, Object> param) throws SQLException;
	
	public List<OrderStatusBO> selectOrderStatusList(Map<String, Object> param) throws SQLException;
	
	public OrderStatusBO selectOrderStatusDetail(Map<String, Object> param) throws SQLException;
	
	public int updateOrderCancel(Map<String, Object> param) throws SQLException;
	
	public CusCreditBO selectCusCredit(Map<String, Object> param) throws SQLException;

	public CusCreditBO selectCusCredit2(Map<String, Object> param) throws SQLException;
	
	public List<DelvCustBO> selectDelvCust(Map<String, Object> param) throws SQLException;
	
	public List<CarNoBO> selectCarNo(Map<String, Object> param) throws SQLException;
	
	public OrderCostBO selectOrderCost(Map<String, Object> param) throws SQLException;
	
	public int insertOrderDetailList(Map<String, Object> param) throws SQLException;
	
	public List<SmsBO>  selectSmsTarget(Map<String, Object> param) throws SQLException;
	
	public String selectTradeListCnt(Map<String, Object> param) throws SQLException;
	
	public List<OrderSheetBO> selectTradeList(Map<String, Object> param) throws SQLException;	
	
	public List<OrderSheetSumBO> selectTradeSum(Map<String, Object> param) throws SQLException;	
	
	public OrderSheetBO selectTradeDetail(Map<String, Object> param) throws SQLException;
	
	public List<CommCodeBO> selectReprCusList(Map<String, Object> param) throws SQLException;
	
	public List<CommCodeBO> selectAgentCusList(Map<String, Object> param) throws SQLException;
	
	public String selectAgentListCnt(Map<String, Object> param) throws SQLException;
	
	public List<AgentCustBO> selectAgentList(Map<String, Object> param) throws SQLException;
	
	public String selectReprListCnt(Map<String, Object> param) throws SQLException;
	
	public List<ReprCustBO> selectReprList(Map<String, Object> param) throws SQLException;	
}
