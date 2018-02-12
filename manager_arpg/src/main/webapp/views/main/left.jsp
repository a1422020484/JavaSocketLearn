<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.saturn.web.controllers.main.dao.Menu"%>
<%@ include file="../index.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />



<link href="${css}/style.css" rel="stylesheet" type="text/css" />
<link href="${res}/index.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/color.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/demo/demo.css" />


<script type="text/javascript" src="${js}/main-left/jquery.min.js"></script>
<script type="text/javascript" src="${js}/main-left/tendina.min.js"></script>
<script type="text/javascript" src="${js}/main-left/common.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/datagrid-detailview.js"></script>


<style type="text/css">
</style>

<script>
	function menu_click(self, names) {

		var menus = document.getElementsByName(names);
		for ( var menu in menus) {
			menu.style.display = "none";
		}

		var menus = document.getElementsByName(names);

		//alert(menus.length);
		return;
	}
</script>

</head>



<body>
	<div class="easyui-panel" style="padding: 5px">
		<ul class="easyui-tree"
			data-options="url:'${libs}/demo/main/tree_data1.json',method:'get',animate:true,lines:true"></ul>
	</div>
	<!-- <table border="0" cellspacing="0" cellpadding="0" style="height: 100%">
		<tr>
			<td width="8" height="8"><img src="${images}/main/index1_23.gif"
				width="8" height="29" /></td>

			<td style="background:url(${images}/main/index1_24.gif)" width="99%"></td>

			<td width="8" height="8"><img src="${images}/main/index1_26.gif"
				width="8" height="29" /></td>
		</tr>
		<tr>
			<td style="background:url(${images}/main/index1_45.gif)"></td>
			<td bgcolor="#FFFFFF"
				style="padding: 0 2px 0 2px; vertical-align: top; height: 96%;">

				<div style="overflow: auto">

					<ul id="menu">
						<%
							
						%>
					</ul>



				</div>

			</td>
			<td style="background:url(${images}/main/index1_47.gif)"></td>
		</tr>
		<tr>
			<td width="8" height="8"><img src="${images}/main/index1_91.gif"
				width="8" height="8" /></td>
			<td style="background:url(${images}/main/index1_92.gif)"></td>
			<td width="8" height="8"><img src="${images}/main/index1_93.gif"
				width="8" height="8" /></td>
		</tr>
	</table> -->



</body>


</html>


