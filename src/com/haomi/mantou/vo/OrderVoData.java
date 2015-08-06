package com.haomi.mantou.vo;

import java.util.List;

import com.haomi.mantou.vo.base.CommonResultVo;

public class OrderVoData extends CommonResultVo{

	private List<OrderVo> data;

	public List<OrderVo> getData() {
		return data;
	}

	public void setData(List<OrderVo> data) {
		this.data = data;
	}
}
