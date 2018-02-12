<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../index.jsp"%>
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

</head>
<body>
<div id="body2" 
	 	style="width: 98%; margin: 0 auto; text-align: center;overflow:auto " >
	 	<div style="width: 90%; text-align: center;">
		<form id="myfrom" target="_top">
			<input type="hidden" value="${id}"  id="id" name="id" />
			<table border="1">
				<tr>
					<td style="background-color: rgb(184, 204, 228);" width="80">礼包名称:</td>
					<td><input class="input_from" name="name" id="name" value="${name}" style="width: 80px;"type="text" /></td>
					<td style="background-color: rgb(184, 204, 228);" width="80">礼包数量:</td>
					<td><input class="input_from" name="num" id="num" value="${num}" style="width: 80px;"type="text" /></td>
					
					</td>
					<td style="background-color: rgb(184, 204, 228);" width="80">礼包类型:</td>
					<td>
					 <input class="easyui-numberbox" name="type" id="type" value="${type}" style="width:100%;">
					</td>
					<td style="background-color: rgb(184, 204, 228);" width="80">礼包id:</td>
					<td><input class="input_from" name="gift_id" id="gift_id" value="${gift_id}" style="width: 80px;"type="text" /></td>
				
				</tr>
			</table>
			<br/><br/>
			<br/>
			<table id="dg" title="礼包奖励内容"  style="height: 500px" toolbar="#toolbar"
			pagination="true" fitColumns="true" singleSelect="true">
				<thead>
					<tr>
					<th field="id" width="50">序号</th>
					<th field="item_id" width="50">物品id</th>
					<th field="item_name" width="50">物品名称</th>
					<th field="item_num" width="50">物品数量</th>
				</thead>
			</table>
			<div id="toolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="newItem('#dg')">添加</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="destroyItem('#dg','delAward')">删除</a>
			</div>	
			<br/><br/>
			<div class="control-group"
				style="widrh: 100%; margin: 0 auto; text-align: center">
				<button type="submit" class="btn btn-success"
					onclick="return onSubmit();" style="width: 120px;">创建</button>
			</div>
		</form>
		</div>
	</div>
<script type="text/javascript">
$(document).ready(function() {
	var h = document.documentElement.clientHeight;
	$('#body2').css('height', h * 0.9);

	
});



function onSubmit()
{
	 $.messager.confirm('提醒', '是否发送?', function(r){
       if (r){
        	ajaxSub();
        }
     });
	return false;
}

function ajaxSub(){
	var subUrl = "${toEdit}?mainView=true";
	$.ajax({
	  type: "POST",
	  url: subUrl,
	  data:$('#myfrom').serialize(),
	  async: false,
	  datatype: "json",
	  success: function(data) {
	    var obj = eval("[" + data + "]");
	    console.log(obj);
	    if (obj.length > 0) {
	    	$.messager.alert(obj[0].title,obj[0].context,obj[0].type);
	    }
	  },
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

function easyUiLoadGrid(item,url){
	 $(item).datagrid({
	        view: detailview,
	        detailFormatter: function(index, row) {
	            return '<div class="ddv"></div>';
	        },
	        onExpandRow: function(index, row) {
	            var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
	            ddv.panel({
	                border: false,
	                cache: true,
	                href: url,
	                onLoad: function() {
	                    $(item).datagrid('fixDetailRowHeight', index);
	                    $(item).datagrid('selectRow', index);
	                    $(item).datagrid('getRowDetail', index).find('form').form('load', row);
	                }
	            });
	            $(item).datagrid('fixDetailRowHeight', index);
	        }
	    });
}

$(function() {
	<!--- 非30号转盘类型 活动物品奖励,以及条件 --->
    $('#dg').datagrid({
        url: 'getRewAndReq?id=' + <%=request.getAttribute("id") %>
    });
    
	easyUiLoadGrid('#dg','showAward');
});

function saveItem(index,item,saveUrl,updateUrl) {
    var row = $(item).datagrid('getRows')[index];
    var selectId = $('#speArgType').find('option:selected').attr('id');
    var url = row.isNewRecord ? saveUrl+'?id=' + <%=request.getAttribute("id") %>+"&type="+selectId: updateUrl+'?id=' + <%=request.getAttribute("id") %>+'&row=' + row.id+"&type="+selectId;
    $(item).datagrid('getRowDetail', index).find('form').form('submit', {
        url: url,
        onSubmit: function() {
            return $(this).form('validate');
        },
        success: function(data) {
            data = eval('(' + data + ')');
            data.isNewRecord = false;
            $(item).datagrid('collapseRow', index);
            $(item).datagrid('updateRow', {
                index: index,
                row: data
            });
            
            if ("undefined" != typeof(data["msg"])) 
		     	$.messager.alert("提醒",data["msg"],"error");
	     
        }
    });
}
function cancelItem(index,item) {
    var row = $(item).datagrid('getRows')[index];
    if (row.isNewRecord) {
        $(item).datagrid('deleteRow', index);
    } else {
        $(item).datagrid('collapseRow', index);
    }
}
function destroyItem(item,delUrl) {
    var row = $(item).datagrid('getSelected');
    if (row) {
        $.messager.confirm('Confirm', '你确定要删除这项数据?',
        function(r) {
            if (r) {
                var index = $(item).datagrid('getRowIndex', row);
                $.post(delUrl, {row: row.id,id: <%=request.getAttribute("id") %>},
                function() {
                    $(item).datagrid('deleteRow', index);
                });
            }
        });
    }
}
function newItem(item) {
    $(item).datagrid('appendRow', {
        isNewRecord: true
    });
    var index = $(item).datagrid('getRows').length - 1;
    $(item).datagrid('expandRow', index);
    $(item).datagrid('selectRow', index);
} 
</script>
</body>
</html>
