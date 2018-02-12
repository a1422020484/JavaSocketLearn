package cn.saturn.web.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import zyt.component.node.Node;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.controllers.account.dao.AuthorityModel;
import cn.saturn.web.controllers.server.dao.SectionManager;
import cn.saturn.web.controllers.server.dao.SectionModel;
import cn.saturn.web.controllers.server.dao.ServerComponent;
import cn.saturn.web.controllers.server.dao.ServerModel;

/**
 * 应用于jsp的代码
 *
 */
public class WebUtils {

	/**
	 * 生成调用java函数的javascript代码
	 * 
	 * <p/>
	 * return callJava(url, this.value)
	 * 
	 * @param url
	 * @return
	 */
	public static String callJavaCode(String url, String value) {
		return "\"callJava('" + url + "', '" + value + "')\"";
	}

	/**
	 * 服务器下拉菜单
	 * 
	 * @param out
	 * @param id
	 * @param cls
	 * @throws IOException
	 */
	public static void createServerSelected(PageContext pageContext, String name, String clazz) throws IOException {
		createServerSelected(pageContext, name, clazz, null);
	}

	public static void createServerSelected(PageContext pageContext, String name, String clazz, String extStr) throws IOException {

		// 读取上次选择的服务器
		HttpSession session = pageContext.getSession(); // session
		Integer useSrvId = (Integer) session.getAttribute("selectServer");
		int curSrvId = (useSrvId != null) ? useSrvId : 0;

		// 生成服务器选择列表
		createServerSelected(pageContext, name, clazz, curSrvId, extStr);

		// <select class="input_select">
		// <option selected="selected">董事长</option>
		// <option>总经理</option>
		// <option>经理</option>
		// <option>主管</option>
		// </select>
	}

	public static void createServerSelected(PageContext pageContext, String name, String cls, int value, String extStr) throws IOException {
		// select
		ConfigNode select = new ConfigNode("select");
		select.map.put("id", name);
		select.map.put("name", name);
		select.map.put("class", cls);
		select.map.put("style", "width:150px;");
		select.map.put("extStr", extStr);

		// 相应处理
		ServletContext context = pageContext.getServletContext();
		String callUrl = Utils.url(context, "/server/toSctSrv");
		select.map.put("onchange", "callJava('" + callUrl + "', this.value)");

		// 罗列服务器列表
		// ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		// List<ServerModel> list = serverComponent.getList();
		// List<ServerModel> list = ServerComponent.getServerList();
		List<ServerModel> list = ServerComponent.getOperateServerList();
		int size = (list != null) ? list.size() : 0;
		for (int i = 0; i < size; i++) {
			ServerModel server = list.get(i);
			if (server == null || !server.isOperate()) {
				continue;
			}
			int srvId = (int) server.getId();
			// add option
			ConfigNode option = new ConfigNode("option");
			option.map.put("value", srvId + "");
			option.value = server.getSrvStr0();
			// 检测是否被选择
			if (value == srvId) {
				option.map.put("selected", "selected");
			}
			// 添加
			select.add(option);
		}

		// 检测数量
		size = select.size();
		if (size <= 0) {
			ConfigNode option = new ConfigNode("option");
			option.value = "无";
			option.map.put("value", -1 + "");
			select.add(option);
		}

		// 生成选择栏
		createView(pageContext, select);
	}

	/**
	 * 选择特定的服务器
	 * 
	 * @param session
	 * @param valueCode
	 *            列表名称
	 * @return 当前ID
	 */
	public static int setServerSelected(HttpSession session, String valueCode) {
		// 直接解析是否是ID
		Integer intValue = zyt.utils.Utils.ObjectUtils.baseValue0(valueCode, Integer.class);
		int srvId = 0;
		if (intValue == null) {
			// 拆分对象 srvId + "-" + srvName
			srvId = ServerModel.getSrvId(valueCode);
			if (srvId <= 0) {
				return 0; // 错误
			}
		} else {
			srvId = intValue;
		}

		// 设置服务器
		if (srvId > 0) {
			session.setAttribute("selectServer", srvId);
		}
		return srvId;

	}

	public static void setServerSelected(HttpServletRequest request, int srvId) {
		HttpSession session = request.getSession();
		session.setAttribute("selectServer", srvId);
	}

