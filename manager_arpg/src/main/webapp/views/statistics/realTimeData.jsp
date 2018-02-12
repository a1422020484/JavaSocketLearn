<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<script type="text/javascript"
	src="${libs}/easyui/easyui_jgxLoader.js"></script>	
<script type = "text/javascript">
$(document).ready(function() {
	function setTabs(){
    	$('#tt').tabs({
        plain: true,
        narrow: false,
        pill: false,
        justified: false
    })}
});

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
</head>
<body>
    <div style="margin:20px 0 10px 0;"></div>
             开始时间:<input class="easyui-datebox" name="startTime" id="startTime" value="${dayTime}" data-options="formatter:myformatter,parser:myparser">&nbsp;      
             结束时间:<input class="easyui-datebox" name="endTime" id="endTime" value="${dayTime}" data-options="formatter:myformatter,parser:myparser">&nbsp;  
	<a href="javascript:void;" class="easyui-linkbutton" onclick="toSubmit()">确定</a>
	<div style="margin:10px 0 10px 0;"></div>
	<div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyLog()">删除</a>
    </div>
    <div class="easyui-panel" title="时实数据" style="width:100%;height:500px;" data-options="footer:'#ft',tools:'#t1'">
    <div id="tt" class="easyui-tabs" style="width:100%;height:100%">
        <div title="新增" style="padding:10px">
            <table id="bg0" class="easyui-datagrid" title="" style="width:100%;height:100%"
            data-options = "singleSelect:true,
                remoteSort:false,
                multiSort:true" toolbar="#toolbar">
    		</table>
        </div>
        <div title="活跃" style="padding:10px">
            <table id="bg1" class="easyui-datagrid" title="" style="width:100%;height:100%"
            data-options="singleSelect:true,
                remoteSort:false,
                multiSort:true" toolbar="#toolbar">
    		</table>
        </div>
        <div title="留存" style="padding:10px">
             <table id="bg2" class="easyui-datagrid" title="" style="width:100%;height:100%"
            data-options="singleSelect:true,
                remoteSort:false,
                multiSort:true" toolbar="#toolbar">
    		</table>
        </div>
    </div>
    </div>
    <div id="ft" style="padding:5px;">
        Footer Content.
    </div>
    
    <div id="t1">
        <a id="save" href="javascript:void(0)" class="icon-save"  onclick="onCut()"></a>
        <a id="help" href="javascript:void(0)" class="icon-help"></a>
    </div>
    
    
 <script type = "text/javascript">   
    	$('#help').tooltip({
    	position: 'right',
    	showEvent: 'click',
        content: '<span style="color:#fff">平台日志.</span>',
        onShow: function(){
            $(this).tooltip('tip').css({
                backgroundColor: '#666',
                borderColor: '#666'
            });
        }});
    	
    	
    	$(function(){
			$('#tt').tabs({
				onUpdate:function(title,index){
					alert(title+" "+index);
				},
				onSelect:function(title,index){
					loadData(index);
				}
			});
			
			var tab = $('#tt').tabs('getSelected');
			var index = $('#tt').tabs('getTabIndex',tab);
			loadData(index);
			
        });
    	
    	function loadData(index){
    		load();	
			
			var startTime = $('#startTime').datebox('getValue');
			var endTime = $('#endTime').datebox('getValue');
			
			// 通过 ajax 请求数据
			var urlStr = "${sendUrl}/getList?type=" + (index+1) + "&startTime=" + startTime + "&endTime=" + endTime;
			
			$.ajax({
		        url:urlStr,
		        type:'Post',
		        dataType:'json',
		        success:function(data){
		        	var bgid = "#bg" + index;
		            $(bgid).datagrid({
		                columns:[data.title]  
		            });
		            $(bgid).datagrid("loadData",data.rows);  //动态取数据
		            
		        },
		 		error:function(data)
		 		{
		 			$.messager.alert('错误提示',data.responseText,'error');
		 		}
		    });
			
			 disLoad();
    	}
    	
    	function toSubmit() {
    		var tab = $('#tt').tabs('getSelected');
			var index = $('#tt').tabs('getTabIndex',tab);
			loadData(index);
    	}
    	
    	function destroyLog()
        {	
    		var tab = $('#tt').tabs('getSelected');
			var index = $('#tt').tabs('getTabIndex',tab);
			var bgid = "#bg" + index;
        	var row = $(bgid).datagrid('getSelected');
        	console.log(row);
             if (row){
                 $.messager.confirm('提醒','是否要移除日志？',function(r){
                     if (r){
                    	 var deleteUrl = '${sendUrl}/toRemove?id=' + row[0];
                     	$.ajax({
                	         type: "POST",
                	         url: deleteUrl,
                	         datatype: "text",
                	         success: function(data) {
                	            $.messager.alert('提醒',data,'info');
                	            loadData(index);
                	         },
                	     });
                     }
                 });
             }
        }
</script>
</body>
</html>
