<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="index.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Simple Grid</title>
<!-- grid.simple.min.css, grid.simple.min.js -->
<link rel="stylesheet" href="${libs}/bsgrid/merged/bsgrid.all.min.css" />
<script type="text/javascript"
	src="${libs}/bsgrid/js/lang/grid.zh-CN.min.js"></script>
<script type="text/javascript"
	src="${libs}/bsgrid/merged/bsgrid.all.min.js"></script>
</head>




<body style="background-color: #fff;">
	<table id="searchTable">
		<tr>
			<th w_index="XH" width="5%;">XH</th>
			<th w_index="ID" width="5%;">ID</th>
			<th w_index="CHAR" w_align="left" width="15%;">CHAR</th>
			<th w_index="TEXT" w_align="left" width="30%;">TEXT</th>
			<th w_index="DATE" width="15%;">DATE</th>
			<th w_index="TIME" width="15%;">TIME</th>
			<th w_index="NUM" width="5%;">NUM</th>
			<th w_render="operate" width="10%;">Operate</th>
		</tr>
	</table>
	<script type="text/javascript">
		var gridObj;
		$(function() {
			gridObj = $.fn.bsgrid.init('searchTable', {
				url : '${path}/views/data/json.jsp',
				// autoLoad: false,
				pageSizeSelect : true,
				pageSize : 10
			});
		});

		function operate(record, rowIndex, colIndex, options) {
			return '<a href="#" onclick="alert(\'ID='
					+ gridObj.getRecordIndexValue(record, 'ID')
					+ '\');">Operate</a>';
		}
	</script>
</body>
</html>