	/**
	 * 读取session中默认选择服务器ID
	 * 
	 * @param session
	 * @return
	 */
	public static int getServerSelected(HttpSession session) {
		try {
			Integer intValue = (Integer) session.getAttribute("selectServer");
			return (intValue != null) ? intValue : 0;
		} catch (Exception ex) {
		}
		return 0;
	}

	public static int checkSrvId(int srvId, HttpSession session, boolean defualt) {
		if (srvId == -1) {
			return srvId; // 不用处理
		}
		// 获取默认值
		if (srvId == 0) {
			srvId = WebUtils.getServerSelected(session);
		}

		// 默认值处理
		if (defualt && srvId <= 0) {
			return defualSrvId();
		}
		return srvId;
	}

	public static int defualSrvId() {
		List<ServerModel> list = ServerComponent.getServerList();
		int count = (list != null) ? list.size() : 0;
		if (count > 0) {
			ServerModel model = list.get(0);
			return (model != null) ? (int) model.getId() : 0;
		}
		return 0;
	}

	public static int checkSrvId(int srvId, HttpServletRequest request, boolean defualt) {
		HttpSession session = request.getSession();
		return checkSrvId(srvId, session, defualt);
	}

	public static int checkSrvId(int srvId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		return checkSrvId(srvId, session, false);
	}

	// public static void createServerCheckBoxs(PageContext pageContext, String name, String clazz, String extStr) throws IOException {
	// // select
	// ConfigNode div = new ConfigNode("div");
	// // div.map.put("id", name);
	// // div.map.put("name", name);
	// div.map.put("class", clazz);
	// div.map.put("extStr", extStr);
	//
	// // 相应处理
	// String allBoxName = "__srv_all";
	// // String allnoBoxName = "__srv_allno";
	// String allsetFunc = "__srv_func_allset";
	// String idresetFunc = "__srv_func_resetids";
	// String boxothername = "__srv_others";
	//
	// StringBuffer strBuf = new StringBuffer();
	// JspWriter out = pageContext.getOut(); // jsp输出
	// strBuf.append("<script>\n");
	//
	// // 写入内嵌代码(id列表生成)
	// strBuf.append("function ");
	// strBuf.append(idresetFunc);
	// strBuf.append("(othernames) {\n");
	// strBuf.append("\tvar others = document.getElementsByName(othernames);\n");
	// strBuf.append("\tvar valueStr=\"\";\n");
	// strBuf.append("\tfor(var i=0;i<others.length;i++){\n");
	// strBuf.append("\tif(others[i].checked){ valueStr+=others[i].value+\",\"; }\n");
	// strBuf.append("\t}\n");
	// // strBuf.append("\talert(valueStr);\n");
	// strBuf.append("\tvar setViews=document.getElementsByName(\"" + name + "\");\n");
	// strBuf.append("\tsetViews[0].value=valueStr;\n");
	// strBuf.append("}\n");
	// // 写入内嵌代码(全选)
	// strBuf.append("function ");
	// strBuf.append(allsetFunc);
	// strBuf.append("(checkname, othernames) {\n");
	// strBuf.append("\tvar checkBox=document.getElementsByName(checkname);\n");
	// strBuf.append("\tvar others = document.getElementsByName(othernames);\n");
	// strBuf.append("\tfor(var i=0;i<others.length;i++){\n");
	// // strBuf.append("\tif(others[i].checked!=checkBox[0].checked){ others[i].click(); }\n");
	// strBuf.append("\tif(others[i].checked!=checkBox[0].checked){ others[i].checked=checkBox[0].checked; }\n");
	// strBuf.append("\t}\n");
	// // 调用函数
	// strBuf.append(idresetFunc);
	// strBuf.append("(othernames);\n");
	// strBuf.append("}\n");
	// // 写入
	// strBuf.append("</script>\n");
	// out.println(strBuf);
	//
	//
	// // 主输入
	// ConfigNode mainView = new ConfigNode("input");
	// mainView.map.put("id", name);
	// mainView.map.put("name", name);
	// mainView.map.put("class", clazz);
	// mainView.map.put("type", "hidden");
	// mainView.map.put("extStr", extStr);
	// div.add(mainView);
	// // 全选
	// ConfigNode all = new ConfigNode("input");
	// all.map.put("name", allBoxName);
	// all.map.put("type", "checkbox");
	// all.map.put("style", "width: 20px; height: 20px");
	// all.map.put("onclick", allsetFunc + "('" + allBoxName + "','" + boxothername + "')");
	// all.value = "全选";
	// div.add(all);
	//
	// // // 全非
	// // ConfigNode allno = new ConfigNode("input");
	// // allno.map.put("name", allnoBoxName);
	// // allno.map.put("onclick", allsetFunc + "('" + allnoBoxName + "','" + boxothername + "')");
	// // allno.map.put("type", "checkbox");
	// // allno.map.put("style", "width: 20px; height: 20px");
	// // allno.value = "全否";
	// // div.add(allno);
	//
	// // 换行
	// div.add(new ConfigNode("br"));
	//
	// final int lineCount = 4;
	// int lineIndex = 0;
	// // 罗列服务器列表
	// List<ServerModel> list = ServerComponent.getServerList();
	// int size = (list != null) ? list.size() : 0;
	// for (int i = 0; i < size; i++) {
	// ServerModel server = list.get(i);
	// if (server == null || !server.isOperate()) {
	// continue;
	// }
	// int srvId = (int) server.getId();
	// // add option
	// ConfigNode option = new ConfigNode("input");
	// option.map.put("name", boxothername);
	// option.map.put("type", "checkbox");
	// option.map.put("value", String.valueOf(srvId));
	// option.map.put("style", "width: 20px; height: 20px");
	// option.map.put("onclick", idresetFunc + "('" + boxothername + "')");
	// option.value = server.getSrvStr0();
	//
	// // 添加
	// div.add(option);
	//
	// // 检测换行
	// lineIndex++;
	// if (lineIndex % lineCount == 0) {
	// // 换行
	// div.add(new ConfigNode("br"));
	// }
	//
	// }
	//
	// // 生成选择栏
	// createView(pageContext, div);
	// }

