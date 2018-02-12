package cn.saturn.web.controllers.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.server.dao.VersionManager;
import cn.saturn.web.controllers.server.dao.VersionModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;

@Path("version")
public class VersionController {

	@UserAuthority(PageModel.version_page)
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/server/version/list");
	}

	@MainView
	@UserAuthority(PageModel.version_page)
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {
		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();

		request.setAttribute("tableUrl", Utils.url(inv, "/server/version/listJson"));
		request.setAttribute("addUrl", Utils.url(inv, "/server/version/edit"));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/server/version/toDelete"));
		request.setAttribute("editUrl", Utils.url(inv, "/server/version/edit"));

		return "version/list";
	}

	@MainView
	@UserAuthority(PageModel.version_page)
	@LoginCheck
	@Get("edit")
	public String edit(Invocation inv, @Param("id") long id) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		if (id > 0) {
			// 编辑
			VersionManager versionManager = ComponentManager.getComponent(VersionManager.class);
			VersionModel model = versionManager.find(id);
			if (model != null) {
				// 找到对应的数据
				request.setAttribute("id", id);
				request.setAttribute("platform", model.getPlatform());
				request.setAttribute("version", model.getVersion());
				request.setAttribute("url", model.getUrl());
				request.setAttribute("notice", model.getNotice());

				// 禁止修改默认版本的渠道号
				if (id == VersionManager.defualID) {
					request.setAttribute("platformAttribute", "disabled=\"disabled\"");
				}

				request.setAttribute("btn", "修改");

			} else {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没找到对应信息");
			}
		} else {
			// 新增
			request.setAttribute("btn", "新增");
		}
		request.setAttribute("toEditUrl", Utils.url(inv, "/server/version/toEdit"));
		return "version/edit";
	}

	@LoginCheck
	@UserAuthority(PageModel.version_page)
	@Post("toEdit")
	public String toEdit(Invocation inv, @Param("id") int id, @Param("platform") String platform,
			@Param("version") String version, @Param("url") String url, @Param("notice") String notice) {
		String defaultUrl = "server/version/list?mainView=true";

		if (StringUtils.isEmpty(platform)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "平台不能为空");
		}

		// 检测是否存在重复的
		VersionManager versionManager = ComponentManager.getComponent(VersionManager.class);
		VersionModel check = versionManager.find1(platform, version);
		if (check != null) {
			// 检测是否是自身
			if (check.getId() != id) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "存在相同版本号");
			}
		}
		boolean create = id <= 0;

		// 读取服务器
		VersionModel model = null;
		if (create) {
			// 新增
			model = new VersionModel();
		} else {
			// 修改
			model = versionManager.find(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败");
			}
		}

		// 修改参数
		model.setPlatform(platform);
		model.setVersion(version);
		model.setUrl(url);
		model.setNotice(notice);

		// 保存操作
		if (create) {
			// 添加保存
			if (!versionManager.add(model)) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "添加失败");
			}
		}
		// 修改保存
		if (!versionManager.update(model)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败");
		}

		RedisUtils.del(RedisKeys.K_VERSION);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, ((create) ? "添加成功" : "修改成功"), defaultUrl);

	}

	@LoginCheck
	@UserAuthority(PageModel.version_page)
	@Get("toDelete")
	public String toDelete(Invocation inv, @Param("id") int id) throws Throwable {
		if (id == 1) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "不能删除默认版本号");
		}

		VersionManager versionManager = ComponentManager.getComponent(VersionManager.class);
		VersionModel model = versionManager.remove(id);
		if (model == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "删除失败");
		}

		RedisUtils.del(RedisKeys.K_VERSION);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "server/version/list?mainView=true");
	}

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {
		// return "@";

		HttpServletRequest request = inv.getRequest();
		VersionManager versionManager = ComponentManager.getComponent(VersionManager.class);
		int size = versionManager.getSize();
		// 读取参数
		int totalRows = size;
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		// Object totalRows0 = request.getParameter("totalRows");

		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// int endRow = BsgridUtils.endRow(curPage, pageSize, totalRows);
		// 遍历
		List<VersionModel> list = versionManager.getList(startRow, pageSize);

		// 转换接口
		BsgridUtils.IConvert<VersionModel> action = new BsgridUtils.IConvert<VersionModel>() {

			@Override
			public boolean convert(VersionModel obj, Map<String, Object> map) {
				map.put("id", obj.getId());
				map.put("platform", obj.getPlatform());
				map.put("version", obj.getVersion());
				map.put("url", obj.getUrl());
				map.put("notice", obj.getNotice());
				return true;
			}

		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		// 生成json
		String jsonStr = BsgridUtils.jsonStr(totalRows, curPage, pageSize, listDatas);
		// System.out.println(jsonStr);
		return "@" + jsonStr;
	}

}
