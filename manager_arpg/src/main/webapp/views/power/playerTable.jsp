<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- 调用CSS，JS -->
<link href="${css}/server/formui.css" rel="stylesheet" type="text/css" />


<style type="text/css">
.showTbA {
	background: #efefef;
	color: #323D53;
	border-style: solid;
}

.showTbB {
	background: #fefefe;
	color: #323D23;
	border-style: solid;
}
</style>

<script type="text/javascript">
	
</script>

</head>



<body>
	<div class="div_from_aoto" style="width: 500px;">
		<table class="tb">
			<tr>
				<td class="showTbA">id</td>
				<td>${id}</td>
				<td class="showTbA">名称</td>
				<td>${name}</td>
				<td class="showTbB">角色等级</td>
				<td>${lv}</td>
			</tr>
			<tr>
				<td class="showTbB">账号ID</td>
				<td>${accountId}</td>
				<td class="showTbA">账号</td>
				<td>${account}</td>
				<td class="showTbA">平台</td>
				<td>${platfrom}</td>
			</tr>
			<tr>
				<td class="showTbA">金钱</td>
				<td>${money}</td>
				<td class="showTbA">钻石</td>
				<td>${crystal}</td>
				<td class="showTbB">经验</td>
				<td>${exp}</td>
			</tr>

			<tr>
				<td class="showTbB">vip等级</td>
				<td>${vip}</td>
				<td class="showTbA">登陆时间</td>
				<td>${loginTime}</td>
				<td class="showTbA"></td>
				<td></td>
			</tr>

		</table>
	</div>

</body>



</html>
