<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../index.jsp"%>
<%@ page import="proto.Protocol.PlayerInfo"%>
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
<style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
        .fitem input{
            width:160px;
        }
    </style>

<script type="text/javascript">
$(document).ready(function() {
	changeServer();
});
	function changeServer()
	{
		var text = $('#selectSrvId').find("option:selected").val();
		var urlStr = '${path}/notice/blacklist/listJson' +'?srvId='+text;
		$('#curSrvId').text(text);
		$('#srvCode').text($('#selectSrvId').find("option:selected").name);
	
		$.ajax({
	        type: "POST",
	        url: urlStr,
	        datatype: "json",
	        success: function(data) {
	            var obj = eval("[" + data + "]");
	            if (obj.length > 0 && undefined != obj[0].type) {
	              $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
	            }
	  			
	            if (obj.length > 0 && undefined == obj[0].type) {
	            	var obj2 = eval('(' + data + ')');
	            	$('#dg').datagrid('loadData', obj2);
	            }
	        },
	    });
	}
</script>


</head>



<body>
	<div class="div_from_aoto" style="width: 500px;">
		<div class="control-group">
			<input type="hidden" value="${curSrvId}" name="srvId" id="srvId" />
			<div class="controls">
				<label class="laber_from">当前服务器:</label> <label id="srvCode" >${srvCode}</label>
				<p class="help-block"></p>
			</div>
			<div class="controls">
				<%
					WebUtils.createServerSelected(pageContext, "selectSrvId", "input_select");
				%>
				<button class="btn btn-success" style="width: 120px;"
					onclick="changeServer();">切换</button>

				<p class="help-block"></p>
			</div>
		</div>
	</div>
	<div id="body2" style="width: 98%;margin: 0 auto;text-align: center;">
	<div id="server-content">
	 <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
    </div>
	<table id="dg" title="黑名单列表" style="width:90%;height:500px" toolbar="#toolbar" 
            data-options="rownumbers:true,singleSelect:true,method:'post'">
        <thead>
            <tr>
                <th data-options="field:'id',width:100">玩家ID</th>
                <th data-options="field:'name',width:100">玩家名称</th>
                <th data-options="field:'lv',width:100">玩家等级</th>
                <th data-options="field:'vip',width:150">vip等级</th>
                 <th data-options="field:'fight',width:150">战斗力</th>
            </tr>
        </thead>
    </table>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" data-options="modal:true">
        <div class="ftitle">添加黑名单</div>
        <form id="fm">
            <div id="srvIdDiv" class="fitem">
            </div>
            <div class="fitem">
                <label>接受者：</label>
                <input name="playerId" class="easyui-textbox">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">添加</a>
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

<script type="text/javascript">
        $(function(){
            var pager = $('#dg').datagrid().datagrid('getPager');    // get the pager of datagrid
        })
        
         function newUser(){
        	
        	var srvText = $('#selectSrvId').find("option:selected").text();
        	$('#srvIdDiv').html("服务器："+srvText);
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','添加黑名单');
            $('#fm').form('clear');
        }
        
        function destroyUser()
        {
        	 var row = $('#dg').datagrid('getSelected');
             if (row){
                 $.messager.confirm('提醒','是否要删掉这个用户？',function(r){
                     if (r){
                    	 var srvId = $('#selectSrvId').find("option:selected").val();
                    	 var deleteUrl = '${path}/notice/blacklist/toRemove?playerId=' + row.id + "&srvId=" + srvId;
                     	$.ajax({
                	         type: "get",
                	         url: deleteUrl,
                	         datatype: "json",
                	         success: function(data) {
                	             var obj = eval("[" + data + "]");
                	             if (obj.length > 0 && obj[0].type!= 'undefined') {
                	               $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
                	               $('#dg').datagrid('reload');
                	               $('#dlg').dialog('close');
                	               changeServer();
                	             }
                	         },
                	     });
                     }
                 });
             }
        }
        
        function saveUser(){
        	var srvId = $('#selectSrvId').find("option:selected").val();
        	var addUrl = '${path}/notice/blacklist/toAdd?srvId='+srvId;
        	 $.ajax({
    	         type: "POST",
    	         url: addUrl,
    	         data:$('#fm').serialize(),
    	         async: false,
    	         datatype: "json",
    	         success: function(data) {
    	             var obj = eval("[" + data + "]");
    	             if (obj.length > 0) {
    	               $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
    	               if(obj[0].type=='info')
    	               {	
    	               	$('#dlg').dialog('close'); 
    	               	changeServer();
    	               }
    	             }
    	         },
    	     });
        }
    </script>
</body>
</html>
