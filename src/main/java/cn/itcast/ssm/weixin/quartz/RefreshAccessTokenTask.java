package cn.itcast.ssm.weixin.quartz;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import cn.itcast.ssm.weixin.model.AccessToken;
import cn.itcast.ssm.weixin.model.ErrorEntity;
import cn.itcast.ssm.weixin.model.WeixinContext;
import cn.itcast.ssm.weixin.model.WeixinFinalValue;
import cn.itcast.ssm.weixin.util.JsonUtil;

//刷新token值
@Component
public class RefreshAccessTokenTask {
	public static String at = WeixinContext.getAccessToken();
	public void refreshToken(){
		
		HttpGet get = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse resp = null;
		
		try {
			client = HttpClients.createDefault();
			String url = WeixinFinalValue.ACCESS_TOKEN_URL;
			url = url.replace("APPID", WeixinFinalValue.APPID);
			url = url.replace("APPSECRET", WeixinFinalValue.APPSECRET);
			get = new HttpGet(url);
			resp = client.execute(get);
			int statusCode = resp.getStatusLine().getStatusCode();
			if(statusCode>=200&&statusCode<300){
				HttpEntity entity = resp.getEntity();
				String content = EntityUtils.toString(entity);
				//把获取到的token字符串json转换成java对象
				try {
					AccessToken at = (AccessToken)JsonUtil.getInstance().json2obj(content, AccessToken.class);
					WeixinContext.setAccessToken(at.getAccess_token());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					ErrorEntity erro = (ErrorEntity)JsonUtil.getInstance().json2obj(content, ErrorEntity.class);
					System.out.println("获取token异常:"+erro.getErrcode()+","+erro.getErrmsg());
					//如果发生异常,重新获取token
					refreshToken();
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
				try {
					if(resp!=null) resp.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try{
					if(client!=null) client.close();
				} catch(IOException e){
					e.printStackTrace();
				}
		}
	}
}
