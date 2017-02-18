package cn.itcast.ssm.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.itcast.ssm.weixin.kit.SecurityKit;
import cn.itcast.ssm.weixin.model.WeixinContext;

@Controller
public class WeinController {
	
	public static final String TOKEN = "ynkonghao";
	
	@RequestMapping(value="/wget",method=RequestMethod.GET)
	//微信初始化接入
	public void init(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		//signature 微信加密签名,signature结合了开发者填写的token参数和请求中的time
		//timestramp 时间戳
		//nonce 随机数
		//echostr 随机字符串
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		System.out.println(timestamp);
		//创建一个数组,存放相关参数
		String[] arrs = {WeinController.TOKEN,nonce,timestamp};
		//对数组进行排序
		Arrays.sort(arrs);
		//1）将token、timestamp、nonce三个参数进行字典序排序
		//2）将三个参数字符串拼接成一个字符串进行sha1加密
		//创建一个字符串
		StringBuffer sb = new StringBuffer();
		for(String a:arrs){
			//遍历数组添加到字符串中
			sb.append(a);
		}
		//获取sha1加密后的数据
		String sha1 = SecurityKit.sha1(sb.toString());
		//比较sha1与signature是否相等
		if(sha1.equals(signature)){
			resp.getWriter().println(echostr);
		}
	}
	@RequestMapping(value="/wget",method=RequestMethod.POST)
	public void getInfo(HttpServletRequest req,HttpServletResponse resp) throws IOException {
//		Map<String,String> msgMap = MessageKit.reqMsg2Map(req);
//		String respCon = MessageKit.handlerMsg(msgMap);
//		resp.setContentType("application/xml;charset=UTF-8");
//		resp.setCharacterEncoding("UTF-8");
//		System.out.println(respCon);
//		resp.getWriter().write(respCon);
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String str = null;
		while((str=br.readLine())!=null){
			System.out.println(str);
		}
	}
	//获取token
	@RequestMapping("/at")
	public void testAccessToken(HttpServletResponse resp) throws IOException{
		resp.getWriter().println(WeixinContext.getAccessToken());
	}
}
