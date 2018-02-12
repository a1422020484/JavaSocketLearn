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
<script type="text/javascript" src="${libs}/dist/jquery.table2excel.js"></script>
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
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteClockWhite()">删除</a>
    <div id="ft" style="padding:2px 5px;">
        <a href="javascript:void;" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="toExport()">导出excel</a>
    </div>
	</div>
	<table id="dg" class="datagrid-view" title="报警白名单" style="width:90%;" toolbar="#toolbar" 
            data-options="rownumbers:false,singleSelect:true,pagination:true,url:'${tableUrl}',method:'post'">
        <thead class = "noExl">
            <tr>
                <th data-options="field:'id',width:70">id</th>
                <th data-options="field:'accountid',width:150">账号id</th>
                <th data-options="field:'roleid',width:100">角色id</th>
                <th data-options="field:'playername',width:150">玩家名字</th>
                <th data-options="field:'level',width:70">等级</th>
                <th data-options="field:'viplevel',width:70">vip等级</th>
                <th data-options="field:'todcrystal',width:80">水晶</th>
                <th data-options="field:'gold',width:80">金币</th>
                <th data-options="field:'srvid',width:70">服务器id</th>
            </tr>
        </thead>
    </table>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" data-options="modal:true">
        <div class="ftitle">白名单信息</div>
        <form id="fm">
            <div class="fitem">
                <label>服务器Id:</label>
                <input name="srvid" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>角色Id:</label>
                <input name="roleid" class="easyui-textbox">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="insertclockwhite()" style="width:90px">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
        
    <div id="dlgedit" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlgedit-buttons" data-options="modal:true">
        <div class="ftitle">白名单信息</div>
        <form id="fmedit">
            <div class="fitem">
                <label>:</label>
                <input name="roleid" class="" required="true" readonly="readonly">
            </div>
            <div class="fitem">
                <label>玩家名字:</label>
                <input name="playername" class="">
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
            
           
        })
       
        function toExport(){
		$(".datagrid-view").table2excel({
				exclude: ".noExl",
				name: "Excel Document Name",
				filename: "myFileName",
				exclude_img: true,
				exclude_links: true,
				exclude_inputs: true
		});
	}

        
         function openInsertDailog(){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增白名单');
            $('#fm').form('clear');
        }

        function deleteClockWhite()
        {
        	var row = $('#dg').datagrid('getSelected');
             
        	 if(!row){
        		 $.messager.confirm('提醒','请选中一行数据！',function(r){
        		if (r){  alert('确定'); }
        			 });
        	 }
             if (row){
                 $.messager.confirm('提醒','是否要删掉白名单？',function(r){
                     if (r){
                    	 var deleteUrl = "${toDeleteUrl}?id=" + row.id;
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
        
        function insertclockwhite(){
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
