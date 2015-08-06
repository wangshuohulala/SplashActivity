package com.haomi.mantou.vo;

import java.util.List;

import com.haomi.mantou.vo.base.CommonResultVo;

public class IncomeListVoData extends CommonResultVo{

	private List<IncomeListVo> data;

	public List<IncomeListVo> getData() {
		return data;
	}

	public void setData(List<IncomeListVo> data) {
		this.data = data;
	}
}
