<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="proto.ProtocolWeb.AccountInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- 调用CSS，JS -->
<!-- 
<link rel="stylesheet" href="${css}/main/add.css" type="text/css" />
 -->
<link href="${css}/server/formui.css" rel="stylesheet" type="text/css" />
<script src="${libs}/layer/layer.js"></script>




<link rel="stylesheet" href="${libs}/builds/merged/bsgrid.all.min.css" />
<script type="text/javascript"
	src="${libs}/builds/js/lang/grid.zh-CN.min.js"></script>
<script type="text/javascript"
	src="${libs}/builds/merged/bsgrid.all.min.js"></script>



<style type="text/css">
.div_from_aoto {
	margin-left: auto;
	margin-right: auto;
	padding-left: 10px;
	margin-top: 30px;
}

.control-group {
	padding-bottom: 10px;
}

.laber_from {
	width: 100px;
	float: left;
	line-height: 35px;
	color: #222;
	font-weight: normal;
}

.input_select {
	width: 100px;
	height: 30px;
	cursor: pointer;
}
</style>


<script>
	
</script>


</head>



<body>

	<div class="div_from_aoto" style="width: 500px;">
		<div class="control-group">
			<input type="hidden" value="${curSrvId}" name="srvId" id="srvId" />
			<div class="controls">
				<label class="laber_from">当前服务器:</label> <label class="laber_from">${srvCode}</label>
				<p class="help-block"></p>
			</div>
			<div class="controls">
				<%
					WebUtils.createServerSelected(pageContext, "selectSrvId",
							"input_select");
				%>
				<button class="btn btn-success" style="width: 120px;"
					onclick="window.top.location.href='${path}/power/banned/list?srvId='+getValue('selectSrvId')">切换</button>

				<p class="help-block"></p>
			</div>
		</div>
	</div>

	<div id="server-content"></div>

	<table id="searchTable" style="width: 98%">
		<!-- 表头部分 -->
		<tr>
			<th w_index="accountId" width="5%;">账号ID</th>
			<th w_index="accountName" width="5%;">账号</th>
			<th w_render="operate" width="5%;">编辑</th>
		</tr>
	</table>

	<script type="text/javascript">
		var gridObj;
		$(function() {
			gridObj = $.fn.bsgrid.init('searchTable', {
				url : "${tableUrl}",
				// autoLoad: false,
				dataType : 'json',
				pageSizeSelect : true,
				pageSize : 10
			});
		});

		function operate(record, rowIndex, colIndex, options) {
			return '<a href="#" onclick="alert(\'accountId='
					+ gridObj.getRecordIndexValue(record, 'accountId')
					+ '\');">Operate</a>';
		}
	</script>

</body>



</html>
