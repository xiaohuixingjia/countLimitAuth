package com.umpay.proxyservice;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.bean.BaseLimitBean;
import com.umpay.bean.DayLimit;
import com.umpay.bean.LimitConfig;
import com.umpay.bean.SecondLimit;
import com.umpay.bean.TotalCountLimit;
import com.umpay.bean.WriteInfoBean;
import com.umpay.exception.BaseException;
import com.umpay.exception.BaseLimitException;
import com.umpay.proxyservice.util.CastUtil;
import com.umpay.proxyservice.util.DateUtil;
import com.umpay.proxyservice.util.FileUtil;
import com.umpay.proxyservice.util.HttpMap;
import com.umpay.proxyservice.util.PropUtil;
import com.umpay.proxyservice.util.WriterUtil;
import com.umpay.tactics.fileAnalz.FileAnalzTactics;
import com.umpay.tactics.writer.impl.TotalNumMemoryWriter;
import com.umpay.warnMsg.count.LimitErrorCount;

/**
 * 处理风影请求类
 * 
 * @author
 * @date 2016年6月15日 上午10:14:47
 */
public class TaskHandler {
	private static final Logger log = LoggerFactory.getLogger(TaskHandler.class);
	/**
	 * 超控时打印的超控日志
	 */
	private static Logger logLimitOut = LoggerFactory.getLogger("limitOutLog");
	/**
	 * 日志记录参数
	 */
	private static final ThreadLocal<String> LOG_ARGUS = new ThreadLocal<String>();
	
	
	/**
	 * 请求类型
	 */
	public static final ThreadLocal<Integer> QUERY_TYPE = new ThreadLocal<Integer>();
	
	
	public static final String LOG_START = "开始校验：";
	public static final String LOG_END = "结束校验：";
	public static final String LOG_ERROR = "校验出错：";
	public static final String LOG_NO_PASS = "校验不通过：";
	public static final String LOG_TIME_CONSUMING = "  校验耗时：";
	/*
	 * 默认的每日限制
	 */
	private int defaultDayMaxLimit;
	/*
	 * 默认的每秒限制
	 */
	private int defaultSecondMaxLimit;
	/*
	 * 异常计数告警发送
	 */
	private LimitErrorCount limitErrorCount;
	/*
	 * 每日限制的配置map集合 key为merid+“,”+funcode
	 */
	private Map<String, LimitConfig> configMap;
	/*
	 * 最大量访问的指定目录
	 */
	private String pathName;
	/*
	 * 总流量限制时的减少数记录文件的配置
	 */
	private int recordSubNum;
	/*
	 * 日限制写文件时是否追加写入文件
	 */
	private boolean isAppend;
	/*
	 * 简单文件解析策略
	 */
	private FileAnalzTactics simpleFileAnalz;
	/*
	 * properties配置文件名称
	 */
	private String propName;
	/*
	 * 生成的文件的存放地址
	 */
	private String generFilePath;
	/*
	 * 实时配置修改的必输项元素集合
	 */
	private List<String> configAlterElemList;
	/*
	 * 实时配置修改的类型
	 */
	private Map<String, Integer> configTypeMap;
	/*
	 * 备份的文件路径
	 */
	private String backFilePath;

	public void setBackFilePath(String backFilePath) {
		this.backFilePath = backFilePath;
	}

	public void setConfigTypeMap(Map<String, Integer> configTypeMap) {
		this.configTypeMap = configTypeMap;
	}

