package com.apcmob.front.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.apcmob.common.CommPath;
import com.apcmob.front.dao.CommonDAO;
import com.apcmob.front.dao.CusMasterDAO;
import com.apcmob.front.dao.OrderDAO;
import com.apcmob.front.service.OrderService;
import com.apcmob.object.AgentCustBO;
import com.apcmob.object.CarNoBO;
import com.apcmob.object.CommCodeBO;
import com.apcmob.object.CusCreditBO;
import com.apcmob.object.CusMasterMstBO;
import com.apcmob.object.DelvCustBO;
import com.apcmob.object.OrderCostBO;
import com.apcmob.object.OrderSheetBO;
import com.apcmob.object.OrderSheetSumBO;
import com.apcmob.object.OrderStatusBO;
import com.apcmob.object.ReprCustBO;
import com.apcmob.object.SmsBO;
import com.apcmob.util.Common;
import com.apcmob.util.SendSms;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	private final Log log = LogFactory.getLog(getClass());
	
	@Resource(name = "orderDAO")
	private OrderDAO orderDAO;

	@Resource(name = "cusMasterDAO")
	private CusMasterDAO cusMasterDAO;

	@Resource(name = "commonDAO")
	private CommonDAO commonDAO;
	
	
	@Override
	public Map<String, Object> pageOrderList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		log.info("=============================================");
		log.info("CUS_CODE       : " + user.getCusCode());
		log.info("=============================================");
		
		//사용자 정보
		if(user.getCusGubun().equals("R") ){ //법인의 거래처 목록 
			param.put("CUS_CODE", user.getCusCode());
			
			List<CommCodeBO> reprCusList = orderDAO.selectReprCusList(param);
			param.put("reprCusList", reprCusList);	
		}else if(user.getCusGubun().equals("A") ){ //딜러의 거래처 목록
			param.put("AGENT_CODE", user.getAgentCode());
			
			List<CommCodeBO> agentCusList = orderDAO.selectAgentCusList(param);
			param.put("agentCusList", agentCusList);
		}
		
		//사용자 정보
		param.put("user", user);
		
		//날짜 셋팅
		param.put("TO_DATE"     ,Common.getDate((long)0));
		param.put("TO_DATE_M7"  ,Common.getDate((long)-7));		
		
		return param;
		
	}
		
	
	@Override
	public Map<String, Object> selectOrderStatusList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//저장 목록
		log.info("=============================================");
		log.info("CUS_GUBUN         : " + user.getCusGubun());
		log.info("CUS_CODE          : " + user.getCusCode());
		log.info("BRANCH_CODE       : " + user.getBranchCode());
		log.info("S_CUS_CODE        : " + request.getParameter("S_CUS_CODE"));
		log.info("S_ORDER_STAT      : " + request.getParameter("order_status"));
		log.info("S_CUS_NAME        : " + request.getParameter("ijuyu_name"));
		log.info("S_ORDER_DATE1     : " + request.getParameter("from1"));
		log.info("S_ORDER_DATE2     : " + request.getParameter("to1"));
		log.info("=============================================");

		param.put("CUS_GUBUN",         user.getCusGubun());
		param.put("CUS_CODE",          user.getCusCode());
		param.put("BRANCH_CODE",       user.getBranchCode());    //브랜치코드
		param.put("S_CUS_CODE",        Common.nvl(request.getParameter("S_CUS_CODE"),""));
		param.put("S_ORDER_STAT",      Common.nvl(request.getParameter("order_status"),""));
		param.put("S_CUS_NAME",        Common.nvl(request.getParameter("ijuyu_name"),""));
		param.put("S_ORDER_DATE1",     Common.nvl(request.getParameter("from1"),"").replaceAll("-", ""));
		param.put("S_ORDER_DATE2",     Common.nvl(request.getParameter("to1"),"").replaceAll("-", ""));
		
		if(user.getCusGubun().equals("A") ){ //딜러의 거래처 목록
			param.put("AGENT_CODE",          user.getAgentCode());
		}
		
		//paging
		String selectPageNo = (String) request.getParameter("selectPageNo");
		if (selectPageNo == null || selectPageNo.equals("")) {
			selectPageNo = "1";
		}
		
		int pageRange   = 10;
		int rowRange    = 10;
		int selectPage  = Integer.parseInt(selectPageNo);
		int rowTotalCnt = Integer.parseInt(orderDAO.selectOrderStatusListCnt(param));
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
//		param.put("searchRangeEnd",   searchRangeEnd);
		param.put("searchRangeEnd",   rowTotalCnt); //전체 조회
		
		List<OrderStatusBO> list = orderDAO.selectOrderStatusList(param);

		log.info("=============================================");
		log.info("list   : " + list.size());
		log.info("=============================================");
		
		param.put("orderStatusList",  list);
		
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
	public Map<String, Object> selectOrderStatusDetail(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//저장 목록
		log.info("=============================================");
		log.info("CUS_GUBUN         : " + user.getCusGubun());
		log.info("CUS_CODE          : " + user.getCusCode());
		log.info("BRANCH_CODE       : " + user.getBranchCode());
		log.info("S_ORDER_IDX       : " + request.getParameter("orderIdx"));
		log.info("=============================================");

		param.put("CUS_GUBUN",         user.getCusGubun());
		param.put("CUS_CODE",          user.getCusCode());
		param.put("BRANCH_CODE",       user.getBranchCode());
		param.put("S_CUS_NAME",        "");
		param.put("S_CUS_CODE",        "");
		param.put("S_ORDER_IDX",       Common.nvl(request.getParameter("orderIdx"),""));
		
		if(user.getCusGubun().equals("A") ){ //딜러의 거래처 목록
			param.put("AGENT_CODE",          user.getAgentCode());
		}

		param.put("searchRangeStart", 1);
		param.put("searchRangeEnd",   1);
		
		OrderStatusBO detail = orderDAO.selectOrderStatusDetail(param);

		log.info("=============================================");
		log.info("detail   : " + detail.toString());
		log.info("=============================================");
		
		param.put("orderStatusDetail",  detail);
		
		return param;
	}
	
	@Override
	public Map<String, Object> updateOrderCancel(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//저장 목록
		log.info("=============================================");
		log.info("ORDER_IDX         : " + request.getParameter("orderIdx"));
		log.info("=============================================");
		
		param.put("S_ORDER_IDX",         Common.nvl(request.getParameter("orderIdx"),""));
		
		
		int cnt = orderDAO.updateOrderCancel(param);
		
		log.info("=============================================");
		log.info("cnt : " + cnt);
		log.info("=============================================");
		
		param.put("updateCnt",  cnt);
		
		return param;
	}


	@Override
	public Map<String, Object> selectCusCredit(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		log.info("=============================================");
		log.info("CUS_GUBUN      : " + user.getCusGubun());
		log.info("CUS_CODE       : " + user.getCusCode());
		log.info("BRANCH_CODE    : " + user.getBranchCode());
		log.info("CUS_CODE       : " + request.getParameter("CUS_CODE"));
		log.info("REPR_CUS_YN    : " + user.getReprCusYn());
		log.info("=============================================");
		
		if(user.getCusGubun().equals("C")){ //일반 사용자는 본인의 정보
			param.put("CUS_CODE",    user.getCusCode());
			param.put("BRANCH_CODE", user.getBranchCode());

			//여신한도현황
			param.put("REPR_CUS_YN", user.getReprCusYn());
			CusCreditBO bo = orderDAO.selectCusCredit(param);
			param.put("cusCreditBO", bo);
			
			//납품거래처
			List<DelvCustBO> delvCustList = orderDAO.selectDelvCust(param);
			param.put("delvCustList", delvCustList);
			
		}else if( user.getCusGubun().equals("R")){ //법인은 선택한 고객정보
			//법인은 법인으 한도를 조회
			param.put("CUS_CODE",    user.getCusCode());
			param.put("BRANCH_CODE", user.getBranchCode());
			
			//여신한도현황
			param.put("REPR_CUS_YN", user.getReprCusYn());
			CusCreditBO bo = orderDAO.selectCusCredit(param);
			param.put("cusCreditBO", bo);
			
			//선택한 고객
			param.put("CUS_CODE",    request.getParameter("CUS_CODE"));

			CusMasterMstBO cusUser = cusMasterDAO.selectCusMasterDetail2(param);
			param.put("cusUser",     cusUser);
			
			//납품거래처
			List<DelvCustBO> delvCustList = orderDAO.selectDelvCust(param);
			param.put("delvCustList", delvCustList);
			
			param.put("RCUS_CODE",   request.getParameter("CUS_CODE"));
		}else if(user.getCusGubun().equals("A") ){ //딜러는 선택한 고객정보
			param.put("CUS_CODE",    request.getParameter("CUS_CODE"));
			param.put("BRANCH_CODE", user.getBranchCode());
			
			//여신한도현황(선택한 고객의 한도조회)
			param.put("REPR_CUS_YN", user.getReprCusYn());
			CusCreditBO bo = orderDAO.selectCusCredit(param);
			param.put("cusCreditBO", bo);
			
			CusMasterMstBO cusUser = cusMasterDAO.selectCusMasterDetail2(param);
			
			//납품거래처
			List<DelvCustBO> delvCustList = orderDAO.selectDelvCust(param);
			param.put("delvCustList", delvCustList);
			
			param.put("cusUser",     cusUser);
		}
		
		//공통코드[상품구분]
		param.put("GRP_CODE", "BRDDIV");
		List<CommCodeBO> brandDivisionList = commonDAO.selectCommCode(param);
		param.put("brandDivisionList", brandDivisionList);
		
		//공통코드[제품명]
		param.put("GRP_CODE", "GOODCD");
		List<CommCodeBO> goodCodeList = commonDAO.selectCommCode(param);
		param.put("goodCodeList", goodCodeList);
		
		//공통코드[출하구분]
		param.put("GRP_CODE", "SHPFLG");
		List<CommCodeBO> shpflgList = commonDAO.selectCommCode(param);
		param.put("shpflgList", shpflgList);

		//공통코드[출하지(출하구분 '1':정유사)]
		param.put("GRP_CODE", "NABCOD");
		List<CommCodeBO> nabcodList = commonDAO.selectCommCode(param);
		param.put("nabcodList", nabcodList);
		
		//공통코드[출하지(출하구분 '2':저장소)]
		param.put("GRP_CODE", "SPOCOD");
		List<CommCodeBO> spocodList = commonDAO.selectCommCode(param);
		param.put("spocodList", spocodList);
		
		//공통코드[운송방법]
		param.put("GRP_CODE", "ARVSEC");
		List<CommCodeBO> arvsecList = commonDAO.selectCommCode(param);
		param.put("arvsecList", arvsecList);
		
		//차량명
		List<CarNoBO> carNoList = orderDAO.selectCarNo(param);
		param.put("carNoList", carNoList);
		
		//사용자 정보
		param.put("user", user);
		
		//날짜 셋팅
		param.put("TO_DATE"    ,Common.getDate((long)0));
		
		return param;
		
	}


	@Override
	public Map<String, Object> selectOrderCost(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		log.info("=============================================");
		log.info("BRANCH_CODE   : 100");
		log.info("CUS_CODE      : " + user.getCusCode());
		log.info("CUS_CODE      : " + request.getParameter("CUS_CODE"));
		log.info("BUY_CUS_CODE  : " + request.getParameter("BUY_CUS_CODE"));
		log.info("BRAND_CODE    : " + request.getParameter("BRAND_CODE"));
		log.info("T_GOOD_CODE   : " + request.getParameter("GOOD_CODE"));
		log.info("SHIP_FLAG     : " + request.getParameter("SHIP_FLAG"));
		log.info("T_NAB_CODE    : " + request.getParameter("NAB_CODE"));
		log.info("T_SPO_CODE    : " + request.getParameter("SPO_CODE"));
		log.info("=============================================");
		
		param.put("BRANCH_CODE",  "100");
		if(user.getCusGubun().equals("C") ){ //일반은 본인의 정보 
			param.put("CUS_CODE",     user.getCusCode());
		}else if(user.getCusGubun().equals("A") || user.getCusGubun().equals("R")  ){ //딜러, 법인은 선택한 고객정보
			param.put("CUS_CODE",     Common.nvl(request.getParameter("CUS_CODE"),""));
		}

		
		
		param.put("BUY_CUS_CODE", Common.nvl(request.getParameter("BUY_CUS_CODE"),""));
		param.put("BRAND_CODE",   Common.nvl(request.getParameter("BRAND_CODE"),""));

		String T_GOOD_CODE       = Common.nvl(request.getParameter("GOOD_CODE")," <<@>> ");
		String GOOD_CODE          = Common.nvl(T_GOOD_CODE.split("<<@>>")[0].trim(),"");
		param.put("GOOD_CODE",    GOOD_CODE);
		
		param.put("SHIP_FLAG",    Common.nvl(request.getParameter("SHIP_FLAG").trim(),""));
		String T_NAB_CODE        = Common.nvl(request.getParameter("NAB_CODE")," <<@>> ");
		String NAB_CODE          = Common.nvl(T_NAB_CODE.split("<<@>>")[0].trim(),"");

		String T_SPO_CODE        = Common.nvl(request.getParameter("SPO_CODE")," <<@>> ");
		String SPO_CODE          = Common.nvl(T_SPO_CODE.split("<<@>>")[0].trim(),"");

		String SHIP_FLAG         = Common.nvl(request.getParameter("SHIP_FLAG").trim(),"");		
		if(SHIP_FLAG.equals("1")){
			param.put("NAB_CODE",       NAB_CODE);    //출하지코드(출하구분 '1'일 경우만 들어감)
		}else{
			param.put("NAB_CODE",       SPO_CODE);    //출하지코드(출하구분 '2'일 경우만 들어감)				
		}

		log.info("GOOD_CODE   : " + GOOD_CODE);
		log.info("NAB_CODE    : " + NAB_CODE);
		log.info("SPO_CODE    : " + SPO_CODE);
		
		/*param.put("NAB_CODE",    Common.nvl(request.getParameter("SHIP_FLAG"),"").trim().equals("1") ?  Common.nvl(request.getParameter("NAB_CODE"),"").trim() : Common.nvl(request.getParameter("SPO_CODE").trim(),""));*/
		
		//상품단가
		OrderCostBO orderCost = orderDAO.selectOrderCost(param);
		param.put("orderCost", orderCost);
		
		return param;
		
	}


	@Override
	public Map<String, Object> checkOrderLimit(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//저장 목록
		log.info("=============================================");
		log.info("CUS_CODE      : " + user.getCusCode());
		log.info("BRANCH_CODE   : " + user.getBranchCode());
		log.info("CUS_CODE      : " + request.getParameter("CUS_CODE"));
		log.info("REPR_CUS_YN   : " + user.getReprCusYn());
		log.info("DEAL_AMT      : " + request.getParameter("DEAL_AMT"));
		log.info("=============================================");

		String DEAL_AMT   = request.getParameter("DEAL_AMT").replace(",", "");         //주문금액
		Long TOTAL_AMT    = Long.parseLong(DEAL_AMT);
		
		//여신한도현황
		if(user.getCusGubun().equals("C") || user.getCusGubun().equals("R") ){ //일반, 법인은 본인의 한도 정보 
			param.put("CUS_CODE",     user.getCusCode());
		}else if(user.getCusGubun().equals("A") ){ //딜러는 선택한 고객정보
			param.put("CUS_CODE",     Common.nvl(request.getParameter("CUS_CODE"),""));
		}
		param.put("BRANCH_CODE",    user.getBranchCode());    //브랜치코드
		param.put("REPR_CUS_YN",    user.getReprCusYn());
		
		CusCreditBO bo = orderDAO.selectCusCredit2(param); //한도 체크할때는 새로운 함수로 체크
		
		if(Long.parseLong(bo.getOrderAbleAmt()) < TOTAL_AMT){ //한도초과
			bo = orderDAO.selectCusCredit(param); //alert출력할때는 기존의 함수를 보여줌
			param.put("limitYn",  "Y");
			param.put("overAmt",  TOTAL_AMT - Long.parseLong(bo.getOrderAbleAmt()) );
			param.put("totalAmt", TOTAL_AMT );
			param.put("ableAmt",  bo.getOrderAbleAmt() );
			
			//한도초과 SMS발송
			String msg = "여신한도 초과 되었습니다 유류대금 입금 부탁드립니다.";
			if(user.getHpNo() != null && user.getHpNo().length()>=10 && CommPath.SMS_YN){
				log.info("SEND SMS   To : " + user.getHpNo() + " Msg : " + msg);
				//SendSms.send(user.getHpNo(), msg); //임시 주석처리
			}
			
		}else{ //주문가능
			param.put("limitYn", "N");
			param.put("CREDIT_LIMIT",  bo.getCreditLimit()); //이전 여신한도
		}
		
		return param;
	}
	

	@Override
	public Map<String, Object> insertOrderDetailList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//저장 목록
		log.info("=============================================");
		log.info("CUS_CODE            : " + user.getCusCode());
		log.info("BRANCH_CODE         : " + user.getBranchCode());
		log.info("CUS_CODE            : " + request.getParameter("CUS_CODE"));
		log.info("CREDIT_LIMIT        : " + request.getParameter("CREDIT_LIMIT"));
		log.info("=============================================");
		
	
		String T_DELV_CUS_CODE   = Common.nvl(request.getParameter("DELV_CUS_CODE")," <<@>> <<@>> <<@>> ");
		String DELV_CUS_CODE     = Common.nvl(T_DELV_CUS_CODE.split("<<@>>")[0].trim(),"");                                               //납품거래처코드
		String TRANS_CUS_CODE    = Common.nvl(T_DELV_CUS_CODE.split("<<@>>")[1].trim(),"");                                               //정산거래처코드
		String BUY_CUS_CODE      = Common.nvl(T_DELV_CUS_CODE.split("<<@>>")[2].trim(),"");                                               //매입처코드
		String BRAND_CODE        = Common.nvl(T_DELV_CUS_CODE.split("<<@>>")[3].trim(),"");                                               //브랜드코드
		String SHIP_RQST_DATE    = Common.nvl(request.getParameter("SHIP_RQST_DATE").trim().replace("-", ""),"");                         //출하요청일
		String SHIP_RQST_DATE2   = Common.nvl(request.getParameter("SHIP_RQST_DATE").trim(),"");                                          //출하요청일
		String BRAND_DIVISION    = Common.nvl(request.getParameter("BRAND_DIVISION").trim(),"");                                          //상품구분

		String T_GOOD_CODE       = Common.nvl(request.getParameter("GOOD_CODE")," <<@>> ");
		String GOOD_CODE         = Common.nvl(T_GOOD_CODE.split("<<@>>")[0].trim(),"");                                                   //제품 코드
		String GOOD_NAME         = Common.nvl(T_GOOD_CODE.split("<<@>>")[1].trim(),"");                                                   //제품 명
		
		String DEAL_QTY          = Common.nvl(request.getParameter("DEAL_QTY").trim(),"0").replace(",", "");                              //수량
		String ARRIVE_SEC        = Common.nvl(request.getParameter("ARRIVE_SEC").trim(),"");                                              //운송방법
		String SHIP_FLAG         = Common.nvl(request.getParameter("SHIP_FLAG").trim(),"");                                               //출하구분
		String SHIP_NAME         = "";                                                                                                    //출하지명

		String T_NAB_CODE        = Common.nvl(request.getParameter("NAB_CODE")," <<@>> ");
		String NAB_CODE          = Common.nvl(T_NAB_CODE.split("<<@>>")[0].trim(),"");                                                    //출하지(저유소)
		String NAB_NAME          = Common.nvl(T_NAB_CODE.split("<<@>>")[1].trim(),"");                                                    //출하지명(저유소))

		String T_SPO_CODE        = Common.nvl(request.getParameter("SPO_CODE")," <<@>> ");
		String SPO_CODE          = Common.nvl(T_SPO_CODE.split("<<@>>")[0].trim(),"");                                                    //출하지(저장소)
		String SPO_NAME          = Common.nvl(T_SPO_CODE.split("<<@>>")[1].trim(),"");                                                    //출하지명(저장소))
		
		String CAR_INFO          = Common.nvl(request.getParameter("CAR_INFO")," <<@>> <<@>> <<@>> <<@>> ");                              //차량코드
		String CAR_NO            = Common.nvl(CAR_INFO.split("<<@>>")[0].trim(),"");                                                      //차량코드
		String DRIVER_NAME       = Common.nvl(CAR_INFO.split("<<@>>")[1].trim(),"");                                                      //운전자명
		String DRIVER_HP_NO      = Common.nvl(CAR_INFO.split("<<@>>")[2].trim(),"");                                                      //운전자_휴대폰번호
		String PLACE_CODE        = Common.nvl(CAR_INFO.split("<<@>>")[3].trim(),"");                                                      //운송회사
		String CAR_NAME          = Common.nvl(CAR_INFO.split("<<@>>")[4].trim(),"");                                                      //차량명
		String SALE_PRICE        = Common.nvl(request.getParameter("SALE_PRICE").trim(),"0").replace(",", "");                            //매출단가
		String BASE_PRICE        = Common.nvl(request.getParameter("BASE_PRICE").trim(),"0").replace(",", "");                            //기준단가
		String BUY_PRICE         = Common.nvl(request.getParameter("BUY_PRICE").trim(),"0").replace(",", "");                             //매입단가
		String PRICE_BASE_DATE   = Common.nvl(request.getParameter("PRICE_BASE_DATE").trim(),"");                                         //단가기준일자
		String SALE_AMT          = Common.nvl(request.getParameter("SALE_AMT").trim(),"0").replace(",", "");                              //매출금액
		String DEAL_AMT          = Common.nvl(request.getParameter("DEAL_AMT").trim(),"0").replace(",", "");                              //주문금액
		String DEAL_PRICE        = Common.nvl(request.getParameter("DEAL_PRICE").trim(),"0").replace(",", "");                            //거래단가
		String SALE_TRANS_PRI    = Common.nvl(request.getParameter("SALE_TRANS_PRI").trim(),"0").replace(",", "");                        //수송단가
		String SALE_TRANS_AMT    = Common.nvl(request.getParameter("SALE_TRANS_AMT").trim(),"0").replace(",", "");                        //수송금액
		String REMARK            = Common.nvl(request.getParameter("REMARK").trim(),"");                                                  //특이사항
		String CREDIT_LIMIT     = Common.nvl(request.getParameter("CREDIT_LIMIT"),"0").replace(",", "");                                  //이전_여신한도
		
		log.info(" 시작 ====================================");
		log.info(" DELV_CUS_CODE         : " + DELV_CUS_CODE); 
		log.info(" TRANS_CUS_CODE        : " + TRANS_CUS_CODE); 
		log.info(" BUY_CUS_CODE          : " + BUY_CUS_CODE); 
		log.info(" LOGIN_CUS_CODE        : " + user.getCusCode()); 
		log.info(" BRAND_CODE            : " + BRAND_CODE); 
		log.info(" SHIP_RQST_DATE        : " + SHIP_RQST_DATE); 
		log.info(" SHIP_RQST_DATE2       : " + SHIP_RQST_DATE2); 
		log.info(" BRAND_DIVISION        : " + BRAND_DIVISION); 
		log.info(" GOOD_CODE             : " + GOOD_CODE);
		log.info(" GOOD_NAME             : " + GOOD_NAME);  
		log.info(" DEAL_QTY              : " + DEAL_QTY); 
		log.info(" ARRIVE_SEC            : " + ARRIVE_SEC); 
		log.info(" SHIP_FLAG             : " + SHIP_FLAG); 
		log.info(" NAB_CODE              : " + NAB_CODE); 
		log.info(" NAB_NAME              : " + NAB_NAME);  
		log.info(" SPO_CODE              : " + SPO_CODE); 
		log.info(" SPO_NAME              : " + SPO_NAME);  
		log.info(" CAR_NO                : " + CAR_NO); 
		log.info(" DRIVER_NAME           : " + DRIVER_NAME); 
		log.info(" DRIVER_HP_NO          : " + DRIVER_HP_NO); 
		log.info(" PLACE_CODE            : " + PLACE_CODE); 
		log.info(" CAR_NAME              : " + CAR_NAME); 
		log.info(" SALE_PRICE            : " + SALE_PRICE); 
		log.info(" BASE_PRICE            : " + BASE_PRICE); 
		log.info(" BUY_PRICE             : " + BUY_PRICE); 
		log.info(" PRICE_BASE_DATE       : " + PRICE_BASE_DATE); 
		log.info(" SALE_AMT              : " + SALE_AMT);
		log.info(" DEAL_AMT              : " + DEAL_AMT);  
		log.info(" DEAL_PRICE            : " + DEAL_PRICE); 
		log.info(" SALE_TRANS_PRI        : " + SALE_TRANS_PRI); 
		log.info(" SALE_TRANS_AMT        : " + SALE_TRANS_AMT); 
		log.info(" CREDIT_LIMIT          : " + CREDIT_LIMIT); 
		log.info(" IP_ADDRESS            : " + java.net.InetAddress.getLocalHost().getHostAddress()); 
		log.info(" LOGIN_DATE            : " + user.getLoginDate()); 
		log.info(" REMARK                : " + REMARK); 
		log.info(" 종료 ====================================");
			
		param.put("SHIP_RQST_DATE",  SHIP_RQST_DATE.replaceAll("-", "")); //출하요청일자
		param.put("BRAND_CODE",      BRAND_CODE);     //브랜드구분('0':NONE BRAND, '1':BRAND)
		param.put("AGENT_CODE",      user.getAgentCode());             //알선업체코드
		param.put("SHIP_FLAG",       SHIP_FLAG);      //출하구분('1':S-OIL, '2':저장소)
		if(SHIP_FLAG.equals("1")){
			SHIP_NAME = NAB_NAME;                     //출하지명
			param.put("NAB_CODE",       NAB_CODE);    //출하지코드(출하구분 '1'일 경우만 들어감)
			param.put("SPO_CODE",       "");          //저장소코드(출하구분 '2'일 경우만 들어감)
		}else{
			SHIP_NAME = SPO_NAME;                     //출하지명
			param.put("NAB_CODE",       "");          //출하지코드(출하구분 '1'일 경우만 들어감)
			param.put("SPO_CODE",       SPO_CODE);    //저장소코드(출하구분 '2'일 경우만 들어감)				
		}
		//여신한도현황
		if(user.getCusGubun().equals("C")){ //일반은 본인의 정보 
			param.put("CUS_CODE",     user.getCusCode()); //거래처코드(로그인_사용자 거래처코드)
		}else if(user.getCusGubun().equals("A")  || user.getCusGubun().equals("R") ){ //딜러, 법은인은 선택한 고객정보
			param.put("CUS_CODE",     Common.nvl(request.getParameter("CUS_CODE"),""));
		}
		param.put("BRANCH_CODE",     user.getBranchCode());    //브랜치코드
		param.put("DELV_CUS_CODE",   DELV_CUS_CODE);   //남품거래처코드
		param.put("TRANS_CUS_CODE",  TRANS_CUS_CODE);  //정산거래처코드
		param.put("BRAND_DIVISION",  BRAND_DIVISION);  //상품구분
		param.put("GOOD_CODE",       GOOD_CODE);       //제품코드
		param.put("DEAL_QTY",        DEAL_QTY);        //거래수량
		param.put("ARRIVE_SEC",      ARRIVE_SEC);      //운송방법
		param.put("TRANS_CORP",      PLACE_CODE);      //운송회사
		param.put("CAR_NO",          CAR_NO);          //수송차량번호
		param.put("DRIVER_NAME",     DRIVER_NAME);     //운전자명
		param.put("HP_NO",           DRIVER_HP_NO);    //운전자_휴대폰번호
		param.put("PRICE_BASE_DATE", PRICE_BASE_DATE); //단가기준일자
		param.put("BASE_PRICE",      BASE_PRICE);      //기준단가
		param.put("BUY_PRICE",       BUY_PRICE);       //매입단가
		param.put("SALE_PRICE",      SALE_PRICE);      //매출단가
		param.put("SALE_AMT",        SALE_AMT);        //매출금액
		param.put("DEAL_AMT",        DEAL_AMT);        //거래금액
		param.put("DEAL_PRICE",      DEAL_PRICE);      //거래단가
		param.put("SALE_TRANS_PRI",  SALE_TRANS_PRI);  //수송단가
		param.put("SALE_TRANS_AMT",  SALE_TRANS_AMT);  //수송금액
		param.put("CREDIT_LIMIT",    CREDIT_LIMIT);    //이전_여신한도(해당 거래제외 여신한도)
		param.put("IP_ADDRESS",      java.net.InetAddress.getLocalHost().getHostAddress());//접속IP
		param.put("LOGIN_DATE",      user.getLoginDate());//접속일시
		param.put("REMARK",          REMARK);          //특이사항
		param.put("LOGIN_CUS_CODE",  user.getCusCode()); //로그인한 거래처코드 (2014.03.06 추가)
		param.put("BUY_CUS_CODE",    BUY_CUS_CODE);    //매입처코드 (2014.03.06 추가)

