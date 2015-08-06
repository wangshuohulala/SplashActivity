package com.haomi.mantou.vo.base;

import java.util.List;

public abstract class CommonResultListVo extends CommonResultVo implements ResponseBodyBase{

	@Override
	public CommonResultListVo getResult() {
		return this;
	}

	public abstract List<?> getList();

}
