package com.apcmob.front.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface OrderService {

	public Map<String, Object> selectOrderStatusList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> selectOrderStatusDetail(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> updateOrderCancel(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> selectCusCredit(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> pageOrderList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> selectOrderCost(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> checkOrderLimit(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> insertOrderDetailList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> selectTradeList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> selectTradeSum(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> selectTradeDetail(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> selectAgentList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
	
	public Map<String, Object> selectReprList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception;
}
