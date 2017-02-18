package cn.itcast.ssm.weixin.model;

//token信息类
public class WeixinContext {
	
	private static String accessToken;

	public static String getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(String accessToken) {
		WeixinContext.accessToken = accessToken;
	}
	
	
}