	public static void createServerCheckBoxs(PageContext pageContext, String name, String clazz, String extStr, int[] selectSrvIds) throws IOException {
		// select
		ConfigNode div = new ConfigNode("div");
		div.map.put("class", clazz);
		div.map.put("extStr", extStr);

		// 相应处理
		String allBoxName = "__srv_all";
		
		String apartBoxName = "__srv_apart";
		// String allnoBoxName = "__srv_allno";
		String allsetFunc = "__srv_func_allset";
		String idresetFunc = "__srv_func_resetids";
		String boxothername = "__srv_others";

		StringBuffer strBuf = new StringBuffer();
		JspWriter out = pageContext.getOut(); // jsp输出
		strBuf.append("<script>\n");

		// 写入内嵌代码(id列表生成)
		strBuf.append("function ");
		strBuf.append(idresetFunc);
		strBuf.append("(othernames) {\n");
		strBuf.append("\tvar others = document.getElementsByName(othernames);\n");
		strBuf.append("\tvar valueStr=\"\";\n");
		strBuf.append("\tfor(var i=0;i<others.length;i++){\n");
		strBuf.append("\tif(others[i].checked){ valueStr+=others[i].value+\",\"; }\n");
		strBuf.append("\t}\n");
		// strBuf.append("\talert(valueStr);\n");
		strBuf.append("\tvar setViews=document.getElementsByName(\"" + name + "\");\n");
		strBuf.append("\tsetViews[0].value=valueStr;\n");
		strBuf.append("}\n");
		// 写入内嵌代码(全选)
		strBuf.append("function ");
		strBuf.append(allsetFunc);
		strBuf.append("(checkname, othernames) {\n");
		strBuf.append("\tvar checkBox=document.getElementsByName(checkname);\n");
		strBuf.append("\tvar others = document.getElementsByName(othernames);\n");
		strBuf.append("\tfor(var i=0;i<others.length;i++){\n");
		// strBuf.append("\tif(others[i].checked!=checkBox[0].checked){ others[i].click(); }\n");
		strBuf.append("\tif(others[i].checked!=checkBox[0].checked){ others[i].checked=checkBox[0].checked; }\n");
		strBuf.append("\t}\n");
		// 调用函数
		strBuf.append(idresetFunc);
		strBuf.append("(othernames);\n");
		strBuf.append("}\n");
		
		strBuf.append("\tfunction apartSelected(checkBox){\n");
		strBuf.append("\tvar isChecked = checkBox.checked;\n");
		strBuf.append("\tvar checkBox=$(\"[name='__srv_others']\");\n");
		strBuf.append("\tvar startInput=$(\"[name='startInput']\").val();\n");
		strBuf.append("\tvar endInput=$(\"[name='endInput']\").val();\n");
		strBuf.append("\tif(startInput===''||endInput===''){\n");
		strBuf.append("\tif(isChecked){\n");
		strBuf.append("\t $(\"[name='__srv_apart']\")[0].checked=false;\n");
		strBuf.append("\t return ;\n");
		strBuf.append("\t}\n");
		strBuf.append("\telse{\n");
		strBuf.append("\t $(\"[name='__srv_apart']\")[0].checked=true;\n");
		strBuf.append("\treturn ;\n");
		strBuf.append("\t}\n");
		strBuf.append("\t}\n");
		strBuf.append("\t startInput= Number(startInput);\n");
		strBuf.append("\t endInput= Number(endInput);\n");
		strBuf.append("\tfor(var i=0;i<checkBox.length;i++){\n");
		strBuf.append("\tvar title = checkBox[i].nextSibling.nodeValue.split('-')[0];\n");
		strBuf.append("\t title=Number(title);\n");
		strBuf.append("\t if(title>=startInput&&title<=endInput){\n");
		strBuf.append("\t if(isChecked){\n");
		strBuf.append("\tcheckBox[i].checked=true;\n");
		strBuf.append("\t}\n");
		strBuf.append("\telse{\n");
		strBuf.append("\t checkBox[i].checked=false;\n");
		strBuf.append("\t}\n");
		strBuf.append("\t}\n");
		strBuf.append("\t}\n");
		strBuf.append("\t__srv_func_resetids('__srv_others')\n");
		strBuf.append("\t}\n");
		// 写入
		strBuf.append("</script>\n");
		out.println(strBuf);

		// 计算数值
		StringBuffer strBuf0 = new StringBuffer();
		int count0 = (selectSrvIds != null) ? selectSrvIds.length : 0;
		for (int i = 0; i < count0; i++) {
			int value = selectSrvIds[i];
			strBuf0.append(value);
			strBuf0.append(",");
		}

		// 主输入用于返回值
		ConfigNode mainView = new ConfigNode("input");
		mainView.map.put("id", name);
		mainView.map.put("name", name);
		mainView.map.put("class", clazz);
		mainView.map.put("type", "hidden");
		mainView.map.put("extStr", extStr);
		mainView.map.put("value", strBuf0.toString());
		div.add(mainView);
		
		// 开始项
		ConfigNode startInput = new ConfigNode("input");
		startInput.map.put("onkeyup", "value=value.replace(/[^\\d.]/g,'')");
		startInput.map.put("maxlength", "4");
		startInput.map.put("name", "startInput");
		startInput.map.put("type", "text");
		startInput.map.put("style", "width: 40px; height: 20px;");
        startInput.value = "--";
		div.add(startInput);
		// 结束项
		ConfigNode endInput = new ConfigNode("input");
		endInput.map.put("onkeyup", "value=value.replace(/[^\\d.]/g,'')");
		endInput.map.put("maxlength", "4");
		endInput.map.put("name", "endInput");
		endInput.map.put("type", "text");
		endInput.map.put("style", "width: 40px; height: 20px;");
		endInput.value = "部分选择";
		div.add(endInput);
		
		
		
		// 部分选择
		ConfigNode apart = new ConfigNode("input");
		apart.map.put("lebel", "选择");
		apart.map.put("name", apartBoxName);
		apart.map.put("type", "checkbox");
		apart.map.put("style", "width: 20px; height: 20px;vertical-align:middle;margin-right:300px;");
		apart.map.put("onclick",  "apartSelected (this)");
//		apart.value = "部分选择";
		div.add(apart);
		
		// 全选
		ConfigNode all = new ConfigNode("input");
		all.map.put("name", allBoxName);
		all.map.put("type", "checkbox");
		all.map.put("style", "width: 20px; height: 20px");
		all.map.put("onclick", allsetFunc + "('" + allBoxName + "','" + boxothername + "')");
		all.value = "全选";
		div.add(all);

		

		// table
		ConfigNode table = new ConfigNode("table");
		table.map.put("border", "1");
		div.add(table);

		ConfigNode tr = new ConfigNode("tr");
		table.add(tr);

		final int lineCount = 4;
		int lineIndex = 0;
		// 罗列服务器列表
		List<ServerModel> list = ServerComponent.getServerList();
		int size = (list != null) ? list.size() : 0;
		for (int i = 0; i < size; i++) {
			ServerModel server = list.get(i);
			if (server == null || !server.isOperate()) {
				continue;
			}
			int srvId = (int) server.getId();
			boolean enable = Utils.ArrayUtils.find(selectSrvIds, srvId);

			// td
			ConfigNode td = new ConfigNode("td");
			tr.add(td);

			// add option
			ConfigNode option = new ConfigNode("input");
			option.map.put("name", boxothername);
			option.map.put("type", "checkbox");
			option.map.put("value", String.valueOf(srvId));
			option.map.put("style", "width: 20px; height: 20px");
			option.map.put("onclick", idresetFunc + "('" + boxothername + "')");
			if (enable) {
				option.map.put("checked", "true");
			}
			option.value = server.getSrvStr0();

			// 添加
			// div.add(option);
			td.add(option);

			// 检测换行
			lineIndex++;
			if (lineIndex % lineCount == 0) {
				// 换行
				// div.add(new ConfigNode("br"));
				// 换行
				tr = new ConfigNode("tr");
				table.add(tr);
			}

		}

		// 生成选择栏
		createView(pageContext, div);
	}

