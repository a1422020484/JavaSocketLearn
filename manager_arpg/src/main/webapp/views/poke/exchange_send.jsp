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
	 $.messager.confirm('提醒', '是否更新闪光精灵配置到服务器?', function(r){
       if (r){
        	ajaxSub();
        }
     });
	return false;
}

function ajaxSub()
{
	var checkedItems = $('#dg').datagrid('getChecked');
	var name = "0";
	$.each(checkedItems, function(index, item){
		name = item.pokeId;
	});  
	
	 var subUrl = "toSend?pokeId="+name;
	   $.ajax({
	         type: "POST",
	         url: subUrl,
	         data:$('#myfrom').serialize(),
	         async: false,
	         datatype: "json",
	         success: function(data) {
	             var obj = eval("[" + data + "]");
	             if (obj.length > 0) {
	               $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
	               if(obj[0].redUrl!='')
		               	center_iframe(obj[0].redUrl);
	             }
	         },
	     });
}
</script>
</head>
<body>
<h2>选择发送闪光精灵配置</h2>
<div id="body2" style="width: 98%; margin: 0 auto; text-align: center;overflow:auto " >
	<div style="width: 90%; text-align: center;"> 
	<form id="myfrom" name="myfrom">
	<table>
		<tr height="100">
		<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
		width="120">配置服务器：</td>
		<td width="75%" height="30px"
			style="border: rgb(144, 144, 144) solid 1px">
		<% int[] srvIds = (int[]) request.getAttribute("selectSrvIds");
		   WebUtils.createServerCheckBoxs(pageContext, "srvIds", "controls", null, srvIds);
		%>
		</td>
		<td style="border: rgb(144, 144, 144) solid 1px">此处为服务器选择，可进行单服选择操作，全服选择操作</td>
		</tr>
	</table>
	</form>
	<br /><br />
	<table id="dg" title="热点精灵" style="height: 500px" 
	fitColumns="true"  data-options="rownumbers:true,singleSelect:true">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th field="pokeId" width="50">闪光交换id</th>
				<th field="pokeDescription" width="50">闪光交换描述</th>
			</tr>
		</thead>
 	</table>
	<br /> <br />
	<div class="control-group"
		style="widrh: 100%; margin: 0 auto; text-align: center">
	<button type="submit" class="btn btn-success"
		onclick="return onSubmit();" style="width: 120px;">发送</button>
	</div>
</div>
</div>
<script type = "text/javascript"> 
	$(document).ready(function() {
		var h = document.documentElement.clientHeight;
		$('#body2').css('height', h * 0.9);
		
		var selectId = $('#type').find('option:selected').attr('id');
	    $('#dg').datagrid({
	        url: 'getGrid2EasyUi',
	        singleSelect:true,
	        selectOnCheck:true,
	        checkOnSelect:true,
	    });
	});

	function refTable() {
	    var row = $('#dg').datagrid('getRows');
	    // 修改后的数据放到
	    $('#dg').datagrid('loadData', row);
	}
</script>
</body>
</html>
