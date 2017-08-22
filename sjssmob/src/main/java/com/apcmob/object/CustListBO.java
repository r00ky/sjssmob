package com.apcmob.object;

public class CustListBO {

	private String cusCode ="";
	private String cusName ="";
	
	public String getCusCode() {
		return cusCode;
	}
	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	@Override
	public String toString() {
		return "CustListBO [cusCode=" + cusCode + ", cusName=" + cusName + "]";
	}

}
