package cn.itcast.ssm.weixin.msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import cn.itcast.ssm.weixin.model.TemplateMsg;
import cn.itcast.ssm.weixin.model.WeixinFinalValue;
import cn.itcast.ssm.weixin.quartz.RefreshAccessTokenTask;

//处理消息的类
public class MessageKit {
	private static Map<String,String> replyMsgs = new HashMap<String,String>();
	static{
		replyMsgs.put("123", "你输入了123");
		replyMsgs.put("hello", "world");
		replyMsgs.put("run", "祝你一路走好!");
	}
	
	//告诉编译器忽略 unchecked 警告信息，如使用List，ArrayList等未进行参数化产生的警告信息。
	@SuppressWarnings("unchecked")
	public static Map<String,String> reqMsg2Map(HttpServletRequest req) throws IOException{
		String xml = req2xml(req);
		try {
			Map<String,String> maps = new HashMap<String, String>();
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			List<Element> eles = root.elements();
			for(Element e:eles) {
				maps.put(e.getName(), e.getTextTrim());
			}
			return maps;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//从request到xml的方法
	private static String req2xml(HttpServletRequest req) throws IOException{
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String str = null;
		StringBuffer sb = new StringBuffer();
		while((str=br.readLine())!=null) {
			sb.append(str);
		}
		return sb.toString();
	}
	
	//处理消息
	public static String handlerMsg(Map<String,String> msgMap) throws IOException{
		//根据map的key获取消息的类型
		String msgType = msgMap.get("MsgType");
		
		if(msgType.equals(WeixinFinalValue.MSG_EVENT_TYPE)){
			//如果类型是event类型
		}else if(msgType.equals(WeixinFinalValue.MSG_TEXT_TYPE)){
			//如果类型是text类型
			return textTypeHandler(msgMap);
		}else if(msgType.equals(WeixinFinalValue.MSG_IMAGE_TYPE)){
			//处理图文消息
			return imageTypeHandler(msgMap,"RW71TU43MYggcXTIQJ6VfJC0yAYSyxiQVN9QW5ru_11lsXlIfboZxsDZLjsfztyF");
		}
		
		return null;
	}
	//处理图文消息
	private static String imageTypeHandler(Map<String, String> msgMap,
			String mediaId) throws IOException {
		Map<String,String> map = new HashMap<String, String>();
		map.put("ToUserName", msgMap.get("FromUserName"));
		map.put("FromUserName", msgMap.get("ToUserName"));
		map.put("CreateTime", new Date().getTime()+"");
		map.put("MsgType", "image");
		map.put("Image", "<MediaId>"+mediaId+"</MediaId>");
		return map2xml(map);
	}

	//处理text类型
	private static String textTypeHandler(Map<String,String> msgMap) throws IOException{
		Map<String,String> map = new HashMap<String, String>();
		map.put("ToUserName", msgMap.get("FromUserName"));
		map.put("FromUserName", msgMap.get("ToUserName"));
		map.put("CreateTime", new Date().getTime()+"");
		map.put("MsgType", "text");
		String replyContent = "你请求的消息的内容不正确!";
		String con = msgMap.get("Content");
		if(replyMsgs.containsKey(con)) {
			replyContent = replyMsgs.get(con);
		}
		map.put("Content", replyContent);
		return map2xml(map);
	}
	//map对象转换成xml文件格式
	private static String map2xml(Map<String, String> map) throws IOException {
		// 创建一个document对象
		Document d = DocumentHelper.createDocument();
		Element root = d.addElement("xml");
		Set<String> keys = map.keySet();
		//遍历map添加到创建 的root节点下
		for(String key:keys){
			root.addElement(key).addText(map.get(key));
		}
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw);
		xw.setEscapeText(false);
		xw.write(d);
		return sw.toString();
	}
	
	public static String postTemplateMsg(TemplateMsg tm){
		CloseableHttpClient client = null;
		CloseableHttpResponse resp = null;
		String url = WeixinFinalValue.SEND_TEMPLATE_MSG;
		url.replace("ACCESS_TOKEN", RefreshAccessTokenTask.at);
		
	}
	
}
