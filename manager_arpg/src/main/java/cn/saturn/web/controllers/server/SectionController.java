package cn.saturn.web.controllers.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.server.dao.SectionManager;
import cn.saturn.web.controllers.server.dao.SectionModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import zyt.spring.component.ComponentManager;

@Path("section")
public class SectionController {

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/server/section/list");
	}

	@MainView
	@LoginCheck
	@Get("list")
	public String list(Invocation inv) throws Throwable {
		// 读取服务器ID
		HttpServletRequest request = inv.getRequest();

		request.setAttribute("tableUrl", Utils.url(inv, "/server/section/listJson"));
		request.setAttribute("addUrl", Utils.url(inv, "/server/section/edit"));
		request.setAttribute("toDeleteUrl", Utils.url(inv, "/server/section/toDelete"));
		request.setAttribute("editUrl", Utils.url(inv, "/server/section/edit"));

		return "section/list";
	}

	@MainView
	@LoginCheck
	@Get("edit")
	public String edit(Invocation inv, @Param("id") int id) throws Throwable {
		HttpServletRequest request = inv.getRequest();
		if (id > 0) {
			// 编辑
			SectionManager sectionManager = ComponentManager.getComponent(SectionManager.class);
			SectionModel model = sectionManager.find(id);
			if (model != null) {
				// 找到对应的数据
				request.setAttribute("id", id);
				request.setAttribute("name", model.getName());
				request.setAttribute("state", model.getState());
				request.setAttribute("remark", model.getTag());
				request.setAttribute("platforms", model.getPlatforms());
				// 推荐选择
				String recommendStr = (model.isRecommend()) ? "checked=\"checked\"" : "";
				request.setAttribute("recommend", recommendStr);
				request.setAttribute("btn", "修改");
			} else {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "没找到对应信息");
			}
		} else {
			// 新增
			request.setAttribute("btn", "新增");
		}
		return "section/edit";
	}

	@LoginCheck
	@Post("toEdit")
	public String toEdit(Invocation inv, @Param("id") int id, @Param("name") String name, @Param("state") int state,
			@Param("remark") String remark, @Param("platforms") String platforms) {
		if (StringUtils.isEmpty(name)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "name不能为空！");
		}

		String defaultUrl = "server/section/list?mainView=true";
		String recommendStr = inv.getParameter("recommend");
		boolean recommend = recommendStr != null;
		boolean create = id <= 0;
		// 读取服务器
		SectionManager sectionManager = ComponentManager.getComponent(SectionManager.class);
		SectionModel model = null;
		if (create) {
			// 新增
			model = new SectionModel();
		} else {
			// 修改
			model = sectionManager.find(id);
			if (model == null) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败");
			}
		}

		// 修改参数
		model.setName(name);
		model.setState(state);
		model.setRecommend(recommend);
		model.setTag((remark == null) ? "" : remark);
		model.setPlatforms(platforms);

		// 保存操作
		if (create) {
			// 添加保存
			if (!sectionManager.add(model)) {
				return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "添加失败");
			}
		}
		// 修改保存
		if (!sectionManager.update(model)) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败");
		}

		RedisUtils.del(RedisKeys.K_SERVER_SECTION);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, ((create) ? "新增成功" : "修改成功"), defaultUrl);

	}

	@LoginCheck
	@Get("toDelete")
	public String toDelete(Invocation inv, @Param("id") int id) throws Throwable {

		SectionManager sectionManager = ComponentManager.getComponent(SectionManager.class);
		SectionModel sectionModel = sectionManager.remove(id);
		if (sectionModel == null) {
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "删除失败");
		}

		RedisUtils.del(RedisKeys.K_SERVER_SECTION);

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "server/section?mainView=true");
	}

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {
		// return "@";

		HttpServletRequest request = inv.getRequest();
		SectionManager sectionManager = ComponentManager.getComponent(SectionManager.class);
		// SectionManager sectionManager = serverComponent.getSectionManager();
		int size = sectionManager.getSize();
		// 读取参数
		int totalRows = size;
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		// Object totalRows0 = request.getParameter("totalRows");

		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// int endRow = BsgridUtils.endRow(curPage, pageSize, totalRows);
		// 遍历
		List<SectionModel> list = sectionManager.getList(startRow, pageSize);

		// 转换接口
		BsgridUtils.IConvert<SectionModel> action = new BsgridUtils.IConvert<SectionModel>() {

			@Override
			public boolean convert(SectionModel obj, Map<String, Object> map) {
				map.put("id", obj.getId());
				map.put("name", obj.getName());
				map.put("state", obj.getStateStr());
				map.put("recommend", obj.isRecommend() ? "是" : "否");
				map.put("platforms", obj.getPlatforms());
				map.put("remark", obj.getTag());
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
