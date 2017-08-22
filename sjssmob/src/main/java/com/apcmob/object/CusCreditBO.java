package com.apcmob.object;

public class CusCreditBO {

	private String creditLimit   = "";
	private String prevCreditBlc = "";
	private String currRecvAmt   = "";
	private String currSaleAmt   = "";
	private String orderAbleAmt  = "";
	
	public String getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}
	public String getPrevCreditBlc() {
		return prevCreditBlc;
	}
	public void setPrevCreditBlc(String prevCreditBlc) {
		this.prevCreditBlc = prevCreditBlc;
	}
	public String getCurrRecvAmt() {
		return currRecvAmt;
	}
	public void setCurrRecvAmt(String currRecvAmt) {
		this.currRecvAmt = currRecvAmt;
	}
	public String getCurrSaleAmt() {
		return currSaleAmt;
	}
	public void setCurrSaleAmt(String currSaleAmt) {
		this.currSaleAmt = currSaleAmt;
	}
	public String getOrderAbleAmt() {
		return orderAbleAmt;
	}
	public void setOrderAbleAmt(String orderAbleAmt) {
		this.orderAbleAmt = orderAbleAmt;
	}
	
}
