package com.apcmob.front.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.apcmob.front.dao.OrderDAO;
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


@Repository("orderDAO")
public class OrderDAOImpl extends SqlSessionDaoSupport implements OrderDAO {

	@Override
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public String selectOrderStatusListCnt(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("order.selectOrderStatusListCnt", param);
	}
	
	@Override
	public List<OrderStatusBO> selectOrderStatusList(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("order.selectOrderStatusList", param);
	}
	
	@Override
	public OrderStatusBO selectOrderStatusDetail(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("order.selectOrderStatusList", param);
	}
	
	@Override
	public int updateOrderCancel(Map<String, Object> param) throws SQLException {
		return getSqlSession().update("order.updateOrderCancel", param);
	}
	
	@Override
	public CusCreditBO selectCusCredit(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("order.selectCusCredit", param);
	}
	
	@Override
	public CusCreditBO selectCusCredit2(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("order.selectCusCredit2", param);
	}
	
	@Override
	public List<DelvCustBO> selectDelvCust(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("order.selectDelvCust", param);
	}

	@Override
	public List<CarNoBO> selectCarNo(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("order.selectCarNo", param);
	}

	@Override
	public OrderCostBO selectOrderCost(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("order.selectOrderCost", param);
	}

	@Override
	public int insertOrderDetailList(Map<String, Object> param) throws SQLException {
		return getSqlSession().insert("order.insertOrderDetailList", param);
	}
	
	@Override
	public List<SmsBO> selectSmsTarget(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("order.selectSmsTarget", param);
	}

	
	@Override
	public String selectTradeListCnt(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("order.selectTradeListCnt", param);
	}
	
	@Override
	public List<OrderSheetBO> selectTradeList(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("order.selectTradeList", param);
	}
	
	
	@Override
	public List<OrderSheetSumBO> selectTradeSum(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("order.selectTradeSum", param);
	}
	
	@Override
	public OrderSheetBO selectTradeDetail(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("order.selectTradeList", param);
	}
	
	@Override
	public List<CommCodeBO> selectReprCusList(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("order.selectReprCusList", param);
	}	
	
	@Override
	public List<CommCodeBO> selectAgentCusList(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("order.selectAgentCusList", param);
	}

	@Override
	public String selectAgentListCnt(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("order.selectAgentListCnt", param);
	}
	
	@Override
	public List<AgentCustBO> selectAgentList(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("order.selectAgentList", param);
	}
	
	@Override
	public String selectReprListCnt(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectOne("order.selectReprListCnt", param);
	}
	
	@Override
	public List<ReprCustBO> selectReprList(Map<String, Object> param) throws SQLException {
		return getSqlSession().selectList("order.selectReprList", param);
	}	
}
