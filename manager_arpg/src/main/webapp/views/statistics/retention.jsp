<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="proto.ProtocolWeb.AccountInfo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- 调用CSS，JS -->
<link href="${css}/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${css}/main/add.css" type="text/css" />
<link rel="stylesheet" href="${res}/utilLib/bootstrap.min.css"type="text/css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/color.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/demo/demo.css" />

<script type="text/javascript" src="${js}/main/jquery.min.js"></script>
<script type="text/javascript" src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${libs}/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="${libs}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${js}/easyui_iframe.js"></script>

<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}

.fitem input {
	width: 160px;
}
</style>
</head>
<body>
	<div class="col-xs-12" style="width: 90%;border: 1px solid #95B8E7;margin-bottom:4px"  >
		<form id="ff" method="post" role="form">
			<div class="row" style="margin: 2px">
				<div class="col-xs-2" align="right">服&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区:</div>
				<div class="col-xs-2" align="left" style="padding-left: 0px;">
				<select name="srvId" id="srvId" class="easyui-combobox" panelHeight="auto" style="width:156px">
			<option value="-1">---------请选择---------</option>
			<c:forEach items="${servers}" var="server">
			<option  value="${server.getId()}">${server.getName()}</option>
			</c:forEach>
		</select>
		</div>
				<!-- <input class="col-xs-2" type="text" name="name"style="height: 24px;" /> -->
				<div class="col-xs-2" align="right">渠&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;道:</div>
				<div class="col-xs-2" align="left" style="padding-left: 0px;">
				<select name="channelId" id="channelId" class="easyui-combobox"  style="width:156px">
			<option value="-1">---------请选择---------</option>
			<c:forEach items="${platforms}" var="platform">
			<option value="${platform}">${platform}</option>
			</c:forEach>
		</select>
		</div>
				<!-- <input class="col-xs-2" type="text" name="email"style="height: 24px;" /> -->
				<div class="col-xs-2" align="right" style="height: 24px;">子渠道:</div>
				<div class="col-xs-2" align="left" style="padding-left: 0px;">
				<select name="channelId" id="channelId" class="easyui-combobox"  style="width:156px">
			<option value="-1">---------请选择---------</option>
			<c:forEach items="${platforms}" var="platform">
			<option value="${platform}">${platform}</option>
			</c:forEach>
		</select>
		</div>
			<!-- 	<input class="col-xs-2" type="text" name="email"style="height: 24px;" /> -->
			</div>
			<div class="row" style="margin: 2px">
				<div  class="col-xs-2" align="right">开始时间:</div>
				<div class="col-xs-2" align="left" style="padding-left: 0px;"><input  id="startdt"name="email"style="height: 24px;width: 156px;" /></div>
				<div class="col-xs-2" align="right">结束时间:</div>
				<div class="col-xs-2" align="left" style="padding-left: 0px;"><input  id="enddt"name="email"style="height: 24px;width: 156px;" /></div>
				<div class="col-xs-4" align="right">
					<button type="submit" class="btn btn-default"style="width: 60px; height: 24px; padding-top: 0px;">查询</button>
				</div>
			</div>

		</form>
	</div>
	<div class="tb">
		<table class="easyui-datagrid" title="充值玩家留存分析表" id="dg"
			style="width: 90%;" toolbar="#toolbar"
			data-options="collapsible:true,method:'get',rownumbers:true,singleSelect:true,pagination:true,url:'${tableUrl}',method:'post'">
			<thead>
				<tr>
					<th data-options="field:'code', width:104">日期</th>
					<th data-options="field:'name', width:104">新增账号</th>
					<th data-options="field:'price', width:104">新增设备</th>
					<th data-options="field:'price', width:104">首付人数</th>
					<th data-options="field:'price', width:104">首付次日存留数</th>
					<th data-options="field:'price', width:104">次日付费人数</th>
					<th data-options="field:'price', width:104">七日付费人数</th>
					<th data-options="field:'price', width:104">首付七日存留数</th>
					<th data-options="field:'price', width:104">新增设备充值人数</th>
					<th data-options="field:'price', width:104">新增设备充值总额</th>
					<th data-options="field:'price', width:104">日活跃</th>
					<th data-options="field:'price', width:104">周活跃</th>
					<th data-options="field:'price', width:104">日活跃付费人数</th>
					<th data-options="field:'price', width:104">日活跃付费率%</th>
					<th data-options="field:'price', width:104">新增设备ARPU</th>
					<th data-options="field:'price', width:104">新增设备ARPPU</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>001</td>
					<td>name1</td>
					<td>2323</td>
				</tr>
				<tr>
					<td>002</td>
					<td>name2</td>
					<td>4612</td>
				</tr>
			</tbody>
		</table>


	</div>

<script type="text/javascript">

var h = document.documentElement.clientHeight;
$('#dg').css('height', h * 0.8);
$('#startdt').datetimebox({
    required: false,
    showSeconds: true
});
$('#enddt').datetimebox({
    required: false,
    showSeconds: true
});
$(function(){
	$('.datagrid-pager.pagination').pagination({
		buttons: ['-',{
			iconCls:'icon-save',
			handler:function(){alert('save')}
		}]
	});
	
});


</script>
</body>
</html>