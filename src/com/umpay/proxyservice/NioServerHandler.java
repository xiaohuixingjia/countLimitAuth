package com.umpay.proxyservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.http.MutableHttpRequest;
import org.apache.mina.filter.codec.http.MutableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bs3.inf.IProcessors.HSessionInf;
import com.bs3.nio.mina2.Mina2H4Rpc2;
import com.bs3.nio.mina2.codec.IHttp;
import com.umpay.proxyservice.util.SpringUtil;
import com.umpay.proxyservice.util.XmlUtils;

/**
 * 
 * @author huaxiaoqiang
 *
 */
public class NioServerHandler extends Mina2H4Rpc2 {

	private final static Logger _log = LoggerFactory.getLogger(NioServerHandler.class);
	public final static String REQ_XML_ANALYZE_ERROR = "请求报文解析错误";
	/* 起始时间 */
	public static final ThreadLocal<Long> BEGIN_TIME = new ThreadLocal<Long>();

	@Override
	protected void onServerReadReq(HSessionInf session, Object req) {
		String reqXML = null;
		try {
			// 设置起始时间
			setBeginTime();

			// 获取url
			MutableHttpRequest request = (MutableHttpRequest) req;
			String requestURL = "";
			if (req != null && request.getRequestUri() != null) {
				requestURL = request.getRequestUri().getPath();
			}
			_log.info("接收到的url:{}", requestURL);
			String responseStr = "";
			// 获取处理对象
			TaskHandler taskHandler = (TaskHandler) SpringUtil.getInstance().getContext().getBean("taskHandler");
			if (requestURL.startsWith(Constant.CONFIG_WRITER)) {
				// 打印当前内存中的配置到指定的文件夹下
				responseStr = taskHandler.configWriter();
			} else {
				// 获取报文
				reqXML = getRequXml(req);
				_log.info("超频控制工程收到的报文为：{}", reqXML);
				// 解析报文为map格式
				Map<String, String> reqMap = getReqXmlMap(reqXML);
				if (requestURL.startsWith(Constant.CONFIG_ALTER)) {
					// 实时配置修改
					responseStr = taskHandler.configAlter(reqMap);
					//查得请求url
				} else if (requestURL.startsWith(Constant.LIMIT_RES)) {
					responseStr = taskHandler.execute(reqMap,Constant.QUERY_TYPE_RES);
				}else{
					// 流量限制
					responseStr = taskHandler.execute(reqMap,Constant.QUERY_TYPE_REQ);
				}
			}
			// 将处理后的信息返回
			this.responseContent(session, responseStr);
		} catch (Exception e) {
			// 校验出异常
			_log.error(TaskHandler.LOG_ERROR + (reqXML == null ? NioServerHandler.REQ_XML_ANALYZE_ERROR : reqXML), e);
			this.responseContent(session, RetCode.SUCCESS);
		}
	}

	/**
	 * 设置起始时间
	 */
	public void setBeginTime() {
		NioServerHandler.BEGIN_TIME.set(System.currentTimeMillis());
	}

	/**
	 * 将xml报文解析为hashmap对象 解析出错返回一个空的hashmap
	 * 
	 * @param reqXml
	 *            解析的xml文本
	 * @return
	 */
	private Map<String, String> getReqXmlMap(String reqXml) {
		try {
			Map<String, String> reqXmlMap = XmlUtils.xmlToMap(reqXml);
			return reqXmlMap;
		} catch (Exception e) {
			_log.error("将请求报文解析为hashmap出错" + reqXml + e);
			return new HashMap<String, String>();
		}

	}

	/**
	 * 获取请求中的xml报文
	 * 
	 * @param req
	 * @return
	 */
	private String getRequXml(Object req) {
		/* 接收请求，解析POST报文体 */
		MutableHttpRequest request = (MutableHttpRequest) req;
		IoBuffer content = (IoBuffer) request.getContent();
		byte[] conBytes = new byte[content.limit()];
		content.get(conBytes);
		String reqXML = new String(conBytes);
		return reqXML;
	}

	/**
	 * 返回响应给商户的方法
	 * 
	 * @param session
	 * @param responseStr
	 */
	private void responseContent(HSessionInf session, String responseStr) {
		try {
			/* 第四步：返回 */
			// _log.info("返回的报文如下:\n"+responseStr);
			MutableHttpResponse res = IHttp.makeResp(new IHttp.HResponse(), IHttp.HConst.SC_OK, "", null, "text/plain",
					responseStr.getBytes());
			session.write(res);
		} catch (Exception e) {
			_log.error("", e);
			session.close("");
		}
	}

}
