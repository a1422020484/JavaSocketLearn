<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="cn.saturn.web.controllers.server.dao.SectionModel"%>
<%@ page import="cn.saturn.web.controllers.server.dao.ServerModel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- 调用CSS，JS -->
<link href="${css}/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${css}/main/add.css" type="text/css" />
<link rel="stylesheet" href="${res}/utilLib/bootstrap.min.css"
	type="text/css" />
	
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/color.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/demo/demo.css" />
	
	
<script type="text/javascript" src="${js}/main/jquery.min.js"></script>
<script type="text/javascript" src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/datagrid-detailview.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${js}/easyui_iframe.js"></script>
<script type = "text/javascript">
function onSubmit()
{
	 $.messager.confirm('提醒', '是否确定发送?', function(r){
       if (r){
        	ajaxSub();
        }
     });
	return false;
}

function ajaxSub()
{
	 var subUrl = "${toEdit}";
	   $.ajax({
	         type: "POST",
	         url: subUrl,
	         data:$('#myfrom').serialize(),
	         async: false,
	         datatype: "json",
	         success: function(data) {
	             var obj = eval("[" + data + "]");
	             if (obj.length > 0) {
	               $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
	               if(obj[0].redUrl!='')
		               	center_iframe(obj[0].redUrl);
	             }
	         },
	     });
}
</script>
</head>
<body>

	<div id="body2" style="width: 100%;margin: 0 auto; text-align: center;overflow:auto">
	<div id="body3" style="width: 500px;margin: 0 auto;text-align: center;">
		<form id="myfrom" target="_top">
			<input type="hidden" value="${id}" id="id" name="id" />
			<div class="control-group">
				<label class="laber_from">服务器ID</label>
				<div class="controls">
					<input class="input_from" name="newid" id="newid" type="text"
						value="${id}" ${idStr} />
					<p class="help-block"></p>
				</div>
			</div>

			<div class="control-group">
				<label class="laber_from">服务器名</label>
				<div class="controls">
					<input class="input_from" name="srvname" type="text"
						value="${srvname}" ${serNameStr} />
					<p class="help-block"></p>
				</div>
			</div>
			<div class="control-group">
				<label class="laber_from">服务器访问地址</label>
				<div class="controls">
					<input class="input_from" name="srvurl" type="text"
						value="${srvurl}" ${serUrlStr} />
					<p class="help-block"></p>
				</div>
			</div>

			<div class="control-group">
				<label class="laber_from">状态</label>
				<div class="controls">
					<%
						//可空状态
						Boolean stateEableE0 = (Boolean) request.getAttribute("stateEableE");
						boolean stateEable = (stateEableE0 != null) ? stateEableE0 : false;
						//选项额外文本
						String stateStr = (String) request.getAttribute("stateStr");
						//状态
						Integer stateObj = (Integer) request.getAttribute("state");
						int state = (stateObj != null) ? stateObj : 0;
						WebUtils.createSelected(pageContext, "state", "input_select", ServerModel.srvStateStrs, state, false, stateStr, stateEable);
					%>
					<p class="help-block"></p>
				</div>
			</div>


			<div class="control-group">
				<label class="laber_from">分区</label>
				<div class="controls">
					<!--  
					<input class="input_from" name="section" type="text"
						value="${section}" />
						-->

					<%
						String sectionStr = (String) request.getAttribute("sectionStr");
						Integer sectionObj = (Integer) request.getAttribute("section");
						int section = (sectionObj != null) ? sectionObj : 0;
						WebUtils.createSectionSelected(pageContext, "section", "input_select", section, sectionStr);
					%>
					<p class="help-block"></p>
				</div>
			</div>

			<div class="control-group">
				<label class="laber_from">推荐/优先</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="recommend"
						type="checkbox" ${recommendStr} /> <input class="input_from"
						style="width: 70px; height: 30px" name="priority" type="text"
						value="${priority}" ${priorityStr} } />
					<p class="help-block"></p>
				</div>
			</div>

			<div class="control-group">
				<label class="laber_from">操作/备注</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="operate"
						type="checkbox" ${operateStr} /> <input class="input_from"
						name="remark" type="text" value="${remark}" />
					<p class="help-block"></p>
				</div>
			</div>
			<!-- 
			<div class="control-group">
				<label class="laber_from">备注</label>
				<div class="controls">
					<input class="input_from" name="remark" type="text"
						value="${remark}" />
					<p class="help-block"></p>
				</div>
			</div>
		 -->
			<div class="control-group">
				<label class="laber_from">平台筛选</label>
				<div class="controls">
					<input class="input_from" name="platforms" type="text"
						value="${platforms}" />
					<p class="help-block"></p>
				</div>
			</div>
			<div class="control-group">
				<label class="laber_from">维护说明</label>
				<div class="controls">
					<!-- 
					<input class="input_from" name="maintainText" type="text"
						value="${maintainText}" />
						 -->

					<textarea rows="4" cols="40" name='maintainText' id='maintainText'
						style="max-width: 300px;">${maintainText}</textarea>

					<p class="help-block"></p>
				</div>
			</div>

			<div class="control-group">
				<label class="laber_from">定时开服</label>
				<div class="controls">
					<input class="input_from" name="openTime" type="datetime-local"
						value="${openTime}" />
					<p class="help-block"></p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="laber_from">开服时间</label>
				<div class="controls">
					<input class="input_from" name="open_time" type="datetime"
						value="${open_time}" />
					<p class="help-block"></p>
				</div>
			</div>
			<span>开服时间: (用于查询，必填) eg：2015-01-29</span>
			<div class="control-group">
				<label class="laber_from"></label>
				<div class="controls">
					<button class="btn btn-success" onclick="return onSubmit();" style="width: 120px;">${btn}</button>
				</div>
			</div>
		</form>
	</div>
	</div>
<script type = "text/javascript"> 
	<!---rew type 01--->
	$(document).ready(function() {
		var h = document.documentElement.clientHeight;
		$('#body2').css('height', h * 0.9);
    });
</script>
</body>



</html>
