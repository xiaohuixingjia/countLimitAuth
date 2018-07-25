package com.umpay.warnMsg.count;

import com.umf.warnMsg.service.WXWarnService;

/**
 *  计数限制工程异常计数器
 * @author xuxiaojia
 */
public class LimitErrorCount extends BaseCount{

	@Override
	public void reachLimit(int num) {
		WXWarnService.sendMsg("流向限制已达"+num+"条，请查看限制工程简要日志");
	}

}
