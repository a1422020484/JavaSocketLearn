<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="proto.ProtocolWeb.AccountInfo"%>
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
	var urlStr = '${path}/power/banned/listJson' +'?srvId='+text;
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
            	//$('#dg').datagrid('url',{u});
            }
        },
    });
	
	return false;
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
	<div id="server-content">
	 <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
    </div>
	<table id="dg" class="easyui-datagrid" title="封号列表" style="width:90%;height:500px" toolbar="#toolbar" 
            data-options="rownumbers:true,singleSelect:true">
        <thead>
            <tr>
                <th data-options="field:'account',width:100">账号</th>
                 <th data-options="field:'playerId',width:100">角色id</th>
                <th data-options="field:'playerName',width:100">角色名</th>
                <th data-options="field:'lv',width:150,align:'right'">等级</th>
            </tr>
        </thead>
    </table>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" data-options="modal:true">
        <div class="ftitle">添加黑名单</div>
        <form id="fm">
            <div id="srvIdDiv" class="fitem">
                <label>服务器：</label>
                <input name="accountId" class="easyui-textbox" >
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
                 $.messager.confirm('提醒','是否要把这个用户移除黑名单？',function(r){
                     if (r){
                    	 var srvId = $('#selectSrvId').find("option:selected").val();
                    	 var deleteUrl = '${path}/power/banned/toRemove?playerId=' + row.playerId + "&srvId=" + srvId;
                     	$.ajax({
                	         type: "get",
                	         url: deleteUrl,
                	         datatype: "json",
                	         success: function(data) {
                	             var obj = eval("[" + data + "]");
                	             if (obj.length > 0 && undefined != obj[0].type) {
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
        	var addUrl = '${path}/power/banned/toAdd?srvId='+srvId;
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
