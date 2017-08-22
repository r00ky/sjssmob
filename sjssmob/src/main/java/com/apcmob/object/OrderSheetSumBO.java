package com.apcmob.object;

public class OrderSheetSumBO {

	private String rowNum       = "";
	private String dataKey      = "";
	private String goodName     = "";
	private String dealQty      = "";
	private String saleAmt      = "";
	private String recvAmt      = "";
	private String blcAmt       = "";
	
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
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
	public String getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(String saleAmt) {
		this.saleAmt = saleAmt;
	}
	public String getRecvAmt() {
		return recvAmt;
	}
	public void setRecvAmt(String recvAmt) {
		this.recvAmt = recvAmt;
	}
	public String getBlcAmt() {
		return blcAmt;
	}
	public void setBlcAmt(String blcAmt) {
		this.blcAmt = blcAmt;
	}
	
	@Override
	public String toString() {
		return "OrderSheetBO [rowNum=" + rowNum + ", dataKey=" + dataKey + ", goodName=" + goodName + ", dealQty="
				+ dealQty + ", saleAmt=" + saleAmt + ", recvAmt=" + recvAmt + ", blcAmt=" + blcAmt + "]";
	}
	
}