	public static void createItemList(PageContext pageContext, String name, int count) throws IOException {
		JspWriter out = pageContext.getOut(); // jsp输出
		out.println("<table >");
		out.println("<tr>");

		out.println("<th>");
		String requireStr = String.format("%s_require", name);
		out.println("<input id=\"" + requireStr + "\" name=\"" + requireStr + "\" style=\"width: 80px\" placeholder=\"条件\"/>");
		out.println("</th>");

		for (int i = 0; i < count; i++) {
			String itemName = String.format("%s_%02d", name, i);
			out.println("<th>");
			createItemBox(pageContext, itemName);
			out.println("</th>");
		}

		out.println("</tr>");
		out.println("</table>");

	}

	public static void createItemBox(PageContext pageContext, String name) throws IOException {
		// select
		ConfigNode root = new ConfigNode("table");
		root.map.put("id", name);
		root.map.put("name", name);
		root.map.put("style", "border-style: solid");

		// 一列
		ConfigNode tr = new ConfigNode("tr");
		root.add(tr);

		// 生成item id
		ConfigNode th = new ConfigNode("th");
		tr.add(th);
		ConfigNode input = new ConfigNode("input");
		input.map.put("id", name + "_id");
		input.map.put("name", name + "_id");
		input.map.put("style", "width: 80px");
		input.map.put("placeholder", "id");
		th.add(input);

		// 生成item count
		th = new ConfigNode("th");
		tr.add(th);
		input = new ConfigNode("input");
		input.map.put("id", name + "_count");
		input.map.put("name", name + "_count");
		input.map.put("style", "width: 80px");
		input.map.put("placeholder", "count");
		th.add(input);

		// 生成选择栏
		createView(pageContext, root);
	}

