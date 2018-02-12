package cn.saturn.web.controllers.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.jump.JumpController;
import cn.saturn.web.controllers.login.dao.UserDAO;
import cn.saturn.web.controllers.login.dao.UserModel;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("")
public class AccountController {
	@Autowired
	UserDAO dao;

	@Get("")
	public String index(Invocation inv) throws Throwable {
		return Utils.redirect(inv, "/account/changePwd");
	}

	@UserAuthority(PageModel.account_page)
	@MainView
	@Get("list")
	public String list(Invocation inv) throws Throwable {

		HttpServletRequest request = inv.getRequest();
		List<UserModel> list = dao.get();
		request.setAttribute("list", list);

		return "list";
	}

	@UserAuthority(PageModel.account_page)
	@MainView
	@Get("add")
	public String add(Invocation inv, @Param("id") int id) throws Throwable {
		// HttpServletRequest request = inv.getRequest();

		return "add";
	}

	@UserAuthority(PageModel.account_page)
	@Post("toAdd")
	public String toAdd(Invocation inv, @NotBlank @Param("username") String username, @NotBlank @Param("password") String password, @NotBlank @Param("checkPwd") String checkPwd) throws Throwable {
		String defualUrl = Utils.url(inv, "/account/add");
		// 读取参数
		String remark = (String) inv.getParameter("remark");
		int authority = zyt.utils.Utils.ObjectUtils.baseValue(inv.getParameter("authority"), Integer.class);

		if (username.equals("") || password.equals("")) {
			JumpController.jump(inv, "账号密码错误", defualUrl);
		}

		// 检测旧密码
		if (StringUtils.isBlank(password)) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "密码不能为空");
		}
		// 验证密码
		if (!password.equals(checkPwd)) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "密码不相同");
		}

		try {
			// 创建账号
			UserModel userModel = new UserModel();
			userModel.setUsername(username);
			userModel.setAuthority(authority);
			userModel.setRemark((remark != null) ? remark : "");
			// 新建账号
			Integer userId = dao.insert(userModel, password);
			if (userId == null) {
				return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "新建账号错误");
			}
		} catch (Exception e) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "新建账号错误");
		}

		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "添加成功","account/list?mainView=true");
	}

	@UserAuthority(PageModel.account_page)
	@MainView
	@Get("edit")
	public String edit(Invocation inv, @NotBlank @Param("id") int id) throws Throwable {

		HttpServletRequest request = inv.getRequest();
		request.setAttribute("id", id);

		// 读取角色属性
		UserModel userModel = dao.get(id);
		if (userModel == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "找不到角色");
		}
		request.setAttribute("username", userModel.getUsername());
		request.setAttribute("remark", userModel.getRemark());
		request.setAttribute("authority", userModel.getAuthority());

		return "edit";
	}

	@UserAuthority(PageModel.account_page)
	@Post("toEdit")
	public String toEdit(Invocation inv, @NotBlank @Param("id") int id) throws Throwable {

		// 读取参数
		String remark = (String) inv.getParameter("remark");
		int authority = zyt.utils.Utils.ObjectUtils.baseValue(inv.getParameter("authority"), Integer.class);

		// 读取角色属性
		UserModel userModel = dao.get(id);
		if (userModel == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "找不到角色");
		}

		// 设置
		userModel.setRemark(remark);
		userModel.setAuthority(authority);
		int count = dao.update(userModel);
		if (count <= 0) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改失败");
		}

		// 密码强制改动
		String password = (String) inv.getParameter("password");
		if (password != null && password.length() > 0) {
			count = dao.changePwd(id, password);
			if (count <= 0) {
				return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "密码修改失败");
			}
		}

		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "修改成功","account/list?mainView=true");
	}

	@UserAuthority(PageModel.account_page)
	@Get("toRemove")
	public String toRemove(Invocation inv, @Param("id") int id) throws Throwable {

		int count = dao.delete(id);
		if (count <= 0) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "删除失败");
		}

		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功","account/list?mainView=true");
	}

	@LoginCheck
	@MainView
	@Get("changePwd")
	public String changePwd(Invocation inv) throws Throwable {
		return "pwd";
	}

	@LoginCheck
	@Post("toChangePwd")
	public String toChangePwd(Invocation inv, @Param("newPwd") String newPwd, @Param("checkPwd") String checkPwd) throws Throwable {

		// 验证密码
		if (!newPwd.equals(checkPwd)) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "新密码不相同");
		}

		// 检测旧密码
		String password = (String) inv.getParameter("password");
		if (StringUtils.isBlank(password)) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "密码不能为空");
		}

		// 读取账号信息
		HttpServletRequest request = inv.getRequest();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("userName");
		// 检测是否有该账号
		UserModel userModel = dao.get(username, password);
		if (userModel == null) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "账号密码错误");
		}

		// 检测修改
		if (newPwd.equals(password)) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "密码不需要修改");
		}

		// 修改密码
		Integer count = dao.changePwd(userModel.getId(), newPwd);
		if (count == null || count <= 0) {
			return "@"+PageNoticeUtil.notic(PageNoticeUtil.ERROR, "修改错误");
		}

		return "@"+PageNoticeUtil.notic(PageNoticeUtil.INFO, "修改成功","account/changePwd?mainView=true");
	}
}
