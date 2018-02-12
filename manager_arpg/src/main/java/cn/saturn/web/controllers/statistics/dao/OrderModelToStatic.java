package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;

public class OrderModelToStatic {
	
	private String order_id;
	private Date create_time;
	private Date finish_time;
	private int goods_id;
	private double goods_price;
	private double amount;
	private int server_id;
	private int account_id;
	private String account_name;
	private String player_name;	
	private String platform;
	private String platform_order_no;
	private  String pay_type;
	private int server_state;
	private String platform_order_no_sign;
	private String ext;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	public double getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getServer_id() {
		return server_id;
	}
	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getPlayer_name() {
		return player_name;
	}
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getPlatform_order_no() {
		return platform_order_no;
	}
	public void setPlatform_order_no(String platform_order_no) {
		this.platform_order_no = platform_order_no;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public int getServer_state() {
		return server_state;
	}
	public void setServer_state(int server_state) {
		this.server_state = server_state;
	}
	public String getPlatform_order_no_sign() {
		return platform_order_no_sign;
	}
	public void setPlatform_order_no_sign(String platform_order_no_sign) {
		this.platform_order_no_sign = platform_order_no_sign;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public OrderModelToStatic() {
		super();
	}
	public OrderModelToStatic(String order_id, Date create_time, Date finish_time, int goods_id, double goods_price,
			double amount, int server_id, int account_id, String account_name, String player_name, String platform,
			String platform_order_no, String pay_type, int server_state, String platform_order_no_sign, String ext) {
		super();
		this.order_id = order_id;
		this.create_time = create_time;
		this.finish_time = finish_time;
		this.goods_id = goods_id;
		this.goods_price = goods_price;
		this.amount = amount;
		this.server_id = server_id;
		this.account_id = account_id;
		this.account_name = account_name;
		this.player_name = player_name;
		this.platform = platform;
		this.platform_order_no = platform_order_no;
		this.pay_type = pay_type;
		this.server_state = server_state;
		this.platform_order_no_sign = platform_order_no_sign;
		this.ext = ext;
	}
	@Override
	public String toString() {
		return "OrderModelToStatic [order_id=" + order_id + ", create_time=" + create_time + ", finish_time="
				+ finish_time + ", goods_id=" + goods_id + ", goods_price=" + goods_price + ", amount=" + amount
				+ ", server_id=" + server_id + ", account_id=" + account_id + ", account_name=" + account_name
				+ ", player_name=" + player_name + ", platform=" + platform + ", platform_order_no=" + platform_order_no
				+ ", pay_type=" + pay_type + ", server_state=" + server_state + ", platform_order_no_sign="
				+ platform_order_no_sign + ", ext=" + ext + "]";
	}
	
	
	
}
