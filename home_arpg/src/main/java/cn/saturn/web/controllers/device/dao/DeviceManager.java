package cn.saturn.web.controllers.device.dao;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class DeviceManager {
    @Resource
    DeviceDAO dao;
}
