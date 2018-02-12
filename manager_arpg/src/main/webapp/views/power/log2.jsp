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
	
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/themes/color.css" />
<link rel="stylesheet" type="text/css"
	href="${libs}/easyui/demo/demo.css" />
	
	
<script type="text/javascript" src="${js}/main/jquery.min.js"></script>
<script type="text/javascript" src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/datagrid-detailview.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${js}/easyui_iframe.js"></script>
<script type = "text/javascript">
function onSubmit()
{
	 $.messager.confirm('提醒', '是否查看日志?', function(r){
       if (r){
        	ajaxSub();
        }
     });
	return false;
}

function ajaxSub()
{
	 var srvId = $('#srvId').val();
	 var playerText = $('#playerText').val();
	 var type1 = $('#type1').val();
	 var type2 = $('#type2').val();
	 var startTime= $('#startTime').val();
	 var endTime = $('#endTime').val();
	 var ajaxUrl = "${path}"+'/power/log2/toCheck01?srvId='+srvId+"&playerText="+playerText
			 +"&startTime="+startTime+"&endTime="+endTime+"&mainView=true";
	 var subUrl =  "${path}"+'/power/log2/toCheck?srvId='+srvId+"&playerText="+playerText
	 +"&startTime="+startTime+"&endTime="+endTime+"&mainView=true";
	 
	 $.ajax({
	        type: "get",
	        url: ajaxUrl,
	        datatype: "json",
	        success: function(data) {
	            var obj = eval("[" + data + "]");
	            if (obj.length > 0 && undefined != obj[0].type) {
	              $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
	            }
	  			
	            if (obj.length > 0 && 'info' == obj[0].type) {
	            	center_iframe(subUrl);
	            }
	        },
	    });
	 
	 
	 return false;
}
</script>
</head>
<body>
	<div class="div_from_aoto" style="width: 500px;">
		<form id="myfrom" target="_top">

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
					<!--  
					<input class="input_from" name="playerText" id="playerText"
						type="text" style="width: 250px;" />
					-->
					<%
						WebUtils.createUserText(pageContext, "playerText", "getValue('srvId')");
					%>
					<p class="help-block"></p>
				</div>
				<label class="laber_from"></label>
			</div>

			

			<div class="control-group">
				<label class="laber_from">起始时间</label>
				<div class="controls">
					<input class="input_from" id="startTime" name="startTime" type="datetime-local"
						value="${startTime}" placeholder="yyyy-MM-ddTHH:mm" />
					<p class="help-block"></p>
				</div>
				<label class="laber_from">结束时间</label>
				<div class="controls">
					<input class="input_from" name="endTime" id="endTime" type="datetime-local"
						value="${endTime}" placeholder="yyyy-MM-ddTHH:mm" />
					<p class="help-block"></p>
				</div>
			</div>

			<div class="control-group">
				<label class="laber_from"></label>
				<div class="controls">
					<button class="btn btn-success" onclick="return onSubmit();" style="width: 120px;">查看</button>
				</div>
			</div>

			<div id="result_info" />

		</form>

	</div>


</body>



</html>
