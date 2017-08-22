package com.apcmob.object;

public class OrderSheetBO {
	
	private String dealDate      = "";
	private String dataKey       = "";
	private String goodName      = "";
	private String dealQty       = "";
	private String dealPrice     = "";
	private String saleAmt       = "";
	private String creditRecvAmt = "";
	private String cardRecvAmt   = "";
	private String bankRecvAmt   = "";
	private String totalRecvAmt  = "";
	private String blcAmt        = "";
	
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public String getDataKey() {
		return dataKey;
	}
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getDealQty() {
		return dealQty;
	}
	public void setDealQty(String dealQty) {
		this.dealQty = dealQty;
	}
	public String getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(String dealPrice) {
		this.dealPrice = dealPrice;
	}
	public String getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(String saleAmt) {
		this.saleAmt = saleAmt;
	}
	public String getCreditRecvAmt() {
		return creditRecvAmt;
	}
	public void setCreditRecvAmt(String creditRecvAmt) {
		this.creditRecvAmt = creditRecvAmt;
	}
	public String getCardRecvAmt() {
		return cardRecvAmt;
	}
	public void setCardRecvAmt(String cardRecvAmt) {
		this.cardRecvAmt = cardRecvAmt;
	}
	public String getBankRecvAmt() {
		return bankRecvAmt;
	}
	public void setBankRecvAmt(String bankRecvAmt) {
		this.bankRecvAmt = bankRecvAmt;
	}
	public String getBlcAmt() {
		return blcAmt;
	}
	public void setBlcAmt(String blcAmt) {
		this.blcAmt = blcAmt;
	}
	public String getTotalRecvAmt() {
		return totalRecvAmt;
	}
	public void setTotalRecvAmt(String totalRecvAmt) {
		this.totalRecvAmt = totalRecvAmt;
	}
	@Override
	public String toString() {
		return "OrderSheetBO [dealDate=" + dealDate + ", dataKey=" + dataKey + ", goodName=" + goodName + ", dealQty="
				+ dealQty + ", dealPrice=" + dealPrice + ", saleAmt=" + saleAmt + ", creditRecvAmt=" + creditRecvAmt
				+ ", cardRecvAmt=" + cardRecvAmt + ", bankRecvAmt=" + bankRecvAmt + ", totalRecvAmt=" + totalRecvAmt
				+ ", blcAmt=" + blcAmt + "]";
	}
	
}
