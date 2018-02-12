package cn.saturn.web.code.action;

import cn.saturn.web.code.Head;
import cn.saturn.web.controllers.server.dao.Package;
import cn.saturn.web.controllers.server.dao.PackageManager;
import cn.saturn.web.controllers.server.dao.Version;
import cn.saturn.web.controllers.server.dao.VersionManager;
import cn.saturn.web.redis.RedisUtils;
import com.alibaba.fastjson.JSON;
import proto.ProtocolWeb.*;
import xzj.core.dispatch.Response;
import xzj.core.dispatch.ResponseFactory;
import xzj.core.dispatch.annotation.Action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Action
public class VersionAction {

    @Action(id = Head.H16001, isEncrypted = false)
    public Response version(VersionWCS cs) {

        // 获取客户端信息
        String platform = cs.getPlatform();
        String version = cs.getVersion();

        // 检测是否有大版本需要更新
        Version ver = checkVersion(platform, version);
        if (ver != null) {
            // 下发大版本信息
            VersionItem.Builder b = VersionItem.newBuilder();
            b.setVersion(ver.getVersion());
            b.setUrl(ver.getUrl());
            b.setNotice(ver.getNotice());
            // 返回消息
            VersionWSC.Builder b2 = VersionWSC.newBuilder();
            b2.setVer(b.build());
            return ResponseFactory.ok(Head.H16001, b2.build());
        }

        // 检测这个版本的资源版本是否是最新的
        String resversion = cs.getResversion();
        String resplatform = cs.getResplatform();
        int resversion0 = 0;
        try {
            resversion0 = Integer.valueOf(resversion);
        } catch (NumberFormatException e) {
        }

        List<Package> retList = checkPackage(resplatform, version, resversion0, Package.big_pack);
        int count = (retList != null) ? retList.size() : 0;

        // 返回信息
        VersionWSC.Builder b2 = VersionWSC.newBuilder();
        for (int i = 0; i < count; i++) {
            Package model = retList.get(i);
            // 返回信息
            VersionItem.Builder b = VersionItem.newBuilder();
            b.setVersion(String.valueOf(model.getResversion()));
            b.setUrl(model.getResurl());
            b.setNotice(model.getNotice());
            b2.addRes(b.build());
        }
        return ResponseFactory.ok(Head.H16001, b2.build());
    }

    @Action(id = Head.H16002, isEncrypted = false)
    public Response smallVersion(VersionSmallWCS cs) {

        String platform = cs.getPlatform();
        String version = cs.getVersion();

        // 获取客户端信息
        String resversion = cs.getResversion();
        String resplatform = cs.getResplatform();
        int resversion0 = 0;
        try {
            resversion0 = Integer.valueOf(resversion);
        } catch (NumberFormatException e) {
        }

        List<Package> retList = checkPackage(resplatform, version, resversion0, Package.smial_pack);
        int count = (retList != null) ? retList.size() : 0;

        // 返回信息
        VersionSmallWSC.Builder b2 = VersionSmallWSC.newBuilder();
        for (int i = 0; i < count; i++) {
            Package model = retList.get(i);
            // 返回信息
            VersionItem.Builder b = VersionItem.newBuilder();
            b.setVersion(String.valueOf(model.getResversion()));
            b.setUrl(model.getResurl());
            b.setNotice(model.getNotice());
            b2.addRes(b.build());
        }
        return ResponseFactory.ok(Head.H16002, b2.build());
    }

    // 检测包版本
    public static List<Package> checkPackage(String platform, String version, int resversion, int type) {
        // 版本信息
        List<Package> allmodels = null;
        if (RedisUtils.RedisVersion) {
            String values = RedisUtils.get("packages");
            try {
                allmodels = JSON.parseArray(values, Package.class);
            } catch (Exception e) {
            }
        }
        // redis获取不到信息, 从本地获取
        if (allmodels == null) {
            // 获取版本信息
            allmodels = PackageManager.getList();
        }

        // 筛选出平台数据
        List<Package> models = PackageManager.findByPlatformAndType(allmodels, platform, version, type);
        int count = (models != null) ? models.size() : 0;
        if (count <= 0) {
            return null; // 不存在资源版本
        }
        // 排序
        Collections.sort(models, PackageManager.PACKAGE_COMPARATOR);

        // 筛选出比这个资源版本大的版本
        List<Package> retList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Package model = models.get(i);
            // 版本信息
            int resversion0 = model.getResversion();
            if (resversion0 > resversion) {
                retList.add(model);
            }
        }
        return retList;
    }

    // 版本信息
    public static Version checkVersion(String platform, String version) {
        // 版本信息
        List<Version> allVersions = VersionManager.getVersionList();

        // 筛选出平台数据 && 通过类型
        List<Version> versions = VersionManager.findByPlatform(allVersions, platform);

        // 如果不存在则获取默认版本 && 通过类型
        if (versions == null) {
            versions = VersionManager.findByPlatform(allVersions, VersionManager.DEFAULT_PLATFORM);
        }

        int count = (versions != null) ? versions.size() : 0;
        if (count <= 0) {
            return null; // 不存在版本
        }

        // 排序
        Collections.sort(versions, VersionManager.VERSION_COMPARATOR);

        // 获取最大的版本号
        Version lastModel = versions.get(count - 1);
        String maxVersion = lastModel.getVersion();

        // 比较版本号
        int c = maxVersion.compareTo(version);
        if (c <= 0) {
            return null; // 是最新的版本号(等于或大于当前最大版本号)
        }
        
//        boolean cBool =  maxVersion.equalsIgnoreCase(version);
//        if(!cBool)
//        	return null;// 是否对应的版本号(要相等)

        // 返回最新版本信息
        return lastModel;
    }
}
