<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="cn.saturn.web.controllers.statistics.SystemLogParams"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- 调用CSS，JS -->
<link href="${css}/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${css}/main/add.css" type="text/css" />
<link rel="stylesheet" href="${res}/utilLib/bootstrap.min.css"
	type="text/css" />
<script type="text/javascript" src="${js}/main/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/color.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/demo/demo.css" />
<script src="${libs}/layer/layer.js"></script>
<script type="text/javascript" src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/datagrid-detailview.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/easyui_jgxLoader.js"></script>

<style type="text/css">
</style>

<script type="text/javascript">
	function toCheck(dayStr, srvId) {
		load();
		
		var type  = $("select[name=type] option:selected").val();
		var retStr = "${sendUrl}?dayStr=" + dayStr + "&srvId=" + srvId
				+ "&type=" + type;
		
		$.ajax({
	        url:retStr,
	        type:'get',
	        dataType:'text',
	        success:function(data){
	        	$("#result_info").html(data);
	            disLoad();
	        },
	 		error:function(data)
	 		{
	 			 disLoad();
	 			 $.messager.alert('错误提示',data.responseText,'error');
	 		}
	    });
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
			<label class="laber_from">日期</label> <input class="input_from"
				name="dayTime" id="dayTime" type="date" value="${dayTime}" />
		</div>
		<div class="control-group">
			<label class="laber_from">类型</label>

			<%
				WebUtils.createSelected(pageContext, "type", "input_select", SystemLogParams.getKeys(), 0);
			%>



		</div>


		<div class="control-group">
			<label class="laber_from"></label>
			<div class="controls">
				<button class="btn btn-success" style="width: 120px;"
					onclick="toCheck(getValue('dayTime'),  getValue('srvId'))">查看</button>
			</div>
		</div>


	</div>
	
	<div style="width: 100%;margin: 0 auto; text-align: center;overflow:auto">
	<div id="body2" style="width: 98%;margin: 0 auto;text-align: center;">
	<div id="result_info" />
	</div>
	</div>
<script type = "text/javascript"> 
	<!---rew type 01--->
	$(document).ready(function() {
		var h = document.documentElement.clientHeight;
		$('#body2').css('height', h * 0.5);
    });
</script>
</body>



</html>
