<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
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
</head>
<body>
	<div id="body2" style="width: 98%; margin: 0 auto; text-align: center;overflow:auto " >
	<div style="width: 90%; text-align: center;">
	<div>
	<table>
	<tr>
	<td>
	<label class="laber_from"  style="width:80px"> 请选择活动：  </label>
	 <select name="id" id="id" style="width:300px">
		<option value="-1">--请选择--</option>
		<c:forEach items="${presetModel}" var="model">
		<option  value="${model.getId()}">${model.getId()} —— ${model.getTital()}</option>
		</c:forEach>
	</select>
	<input class="btn btn-success" type="button" style="width:80px" onclick="onCheck();" value='提交'></input>
	</td>
	</tr>
	</table>
	</div>
	<br />
	<table id="dg" title="服务器上活动" style="height: 500px" 
	pagination="true" fitColumns="true" singleSelect="true">
		<thead>
		<tr>
		<th field="id" width="50">服务器id</th>
		<th field="name" width="50">服务器名称</th>
		<th field="a_id" width="50">活动id</th>
		<th field="a_type" width="50">活动类型</th>
		<th width="50" data-options="field:'startTime',width:80,align:'right',formatter:formatDate">开始时间</th>
		<th data-options="field:'endTime',width:80,align:'right',formatter:formatDate">结束时间</th>
		</tr>
		</thead>
	</table>
	</div>
	</div>
<script type="text/javascript">
	$(document).ready(function() {
		var h = document.documentElement.clientHeight;
		$('#body2').css('height', h * 0.9);
		
		$('#dg').datagrid({url: 'listJsonByActivityId?id=-1'});
	});
	
	function onCheck()
	{
		var id = $("#id").val();
		$('#dg').datagrid({url: 'listJsonByActivityId?id=' + id});
	}
	
	 function formatDate(val,row){
		 
		 var nowDate = new Date();
		 var oldDate = new Date(val);
		 var dDate = oldDate.getTime() - nowDate.getTime();
         if (dDate < 0){
             return '<span style="color:red;">('+val+')</span>';
         } else {
             return val;
         }
     }
</script>
</body>
</html>