	public static void createLabel(PageContext pageContext, String clazz, String value) throws IOException {
		// select
		ConfigNode root = new ConfigNode("label");
		root.map.put("class", clazz);
		root.value = value;
		createView(pageContext, root);
	}

	public static void createInput(PageContext pageContext, String name, String clazz, String value, String placeholder) throws IOException {
		// select
		ConfigNode root = new ConfigNode("input");
		root.map.put("id", name);
		root.map.put("name", name);
		root.map.put("class", clazz);
		root.map.put("value", value);
		root.map.put("placeholder", placeholder);
		// root.value = value;
		createView(pageContext, root);
	}

	/**
	 * 区列表选择
	 * 
	 * @param pageContext
	 * @param name
	 * @param clazz
	 * @param value
	 * @throws IOException
	 */
	public static void createSectionSelected(PageContext pageContext, String name, String clazz, int value) throws IOException {
		createSectionSelected(pageContext, name, clazz, value, null);
	}

	public static void createSectionSelected(PageContext pageContext, String name, String clazz, int value, String extStr) throws IOException {
		// select
		ConfigNode select = new ConfigNode("select");
		select.map.put("id", name);
		select.map.put("name", name);
		select.map.put("class", clazz);
		select.map.put("style", "width:150px;");
		select.map.put("extStr", extStr);

		// 罗列服务器列表
		SectionManager sectionManager = ComponentManager.getComponent(SectionManager.class);
		List<SectionModel> list = sectionManager.getList();
		int size = (list != null) ? list.size() : 0;
		for (int i = 0; i < size; i++) {
			SectionModel model = list.get(i);
			if (model == null) {
				continue;
			}
			int id = (int) model.getId();
			// add option
			ConfigNode option = new ConfigNode("option");
			option.map.put("value", id + "");
			option.value = model.getSectionStr();
			// 检测是否被选择
			if (value == id) {
				option.map.put("selected", "selected");
			}
			// 添加
			select.add(option);
		}
		// 生成选择栏
		createView(pageContext, select);
	}

