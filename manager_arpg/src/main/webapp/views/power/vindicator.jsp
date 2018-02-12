<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="proto.ProtocolWeb.AccountInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">

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
</head>
<body>
	 <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新建</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
    </div>
	<table id="dg" title="维护帐号" style="width:90%;height:500px" toolbar="#toolbar" 
            data-options="rownumbers:true,singleSelect:true,pagination:true,url:'${tableUrl}',method:'post'">
        <thead>
            <tr>
                <th data-options="field:'accountId',width:100">账号ID</th>
                <th data-options="field:'accountName',width:100">账号</th>
                <th data-options="field:'platfrom',width:100,align:'right'">渠道</th>
                <th data-options="field:'createTime',width:150,align:'right'">创建时间</th>
            </tr>
        </thead>
    </table>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" data-options="modal:true">
        <div class="ftitle">帐号信息</div>
        <form id="fm">
            <div class="fitem">
                <label>账号ID:</label>
                <input name="accountId" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>帐号:</label>
                <input name="accountName" class="easyui-textbox">
            </div>
            <div class="fitem">
                <label>渠道:</label>
                <input name="platfrom" class="easyui-textbox">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
    <script type="text/javascript">
        $(function(){
            var pager = $('#dg').datagrid().datagrid('getPager');    // get the pager of datagrid
        })
        
         function newUser(){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','创建维护帐号');
            $('#fm').form('clear');
        }
        
        function destroyUser()
        {
        	var row = $('#dg').datagrid('getSelected');
        	
        	 var row = $('#dg').datagrid('getSelected');
             if (row){
                 $.messager.confirm('提醒','是否要删掉这个用户？',function(r){
                     if (r){
                    	 var deleteUrl = "${toDeleteUrl}?accountId=" + row.accountId;
                     	$.ajax({
                	         type: "get",
                	         url: deleteUrl,
                	         datatype: "json",
                	         success: function(data) {
                	             var obj = eval("[" + data + "]");
                	             if (obj.length > 0) {
                	               $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
                	               $('#dg').datagrid('reload')
                	             }
                	         },
                	     });
                     }
                 });
             }
        }
        
        function saveUser(){
        	 $.ajax({
    	         type: "get",
    	         url: "${toAddUrl}",
    	         data:$('#fm').serialize(),
    	         async: false,
    	         datatype: "json",
    	         success: function(data) {
    	             var obj = eval("[" + data + "]");
    	             if (obj.length > 0) {
    	               $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
    	               $('#dlg').dialog('close'); 
    	               $('#dg').datagrid('reload')
    	             }
    	         },
    	     });
        }
    </script>

</body>



</html>
