<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- 调用CSS，JS -->
<link href="${css}/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${css}/main/add.css" type="text/css" />
<link rel="stylesheet" href="${res}/utilLib/bootstrap.min.css"
	type="text/css" />

<style type="text/css">
</style>

</head>



<body>

	<div class="div_from_aoto" style="width: 500px;">
		<form action="${path}/mail/toSend" method="post" target="_top">
			<div class="control-group">
				<label class="laber_from">服务器</label>
				<div class="controls">
					<%
						WebUtils.createServerSelected(pageContext, "srvCode", "input_select");
					%>
					<!-- 
					<select class="input_select" onclick="javascript:return callJava('${path}/server/toSelectServer')">
						<option selected="selected">董事长</option>
						<option>总经理</option>
						<option>经理</option>
						<option>主管</option>
					</select>
					 -->
				</div>
			</div>

			<div class="control-group">
				<label class="laber_from">发送内容</label>
				<div class="controls">
					<input class="input_from" name="recvId" type="text"
						value="${srvname}" />
					<p class="help-block"></p>
				</div>
				<label class="laber_from"></label>
			</div>

			<div class="control-group">
				<label class="laber_from"></label>
				<div class="controls">
					<button class="btn btn-success"
						onclick="javascript:return dialog('是否确定发送?')"
						style="width: 120px;">发送</button>
				</div>
			</div>
		</form>


	</div>


</body>



</html>
