package com.apcmob.object;

public class FinishSheet1 {

	private String goodCode = "";
	private String goodName = "";
	private String openQty  = "";
	private String inQty    = "";
	private String outQty   = "";
	private String lossQty  = "";
	private String closeQty = "";
	
	public String getGoodCode() {
		return goodCode;
	}
	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getOpenQty() {
		return openQty;
	}
	public void setOpenQty(String openQty) {
		this.openQty = openQty;
	}
	public String getInQty() {
		return inQty;
	}
	public void setInQty(String inQty) {
		this.inQty = inQty;
	}
	public String getOutQty() {
		return outQty;
	}
	public void setOutQty(String outQty) {
		this.outQty = outQty;
	}
	public String getLossQty() {
		return lossQty;
	}
	public void setLossQty(String lossQty) {
		this.lossQty = lossQty;
	}
	public String getCloseQty() {
		return closeQty;
	}
	public void setCloseQty(String closeQty) {
		this.closeQty = closeQty;
	}
	@Override
	public String toString() {
		return "FinishSheet1 [goodCode=" + goodCode + ", goodName=" + goodName + ", openQty=" + openQty + ", inQty="
				+ inQty + ", outQty=" + outQty + ", lossQty=" + lossQty + ", closeQty=" + closeQty + "]";
	}
	
	
}