	public static void createSelected(PageContext pageContext, String name, String cls, String[] strs, int value, boolean showIndex, String extStr, boolean empyt) throws IOException {
		// select
		ConfigNode select = new ConfigNode("select");
		select.map.put("name", name);
		select.map.put("id", name);
		select.map.put("class", cls);
		select.map.put("extStr", extStr);

		// 添加默认选项
		if (empyt) {
			int value0 = -1;
			ConfigNode option = new ConfigNode("option");
			option.map.put("value", String.valueOf(value0));
			option.value = "未选择";
			if (value < 0) {
				option.map.put("selected", "selected");
			}
			select.add(option);
		}

		// 状态列表
		initSelectedConfig(select, strs, value, showIndex);

		// 生成选择栏
		createView(pageContext, select);

	}
	
	public static void createAuthoritySelected(PageContext pageContext, String name, String cls, String[] strs, int value, boolean showIndex, String extStr, boolean empyt) throws IOException {
		// select
		ConfigNode select = new ConfigNode("select");
		select.map.put("name", name);
		select.map.put("id", name);
		select.map.put("class", cls);
		select.map.put("extStr", extStr);

		// 添加默认选项
		if (empyt) {
			int value0 = -1;
			ConfigNode option = new ConfigNode("option");
			option.map.put("value", String.valueOf(value0));
			option.value = "未选择";
			if (value < 0) {
				option.map.put("selected", "selected");
			}
			select.add(option);
		}

		// 状态列表
		initAuthoritySelectedConfig(select, strs, value, showIndex);

		// 生成选择栏
		createView(pageContext, select);

	}

	public static void createSelected(PageContext pageContext, String name, String cls, String[] strs, int value, boolean showIndex) throws IOException {
		createSelected(pageContext, name, cls, strs, value, showIndex, null, false);
	}

	public static void createSelected(PageContext pageContext, String name, String cls, String[] strs, int value) throws IOException {
		createSelected(pageContext, name, cls, strs, value, false, null, false);
	}
	
	public static void createAuthoritySelected(PageContext pageContext, String name, String cls, String[] strs, int value) throws IOException {
		createAuthoritySelected(pageContext, name, cls, strs, value, false, null, false);
	}

	/**
	 * 初始化选择配置, 通过字符串数组
	 * 
	 * @param root
	 * @param strs
	 * @param value
	 */
	protected static void initSelectedConfig(ConfigNode root, String[] strs, int value) {
		initSelectedConfig(root, strs, value, false);
	}

	protected static void initSelectedConfig(ConfigNode root, String[] strs, int value, boolean showIndex) {
		// 遍历添加
		int size = (strs != null) ? strs.length : 0;
		for (int i = 0; i < size; i++) {
			String str = strs[i];
			if (str == null || str.isEmpty()) {
				continue;
			}
			if (showIndex) {
				str = i + "-" + str;
			}

			// add option
			ConfigNode option = new ConfigNode("option");
			option.map.put("value", i + "");
			option.value = str;
			// 检测是否被选择
			if (value == i) {
				option.map.put("selected", "selected");
			}
			// 添加
			root.add(option);
		}
	}
	
