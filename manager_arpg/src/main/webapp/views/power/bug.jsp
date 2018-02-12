<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="cn.saturn.web.controllers.power.LogController"%>
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


<style type="text/css">
</style>

<script type="text/javascript">
	function toSubmit()
	{
		var servId = $('#srvId').find('option:selected').attr('value'); 
		var subUrl = "${path}/power/bug/toCheck?srvId="+servId+"&mainView=true";
		center_iframe(subUrl);
	}
</script>

</head>



<body>
	<div class="div_from_aoto" style="width: 500px;">
			<div class="control-group">
				<label class="laber_from">服务器</label>
				<div class="controls">
					<%
						WebUtils.createServerSelected(pageContext, "srvId", "input_select");
					%>
				</div>
			</div>


			<div class="control-group">
				<label class="laber_from"></label>
				<div class="controls">
					<button class="btn btn-success" style="width: 120px;" onclick="toSubmit();">查看</button>
				</div>
			</div>

			<div id="result_info" />
	</div>
</body>
</html>
