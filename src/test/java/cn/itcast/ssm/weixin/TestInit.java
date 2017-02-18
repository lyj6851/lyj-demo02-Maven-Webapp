package cn.itcast.ssm.weixin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import cn.itcast.ssm.weixin.kit.SecurityKit;
import cn.itcast.ssm.weixin.model.WeixinContext;
import cn.itcast.ssm.weixin.model.WeixinFinalValue;
import cn.itcast.ssm.weixin.model.WeixinMenu;
import cn.itcast.ssm.weixin.quartz.RefreshAccessTokenTask;
import cn.itcast.ssm.weixin.util.JsonUtil;

//测试sha1加密算法
public class TestInit {
	@Test
	public void testSha1(){
		System.out.println(SecurityKit.sha1("hello"));
	}
	//换取token测试
	@Test
	public void testHttpClient(){
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			String url = WeixinFinalValue.ACCESS_TOKEN_URL;
			url = url.replace("APPID", WeixinFinalValue.APPID);
			url = url.replace("APPSECRET", WeixinFinalValue.APPSECRET);
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse resp = client.execute(get);
			int statusCode = resp.getStatusLine().getStatusCode();
			if(statusCode>=200&&statusCode<300){
				HttpEntity entity = resp.getEntity();
				String content = EntityUtils.toString(entity);
				//把获取到的token字符串json转换成java对象
				//AccessToken at = (AccessToken)JsonUtil
				
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test01(){
		System.out.println(WeixinContext.getAccessToken());
	}
	
	//测试添加菜单
	@Test
	public void testMenu(){
		try {
			List<WeixinMenu> wms = new ArrayList<WeixinMenu>();
			//创建一级菜单1
			WeixinMenu wm1 = new WeixinMenu();
			wm1.setId(1);
			wm1.setName("进入首页");
			wm1.setType("view");
			wm1.setUrl("http://www.baidu.com");
			wms.add(wm1);
			//创建一级菜单2
			WeixinMenu wm2 = new WeixinMenu();
			wm2.setName("测试资源");
			//创建一级菜单2的子菜单1
			List<WeixinMenu> wm2Sub = new ArrayList<WeixinMenu>();
			wm1 = new WeixinMenu();
			wm1.setId(2);
			wm1.setName("事件测试");
			wm1.setType("click");
			wm1.setKey("A0001");
			wm2Sub.add(wm1);
			//创建一级菜单2的子菜单2
			wm1 = new WeixinMenu();
			wm1.setId(3);
			wm1.setName("扫描测试");
			wm1.setType("pic_sysphoto");
			wm1.setKey("rselfmenu_1_0");
			wm2Sub.add(wm1);
			//把二级菜单放入第二个一级菜单
			wm2.setSub_button(wm2Sub);
			wms.add(wm2);
			//把菜单放入map中
			Map<String,List<WeixinMenu>> maps = new HashMap<String,List<WeixinMenu>>();
			maps.put("button",wms);
			//使用工具类把对象转换成json
			String json = JsonUtil.getInstance().obj2json(maps);
			//System.out.println(json);
			
			//创建httpclient
			CloseableHttpClient client = HttpClients.createDefault();
			String url = WeixinFinalValue.MENU_ADD;
			url = url.replace("ACCESS_TOKEN", RefreshAccessTokenTask.at);
			HttpPost post = new HttpPost(url);
			post.addHeader("Content-Type","application/json");
			StringEntity entity = new StringEntity(json, 
					ContentType.create("application/json", "utf-8"));
			post.setEntity(entity);
			CloseableHttpResponse resp = client.execute(post);
			int sc = resp.getStatusLine().getStatusCode();
			if(sc>=200&&sc<300) {
				System.out.println(EntityUtils.toString(resp.getEntity()));
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
