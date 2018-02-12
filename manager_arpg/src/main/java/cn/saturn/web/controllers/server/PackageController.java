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
import cn.saturn.web.controllers.server.dao.PackageManager;
import cn.saturn.web.controllers.server.dao.PackageModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;

@Path("package")
public class PackageController {

	@UserAuthority(PageModel.package_page)
	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/server/package/list");
	}

	@MainView
	@UserAuthority(PageModel.package_page)
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {
		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();

		request.setAttribute("tableUrl", Utils.url(inv, "/server/package/listJson"));
		request.setAttribute("addUrl", Utils.url(inv, "/server/package/edit"));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/server/package/toDelete"));
		request.setAttribute("editUrl", Utils.url(inv, "/server/package/edit"));

		return "package/list";
	}

	@MainView
	@UserAuthority(PageModel.package_page)
	@LoginCheck
	@Get("edit")
	public String edit(Invocation inv, @Param("id") long id) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		if (id > 0) {
			// 编辑
			PackageManager packageManager = ComponentManager.getComponent(PackageManager.class);
			PackageModel model = packageManager.find(id);
			if (model != null) {
				// 找到对应的数据
				request.setAttribute("id", id);
				request.setAttribute("platform", model.getPlatform());
				request.setAttribute("version", model.getVersion());
				request.setAttribute("resversion", model.getResversion());
				request.setAttribute("resurl", model.getResurl());
				request.setAttribute("notice", model.getNotice());
				request.setAttribute("type", model.getType());

				request.setAttribute("btn", "修改");

			} else {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没找到对应信息");
			}
		} else {
			// 新增
			request.setAttribute("btn", "新增");
		}
		request.setAttribute("toEditUrl", Utils.url(inv, "/server/package/toEdit"));
		return "package/edit";
	}

	@LoginCheck
	@UserAuthority(PageModel.package_page)
	@Post("toEdit")
	public String toEdit(Invocation inv, @Param("id") int id, @Param("platform") String platform,
			@Param("version") String version, @Param("resversion") int resversion, @Param("resurl") String resurl,
			@Param("notice") String notice, @Param("type") int type) {
		String defaultUrl = "server/package/list?mainView=true";
		if (StringUtils.isEmpty(platform)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "platform不能为空");
		}

		// 检测是否存在重复的
		PackageManager packageManager = ComponentManager.getComponent(PackageManager.class);
		PackageModel check = packageManager.find1(platform, version, resversion);
		if (check != null) {
			// 检测是否是自身
			if (check.getId() != id && check.getType() == type) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "存在相同版本号");
			}
		}

		// 读取服务器
		PackageModel model = null;
		if (id <= 0) {
			// 新增
			model = new PackageModel();
		} else {
			// 修改
			model = packageManager.find(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败");
			}
		}

		// 修改参数
		model.setPlatform(platform);
		model.setVersion(version);
		model.setResurl(resurl);
		model.setResversion(resversion);
		model.setNotice(notice);
		model.setType(type);

		// 保存操作
		if (id <= 0) {
			// 添加保存
			if (!packageManager.add(model)) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "添加失败");
			}
			RedisUtils.del(RedisKeys.K_PACKAGE);
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "添加成功", defaultUrl);
		}
		// 修改保存
		if (!packageManager.update(model)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败");
		}

		RedisUtils.del(RedisKeys.K_PACKAGE);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "修改成功", defaultUrl);
	}

	@LoginCheck
	@UserAuthority(PageModel.package_page)
	@Get("toDelete")
	public String toDelete(Invocation inv, @Param("id") int id) throws Throwable {
		// 删除包信息
		PackageManager packageManager = ComponentManager.getComponent(PackageManager.class);
		PackageModel model = packageManager.remove(id);
		if (model == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "删除失败");
		}

		RedisUtils.del(RedisKeys.K_PACKAGE);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "server/package?mainView=true");
	}

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {
		// return "@";

		HttpServletRequest request = inv.getRequest();
		PackageManager packageManager = ComponentManager.getComponent(PackageManager.class);
		int size = packageManager.getSize();
		// 读取参数
		int totalRows = size;
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		// Object totalRows0 = request.getParameter("totalRows");

		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// int endRow = BsgridUtils.endRow(curPage, pageSize, totalRows);
		// 遍历
		List<PackageModel> list = packageManager.getList(startRow, pageSize);

		// 转换接口
		BsgridUtils.IConvert<PackageModel> action = new BsgridUtils.IConvert<PackageModel>() {

			@Override
			public boolean convert(PackageModel obj, Map<String, Object> map) {
				map.put("id", obj.getId());
				map.put("platform", obj.getPlatform());
				map.put("version", obj.getVersion());
				map.put("resversion", obj.getResversion());
				map.put("resurl", obj.getResurl());
				map.put("notice", obj.getNotice());
				map.put("typeInfo", obj.getPackStr());
				return true;
			}

		};
		// 转换数据
		List<Map<String, Object>> listDatas = BsgridUtils.createListDatas(list, action);

		// 生成json
		String jsonStr = BsgridUtils.jsonStr(totalRows, curPage, pageSize, listDatas);

		return "@" + jsonStr;
	}

}
