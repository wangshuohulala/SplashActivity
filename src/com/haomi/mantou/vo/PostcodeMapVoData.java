package com.haomi.mantou.vo;

import java.util.List;

import com.haomi.mantou.vo.base.CommonResultVo;

public class PostcodeMapVoData extends CommonResultVo{

	private List<PostcodeMapVo> data;

	public List<PostcodeMapVo> getData() {
		return data;
	}

	public void setData(List<PostcodeMapVo> data) {
		this.data = data;
	}
}
