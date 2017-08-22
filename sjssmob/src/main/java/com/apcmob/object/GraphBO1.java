package com.apcmob.object;

public class GraphBO1 {

	private String ugQty  = "";
	private String kQty   = "";
	private String hQty   = "";
	private String totQty = "";
	public String getUgQty() {
		return ugQty;
	}
	public void setUgQty(String ugQty) {
		this.ugQty = ugQty;
	}
	public String getkQty() {
		return kQty;
	}
	public void setkQty(String kQty) {
		this.kQty = kQty;
	}
	public String gethQty() {
		return hQty;
	}
	public void sethQty(String hQty) {
		this.hQty = hQty;
	}
	public String getTotQty() {
		return totQty;
	}
	public void setTotQty(String totQty) {
		this.totQty = totQty;
	}
	@Override
	public String toString() {
		return "GraphBO1 [ugQty=" + ugQty + ", kQty=" + kQty + ", hQty=" + hQty + ", totQty=" + totQty + "]";
	}
	
	
}
