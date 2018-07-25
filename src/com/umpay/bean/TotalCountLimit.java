package com.umpay.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.exception.BaseLimitException;
import com.umpay.exception.MoreThanTotalCountLimitException;
import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.TaskHandler;
import com.umpay.proxyservice.util.WriterUtil;

/**
 * 总数限制
 * 
 * @author xuxiaojia
 */
public class TotalCountLimit extends BaseLimitBean {
	public TotalCountLimit(Integer limitType, int recordSubNum, String pathName, boolean isAppend) {
		super(limitType);
		this.subNum = recordSubNum;
		this.filePath = pathName;
		this.isAppend = isAppend;
	}

	private static final Logger log = LoggerFactory.getLogger(TotalCountLimit.class);
	/* 减小的数额 */
	private int subNum;
	/* 记录文件存放的文件路径 */
	private String filePath;
	/* 是否追加 */
	private boolean isAppend;

	public void setIsAppend(boolean isAppend) {
		this.isAppend = isAppend;
	}

	public int getSubNum() {
		return subNum;
	}

	public void setSubNum(int subNum) {
		this.subNum = subNum;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	protected void logLimit(int curLimitCount) {
		int overplusNum = this.getMaxLimit() - curLimitCount;
		log.info(this.getMapKey() + "当前已达 " + curLimitCount + " 次访问，总限制：" + this.getMaxLimit() + " 剩余 "
				+ (overplusNum < 0 ? 0 : overplusNum) + " 次");
		// 达减额配置或剩余次数为0时记录剩余次数
		if (overplusNum >= 0 && (overplusNum % subNum == 0 || overplusNum == 0)) {
			// 写出信息到配置文件
			WriterUtil.writer(new WriteInfoBean(this.getMapKey(), filePath,
					Constant.NEW_LINE + (overplusNum < 0 ? 0 : overplusNum), isAppend));
		}
	}

	@Override
	protected void throwMoreThanLimitException(int curLimitCount) throws BaseLimitException {
		throw new MoreThanTotalCountLimitException(this.getMapKey(), curLimitCount, this.getMaxLimit());
	}

	@Override
	protected boolean have2clearLimit() {
		return false;
	}

	
	
	/**
	 * 总量级现在，对请求，不增加访问量
	 * 对响应，增加访问量
	 */
	@Override
	public void checkLimit() throws BaseLimitException {		
		// 如果配置为不限制，则直接返回不进行校验
		if (this.getMaxLimit() == NO_LIMIT) {
			return;
		}
		int curLimitCount =0;
		if (TaskHandler.QUERY_TYPE.get()==Constant.QUERY_TYPE_REQ) {
			curLimitCount=this.getAtomicInteger().get();
		}else{
			curLimitCount=this.getAtomicInteger().incrementAndGet();
		}
		logLimit(curLimitCount);
		if (this.haveMoreThanLimit(curLimitCount)) {
			throwMoreThanLimitException(curLimitCount);
		}
	}
}
