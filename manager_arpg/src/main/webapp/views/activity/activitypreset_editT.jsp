<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="cn.saturn.web.controllers.activity.dao.PresetActivity"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- 调用CSS，JS -->
<!-- <link href="${css}/style.css" rel="stylesheet" type="text/css" />  -->
<!-- <link rel="stylesheet" href="${css}/main/add.css" type="text/css" />-->
<link rel="stylesheet" href="${res}/utilLib/bootstrap.min.css"
	type="text/css" />

<script type="text/javascript" src="${js}/main/jquery.min.js"></script>


<style type="text/css">
</style>

<script type="text/javascript">
	
</script>

</head>



<body>
	<div class="div_from_aoto"
		style="width: 50%; margin: 0 auto; text-align: center">
		<form id="form" name="form" action="${eidtUrl}" method="post"
			target="_top">
			<input type="hidden" value="${id}" id="id" name="id" />
			<table>
				<tr>
					<td><label class="laber_from">活动ID:</label></td>

					<td><input class="input_from" name="pid" type="text"
						value="${pid}" style="width: 100px;" ${pidStr} /></td>
					<td><input class="input_from" name="cid" value="${cid}"
						style="width: 80px;" ${cidStr} type="text" /></td>
				</tr>
				<tr>
					<td><label class="laber_from">活动标题</label></td>

					<td><input class="input_from" name="tital" type="text"
						value="${tital}" /></td>
				</tr>
				<tr>
					<td><label class="laber_from">活动类型</label></td>
					<td>
						<%
							//状态
							Integer typeObj = (Integer) request.getAttribute("type");
							int type = (typeObj != null) ? typeObj : 0;
							WebUtils.createSelected(pageContext, "type", "input_select", PresetActivity.typeStrs, type, false);
						%>
					</td>


				</tr>


				<tr>
					<td><label class="laber_from">活动分页</label></td>

					<td>
						<%
							//状态
							Integer clazzObj = (Integer) request.getAttribute("clazz");
							int clazz = (clazzObj != null) ? clazzObj : 0;
							WebUtils.createSelected(pageContext, "clazz", "input_select", PresetActivity.clazzStrs, clazz, false);
						%>
					</td>
				</tr>


				<tr>
					<td><label class="laber_from">活动时间</label></td>
					<td><input style="width: 150px;" name="startTime" type="date"
						value="${startTime}" /></td>
					<td><input style="width: 150px;" name="endTime" type="date"
						value="${endTime}" /></td>

				</tr>

				<tr>
					<td><label class="laber_from">活动介绍</label></td>
					<td><textarea rows="3" cols="30" name='intro' id='intro'>${intro}</textarea></td>
				</tr>

				<tr>
					<td><label class="laber_from">活动提醒</label></td>
					<td><textarea rows="3" cols="30" name='tips' id='tips'>${tips}</textarea></td>
				</tr>

				<tr>
					<td><label class="laber_from">备注</label></td>
					<td><textarea rows="3" cols="30" name='remark' id='remark'
							${remarkStr}>${remark}</textarea></td>
				</tr>

				<tr>
					<td><label class="laber_from">特殊参数</label></td>
					<td><input class="input_from" name="speArg" value="${speArg}"
						style="width: 80px;" type="text" /></td>
				</tr>




				<%
					JspWriter out0 = pageContext.getOut(); // jsp输出
					int count = 7;

					for (int i = 0; i < count; i++) {
						out0.println("<tr><td>");
						//StringBuffer strBuf = new StringBuffer();

						//String name = String.format("items_%02d", i);
						//WebUtils.createLabel(pageContext, "laber_from", String.format("奖励_%02d:", i + 1));
						out0.println(String.format("奖励_%02d:", i + 1));
						out0.println("</td><td>");
						//条件数据
						String requireName = String.format("require_%d", i);
						Object requireValue0 = request.getAttribute(requireName);
						String requireValue = (requireValue0 != null) ? requireValue0.toString() : "";
						//条件输入框
						//strBuf.append("<input id=\"");
						//strBuf.append(requireName);
						//strBuf.append("\" name=\"");
						//strBuf.append(requireName);
						//strBuf.append("\" value=\"");
						//strBuf.append(requireValue);
						//strBuf.append("\" placeholder=\"条件\"");
						//strBuf.append(" />");
						//out0.println(strBuf.toString());
						WebUtils.createInput(pageContext, requireName, "", requireValue, "条件");
						out0.println("</td><td>");
						//奖励
						String awardName = String.format("award_%d", i);
						Object awardValue0 = request.getAttribute(awardName);
						String awardValue = (awardValue0 != null) ? awardValue0.toString() : "";
						//条件输入框
						//strBuf.append("<input id=\"");
						//strBuf.append(awardName);
						//strBuf.append("\" name=\"");
						//strBuf.append(awardValue);
						//strBuf.append("\" placeholder=\"奖励字符串\"");
						//strBuf.append(" />");
						//out0.println(strBuf.toString());
						WebUtils.createInput(pageContext, awardName, "", awardValue, "奖励字符串:id_count;id2_count2");

						//WebUtils.createItemList(pageContext, name, 5);
						out0.println("</td></tr>");
					}
				%>

			</table>






			<div class="control-group" style="margin: 0 auto; text-align: left">
				<label class="laber_from">服务器</label>
				<%
					//String srvIdStr = (String) request.getAttribute("srvIdStr");
					WebUtils.createServerCheckBoxs(pageContext, "srvIds", "controls", null, null);
					//WebUtils.createServerCheckBoxs(pageContext, "srvIds", "controls", null);
				%>
			</div>

			<div class="control-group"
				style="widrh: 100%; margin: 0 auto; text-align: center">
				<button type="submit" class="btn btn-success"
					onclick="javascript:return dialog('是否提交?')" style="width: 120px;">${btnStr}</button>

			</div>
		</form>



	</div>

</body>



</html>
