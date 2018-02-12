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
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/color.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/demo/demo.css" />
<link href="${css}/server/formui.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${js}/main/jquery.min.js"></script>
<script src="${libs}/layer/layer.js"></script>
<script type="text/javascript" src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/datagrid-detailview.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${libs}/dist/jquery.table2excel.js"></script>

<style type="text/css">
</style>
<script type="text/javascript">
	function sendGmCmd() {
		//确定
		$.messager.confirm('info', '是否确定发送?', function(r){
            if(r)
            {
            	var cmd = getValue('cmd');
        		if (cmd == null || cmd.length <= 0) {
        			$.messager.alert('提醒\n','没有内容','error');
        			return;
        		}
        		var url = '${path}/mail/gm/toSend?srvId=${curSrvId}&cmd=' + cmd;
        		//访问数据
        		var retMsg = callJava(url);
        		$.messager.alert('返回消息:\n',retMsg,'info');
            }
        });
	}
	
	
	function changeServer(url)
	{
		var text = $('#selectSrvId').find("option:selected").val();
		var urlStr = url +'?srvId='+text+'&mainView=true';
		center_iframe(urlStr);
	}
</script>

</head>
<body>
	<div style="width: 100%;margin: 0 auto; text-align: center;overflow:auto">
	<div id="body2" style="width: 500px;margin: 0 auto;text-align: center;">
		<div class="control-group" >
			<input type="hidden" value="${curSrvId}" name="srvId" id="srvId" />
			<div class="controls">
				<label class="laber_from">当前服务器:</label> <label class="laber_from">${srvCode}</label>
				<p class="help-block"></p>
			</div>
			<div class="controls">
				<%
					WebUtils.createServerSelected(pageContext, "selectSrvId", "input_select");
				%>
				<button class="btn btn-success" style="width: 120px;"
					onclick="changeServer('${path}/mail/gm/index');">切换</button>

				<p class="help-block"></p>
			</div>
		</div>

		<div class="control-group">
			<div class="controls">
				<label class="laber_from">GM指令</label>
			</div>
			<p class="help-block"></p>
			<div class="controls">

				<!-- 
				<input class="input_from" name="cmd" id="cmd" type="text"
					style="width: 250px;"
					onkeypress="if(event.keyCode==13){sendGmCmd()}" />
 -->

				<textarea rows="5" cols="80" name='cmd' id='cmd'
					onkeypress="if(event.keyCode==13){sendGmCmd()}"
					style="max-width: 500px;"></textarea>
				<p class="help-block"></p>


				<!-- 
				<button class="btn btn-success" style="width: 120px;" type="submit"
					onclick="window.top.location.href='${path}/mail/gm/toSend?srvId=${curSrvId}&cmd='+getValue('cmd')">发送</button>
				-->
				<p class="help-block"></p>
				<button class="btn btn-success" style="width: 120px;" type="submit"
					onclick="sendGmCmd()">发送</button>


				<p class="help-block"></p>
			</div>
			<label class="laber_from"></label>
		</div>

		<div class="control-group">
			<label>GM指令列表</label>
			<p>${cmdText}</p>
			<p class="help-block"></p>
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
