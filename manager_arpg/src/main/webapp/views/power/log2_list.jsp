<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="proto.ProtocolWeb.AccountInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- 调用CSS，JS -->
<script src="${libs}/layer/layer.js"></script>


<link rel="stylesheet" href="${libs}/bsgrid/merged/bsgrid.all.min.css" />
<script type="text/javascript"
	src="${libs}/bsgrid/js/lang/grid.zh-CN.min.js"></script>
<script type="text/javascript"
	src="${libs}/bsgrid/merged/bsgrid.all.min.js"></script>



<style type="text/css">
</style>





</head>



<body>


	<table id="searchTable" style="width: 98%">
		<!-- 表头部分 -->
		<tr>
			<th w_index=id width="5%;">记录ID</th>
			<th w_index=playerId width="5%;">玩家ID</th>
			<th w_index="playerName" width="5%;">玩家名称</th>
			<th w_index="type1" width="5%;">类型1</th>
			<th w_index="type2" width="5%;">类型2</th>
			<th w_index="logTime" width="5%;">记录时间</th>
			<th w_index="crysta" width="5%;">钻石数</th>
			<th w_index="gold" width="5%;">金币数</th>
			<th w_index="action_power" width="5%;">体力</th>
			<th w_index="level" width="5%;">等级</th>
			<th w_index="vip_level" width="5%;">vip等级</th>
			<th w_index="content" >内容</th>
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
				pageSize : 10,
				displayBlankRows : false, //是否显示空白行
				//stripeRows : true, //隔行变色
				pageIncorrectTurnAlert : false, //是否提示无法翻页提示
			});
		});
	</script>

</body>



</html>
