package com.apcmob.object;

public class GraphBO2 {

	private String cashQty = "";
	private String blcQty  = "";
	private String tktQty  = "";
	private String crdtQty = "";
	private String totQty  = "";
	public String getCashQty() {
		return cashQty;
	}
	public void setCashQty(String cashQty) {
		this.cashQty = cashQty;
	}
	public String getBlcQty() {
		return blcQty;
	}
	public void setBlcQty(String blcQty) {
		this.blcQty = blcQty;
	}
	public String getTktQty() {
		return tktQty;
	}
	public void setTktQty(String tktQty) {
		this.tktQty = tktQty;
	}
	public String getCrdtQty() {
		return crdtQty;
	}
	public void setCrdtQty(String crdtQty) {
		this.crdtQty = crdtQty;
	}
	public String getTotQty() {
		return totQty;
	}
	public void setTotQty(String totQty) {
		this.totQty = totQty;
	}
	@Override
	public String toString() {
		return "GraphBO2 [cashQty=" + cashQty + ", blcQty=" + blcQty + ", tktQty=" + tktQty + ", crdtQty=" + crdtQty
				+ ", totQty=" + totQty + "]";
	}
	
}
