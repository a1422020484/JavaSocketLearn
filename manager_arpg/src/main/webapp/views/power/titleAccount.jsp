

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="proto.ProtocolWeb.AccountInfo"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
	 	
	 	区服:
        <select name="srvId" id="srvId" class="easyui-combobox"  style="width:100px">
			<option value="-1">--请选择--</option>
			<c:forEach items="${servers}" var="server">
			<option  value="${server.getId()}">${server.getName()}</option>
			</c:forEach>
		</select>
	 	
	 	玩家名字:
        <input name="playerName" id="playerName" class="easyui-textbox" data-options="iconCls:'null'"   style="width:150px"> 
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="select()">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="toAdd()">加入黑名单</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="toAddBan()">封号</a>
    </div>
	<table id="dg" title="全服角色信息" style="width:90%;height:500px" toolbar="#toolbar" 
            data-options="rownumbers:true,singleSelect:true,pagination:true">
        <thead>
            <tr>
                <th data-options="field:'accountId',width:100">账号Id</th>
                <th data-options="field:'srvId',width:100">服务器ID</th>
                <th data-options="field:'srvName',width:100">服务器名字</th>
                <th data-options="field:'playerId',width:100">玩家ID</th>
                <th data-options="field:'playerName',width:100">玩家名字</th>
                <th data-options="field:'playerLv',width:100">玩家等级</th>
            </tr>
        </thead>
    </table>
    
    
    <script type="text/javascript">
        $(function(){
            var pager = $('#dg').datagrid().datagrid('getPager');    // get the pager of datagrid
            
            var pg = $("#dg").datagrid("getPager");  
            if(pg)  
            {  
               $(pg).pagination({  
                   onBeforeRefresh:function(){  
                	   select();  
                },  
                   onRefresh:function(pageNumber,pageSize){  
                	   select();
                    },  
                   onChangePageSize:function(){  
                	   select();  
                    },  
                   onSelectPage:function(pageNumber,pageSize){  
                	   select();
                    }  
               });  
            }  
            
        })
        
         function create(){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','创建维护帐号');
            $('#fm').form('clear');
        }
        
        function toAdd() {
        	
        	 var row = $('#dg').datagrid('getSelected');
        	 
        	 
             if (row){
                 $.messager.confirm('提醒','是否要对玩家加入黑名单',function(r){
                     if (r){
                    	 var deleteUrl = "${toAddUrl}?srvId=" + row.srvId+"&playerId="+row.playerId;
                     	$.ajax({
                	         type: "get",
                	         url: deleteUrl,
                	         datatype: "json",
                	         success: function(data) {
                	             var obj = eval("[" + data + "]");
                	             if (obj.length > 0) {
                	               $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
                	               
                	             }
                	         },
                	     });
                     }
                 });
             }
        }
        
        
        function toAddBan() {
        	
       	 var row = $('#dg').datagrid('getSelected');
       	 
       	 
            if (row){
                $.messager.confirm('提醒','是否要对玩家封号',function(r){
                    if (r){
                   	 var deleteUrl = "${toAddban}?srvId=" + row.srvId+"&playerId="+row.playerId;
                    	$.ajax({
               	         type: "get",
               	         url: deleteUrl,
               	         datatype: "json",
               	         success: function(data) {
               	             var obj = eval("[" + data + "]");
               	             if (obj.length > 0) {
               	               $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
               	               
               	             }
               	         },
               	     });
                    }
                });
            }
       }
        
        
        function select(){
        	
        	  var page = $("#dg" ).datagrid("getPager" ).data("pagination" ).options.pageNumber;
        	  var rows = $("#dg" ).datagrid("getPager" ).data("pagination" ).options.pageSize;
        	
        	var playerName = $('#playerName').textbox('getValue');
        	var srvId = $('#srvId').combobox('getValue');

        	//var $opt = $grid.datagrid('options');
        	// var page = $opt.pageNumber;
        	//var rows = $opt.pageSize;
        	
    		// 通过 ajax 请求数据
    		var urlStr = "${toSelectUrl}?playerName=" + playerName+"&page="+page+"&rows="+rows+"&srvId="+srvId+ "&isSelect=1";
        	
        	
        	 $.ajax({
    	         type: "Post",
    	         url: urlStr,
    	         data:$('#dg').serialize(),
    	         async: false,
    	         datatype: "json",
    	         success: function(data) {
    	        	 var dataJson = $.parseJSON(data);
    	               $("#dg").datagrid("loadData", dataJson);
    	             
    	             
    	         },
    	     });
        }
    </script>

</body>
</html>

