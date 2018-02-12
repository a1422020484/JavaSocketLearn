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


<style type="text/css">
</style>

<script type="text/javascript">
	function toCheck(srvId, playerText) {
		var retStr = callJava("${path}/power/info/toCheck?srvId=" + srvId
				+ "&playerText=" + playerText, null);

		//alert("srvId:" + srvId + " name:" + name + " " + retStr);
		//$('#result').html(retStr);
		var view = $("#result_info")
		view.html(retStr);
		
		var h = document.documentElement.clientHeight;
		$('#body2').css('height', h * 0.9);
	}
</script>

</head>



<body>
	<div class="div_from_aoto" style="width: 500px;">
		<div class="control-group">
			<label class="laber_from">服务器</label>
			<div class="controls">
				<%
					WebUtils.createServerSelected(pageContext, "srvId", "input_select");
				%>

			</div>
		</div>

		<div class="control-group">
			<label class="laber_from">接受者</label>
			<div class="controls">
				<input class="input_from" name="playerText" id="playerText" type="text"
					style="width: 250px;" />
				<p class="help-block"></p>
			</div>
				<label class="laber_from" ></label>
		</div>



		<div class="control-group">
			<label class="laber_from"></label>
			<div class="controls">
				<button class="btn btn-success" style="width: 120px;"
					onclick="toCheck(getValue('srvId'), getValue('playerText')) ">查看</button>
			</div>
		</div>
	</div>
	
	<div id="body2" style="width: 90%;margin: 0 auto;text-align: center;">
		<div id="result_info" />
	</div>
<script type = "text/javascript"> 
	<!---rew type 01--->
	$(document).ready(function() {
		var h = document.documentElement.clientHeight;
		$('#body2').css('height', h * 0.9);
    });
</script>
</body>



</html>
