package com.umpay.proxyservice.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xml 工具类
 * @author 不明
* @date   化小强 修改 2016年6月15日 上午10:17:25
 */
public class XmlUtils {
	private final static Logger _log = LoggerFactory
			.getLogger("XmlUtils");

	public static String mapToXml(Map<String, String> map, String rootName) {
		Element root = new Element(rootName);
		if (map == null)
			return xmlToString(root);
		for (String str : map.keySet())
			root.addContent(new Element(str).setText((map.get(str) == null ? ""
					: map.get(str) + "")));
		return xmlToString(root);
	}

	
	public static Map<String, String> xmlToMap(String xmlStr) throws JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder();
		//Map<String, String> map = new HashMap<String, String>();
		Map<String, String> map = new TreeMap<String, String>();
		
		if(xmlStr==null || "".equals(xmlStr))
			return map;
		
		xmlStr = URLDecoder.decode(xmlStr, "UTF-8");
		Reader in = new StringReader(xmlStr);
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List<Element> list = root.getChildren();
		for (Element e : list)
			map.put(e.getName(), e.getText());
		return map;

	}
	
	/**
	 * 从xml中获得某一个元素的值
	 * @author huaxiaoqiang
	 * @param xml
	 * @param key
	 * @return
	* @date 2016年6月14日 下午6:22:38
	 */
	public static String getParamFromXml(String reqXML,String key){
		try {
			Map<String, String> xmlMap = null;
			xmlMap = XmlUtils.xmlToMap(reqXML);
			if (xmlMap!=null) {
				String val=xmlMap.get(key);
				return val;
			}
		} catch (Exception e) {
			_log.error("",e);
		}
		return "";
	}


	public static String listToXml(List<Map<String, String>> list,
			String rootName, String parentName) {
		Element root = new Element(rootName);
		boolean flag = false;
		Element parentElement = null;
		Element child = null;
		if (list == null)
			return xmlToString(root);
		for (Map<String, String> map : list)
			if (flag) {
				flag = false;
				for (String str : map.keySet()) {
					child = new Element(str).setText(map.get(str) == null ? ""
							: (map.get(str) + ""));
					root.addContent(child);
				}
			} else {
				parentElement = new Element(parentName);
				root.addContent(parentElement);
				for (String str : map.keySet()) {
					child = new Element(str).setText(map.get(str) == null ? ""
							: (map.get(str) + ""));
					parentElement.addContent(child);
				}
			}
		return xmlToString(root);
	}

	
	public static List<Map<String, String>> xmlToList(String xmlStr) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		
		if(xmlStr==null || "".equals(xmlStr)){
			return resultList;
		}
		
		Map<String, String> map = null;
		boolean flag = true;

			xmlStr = URLDecoder.decode(xmlStr, "UTF-8");
			Reader in = new StringReader(xmlStr);
			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			List<Element> list = root.getChildren();
			for (Element e : list) {
				if (e.getChildren().size() == 0) {
					if (flag) {
						flag = false;
						map = new HashMap<String, String>();
						resultList.add(map);
					}
					map.put(e.getName(), e.getText());
				} else {
					map = new HashMap<String, String>();
					List<Element> childrenList = e.getChildren();
					resultList.add(map);
					for (Element element : childrenList) {
						map.put(element.getName(), element.getText());
					}
				}
			}
		
		return resultList;
	}


	public static String xmlToString(Element element) {
		XMLOutputter output = new XMLOutputter();
		output.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
		Document doc = new Document(element);
		String str = output.outputString(doc);
		return str;
	}

	
	public static Element createNodes(Element parentElement,
			Map<String, String> map) {
		String msg = "";
		Iterator<String> it = map.keySet().iterator();
		String tempStr = "";
		Element sonElement = null;
		while (it.hasNext()) {
			tempStr = it.next();
			msg = (map.get(tempStr)) == null ? "" : (map.get(tempStr) + "");
			sonElement = new Element(tempStr);
			parentElement.addContent(sonElement.setText(msg));
		}
		return parentElement;
	}


	public static void createNodes(Element root, String[] strArr) {
		Element e = null;
		for (String str : strArr) {
			e = new Element(str);
			root.addContent(e);
		}

	}


	public static String xmlFileToString(String xmlPath, String rootName) throws JDOMException, IOException {

		SAXBuilder builder = new SAXBuilder();
		Map<String, String> map = new HashMap<String, String>();

			Document doc = builder.build(new File(xmlPath));
			Element root = doc.getRootElement();
			List<Element> list = root.getChildren();
			for (Element e : list)
				map.put(e.getName(), e.getText());

		return mapToXml(map, rootName);
	}

	public static void main(String args[]) throws Exception{
		/*String path = System.getProperty("user.dir")
				+ "/src/upload_request.xml";
		System.out.println(path);
		String str="";
		try {
			str = xmlFileToString(path, "request");
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(str);*/
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("funcode", "funcodeTest");
		map.put("tranid", "trsnsidTest");
		
		
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		for(int i=0;i<10;i++){	
			Map<String,String> map1=new HashMap<String,String>();
			map1.put("mobileid", "121212121"+i);
			map1.put("score", "12"+i);
			map1.put("des", "des"+i);
			//List<Map<String,String>> temp=new ArrayList<Map<String,String>>();
			list.add(map1);

		}
		String str1=listToXml(map,list,"response","creditscoreinfo");
		System.out.println(str1);
		System.out.println(xmlToList(str1));
		
		
		System.out.println(listToXml(map,list,"response","creditscoreinfo"));
		
	/*	String str="<response>"
  +"<sign>d6095f7e1d6adada90c522b719e5670c</sign>"
  +"<retcode>0000</retcode>"
  +"<count>2</count>"
  +"<funcode>0001</funcode>"
  +"<datetime>20150212213557</datetime>"
  +"<transid>ff7461b094e9013220e27fb2179c896d</transid>"
  +"<resultinfo>"
  +"  <score>733</score>"
  +"  <mobileid>18800011019</mobileid>"
  +"</resultinfo>"
  +"<resultinfo>"
  +"  <score>763</score>"
  +"  <mobileid>13810245903</mobileid>"
  +"</resultinfo>"
+"</response>";
		
		List<Map<String,String>> list= xmlToList(str);
		System.out.println(list);*/
		
		
	}
	
	
	public static String listToXml(Map<String,String> first_map,List<Map<String, String>> second_list,String rootName, String parentName) {
		Element root = new Element(rootName);
//		boolean flag = false;
		Element parentElement = null;
		Element child = null;
	
		for (String str : first_map.keySet()) {
			child = new Element(str).setText(first_map.get(str) == null ? ""
					: (first_map.get(str) + ""));
			root.addContent(child);
		}
		
		//for(List<Map<String, String>> list : second_list){
			for (Map<String, String> map : second_list){
				parentElement = new Element(parentName);
				root.addContent(parentElement);
				for (String str : map.keySet()) {
					child = new Element(str).setText(map.get(str) == null ? ""
							: (map.get(str) + ""));
					parentElement.addContent(child);
				}
			}
		//}
		return xmlToString(root);
		
	/*	for (Map<String, String> map : list)
			if (flag) {
				flag = false;
				for (String str : map.keySet()) {
					child = new Element(str).setText(map.get(str) == null ? ""
							: (map.get(str) + ""));
					root.addContent(child);
				}
			} else {
				parentElement = new Element(parentName);
				root.addContent(parentElement);
				for (String str : map.keySet()) {
					child = new Element(str).setText(map.get(str) == null ? ""
							: (map.get(str) + ""));
					parentElement.addContent(child);
				}
			}
		return xmlToString(root);*/
	}
	
	
	public static String listToXml(Map<String,String> first_map,Map<String, String> second_map,String rootName, String parentName) {
		Element root = new Element(rootName);
//		boolean flag = false;
		Element parentElement = null;
		Element child = null;
	
		for (String str : first_map.keySet()) {
			child = new Element(str).setText(first_map.get(str) == null ? ""
					: (first_map.get(str) + ""));
			root.addContent(child);
		}
		
		//for(List<Map<String, String>> list : second_list){
	    if(second_map!=null){
				parentElement = new Element(parentName);
				root.addContent(parentElement);
				for (String str : second_map.keySet()) {
					child = new Element(str).setText(second_map.get(str) == null ? ""
							: (second_map.get(str) + ""));
					parentElement.addContent(child);
				}
	    }
		//}
		return xmlToString(root);

	}
	

}