//		log.info("@@@@@@@@@@@@@@@@@@@@@");
//		log.info(param);
//		log.info("@@@@@@@@@@@@@@@@@@@@@");
		
		//유류주문 저장
		orderDAO.insertOrderDetailList(param);
		
		//기존건 주석처리
//		String msg = Common.strCut(user.getBussName(),null,41,0,false,false) + " 주문이 접수되었습니다. " +timeStr ; //문구가 39Byte이기때문에 거래처명을 41Byte로 제한한다
//		if(user.getMngrHpNo() != null && user.getMngrHpNo().length()>=10 && CommPath.SMS_YN ){
//			log.info("SEND SMS   To : " + user.getMngrHpNo() + " Msg : " + msg);
//			SendSms.send(user.getMngrHpNo(), msg);
//		}
		

		//멀티건 추가
		param.put("BRANCH_CODE",    user.getBranchCode());    //브랜치코드
		List<SmsBO> smsTargetList =  orderDAO.selectSmsTarget(param);
		
		for(SmsBO bo : smsTargetList){

			if(bo!=null){
				log.info("=============================================");
				log.info("SMS SEND"                                     );
			    log.info("MngrEmpName      : " + bo.getMngrEmpName()    );
			    log.info("HpNo             : " + bo.getHpNo()           );
			    log.info("cusName          : " + bo.getCusName()        );
				log.info("=============================================");
				
				String MngrEmpName = Common.nvl( bo.getMngrEmpName(), "");
				String HpNo        = Common.nvl( bo.getHpNo(), "");
				String cusName     = Common.nvl( bo.getCusName(), "");

				//거래처, 날짜, 차량, 유종, 수량, 출하지, 비고
				String msgtype = "1";
				String msg = cusName + "," + SHIP_RQST_DATE2 ;
				if(CAR_NAME.trim().length() > 0 ){
					msg = msg + "," + CAR_NAME ;
				}
				
				msg = msg + "," + GOOD_NAME+ "," + DEAL_QTY ;
				
				if(SHIP_NAME.trim().length() > 0 ){
					msg = msg + "," + SHIP_NAME ;
				}
				
				if(REMARK.trim().length() > 0 ){
					msg = msg + "," + REMARK ;
				}
				
				msg = Common.strCut(msg,null,90,0,false,false) ; //91Byte로 제한한다
								
				if(HpNo != null && HpNo.length()>=10 && CommPath.SMS_YN){
					log.info("SEND SMS   To : " + HpNo + " Msg : " + msg);
					SendSms.send(HpNo, msg, msgtype);
				}
			}else{
				log.info("=============================================");
				log.info("SMS FAIL SEND : 전화번호 정보가 없습니다 ["+user.getCusCode()+"]");
				log.info("=============================================");
			}
			
		}
				
		
		//한도 재조회
		log.info("=============================================");
		log.info("CUS_CODE      : " + user.getCusCode());
		log.info("BRANCH_CODE   : " + user.getBranchCode());
		log.info("REPR_CUS_YN   : " + user.getReprCusYn());
		log.info("=============================================");
		
		//여신한도현황
		param.put("CUS_CODE",    user.getCusCode());
		param.put("REPR_CUS_YN", user.getReprCusYn());
		param.put("BRANCH_CODE", user.getBranchCode());    //브랜치코드
		
		CusCreditBO bo = orderDAO.selectCusCredit(param);
		param.put("cusCreditBO",bo);
		
		return param;
	}

		
	@Override
	public Map<String, Object> selectTradeList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user  = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//매출처 거래원장에서 조회한경우
		String SELL_TRADE_YN = Common.nvl(request.getParameter("SELL_TRADE_YN"),"");
		String CUST_CODE     = Common.nvl(request.getParameter("CUST_CODE"),"");
		
		//저장 목록
		log.info("=============================================");
		log.info("CUS_GUBUN         : " + user.getCusGubun());
		log.info("S_CUS_CODE        : " + request.getParameter("S_CUS_CODE"));
		if(SELL_TRADE_YN == null || SELL_TRADE_YN.equals("") || !SELL_TRADE_YN.equals("Y")){
			log.info("CUS_CODE(1)       : " + user.getCusCode());
		}else{
			log.info("CUS_CODE(2)       :" + CUST_CODE);
		}
		log.info("BRANCH_CODE       : " + user.getBranchCode());
		log.info("FROM_DATE         : " + request.getParameter("from1"));
		log.info("TO_DATE           : " + request.getParameter("to1"));
		log.info("=============================================");
		
		if(user.getCusGubun().equals("C") ){ //일반사용자는 본인의 정보 
			if(SELL_TRADE_YN == null || SELL_TRADE_YN.equals("") || !SELL_TRADE_YN.equals("Y")){
				param.put("CUS_CODE",     user.getCusCode());
			}else{
				param.put("CUS_CODE",     CUST_CODE);
			}
			
		}else if(user.getCusGubun().equals("R") ){ //법인의 거래처 목록
			param.put("CUS_CODE",        Common.nvl(request.getParameter("S_CUS_CODE"),""));
		}else if(user.getCusGubun().equals("A") ){ //딜러의 거래처 목록
			param.put("CUS_CODE",        Common.nvl(request.getParameter("S_CUS_CODE"),""));
		}
		param.put("BRANCH_CODE",    user.getBranchCode());    //브랜치코드
		param.put("FROM_DATE",    Common.nvl(request.getParameter("from1"),"").replaceAll("-", ""));
		param.put("TO_DATE",      Common.nvl(request.getParameter("to1"),"").replaceAll("-", ""));
		
		//paging
		String selectPageNo = (String) request.getParameter("selectPageNo");
		if (selectPageNo == null || selectPageNo.equals("")) {
			selectPageNo = "1";
		}
		
		int pageRange   = 10;
		int rowRange    = 10;
		int selectPage  = Integer.parseInt(selectPageNo);
		int rowTotalCnt = Integer.parseInt(orderDAO.selectTradeListCnt(param));
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
//		param.put("searchRangeEnd",   searchRangeEnd);
		param.put("searchRangeEnd",   rowTotalCnt); //전체 조회
		
		List<OrderSheetBO> list = orderDAO.selectTradeList(param);
		
		log.info("=============================================");
		log.info("list   : " + list.size());
		log.info("=============================================");
		
		param.put("tradeList",  list);
		
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
	public Map<String, Object> selectTradeSum(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//매출처 거래원장에서 조회한경우
		String SELL_TRADE_YN = Common.nvl(request.getParameter("SELL_TRADE_YN"),"");
		String CUST_CODE     = Common.nvl(request.getParameter("CUST_CODE"),"");
		String BRANCH_CODE   = Common.nvl(request.getParameter("BRANCH_CODE"),"");
		
		//저장 목록
		log.info("=============================================");
		if(SELL_TRADE_YN == null || SELL_TRADE_YN.equals("") || !SELL_TRADE_YN.equals("Y")){
			log.info("CUS_CODE(1)       : " + user.getCusCode());
			log.info("BRANCH_CODE(1)    : " + user.getBranchCode());
		}else{
			log.info("CUS_CODE(2)       :" + user.getCusCode());
			log.info("BRANCH_CODE(2)    :" + user.getBranchCode());
		}
		
		log.info("FROM_DATE         : " + request.getParameter("from1"));
		log.info("TO_DATE           : " + request.getParameter("to1"));
		log.info("=============================================");
		
		
		if(user.getCusGubun().equals("C") ){ //일반사용자는 본인의 정보 
			if(SELL_TRADE_YN == null || SELL_TRADE_YN.equals("") || !SELL_TRADE_YN.equals("Y")){
				param.put("CUS_CODE",     user.getCusCode());
				param.put("BRANCH_CODE",  user.getBranchCode());
			}else{
				param.put("CUS_CODE",     CUST_CODE);
				param.put("BRANCH_CODE",  user.getBranchCode());
			}
		}else if(user.getCusGubun().equals("R") ){ //법인의 거래처 목록
			param.put("CUS_CODE",        Common.nvl(request.getParameter("S_CUS_CODE"),""));
			param.put("BRANCH_CODE",  user.getBranchCode());
		}else if(user.getCusGubun().equals("A") ){ //딜러의 거래처 목록
			param.put("CUS_CODE",        Common.nvl(request.getParameter("S_CUS_CODE"),""));
			param.put("BRANCH_CODE",  user.getBranchCode());
		}
		param.put("FROM_DATE",    Common.nvl(request.getParameter("from1"),"").replaceAll("-", ""));
		param.put("TO_DATE",      Common.nvl(request.getParameter("to1"),"").replaceAll("-", ""));
		
		List<OrderSheetSumBO> sumList = orderDAO.selectTradeSum(param);
		
		param.put("sumList",  sumList);
		
		return param;
	
	}
	
	
	@Override
	public Map<String, Object> selectTradeDetail(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");

		//매출처 거래원장에서 조회한경우
		String SELL_TRADE_YN = Common.nvl(request.getParameter("SELL_TRADE_YN"),"");
		String CUST_CODE     = Common.nvl(request.getParameter("CUST_CODE"),"");
		String BRANCH_CODE   = Common.nvl(request.getParameter("BRANCH_CODE"),"");
		
		//저장 목록
		log.info("=============================================");
		log.info("S_CUS_CODE        : " + request.getParameter("S_CUS_CODE"));
		if(SELL_TRADE_YN == null || SELL_TRADE_YN.equals("") || !SELL_TRADE_YN.equals("Y")){
			log.info("CUS_CODE(1)       : " + user.getCusCode());
			log.info("BRANCH_CODE(1)    : " + user.getBranchCode());
		}else{
			log.info("CUS_CODE(2)       :" + CUST_CODE);
			log.info("BRANCH_CODE(2)    :" + BRANCH_CODE);
		}
		log.info("FROM_DATE         : " + request.getParameter("from1"));
		log.info("TO_DATE           : " + request.getParameter("to1"));
		log.info("DATA_KEY          : " + request.getParameter("tradeKey"));
		log.info("=============================================");
		
		if(user.getCusGubun().equals("C") ){ //일반사용자는 본인의 정보 
			if(SELL_TRADE_YN == null || SELL_TRADE_YN.equals("") || !SELL_TRADE_YN.equals("Y")){
				param.put("CUS_CODE",     user.getCusCode());
				param.put("BRANCH_CODE",  user.getBranchCode());
			}else{
				param.put("CUS_CODE",     CUST_CODE);
				param.put("BRANCH_CODE",  BRANCH_CODE);
			}
		}else if(user.getCusGubun().equals("R") ){ //법인의 거래처 목록
			param.put("CUS_CODE",        Common.nvl(request.getParameter("S_CUS_CODE"),""));
			param.put("BRANCH_CODE",  user.getBranchCode());
		}else if(user.getCusGubun().equals("A") ){ //딜러의 거래처 목록
			param.put("CUS_CODE",        Common.nvl(request.getParameter("S_CUS_CODE"),""));
			param.put("BRANCH_CODE",  user.getBranchCode());
		}
		param.put("FROM_DATE",    Common.nvl(request.getParameter("from1"),"").replaceAll("-", ""));
		param.put("TO_DATE",      Common.nvl(request.getParameter("to1"),"").replaceAll("-", ""));
		param.put("DATA_KEY",     Common.nvl(request.getParameter("tradeKey"),""));
		
		
		param.put("searchRangeStart", 1);
		param.put("searchRangeEnd",   1);
		
		OrderSheetBO detail = orderDAO.selectTradeDetail(param);
		
		log.info("=============================================");
		log.info("detail   : " + detail.toString());
		log.info("=============================================");
		
		param.put("tradeDetail",  detail);
		
		return param;
		
	}

	@Override
	public Map<String, Object> selectAgentList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//저장 목록
		log.info("=============================================");
		log.info("AGENT_CODE          : " + user.getAgentCode());
		log.info("S_CUS_CODE          : " + request.getParameter("S_CUS_CODE"));
		log.info("=============================================");
		
		param.put("AGENT_CODE",     user.getAgentCode());
		param.put("S_CUS_CODE",     Common.nvl(request.getParameter("S_CUS_CODE"),""));
		
		//paging
		String selectPageNo = (String) request.getParameter("selectPageNo");
		if (selectPageNo == null || selectPageNo.equals("")) {
			selectPageNo = "1";
		}
		
		int pageRange   = 10;
		int rowRange    = 8;
		int selectPage  = Integer.parseInt(selectPageNo);
		int rowTotalCnt = Integer.parseInt(orderDAO.selectAgentListCnt(param));
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
		
		List<AgentCustBO> list = orderDAO.selectAgentList(param);
		
		log.info("=============================================");
		log.info("list   : " + list.size());
		log.info("=============================================");
		
		param.put("custList",  list);
		
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
	public Map<String, Object> selectReprList(HttpServletRequest request, Map<String, Object> param, HttpSession session) throws Exception {
		
		//사용자 정보
		CusMasterMstBO user = (CusMasterMstBO) session.getAttribute("ACCOUNT");
		
		//저장 목록
		log.info("=============================================");
		log.info("CUS_CODE          : " + user.getCusCode());
		log.info("S_CUS_CODE          : " + request.getParameter("S_CUS_CODE"));
		log.info("=============================================");
		
		param.put("CUS_CODE",     user.getCusCode());
		param.put("S_CUS_CODE",     Common.nvl(request.getParameter("S_CUS_CODE"),""));
		
		//paging
		String selectPageNo = (String) request.getParameter("selectPageNo");
		if (selectPageNo == null || selectPageNo.equals("")) {
			selectPageNo = "1";
		}
		
		int pageRange   = 10;
		int rowRange    = 6;
		int selectPage  = Integer.parseInt(selectPageNo);
		int rowTotalCnt = Integer.parseInt(orderDAO.selectReprListCnt(param));
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
		
		List<ReprCustBO> list = orderDAO.selectReprList(param);
		
		log.info("=============================================");
		log.info("list   : " + list.size());
		log.info("=============================================");
		
		param.put("custList",  list);
		
		param.put("rowTotalCnt", rowTotalCnt);
		param.put("pageRange",   pageRange);
		param.put("rowRange",    rowRange);
		param.put("selectPage",  selectPage);
		param.put("pageStart",   pageStart);
		param.put("totalPage",   totalPage);
		param.put("pageEnd",     pageEnd);
		
		return param;
	}	
}