	protected static void initAuthoritySelectedConfig(ConfigNode root, String[] strs, int value, boolean showIndex) {
		AuthorityUtils  authorityUtils= 	BeanUtils.instantiate(AuthorityUtils.class);
		List<AuthorityModel>  list  =authorityUtils.settingAuthorityDAO.getList();
		
		// 遍历添加
		int size = (strs != null) ? list.size() : 0;
		for (int i = 0; i < size; i++) {
			
			AuthorityModel model = list.get(i);
			if (model == null) {
				continue;
			}
			String str = model.getAuthority_name();
			if (showIndex) {
				str = model.getAuthority_id() + "-" + model.getAuthority_name();
			}

			// add option
			ConfigNode option = new ConfigNode("option");
			option.map.put("value", model.getAuthority_id() + "");
			option.value = str;
			// 检测是否被选择
			if (value == Integer.valueOf(model.getAuthority_id())) {
				option.map.put("selected", "selected");
			}
			// 添加
			root.add(option);
		}
	}

	/**
	 * 创建用户文本框(单个用户)(用于输入用户ID或者用户名称)
	 * 
	 * @param pageContext
	 * @param name
	 * @param srvId
	 *            服务器ID, 可以是数字, 也可以是js代码
	 * @param gather
	 *            是否是集合对象
	 * @throws IOException
	 */
	public static void createUserText0(PageContext pageContext, String name, Object srvId, String extStr) throws IOException {

		// ServletContext servletContext = pageContext.getServletContext();
		// String cxtPath = servletContext.getContextPath();

		ConfigNode config = new ConfigNode("");
		String viewId = "__";
		String strId = name;
		String strInput = viewId + "inputStr";
		String strText = viewId + "textStr";

		// id
		ConfigNode id = new ConfigNode("input");
		config.add(id);
		id.map.put("id", strId);
		id.map.put("name", name);
		id.map.put("type", "hidden");
		// id.map.put("type", "text");

		// 输入
		ConfigNode input = new ConfigNode("input");
		config.add(input);
		input.map.put("id", strInput);
		input.map.put("name", strInput);
		input.map.put("extStr", extStr);

		// 函数代码
		String funcCode = "findUserInfo('" + strId + "' ,'" + strInput + "' ,'" + strText + "', " + srvId + ")";
		input.map.put("onblur", funcCode);
		input.map.put("value", "");
		input.map.put("style", "width: 150px");
		input.map.put("maxlength", "10");

		// 添加文本
		ConfigNode label = new ConfigNode("label");
		config.add(label);
		label.map.put("id", strText);
		label.map.put("name", strText);

		// 创建视图
		createView(pageContext, config);
	}

	public static void createUserText1(PageContext pageContext, String name, Object srvId, String extStr) throws IOException {

		// ServletContext servletContext = pageContext.getServletContext();
		// String cxtPath = servletContext.getContextPath();

		ConfigNode config = new ConfigNode("");
		String viewId = "__";
		String strId = name;
		String strInput = viewId + "inputStr";
		String strText = viewId + "textStr";

		// id
		ConfigNode id = new ConfigNode("input");
		config.add(id);
		id.map.put("id", strId);
		id.map.put("name", name);
		id.map.put("type", "hidden");
		// id.map.put("type", "text");

		// <textarea rows="5" cols="40" name='msgTxt' id='msgTxt'></textarea>
		// 输入
		ConfigNode input = new ConfigNode("textarea");
		config.add(input);
		input.map.put("id", strInput);
		input.map.put("name", strInput);
		input.map.put("extStr", extStr);
		input.value = "";

		// 函数代码
		String funcCode = "findUserInfo('" + strId + "' ,'" + strInput + "' ,'" + strText + "', " + srvId + ")";
		input.map.put("onblur", funcCode);
		input.map.put("value", "");
		input.map.put("style", "max-width: 150px");
		// input.map.put("maxlength", "10");

		// 添加文本
		ConfigNode label = new ConfigNode("label");
		config.add(label);
		// label.map.put("type", "text");
		label.map.put("id", strText);
		label.map.put("name", strText);
		label.value = "";

		// 创建视图
		createView(pageContext, config);
	}

	public static void createUserText(PageContext pageContext, String name, Object srvId, boolean gather, String extStr) throws IOException {
		if (gather) {
			createUserText1(pageContext, name, srvId, extStr);
		} else {
			createUserText0(pageContext, name, srvId, extStr);
		}
	}

	public static void createUserText(PageContext pageContext, String name, Object srvId) throws IOException {
		createUserText(pageContext, name, srvId, false, null);
	}

