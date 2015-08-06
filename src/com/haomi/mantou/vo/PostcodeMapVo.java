package com.haomi.mantou.vo;

import java.util.List;

public class PostcodeMapVo {

	private String provincecode;

	private String province;//

	private List<City> city;

	@Override
	public String toString() {
		return province;
	}

	public String getProvincecode() {
		return provincecode;
	}

	public String getProvince() {
		return province;
	}

	public List<City> getCity() {
		return city;
	}

	public static class City {
		private String citycode;
		private String cityname;

		@Override
		public String toString() {
			return cityname;
		}

		public String getCitycode() {
			return citycode;
		}

		public String getCityname() {
			return cityname;
		}

	}

}
