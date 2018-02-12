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
				<td class="showTbA">key</td>
				<td>${key}</td>
				<td class="showTbA">类型</td>
				<td>${type}</td>
				<td class="showTbB">到期时间</td>
				<td>${overTime}</td>
			</tr>
			<tr>
				<td class="showTbB">key类型</td>
				<td>${keyType}</td>
				<td class="showTbA">礼包数量</td>
				<td>${totalNum}</td>
				<td class="showTbB">配置上限</td>
				<td>${useLimit}</td>
			</tr>
            <tr>
                <td class="showTbA">单用户次数</td>
				<td>${useCount}</td>
				<td class="showTbA">使用次数</td>
				<td>${usedNum}</td>
				<td class="showTbA">剩余次数</td>
				<td>${leftNum}</td>
			</tr>
		</table>
	</div>

</body>



</html>