	public static void createUserText(PageContext pageContext, String name) throws IOException {
		createUserText(pageContext, name, 0);
	}

	/**
	 * 创建视图
	 * 
	 * @param pageContext
	 * @param config
	 *            配置
	 * @throws IOException
	 */
	public static void createView(PageContext pageContext, ConfigNode config) throws IOException {
		ConfigNode[] childs = config.getChilds(new ConfigNode[] {});
		int count = (childs != null) ? childs.length : 0;

		// 输出
		JspWriter out = pageContext.getOut(); // jsp输出
		out.println(config.head());

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				ConfigNode child = childs[i];
				createView(pageContext, child);
			}
		}

		out.println(config.tail());
	}

	/**
	 * 服务器字符串
	 * 
	 * @param srvId
	 * @return
	 */
	public static String serverStr(int srvId) {
		// 查找服务器
		ServerModel serverModel = ServerUtils.getServer(srvId);
		if (serverModel == null) {
			return null;
		}
		return serverModel.getSrvStr0();
	}

	public static String sectionStr(int sectionId) {
		SectionManager sectionManager = ComponentManager.getComponent(SectionManager.class);
		SectionModel model = sectionManager.find(sectionId);
		if (model != null) {
			return model.getSectionStr();
		}
		return "[" + sectionId + "]未知";
	}

	public static class Html {
		public static final DateFormat datetimelocalFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		public static final DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		public static final DateFormat dateFormatHms = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		public static final int oneDayTime = 60 * 60 * 24;
		public static final long oneDayTimeL = oneDayTime * 1000L;

		/** date **/
		public static long getDate(String datetimeStr) {
			try {
				return dateFormat.parse(datetimeStr).getTime();
			} catch (ParseException e) {
				// e.printStackTrace();
			}
			return 0L;
		}

		public static int getDate0(String datetimeStr) {
			long time = getDate(datetimeStr);
			return (time <= 0) ? 0 : (int) (time * 0.001);
		}

		/** datetimeloacl **/
		public static long getDateTimeLoacl(String datetimeStr) {
			try {
				return datetimelocalFormat.parse(datetimeStr).getTime();
			} catch (ParseException e) {
				// e.printStackTrace();
			}
			return 0L;
		}
		
		/** yyyy-MM-dd HH:mm:ss **/
		public static long getDateTimeLoaclHms(String datetimeStr) {
			try {
				return dateFormatHms.parse(datetimeStr).getTime();
			} catch (ParseException e) {
				// e.printStackTrace();
			}
			return 0L;
		}

		public static String getDateTimeStr(long time) {
			return datetimelocalFormat.format(new Date(time));
		}

		public static int getDateTimeLoacl0(String datetimeStr) {
			long time = getDateTimeLoacl(datetimeStr);
			return (time <= 0) ? 0 : (int) (time * 0.001);
		}
		

		public static int getDateTimeLoacl0Hms(String datetimeStr) {
			long time = getDateTimeLoaclHms(datetimeStr);
			return (time <= 0) ? 0 : (int) (time * 0.001);
		}
		
		public static String getDateTimeStr(int time) {
			return getDateTimeStr(time * 1000);
		}
	}
}

class ConfigNode extends Node<ConfigNode, String> {
	public final Map<String, String> map;
	public String value;

	public ConfigNode(String id) {
		super(id);
		map = new HashMap<String, String>();
	}

	public String head() {
		// 空过滤
		if (id == null || id.length() <= 0) {
			return "";
		}

		// 遍历属性
		StringBuffer values = new StringBuffer();
		Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String value = entry.getValue();

			// 跳过空字段
			if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
				continue;
			}

			// 额外字段
			if (key.equals("extStr")) {
				values.append(value);
				continue;
			}

			values.append(key);
			values.append("=\"");
			values.append(value);
			values.append("\" ");
		}
		// 文本值
		if (value == null && size() <= 0) {
			return "<" + this.id + " " + values.toString() + "/>";
		}

		// 输出
		String valueStr = (value != null) ? value : "";
		return "<" + this.id + " " + values.toString() + ">" + valueStr;
	}

	public String tail() {
		// 空过滤
		if (id == null || id.length() <= 0) {
			return "";
		}
		// 文本值
		if (value == null && size() <= 0) {
			return "";
		}

		return "</" + this.id + ">";
	}

}
