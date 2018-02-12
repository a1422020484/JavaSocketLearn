<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="cn.saturn.web.controllers.statistics.SystemLogParams"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" href="${libs}/bsgrid/merged/bsgrid.all.min.css" />
<script type="text/javascript"
	src="${libs}/bsgrid/js/lang/grid.zh-CN.min.js"></script>
<script type="text/javascript"
	src="${libs}/bsgrid/merged/bsgrid.all.min.js"></script>

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
<script type="text/javascript"
	src="${libs}/easyui/easyui_jgxLoader.js"></script>	
</head>
<body>
	<div id="body2" 
	 	style="width: 90%; margin: 0 auto; text-align: center;overflow:auto " >
	<!--  <div id="bg" class="easyui-datagrid" title="" style="width:100%;height:500px"
            data-options="singleSelect:true,collapsible:true,method:'get',toolbar:'#tb',footer:'#ft'" />
    -->
    
    
	<table id="bg" class="easyui-datagrid" title="" style="width:100%;height:500px" 
            data-options="singleSelect:true,collapsible:true,method:'get',toolbar:'#tb',footer:'#ft'">
        <thead>
           <tr>
            	<th colspan="1">日期</th>
            	<th colspan="2">总计</th>
                <th colspan="2">任务获得</th>
                <th colspan="2">每日登录</th>
                <th colspan="2">活动</th>
            </tr>
            <tr>
            	<th data-options="field:'0',width:100">日期</th>	
            	<th data-options="field:'1',width:70">次数</th>
                <th data-options="field:'2',width:70">水晶</th>
                <th data-options="field:'3',width:70">次数</th>
                <th data-options="field:'4',width:70">水晶</th>
                <th data-options="field:'5',width:70">次数</th>
                <th data-options="field:'6',width:70">水晶</th>
                <th data-options="field:'7',width:70">次数</th>
                <th data-options="field:'8',width:70">水晶</th>
            </tr>
        </thead>
    </table>
    
    <div id="tb" style="padding:2px 5px;">
    	渠道:
    	<select name="channelId" id="channelId" class="easyui-combobox"  style="width:100px">
			<option value="-1">--请选择--</option>
			<c:forEach items="${platforms}" var="platform">
			<option value="${platform}">${platform}</option>
			</c:forEach>
		</select>
        
                    区服:
        <select name="srvId" id="srvId" class="easyui-combobox"  style="width:100px">
			<option value="-1">--请选择--</option>
			<c:forEach items="${servers}" var="server">
			<option  value="${server.getId()}">${server.getName()}</option>
			</c:forEach>
		</select>

                       开始时间:<input class="easyui-datebox" name="startTime" id="startTime" value="${dayTime}" data-options="formatter:myformatter,parser:myparser">       
                        结束时间:<input class="easyui-datebox" name="endTime" id="endTime" value="${dayTime}" data-options="formatter:myformatter,parser:myparser">  
        
        <a href="javascript:void;" class="easyui-linkbutton" iconCls="icon-search" onclick="toSubmit()">查找</a>
    </div>
    <div id="ft" style="padding:2px 5px;">
        <a href="javascript:void;" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="toExport()">导出excel</a>
    </div>
	</div>
	
	
	
	<script type="text/javascript">
	$(document).ready(function() {
		var h = document.documentElement.clientHeight;		
		$('#body2').css('height', h * 0.9);
		$('#bg').css('height',h*0.8);
	});
	
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
	
	function toSubmit() {
		load();
		
		var channelId = $('#channelId').combobox('getValue');
		var srvId = $('#srvId').combobox('getValue');
		var startTime = $('#startTime').datebox('getValue');
		var endTime = $('#endTime').datebox('getValue');
		
		// 通过 ajax 请求数据
		var urlStr = "${sendUrl}&channelId=" + channelId + "&srvId="
				+ srvId + "&startTime=" + startTime 
				+ "&endTime=" + endTime+ "&isSelect=1";
		
		 $.ajax({
		        url:urlStr,
		        type:'get',
		        dataType:'json',
		        success:function(data){
		            $("#bg").datagrid("loadData",data.rows);  //动态取数据
		            disLoad();
		        },
		 		error:function(data)
		 		{
		 			 disLoad();
		 			 $.messager.alert('错误提示',data.responseText,'error');
		 		}
		    });
	}
	
	function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    }
    function myparser(s){
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        var d = parseInt(ss[2],10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
            return new Date(y,m-1,d);
        } else {
            return new Date();
        }
    }
    </script>
</body>
</html>
