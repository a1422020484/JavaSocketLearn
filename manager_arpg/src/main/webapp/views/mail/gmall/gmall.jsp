<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../index.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
	 var subUrl = "${sendUrl}";
	   $.ajax({
	         type: "POST",
	         url: subUrl,
	         data:$('#myfrom').serialize(),
	         async: false,
	         datatype: "json",
	         success: function(data) {
	           $.messager.alert("提示",data,"info");
	         },
	     });
}
</script>
</head>
<body>
	<div id="body2" 
	 	style="width: 98%; margin: 0 auto; text-align: center;overflow:auto " >
	 	<div style="width: 90%; text-align: center;">
		<form id="myfrom" target="_top">
		<!-- 多服务器  -->
		<div id="multi_server" name="multi_server" >
		<table>
		<tr height="100">
			<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
						width="120">配置服务器：</td>
			<td width="75%" height="30px" style="border: rgb(144, 144, 144) solid 1px">
				<% int[] srvIds = (int[]) request.getAttribute("selectSrvIds");
				WebUtils.createServerCheckBoxs(pageContext, "srvIds", "controls", null, srvIds);
				%>
			</td>
			<td style="border: rgb(144, 144, 144) solid 1px">此处为服务器选择，可进行单服选择操作，全服选择操作</td>
		</tr>
		</table>
		<br/>
		</div>
		
		<table>
		<tr></tr>
		<tr>
		<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
				height="50" width="200">內容</td>
		<td rowspan="2">
		<input name='msg' id='msg' class="easyui-textbox" data-options="multiline:true"
				value="${msg}" style="width: 1044px; height: 80px"></td>
		</tr>
		<tr>
		<td></td>
		</tr>
		</table>
		<br />	
		<div class="controls" style="widrh: 100%; margin: 0 auto; text-align: center">
			<button type="submit" class="btn btn-success"
				onclick="return onSubmit();" style="width: 120px;">发送</button>
		</div>
		1. 刷新狩猎场,1全部,2非热点,3热点 <br>
			/refreshHunt type
		
		</form>
		
		</div>
	</div>
<script type = "text/javascript"> 
$(document).ready(function() {
	var h = document.documentElement.clientHeight;
	$('#body2').css('height', h * 0.9);
});
</script>
</body>
</html>
