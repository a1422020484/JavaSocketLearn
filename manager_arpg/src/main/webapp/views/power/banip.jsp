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
<link rel="stylesheet" href="${res}/utilLib/bootstrap.min.css" type="text/css" />
	
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/color.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/demo/demo.css" />
		
<script type="text/javascript" src="${js}/main/jquery.min.js"></script>
<script type="text/javascript" src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${libs}/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="${libs}/easyui/locale/easyui-lang-zh_CN.js"></script>
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
	      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openInsertDailog()">新建</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteBanIp()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openUpdateDailog()">编辑</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span> <input id="searchBanIp"></input>
			<div id="mm" style="width: 15px">
				<div data-options="name:'ip',iconCls:'icon-ok'">IP地址</div>
				<div data-options="name:'note',iconCls:'icon-ok'">说明</div>
			</div></span>

	</div>
	<table id="dg" title="IP黑名单" style="width:90%;" toolbar="#toolbar" 
            data-options="rownumbers:true,singleSelect:true,pagination:true,url:'${tableUrl}',method:'post'">
        <thead>
            <tr>
                <th data-options="field:'ip',width:150">IP地址</th>
                <th data-options="field:'note',width:450">说明</th>
            </tr>
        </thead>
    </table>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" data-options="modal:true">
        <div class="ftitle">IP信息</div>
        <form id="fm">
            <div class="fitem">
                <label>IP地址:</label>
                <input name="ip" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>说明:</label>
                <input name="note" class="easyui-textbox">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="insertBanIp()" style="width:90px">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
        
    <div id="dlgedit" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlgedit-buttons" data-options="modal:true">
        <div class="ftitle">IP信息</div>
        <form id="fmedit">
            <div class="fitem">
                <label>IP地址:</label>
                <input name="ip" class="" required="true" readonly="readonly">
            </div>
            <div class="fitem">
                <label>说明:</label>
                <input name="note" class="">
            </div>
        </form>
    </div>
  <div id="dlgedit-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updateBanIp()" style="width:90px">更新</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgedit').dialog('close')" style="width:90px">取消</a>
  </div>
   
    <script type="text/javascript">
        $(function(){
        	 var h = document.documentElement.clientHeight;
        	 $('#dg').css('height', h * 0.9);
            var pager = $('#dg').datagrid().datagrid('getPager');    // get the pager of datagrid
            
            $('#searchBanIp').searchbox({
            	   searcher:function(value,name){
            		   var param = eval("({\"" +name+"\":\"" +value + "\"})");
            		   $('#dg').datagrid('load', param);
            		/*    $('#dg').datagrid('reload');
            	    alert(param) */
            	    },
            	    
            	    menu:'#mm',
            	    width:'300px'
            	});
        })
       

        
         function openInsertDailog(){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增IP地址');
            $('#fm').form('clear');
        }
        
        function openUpdateDailog(){
        	
        	 $('#dlgedit').dialog('open').dialog('center').dialog('setTitle','修改IP地址');
        	 $('#fmedit').form('clear');
        	 var selectedRow = $('#dg').datagrid('getSelected') ;
        	 $('input[name=ip]').val(selectedRow['ip']);
             $('input[name=note]').val(selectedRow['note']);
                 	
        }
        
        function deleteBanIp()
        {
        	var row = $('#dg').datagrid('getSelected');
             
        	 if(!row){
        		 $.messager.confirm('提醒','请选中一行数据！',function(r){
        		if (r){  alert('确定'); }
        			 });
        	 }
             if (row){
                 $.messager.confirm('提醒','是否要删掉这个IP地址？',function(r){
                     if (r){
                    	 var deleteUrl = "${toDeleteUrl}?ip=" + row.ip;
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
        
        function insertBanIp(){
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
        function updateBanIp(){
       	 $.ajax({
   	         type: "get",
   	         url: "${toAddUrl}",
   	         data:$('#fmedit').serialize(),
   	         async: false,
   	         datatype: "json",
   	         success: function(data) {
   	             var obj = eval("[" + data + "]");
   	             if (obj.length > 0) {
   	               $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
   	               $('#dlgedit').dialog('close'); 
   	               $('#dg').datagrid('reload')
   	             }
   	         },
   	     });
       }
       
    </script>

</body>



</html>
