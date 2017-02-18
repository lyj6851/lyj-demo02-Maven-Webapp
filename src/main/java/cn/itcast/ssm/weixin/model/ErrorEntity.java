package cn.itcast.ssm.weixin.model;
/**
 * 报错时类
 * @author Administrator
 *
 */
public class ErrorEntity {
	private String errcode;
	private String errmsg;
	
	
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	
	public String getErrmsg(){
		return errmsg;
	}
	public void setErrmsg(String errmsg){
		this.errmsg = errmsg;
	}
}
