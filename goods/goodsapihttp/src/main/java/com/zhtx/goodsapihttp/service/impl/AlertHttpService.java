package com.zhtx.goodsapihttp.service.impl;

import org.springframework.stereotype.Service;

import com.zhtx.goodsapihttp.service.inter.IAlertHttpService;
import com.zhtx.goodsentity.common.JsonResult;


@Service
public class AlertHttpService implements IAlertHttpService {
	@Override
	public JsonResult heart() {
		return new JsonResult();
	}

}
