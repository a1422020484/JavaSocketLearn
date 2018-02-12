<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.saturn.web.controllers.server.dao.ServerModel"%>
<%@ page import="cn.saturn.web.utils.AuthorityUtils"%>
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



<script type="text/javascript">
	function toCheck(dayStr, platform) {
		var retStr = callJava("${sendUrl}?dayStr=" + dayStr + "&platform="
				+ platform);
		//alert("retStr:" + retStr);

		var view = document.getElementById("result_info")
		view.innerHTML = retStr;
	}
</script>

</head>



<body>
	<div class="div_from_aoto" style="width: 500px;">
		<div class="control-group">
			<div class="controls">
				<input class="input_from" name="dayTime" id="dayTime" type="date"
					value="${dayTime}" />

				<!-- 
					<input class="input_from" name="platform"
					style="width: 80px;" id="platform" type="text" value="${platform}" />
 -->
				<button class="btn btn-success"
					onclick="toCheck(getValue('dayTime'), getValue('platform'))"
					style="width: 120px;">查看</button>
			</div>
		</div>
	</div>
	<div id="result_info" />
</body>



</html>
