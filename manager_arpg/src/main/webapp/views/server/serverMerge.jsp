<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
$(document).ready(function() {
	
	var h = document.documentElement.clientHeight;		
	$('#body2').css('height', h * 0.9);
});

function onSubmit()
{	
	 var srvId = $('#srvId').combobox('getValue');
	// var subUrl = "${sendUrl}+&srvId="+ srvId;
	  var subUrl = "${sendUrl}";
	   $.ajax({
	         type: "POST",
	         url: subUrl,
	         data:$('#myfrom').serialize(), 
	         async: false,
	         datatype: "json",
	         success: function(data) {
	        	 $.messager.alert("提示",data,"info");
	        	 setTimeout("window.location.reload()",2000); 
	         }
	   		
	     });
	   
}

</script>
</head>
<body>
	<div id="body2" 
	 	style="width: 98%; margin: 0 auto; text-align: center;overflow:auto " >
	 	<div style="width: 90%; text-align: center;">
	 	<h2>服务器合并后，配置主从服务器</h2>
	 	
	 	<form id="myfrom" target="_top">
	 	
	 	<div>
	 		主服务器选择:
        <select name="srvId" id="srvId"  class="easyui-combobox" style="width:150px">
			<option value="-1">--请选择--</option>
			<c:forEach items="${servermerges}" var="servermerge">
			<option  value="${servermerge.getId()}">${servermerge.getId()}-${servermerge.getName()}</option>
			</c:forEach>
		</select>
	 	</div>
	 	<h5>从服务器选择</h5>
	 	<div id="multi_server" name="multi_server" >
			<c:forEach items="${servermerges}" var="servermerge">
			<input type="checkbox" name="servermerge" value="${servermerge.getId()}" style="width:15px;height:15px">${servermerge.getId()}-${servermerge.getName()};&nbsp;
			</c:forEach>
	 	</form>	
	 	
		
		<div class="controls" style="widrh: 100%; margin: 0 auto; text-align: center">
			<button type="button" class="btn btn-success" onclick="onSubmit()" style="width: 120px;">确定</button>
		</div>
		
		
		</div>
	</div>
</body>
</html>
