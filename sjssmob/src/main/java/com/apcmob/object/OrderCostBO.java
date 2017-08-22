package com.apcmob.object;

public class OrderCostBO {

	private String basePrice    ;
	private String buyPrice     ;
	private String salePrice    ;
	private String priceBaseDate;
	private String saleTransPri ;
	public String getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}
	public String getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getPriceBaseDate() {
		return priceBaseDate;
	}
	public void setPriceBaseDate(String priceBaseDate) {
		this.priceBaseDate = priceBaseDate;
	}
	public String getSaleTransPri() {
		return saleTransPri;
	}
	public void setSaleTransPri(String saleTransPri) {
		this.saleTransPri = saleTransPri;
	}
}