	public void setConfigAlterElemList(List<String> configAlterElemList) {
		this.configAlterElemList = configAlterElemList;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public void setGenerFilePath(String generFilePath) {
		this.generFilePath = generFilePath;
	}

	public void setIsAppend(boolean isAppend) {
		this.isAppend = isAppend;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public void setRecordSubNum(int recordSubNum) {
		this.recordSubNum = recordSubNum;
	}

	public void setSimpleFileAnalz(FileAnalzTactics simpleFileAnalz) {
		this.simpleFileAnalz = simpleFileAnalz;
	}

	public void setDefaultDayMaxLimit(int defaultDayMaxLimit) {
		this.defaultDayMaxLimit = defaultDayMaxLimit;
	}

	public void setDefaultSecondMaxLimit(int defaultSecondMaxLimit) {
		this.defaultSecondMaxLimit = defaultSecondMaxLimit;
	}

	public void setLimitErrorCount(LimitErrorCount limitErrorCount) {
		this.limitErrorCount = limitErrorCount;
	}

	/**
	 * 设置日志的打印起始参数
	 * 
	 * @param xmlMap
	 *            封装了前置传过来信息的map对象
	 * @param key
	 */
	public void setLogArgus(Map<String, String> xmlMap, String key) {
		TaskHandler.LOG_ARGUS.set(key + Constant.LOG_PARAM_SEPARATOR + xmlMap.get(HttpMap.TRANSID)
				+ Constant.LOG_PARAM_SEPARATOR + xmlMap.get(HttpMap.SEQUENCE)
				+ Constant.LOG_PARAM_SEPARATOR+QUERY_TYPE.get());
	}

	/**
	 * 获取日志的打印参数
	 * 
	 * @return
	 */
	public String getLogArgus() {
		return TaskHandler.LOG_ARGUS.get();
	}

	public Map<String, LimitConfig> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, LimitConfig> configMap) {
		this.configMap = configMap;
	}

	/**
	 * 从countLimit.properties配置文件中读取配置信息到内存中
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		Properties prop = PropUtil.loadProp(propName);
		configMap = new HashMap<String, LimitConfig>();
		getLimitConfByProp(prop, configMap);
		forEachTotalCount2limitMap();
	}

	/**
	 * 遍历指定目录下的文件来增加流量总量的配置
	 * 
	 * @throws Exception
	 */
	private void forEachTotalCount2limitMap() throws Exception {
		File f = new File(pathName);
		// 过滤出文件集合
		File[] listFiles = f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		if(listFiles!=null&&listFiles.length>0){
			for (File file : listFiles) {
				generTotalLimat2limitMap(file);
			}
		}
	}

	/**
	 * 生成总数限制到限制配置中
	 * 
	 * @param file
	 * @param configMap
	 * @throws Exception
	 */
	private void generTotalLimat2limitMap(File file) throws Exception {
		TotalNumMemoryWriter writeTatics = new TotalNumMemoryWriter();
		simpleFileAnalz.analz(file, writeTatics);
		if (writeTatics.getTotalNum() == null) {
			log.error(file.getName() + "文件的总数配置为 NULL");
			return;
		}
		LimitConfig limitConfig = getLimitConfigByKey(file.getName());
		limitConfig.getLimitList()
				.add(createLimitBean(BaseLimitBean.TOTAL_LIMIT_BEAN, writeTatics.getTotalNum(), file.getName()));
	}

	/**
	 * 从配置文件中读取信息加载的配置的map中
	 * 
	 * @param prop
	 * @param dayCountMap
	 */
	private void getLimitConfByProp(Properties prop, Map<String, LimitConfig> dayCountMap) {
		Set<Object> keySet = prop.keySet();
		for (Object key : keySet) {
			List<String> values = new ArrayList<String>(
					Arrays.asList(PropUtil.getString(prop, key.toString(), "").split(Constant.PROP_VALUE_SEPARATOR)));
			while (values.size() < 2) {
				values.add("");
			}
			// 每日限制
			Integer dayLimitInt = CastUtil.castInt(values.get(0), defaultDayMaxLimit);
			// tps限制
			Integer secondLimitInt = CastUtil.castInt(values.get(1), defaultSecondMaxLimit);
			List<BaseLimitBean> limitList = new ArrayList<BaseLimitBean>();
			// 验证规则为先验证tps限制在验证每日限制 TODO 后续修改验证规则的顺序可以修改这里
			limitList.add(createLimitBean(BaseLimitBean.SECOND_LIMIT_BEAN, secondLimitInt, key.toString()));
			limitList.add(createLimitBean(BaseLimitBean.DAY_LIMIT_BEAN, dayLimitInt, key.toString()));
			// 创建限流配置对象
			LimitConfig limitConfig = new LimitConfig(limitList);
			// 保存配置信息
			dayCountMap.put(key.toString(), limitConfig);
		}
	}

	/**
	 * 当前时间减去起始时间
	 * 
	 * @return 返回相差的毫秒数
	 */
	public long curTimeSubBeginTime() {
		try {
			return System.currentTimeMillis() - NioServerHandler.BEGIN_TIME.get();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 获取耗时统计的字符串（用于日志记录）
	 * 
	 * @return
	 */
	public String getTimeConsuming() {
		return TaskHandler.LOG_TIME_CONSUMING + curTimeSubBeginTime();
	}

	/**
	 * 设置默认的限流配置信息到内存中
	 * 
	 * @param key
	 *            对应map中的key值
	 * @param map
	 *            需要存放的map集合
	 */
	public void setDefalutLimitConfig(String key, Map<String, LimitConfig> map) {
		List<BaseLimitBean> limitList = new ArrayList<BaseLimitBean>();
		// tps限制
		limitList.add(createLimitBean(BaseLimitBean.SECOND_LIMIT_BEAN, defaultSecondMaxLimit, key));
		// 每日访问限制
		limitList.add(createLimitBean(BaseLimitBean.DAY_LIMIT_BEAN, defaultDayMaxLimit, key.toString()));
		// 创建限流配置对象
		LimitConfig limitConfig = new LimitConfig(limitList);
		map.put(key, limitConfig);
	}

	/**
	 * 根据limitType来创建包含限制信息的对象
	 * 
	 * @param limitType
	 *            创建限制信息的类型
	 * @param maxLimitConf
	 *            最大限制数量
	 * @param key
	 *            此对象对应map中的key值
	 * @return
	 */
	private BaseLimitBean createLimitBean(int limitType, Integer maxLimitConf, String key) {
		BaseLimitBean bean = null;
		switch (limitType) {
		case BaseLimitBean.SECOND_LIMIT_BEAN:
			bean = new SecondLimit(BaseLimitBean.SECOND_LIMIT_BEAN);
			break;
		case BaseLimitBean.DAY_LIMIT_BEAN:
			bean = new DayLimit(BaseLimitBean.DAY_LIMIT_BEAN);
			break;
		case BaseLimitBean.TOTAL_LIMIT_BEAN:
			bean = new TotalCountLimit(BaseLimitBean.TOTAL_LIMIT_BEAN, recordSubNum, pathName, isAppend);
			break;
		}
		bean.setMapKey(key);
		bean.setCurrDate(new Date());
		bean.setAtomicInteger(new AtomicInteger(0));
		bean.setMaxLimit(maxLimitConf);
		return bean;
	}

	/**
	 * @param reqXML
	 * @return
	 * 
	 */
	public String execute(Map<String, String> xmlMap,Integer queryType) {
		try {
			//set query type
			QUERY_TYPE.set(queryType);
			// 获取根据规则生成的key
			String key = getKeyFromXmlMap(xmlMap);
			// 设置记录日志的参数
			setLogArgus(xmlMap, key);
			// 记录起始日志
			log.info(TaskHandler.LOG_START + getLogArgus());
			// 获取限制配置信息
			LimitConfig limitConfig = getLimitConfigByKey(key);
			// 如果获得为null则直接返回成功
			if (limitConfig == null) {
				log.info(getLogArgus() + "没有对该访问进行控制配置，如要使用默认控制，请在配置文件中改useDefaultFlag标志为true");
			} else {
				// 开始校验
				try {
					limitConfig.checkLimit();
				} catch (BaseLimitException e) {
					limitErrorCount.increaseNum();
					String loginfo = TaskHandler.LOG_NO_PASS + getLogArgus() + e.toString() + getTimeConsuming();
					// 校验不通过
					log.info(loginfo);
					logLimitOut.info(loginfo);
					return e.getErrorCode();
				}
				// 校验结束
				log.info(TaskHandler.LOG_END + getLogArgus() + getTimeConsuming());
			}
		} catch (Exception e) {
			// 校验出异常
			limitErrorCount.increaseNum();
			log.error(TaskHandler.LOG_ERROR + xmlMap.toString(), e);
		}
		return RetCode.SUCCESS;
	}

	private LimitConfig getLimitConfigByKey(String key) {
		LimitConfig limitConfig = configMap.get(key);
		// 如果配置中允许使用默认配置，则判断限制对象是否为空，如果为空则根据默认创建新的默认配置对象
		if (limitConfig == null) {
			setDefalutLimitConfig(key, configMap);
			limitConfig = configMap.get(key);
		}
		return limitConfig;
	}

	/**
	 * 从xmlmap中获取merid和funcode拼装成的key
	 * 
	 * @param xmlMap
	 * @return
	 */
	private String getKeyFromXmlMap(Map<String, String> xmlMap) {
		String merid = xmlMap.get(HttpMap.MERID);
		String funcode = xmlMap.get(HttpMap.FUNCODE);
		return merid + Constant.LOG_SEPARATOR + funcode;
	}

	/**
	 * 配置信息书写
	 * 
	 * @return
	 * @throws Exception
	 */
	public String configWriter() throws Exception {
		synchronized (this) {
			try {
				FileUtil.fileMove(generFilePath, backFilePath + DateUtil.getCurrDateString(), true);
				FileUtil.generDir(generFilePath);
				Properties properties = new Properties();
				for (Entry<String, LimitConfig> entry : configMap.entrySet()) {
					config2file(properties, entry);
				}
				if (properties.size() > 0) {
					FileOutputStream fos = new FileOutputStream(generFilePath + propName);
					properties.store(fos, "key=merid+\",\"+funcode  value=限制每日超频的数（如没有则不填）+\";\"+限制每秒超频的数（如没有则不填）");
				}
				properties.clear();
				log.info("生成内存中的配置到 "+generFilePath+" 文件夹中成功！！");
			} catch (Exception e) {
				log.error("打印内存中的配置到文件失败：", e);
				return RetCode.ERROR_OTHERS;
			}
			return RetCode.SUCCESS;
		}
	}

	/**
	 * 
	 * @param properties
	 * @param entry
	 */
	private void config2file(Properties properties, Entry<String, LimitConfig> entry) {
		List<BaseLimitBean> limitList = entry.getValue().getLimitList();
		String propConfig = "";
		for (BaseLimitBean limitBean : limitList) {
			switch (limitBean.getLimitType()) {
			case BaseLimitBean.SECOND_LIMIT_BEAN:
				propConfig = "" + limitBean.getMaxLimit();
				break;
			case BaseLimitBean.DAY_LIMIT_BEAN:
				propConfig = limitBean.getMaxLimit() + Constant.PROP_VALUE_SEPARATOR + propConfig;
				break;
			case BaseLimitBean.TOTAL_LIMIT_BEAN:
				WriterUtil.writer(new WriteInfoBean(entry.getKey(), generFilePath, "" + limitBean.curOverplus()));
				break;
			}
		}
		// 写入配置
		properties.setProperty(entry.getKey(), propConfig);
	}

	/**
	 * 
	 * @param reqMap
	 * @return
	 */
	public String configAlter(Map<String, String> reqMap) {
		synchronized (this) {
			try {
				// 必输项校验
				reqMustHaveCheck(reqMap, configAlterElemList);
				// 配置类型校验
				int limitType = getLimitType(reqMap);
				boolean typeNotFound = true;
				// 新的最大限制
				int maxValue = CastUtil.castIntError2throw(reqMap.get(HttpMap.MAX_VALUE));
				// 获取配置
				String key = getKeyFromXmlMap(reqMap);
				LimitConfig limitConfig = getLimitConfigByKey(key);
				List<BaseLimitBean> limitList = limitConfig.getLimitList();
				// 修改配置
				for (BaseLimitBean limit : limitList) {
					if (limit.getLimitType() == limitType) {
						typeNotFound = false;
						alter(reqMap, maxValue, limit);
					}
				}
				// 如果没有找到则创建新的放入配置集合中
				if (typeNotFound) {
					limitList.add(createLimitBean(limitType, maxValue, key));
					// 写出信息到配置文件
					WriterUtil.writer(new WriteInfoBean(key, pathName,
							Constant.NEW_LINE + (maxValue < 0 ? 0 : maxValue), isAppend));
				}
				log.info("将" + key + "的" + reqMap.get(HttpMap.LIMIT_TYPE) + "类型限制设置为： " + maxValue + " 成功");
			} catch (BaseException e) {
				log.error("报文校验未通过：", e);
				return e.getErrorCode();
			} catch (Exception e) {
				log.error("配置变更出现异常", e);
				return RetCode.ERROR_OTHERS;
			}
			return RetCode.SUCCESS;
		}
	}

	/**
	 * 更新配置
	 * @param reqMap
	 * @param maxValue
	 * @param limit
	 */
	private void alter(Map<String, String> reqMap, int maxValue, BaseLimitBean limit) {
		limit.setMaxLimit(maxValue);
		if(Boolean.TRUE.toString().equalsIgnoreCase(reqMap.get(HttpMap.CLEAR))){
			//清空当前配置
			limit.clearLimit();
		}
	}

	/**
	 * 校验配置类型
	 * 
	 * @param reqMap
	 * @throws BaseException
	 */
	private int getLimitType(Map<String, String> reqMap) throws BaseException {
		if (!configTypeMap.containsKey(reqMap.get(HttpMap.LIMIT_TYPE))) {
			throw new BaseException(RetCode.ERROR_INPUT_CONFIG_TYPE, "字段为空");
		}
		return configTypeMap.get(reqMap.get(HttpMap.LIMIT_TYPE));
	}

	/**
	 * 校验必输项
	 * 
	 * @param reqMap
	 * @throws BaseException
	 */
	private void reqMustHaveCheck(Map<String, String> reqMap, List<String> mustHaveElemList) throws BaseException {
		for (String mustField : mustHaveElemList) {
			if (StringUtils.isEmpty(reqMap.get(mustField))) {
				throw new BaseException(RetCode.ERROR_INPUT_INCOMPLETE, mustField + "字段为空");
			}
		}
	}

}
