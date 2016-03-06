package com.pg.pg.bean;

public class Pgdr_price {
	private String price_id;//PRICE_ID
	private String price_name;//PRICE_NAME
	private String price_isvalid;//PRICE_ISVALID 是否是有效 1 有效 0 无效
	private String price_type;//PRICE_TYPE -- 类型 手机回收	衣服回收 塑料瓶回收	易拉罐回收	
							      //纸箱回收 电子设备回收 家电回收 其他回收
	private String price_price;//PRICE_PRICE
	private String price_explain;//PRICE_EXPLAIN
	
	public String getPrice_id() {
		return price_id;
	}
	public void setPrice_id(String price_id) {
		this.price_id = price_id;
	}
	public String getPrice_name() {
		return price_name;
	}
	public void setPrice_name(String price_name) {
		this.price_name = price_name;
	}
	public String getPrice_isvalid() {
		return price_isvalid;
	}
	public void setPrice_isvalid(String price_isvalid) {
		this.price_isvalid = price_isvalid;
	}
	public String getPrice_type() {
		return price_type;
	}
	public void setPrice_type(String price_type) {
		this.price_type = price_type;
	}
	public String getPrice_price() {
		return price_price;
	}
	public void setPrice_price(String price_price) {
		this.price_price = price_price;
	}
	public String getPrice_explain() {
		return price_explain;
	}
	public void setPrice_explain(String price_explain) {
		this.price_explain = price_explain;
	}
}
