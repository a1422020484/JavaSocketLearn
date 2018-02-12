<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="cn.saturn.web.controllers.power.LogController"%>
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
<script type="text/javascript" src="${js}/easyui_iframe.js"></script>
<script type="text/javascript">
	function toClose(srvId, countDown, closeMsg) {
		//alert("srvId:" + srvId + " countDown:" + countDown + " closeMsg:"+closeMsg);
		var tourl = "${toCloseUrl}?srvId=" + srvId + "&countDown=" + countDown
				+ "&closeMsg=" + closeMsg;
		//alert(tourl);
		ajaxSub(tourl);
		//window.top.location.href = tourl;
	}
	
	function ajaxSub(url)
	{
		 var subUrl = url;
		 $.ajax({
		         type: "get",
		         url: subUrl,
		         async: false,
		         datatype: "json",
		         success: function(data) {
		             var obj = eval("[" + data + "]");
		             if (obj.length > 0) {
		               alert(obj[0].title+obj[0].context);
		               
		               if(obj[0].redUrl!='')
		               	center_iframe(obj[0].redUrl);
		             }
		         }
		     });
	}
</script>
</head>
<body>
<div id="body2" 
	 	style="width: 98%; margin: 0 auto; text-align: center;overflow:auto " >
	 	<div style="width: 90%; text-align: center;">
	<div class="div_from_aoto" style="width: 500px;">

		<div class="control-group">
			<%
				//WebUtils.createLabel(pageContext, "", "服务器");
				//WebUtils.createServerSelected(pageContext, "srvId", "input_select");
				WebUtils.createServerCheckBoxs(pageContext, "srvId", "", null, null);
			%>
		</div>


		<div class="control-group">
			<label class="laber_from">关闭倒计时(s)</label>
			<div class="controls">
				<input class="input_from" name="countDown" id="countDown"
					type="number" onchange="numberInput(this)" value="60"
					style="width: 250px;" />
				<p class="help-block"></p>
			</div>
			<label class="laber_from"></label>
		</div>

		<div class="control-group">
			<label class="laber_from">关闭走马灯提醒</label>
			<div class="controls">
				<input class="input_from" name="closeMsg" id="closeMsg" type="text"
					value="服务器即将关闭." style="width: 250px;" />
				<p class="help-block"></p>
			</div>
			<label class="laber_from"></label>
		</div>


		<div class="control-group">
			<label class="laber_from"></label>
			<div class="controls">
				<button class="btn btn-success" style="width: 120px;"
					onclick="toClose(getValue('srvId'), getValue('countDown'), getValue('closeMsg')) ">关闭</button>
			</div>
		</div>

		<div id="result_info" />

	</div>
</div>
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
