package cn.itcast.ssm.weixin.media;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.itcast.ssm.weixin.model.WeixinFinalValue;
import cn.itcast.ssm.weixin.model.WeixinMedia;
import cn.itcast.ssm.weixin.quartz.RefreshAccessTokenTask;
import cn.itcast.ssm.weixin.util.JsonUtil;


public class MediaKit {
	
	//post方式传入图片文件路径;和类型
	public static String postMedia(String path,String type){
		
		//创建client对象
		CloseableHttpClient client = null;
		CloseableHttpResponse resp = null;
		
		try {
			client = HttpClients.createDefault();
			String url = WeixinFinalValue.POST_MEDIA;
			url = url.replace("ACCESS_TOKEN", RefreshAccessTokenTask.at);
			url = url.replace("TYPE", type);
			HttpPost post = new HttpPost(url);
			FileBody fb = new FileBody(new File(path));
			//HttpEntity实体即可以使流也可以使字符串形式。上传文件的一种方式，是浏览器用表单上传文件的方式
			HttpEntity reqEntity = MultipartEntityBuilder
					.create().addPart("media", fb).build();
			post.setEntity(reqEntity);
			//去执行post提交
			resp = client.execute(post);
			//获取状态码
			int sc = resp.getStatusLine().getStatusCode();
			if(sc>=200&&sc<300){
				//正确情况下的返回JSON数据包结果如下：
				String json = EntityUtils.toString(resp.getEntity());
				WeixinMedia wm = (WeixinMedia)JsonUtil.getInstance().json2obj(json, WeixinMedia.class);
				return wm.getMedia_id();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(client!=null) client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(resp!=null) resp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void getMedia(String mediaId,File f) {
		CloseableHttpClient client = null;
		CloseableHttpResponse resp = null;
		
		try {
			client = HttpClients.createDefault();
			String url = WeixinFinalValue.GET_MEDIA;
			url = url.replace("ACCESS_TOKEN", RefreshAccessTokenTask.at);
			url = url.replace("MEDIA_ID", mediaId);
			HttpGet get = new HttpGet(url);
			resp = client.execute(get);
			int sc = resp.getStatusLine().getStatusCode();
			if(sc>=200&&sc<300) {
				InputStream is = resp.getEntity().getContent();
				FileUtils.copyInputStreamToFile(is, f);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(client!=null) client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(resp!=null) resp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
