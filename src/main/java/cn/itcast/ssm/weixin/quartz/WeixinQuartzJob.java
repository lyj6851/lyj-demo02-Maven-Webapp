package cn.itcast.ssm.weixin.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
//定时执行
public class WeixinQuartzJob extends QuartzJobBean{
	
	//注入; 这里使用bean 所有要有set方法
	//@Autowired
	private RefreshAccessTokenTask refreshAccessTokenTask;
	
	public void setRefreshAccessTokenTask(
			RefreshAccessTokenTask refreshAccessTokenTask) {
		this.refreshAccessTokenTask = refreshAccessTokenTask;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		refreshAccessTokenTask.refreshToken();
	}

}
