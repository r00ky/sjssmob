package com.apcmob.front.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.apcmob.front.dao.CarDAO;
import com.apcmob.front.dao.OrderDAO;
import com.apcmob.front.service.CarService;
import com.apcmob.object.CarNoBO;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.object.TransSettingBO;
import com.apcmob.util.Common;

@Service("carService")
public class CarServiceImpl implements CarService {

	private final Log log = LogFactory.getLog(getClass());
	
	@Resource(name = "carDAO")
	private CarDAO carDAO;
	
	@Resource(name = "orderDAO")
	private OrderDAO orderDAO;

	
	@Override
	public Map<String, Object> selectTransSettingList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//저장 목록
		log.info("=============================================");
		log.info("CUS_CODE          : " + user.getCusCode());
		log.info("TRANS_CORP        : " + user.getTransCorp());
		log.info("S_ORDER_STAT      : " + request.getParameter("order_status"));
		log.info("S_GOOD_CODE       : " + request.getParameter("goods_code"));
		log.info("S_SHIP_RQST_DATE1 : " + request.getParameter("from1").replaceAll("-", ""));
		log.info("S_SHIP_RQST_DATE2 : " + request.getParameter("to1").replaceAll("-", ""));
		log.info("S_ORDER_DATE1     : " + request.getParameter("from2").replaceAll("-", ""));
		log.info("S_ORDER_DATE2     : " + request.getParameter("to2").replaceAll("-", ""));
		log.info("=============================================");

		param.put("CUS_CODE",          user.getCusCode());
		param.put("TRANS_CORP",        user.getTransCorp());
		param.put("S_ORDER_STAT",      Common.nvl(request.getParameter("order_status"),""));
		param.put("S_GOOD_CODE",       Common.nvl(request.getParameter("goods_code"),""));
		param.put("S_SHIP_RQST_DATE1", Common.nvl(request.getParameter("from1"),"").replaceAll("-", ""));
		param.put("S_SHIP_RQST_DATE2", Common.nvl(request.getParameter("to1"),"").replaceAll("-", ""));
		param.put("S_ORDER_DATE1",     Common.nvl(request.getParameter("from2"),"").replaceAll("-", ""));
		param.put("S_ORDER_DATE2",     Common.nvl(request.getParameter("to2"),"").replaceAll("-", ""));
		
		//paging
		String selectPageNo = (String) request.getParameter("selectPageNo");
		if (selectPageNo == null || selectPageNo.equals("")) {
			selectPageNo = "1";
		}
		
		int pageRange   = 10;
		int rowRange    = 10;
		int selectPage  = Integer.parseInt(selectPageNo);
		int rowTotalCnt = Integer.parseInt(carDAO.selectTransSettingListCnt(param));
		int pageStart   = ((selectPage - 1) / pageRange) * pageRange + 1;
		int totalPage   = rowTotalCnt / rowRange + ((rowTotalCnt % rowRange > 0) ? 1 : 0);
		int pageEnd     = (totalPage <= (pageStart + pageRange - 1)) ? totalPage : (pageStart + pageRange - 1);

		int searchRangeStart = (rowRange * (selectPage - 1)) + 1;
		int searchRangeEnd = rowRange * selectPage;

		log.info("=============================================");
		log.info("rowTotalCnt      : " + rowTotalCnt);
		log.info("pageRange        : " + pageRange);
		log.info("rowRange         : " + rowRange);
		log.info("selectPage       : " + selectPage);
		log.info("pageStart        : " + pageStart);
		log.info("totalPage        : " + totalPage);
		log.info("pageEnd          : " + pageEnd);
		log.info("searchRangeStart : " + searchRangeStart);
		log.info("searchRangeEnd   : " + searchRangeEnd);
		log.info("=============================================");
		

		param.put("searchRangeStart", searchRangeStart);
		//param.put("searchRangeEnd",   searchRangeEnd);
		param.put("searchRangeEnd",   rowTotalCnt);
		
		List<TransSettingBO> list = carDAO.selectTransSettingList(param);

		log.info("=============================================");
		log.info("list   : " + list.size());
		log.info("=============================================");
		
		param.put("transSettingList",  list);
		
		param.put("rowTotalCnt", rowTotalCnt);
		param.put("pageRange",   pageRange);
		param.put("rowRange",    rowRange);
		param.put("selectPage",  selectPage);
		param.put("pageStart",   pageStart);
		param.put("totalPage",   totalPage);
		param.put("pageEnd",     pageEnd);
		
		
		return param;
	}
	

	
	@Override
	public Map<String, Object> selectTransSettingDetail(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//저장 목록
		log.info("=============================================");
		log.info("TRANS_CORP        : " + user.getTransCorp());
		log.info("ORDER_IDX         : " + request.getParameter("orderIdx"));
		log.info("=============================================");

		param.put("TRANS_CORP",        user.getTransCorp());
		param.put("ORDER_IDX",         request.getParameter("orderIdx"));
		
		param.put("searchRangeStart", 1);
		param.put("searchRangeEnd",   1);
		
		TransSettingBO detail = carDAO.selectTransSettingDetail(param);

		log.info("=============================================");
		log.info("detail   : " + detail.toString());
		log.info("=============================================");

		//차량명
		List<CarNoBO> carNoList = orderDAO.selectCarNo(param);
		
		param.put("carNoList", carNoList);
		param.put("carDetail", detail);

		
		return param;
	}	
	
	@Override
	public Map<String, Object> updateCarInfo(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		String CAR_INFO          = Common.nvl(request.getParameter("CAR_INFO")," <<@>> <<@>> <<@>> ");  //차량코드
		String CAR_NO            = Common.nvl(CAR_INFO.split("<<@>>")[0].trim(),"");                    //차량코드
//		String DRIVER_NAME       = Common.nvl(CAR_INFO.split("<<@>>")[1].trim(),"");                    //운전자명
//		String DRIVER_HP_NO      = Common.nvl(CAR_INFO.split("<<@>>")[2].trim(),"");                    //운전자_휴대폰번호
		String PLACE_CODE        = Common.nvl(CAR_INFO.split("<<@>>")[3].trim(),"");                    //운송회사
		
		String DRIVER_NAME       = Common.nvl(request.getParameter("DRIVER_NAME"),"");                  //운전자명
		String DRIVER_HP_NO      = Common.nvl(request.getParameter("HP_NO"),"");                        //휴대폰번호
		
		//저장 목록
		log.info("=============================================");
		log.info("ORDER_IDX         : " + request.getParameter("orderIdx"));
		log.info("CAR_NO            : " + CAR_NO); 
		log.info("DRIVER_NAME       : " + DRIVER_NAME); 
		log.info("DRIVER_HP_NO      : " + DRIVER_HP_NO);		
		log.info("=============================================");
		
		param.put("ORDER_IDX",       request.getParameter("orderIdx"));
		param.put("CAR_NO",          CAR_NO);          //수송차량번호
		param.put("DRIVER_NAME",     DRIVER_NAME);     //운전자명
		param.put("DRIVER_HP_NO",    DRIVER_HP_NO);    //운전자_휴대폰번호
		
		int updateCnt = carDAO.updateCarInfo(param);
		
		log.info("=============================================");
		log.info("updateCnt   : " + updateCnt);
		log.info("=============================================");
		
		param.put("updateCnt", updateCnt);
		
		return param;
	}	
	
}
