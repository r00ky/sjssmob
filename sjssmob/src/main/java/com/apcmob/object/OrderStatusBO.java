package com.apcmob.object;

public class OrderStatusBO {
	
	private String orderIdx     = "";   
	private String orderDate    = "";
	private String shipRqstDate = "";
	private String delvCusName  = "";
	private String brandDivisionName = "";
	private String goodName     = "";
	private String unitName     = "";
	private String dealQty      = "";
	private String salePrice    = "";
	private String saleAmt      = "";
	private String dealPrice    = "";
	private String dealAmt      = "";
	private String arriveName   = "";
	private String shipName     = "";
	private String nabName      = "";
	private String carName      = "";
	private String saleTransPri = "";
	private String saleTransAmt = "";
	private String statName     = "";
	private String orderStat    = "";
	private String remark       = "";
	private String driverName   = "";
	private String hpNo         = "";
	
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getHpNo() {
		return hpNo;
	}
	public void setHpNo(String hpNo) {
		this.hpNo = hpNo;
	}
	public String getOrderIdx() {
		return orderIdx;
	}
	public void setOrderIdx(String orderIdx) {
		this.orderIdx = orderIdx;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getShipRqstDate() {
		return shipRqstDate;
	}
	public void setShipRqstDate(String shipRqstDate) {
		this.shipRqstDate = shipRqstDate;
	}
	public String getDelvCusName() {
		return delvCusName;
	}
	public void setDelvCusName(String delvCusName) {
		this.delvCusName = delvCusName;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getBrandDivisionName() {
		return brandDivisionName;
	}
	public void setBrandDivisionName(String brandDivisionName) {
		this.brandDivisionName = brandDivisionName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getDealQty() {
		return dealQty;
	}
	public void setDealQty(String dealQty) {
		this.dealQty = dealQty;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(String saleAmt) {
		this.saleAmt = saleAmt;
	}
	public String getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(String dealPrice) {
		this.dealPrice = dealPrice;
	}
	public String getDealAmt() {
		return dealAmt;
	}
	public void setDealAmt(String dealAmt) {
		this.dealAmt = dealAmt;
	}
	public String getArriveName() {
		return arriveName;
	}
	public void setArriveName(String arriveName) {
		this.arriveName = arriveName;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public String getNabName() {
		return nabName;
	}
	public void setNabName(String nabName) {
		this.nabName = nabName;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public String getSaleTransPri() {
		return saleTransPri;
	}
	public void setSaleTransPri(String saleTransPri) {
		this.saleTransPri = saleTransPri;
	}
	public String getSaleTransAmt() {
		return saleTransAmt;
	}
	public void setSaleTransAmt(String saleTransAmt) {
		this.saleTransAmt = saleTransAmt;
	}
	public String getStatName() {
		return statName;
	}
	public void setStatName(String statName) {
		this.statName = statName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderStat() {
		return orderStat;
	}
	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}
	@Override
	public String toString() {
		return "OrderStatusBO [orderIdx=" + orderIdx + ", orderDate=" + orderDate + ", shipRqstDate=" + shipRqstDate
				+ ", delvCusName=" + delvCusName + ", brandDivisionName=" + brandDivisionName + ", goodName=" + goodName
				+ ", unitName=" + unitName + ", dealQty=" + dealQty + ", salePrice=" + salePrice + ", saleAmt="
				+ saleAmt + ", dealPrice=" + dealPrice + ", dealAmt=" + dealAmt + ", arriveName=" + arriveName
				+ ", shipName=" + shipName + ", nabName=" + nabName + ", carName=" + carName + ", saleTransPri="
				+ saleTransPri + ", saleTransAmt=" + saleTransAmt + ", statName=" + statName + ", orderStat="
				+ orderStat + ", remark=" + remark + "]";
	}

}
