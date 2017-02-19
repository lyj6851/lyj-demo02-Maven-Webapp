package cn.itcast.ssm.weixin.model;

public class WeixinFinalValue {
	public final static String APPID = "wxa7720a16b62214ae";
	public final static String APPSECRET = "93554ac4d12cd3f2f92496b4a583afc6";
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public final static String MENU_ADD = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	public final static String MSG_TEXT_TYPE = "text";
	public final static String MSG_IMAGE_TYPE = "image";
	public final static String MSG_VOICE_TYPE = "voice";
	public final static String MSG_VIDEO_TYPE = "video";
	public final static String MSG_SHORTVIDEO_TYPE = "shortvideo";
	public final static String MSG_LOCATION_TYPE = "location";
	public final static String MSG_EVENT_TYPE = "event";
	
	public final static String POST_MEDIA="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	public final static String GET_MEDIA="https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	public final static String SEND_TEMPLATE_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";


	public final static String ADD_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	public final static String QUERY_ALL_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	public final static String QUERY_USER_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
	public final static String UPDATE_GROUP_NAME = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	public final static String MOVE_USER_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
	public final static String MOVE_USERS_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN";
	public final static String DELETE_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";
}
