<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../index.jsp"%>
<%@ page import="cn.saturn.web.controllers.server.dao.SectionModel"%>
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
	 var subUrl = "${path}"+'/server/section/toEdit';
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
	<div class="div_from_aoto" style="width: 500px;">
		<form id="myfrom" target="_top">
			<input type="hidden" value="${id}" name="id" />
			<div class="control-group">
				<label class="laber_from">区名称</label>
				<div class="controls">
					<input class="input_from" name="name" type="text" value="${name}" />
					<p class="help-block"></p>
				</div>
			</div>


			<div class="control-group">
				<label class="laber_from">状态</label>
				<div class="controls">
					<%
						Integer stateObj = (Integer) request.getAttribute("state");
						int state = (stateObj != null) ? stateObj : 0;
						//WebUtils.createSectionStateSelected(pageContext, "state", "input_select", state);
						WebUtils.createSelected(pageContext, "state", "input_select", SectionModel.stateStrs, state);
					%>
					<p class="help-block"></p>
				</div>
			</div>


			<div class="control-group">
				<label class="laber_from">推荐</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="recommend"
						type="checkbox" ${recommend} />
					<p class="help-block"></p>
				</div>
			</div>

			<div class="control-group">
				<label class="laber_from">平台筛选</label>
				<div class="controls">
					<input class="input_from" name="platforms" type="text"
						value="${platforms}" placeholder="格式: 1.分号;分开, !开头为否." />
					<p class="help-block"></p>
				</div>
			</div>


			<div class="control-group">
				<label class="laber_from">备注</label>
				<div class="controls">
					<input class="input_from" name="remark" type="text"
						value="${remark}" />
					<p class="help-block"></p>
				</div>
			</div>


			<div class="control-group">
				<label class="laber_from"></label>
				<div class="controls">
					<button class="btn btn-success" onclick="return onSubmit();" style="width: 120px;">${btn}</button>
				</div>
			</div>
		</form>
	</div>

</body>



</html>
