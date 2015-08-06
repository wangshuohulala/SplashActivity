package com.haomi.mantou.vo;

import java.util.List;

public class IncomeListVo{

	private String date;
	
	private String totalmoney;
	
	private List<IncomeOrder> order;

	public static class IncomeOrder{
		private String order_id;
		private String status;
		private String car_name;
		private String car_color;
		private String car_no;
		private String price;
		private String order_time;
		
		public String getCarInfo(){
			return car_name +"  " +car_color+"  "+car_no;
		}
		public String getOrder_id() {
			return order_id;
		}
		public String getStatus() {
			return status;
		}
		public String getCar_name() {
			return car_name;
		}
		public String getCar_color() {
			return car_color;
		}
		public String getCar_no() {
			return car_no;
		}
		public String getPrice() {
			return price;
		}
		public String getOrder_time() {
			return order_time;
		}
		
	}

	public String getDate() {
		return date;
	}

	public String getTotalmoney() {
		return totalmoney;
	}

	public List<IncomeOrder> getOrder() {
		return order;
	}
	
	
	
	
}
