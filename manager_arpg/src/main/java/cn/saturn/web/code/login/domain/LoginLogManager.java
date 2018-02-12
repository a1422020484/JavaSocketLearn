package cn.saturn.web.code.login.domain;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class LoginLogManager {

	@Resource
	protected LoginLogDAO dao;

	public LoginLogDAO getDAO() {
		return dao;
	}
}
