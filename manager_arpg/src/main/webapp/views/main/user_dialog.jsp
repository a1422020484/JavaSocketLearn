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

<script type="text/javascript" src="${js}/main/jquery.min.js"></script>

<title>${titile}</title>


<style type="text/css">
</style>
<script type="text/javascript">
	
</script>
</head>



<body>
	<div class="div_from_aoto" style="width: 300px">
		<form action="${postUrl}" method="post" target="_top">
			<input type="hidden" value="${srvId}" name="srvId" />

			<div class="control-group">
				<label class="laber_from">服务器</label>
				<div class="controls">
					<%
						//读取选择的服务器, 0为默认
						Integer srvId = (Integer) request.getAttribute("srvId");
						srvId = (srvId != null) ? srvId : 0;

						//输出服务器信息
						out.print("<label>");
						out.print(WebUtils.serverStr(srvId));
						out.print("</label>");
					%>

					<p class="help-block"></p>
				</div>
			</div>


			<div class="control-group">
				<label class="laber_from">接受者</label>
				<div class="controls">
					<%
						//生成输入框
						WebUtils.createUserText(pageContext, "playerId", srvId);
					%>
					<p class="help-block"></p>
				</div>
			</div>
			<div class="control-group">
				<label class="laber_from"></label>
				<div class="controls">
					<button class="btn btn-success"
						onclick="javascript:return dialog('是否确定发送?')"
						style="width: 120px;" title="${postUrl}">确定</button>
				</div>
			</div>
		</form>
	</div>
</body>



</html>
