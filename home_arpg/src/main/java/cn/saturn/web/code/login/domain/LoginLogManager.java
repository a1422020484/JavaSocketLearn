package cn.saturn.web.code.login.domain;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LoginLogManager {

    @Resource
    protected LoginLogDAO dao;

    public LoginLogDAO getDAO() {
        return dao;
    }
}
