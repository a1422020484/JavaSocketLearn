package cn.saturn.web.code.login.domain;

import javax.annotation.Resource;

import cn.saturn.web.code.update.ServerTimer;

public class LoginLogTimerHandler extends ServerTimer {

	@Resource
	LoginLogDAO loginLogDao;

	public LoginLogTimerHandler() {
		// 1 天执行一次
		super(1, "LoginLogTimer", 24 * 60 * 60 * 1000);
	}

	@Override
	protected void update(int count) {
		// 保留 2个月的数据
		loginLogDao.deleteOverdue(2 * 30);
		
	}

}
