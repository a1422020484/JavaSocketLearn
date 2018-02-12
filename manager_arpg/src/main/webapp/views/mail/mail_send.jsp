<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
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
<script type = "text/javascript">
function onSubmit()
{
	 $.messager.confirm('提醒', '是否确定发送?', function(r){
       if (r){
        	ajaxSub();
        }
     });
	return false;
}

function shwoSelectServer(){
	var value = $("#select_server").val();
	if(value==0){
		$("#single_server").hide();
		$("#multi_server").show();
	}else{
		$("#single_server").show();
		$("#multi_server").hide();
	}
}

function ajaxSub()
{
	 var subUrl = "${sendUrl}";
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
	<div id="body2" 
	 	style="width: 98%; margin: 0 auto; text-align: center;overflow:auto " >
	 	<div style="width: 90%; text-align: center;">
		<form id="myfrom" target="_top">
		<input type="hidden" value="${id}" id="id" name="id" />
		<table border="1">
		<tr>
			<td style="background-color: rgb(184, 204, 228)">邮件发送类型</td>
			<td>
			<select name="select_server" id="select_server">
				<option id="0" value="0" <c:if test="${select_server == 0}">selected="true"</c:if> >多服务器</option>
				<option id="1" value="1" <c:if test="${select_server == 1}">selected="true"</c:if>>单服务器</option>
			</select>
			</td>
		</tr>
		</table>
		<br/>
		<!-- 多服务器  -->
		<div id="multi_server" name="multi_server" >
		<table>
		<tr height="100">
			<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
						width="120">配置服务器：</td>
			<td width="75%" height="30px" style="border: rgb(144, 144, 144) solid 1px">
				<% int[] srvIds = (int[]) request.getAttribute("selectSrvIds");
				WebUtils.createServerCheckBoxs(pageContext, "srvIds", "controls", null, srvIds);
				%>
			</td>
			<td style="border: rgb(144, 144, 144) solid 1px">此处为服务器选择，可进行单服选择操作，全服选择操作</td>
		</tr>
		</table>
		<br/>
		</div>
		<!-- 单服务器  -->
		<div id="single_server" name="single_server" >
		<table>	
		<tr >
		<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
						width="120">选择服务器：</td>
		<td><%
			String srvIdStr = (String) request.getAttribute("srvIdStr");
			WebUtils.createServerSelected(pageContext, "srvId", "input_select",srvIdStr);%>
		</td>
		<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
						width="120">全服邮件：<input style="width: 20px; height: 20px" name="allSend"
				type="checkbox" ${allSendStr} />
		</td>
		<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
						width="120">接受者：</td>
		<td><% String recvIdStr = (String) request.getAttribute("recvIdStr");
				WebUtils.createUserText(pageContext, "recvId", "getValue('srvId')", true, recvIdStr);%>
			<p class="help-block"></p>
			<label class="laber_from"></label>
		</td>
		</tr>
		</table>
		<br/>
		</div>
		<table>
		<tr></tr>
		<tr>
		<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
				height="50" width="200">內容</td>
		<td rowspan="2">
		<input name='msgTxt' id='msgTxt' class="easyui-textbox" data-options="multiline:true"
				value="${mailMsg}" style="width: 1044px; height: 80px"></td>
		</tr>
		<tr>
		<td></td>
		</tr>
		</table>
		<br />	
		<table id="dg" title="奖励物品" style="height: 500px" toolbar="#toolbar"
					pagination="true" fitColumns="true" singleSelect="true">
			<thead>
				<tr>
				<th field="item_id" width="50">物品id</th>
				<th field="item_name" width="50">物品名称</th>
				<th field="item_num" width="50">物品数量</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="newItem()">添加</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="destroyItem()">删除</a>
		</div>
		<br/><br/>
		<div class="controls" style="widrh: 100%; margin: 0 auto; text-align: center">
			<button type="submit" class="btn btn-success"
				onclick="return onSubmit();" style="width: 120px;">发送</button>
		</div>
	
		</form>
		
		</div>
	</div>
<script type = "text/javascript"> 
$(document).ready(function() {
	var h = document.documentElement.clientHeight;
	
	 $('#dg').datagrid({
	        url: 'getRewAndReq?id=' + <%=request.getAttribute("id") %>
	    });
	 
	 shwoSelectServer();
	 
	 $('#select_server').change(function(){shwoSelectServer(); });
	
	$('#body2').css('height', h * 0.9);
});

<!-- 选择全服邮件  - -->
$("[name='allSend']:checkbox").on('click', function() {
	var $inputStr = $("#__inputStr")[0];
	var checked = $(this)[0].checked;
	$inputStr.disabled = checked;
});

<!---dg - -->
$(function() {
    $('#dg').datagrid({
        view: detailview,
        detailFormatter: function(index, row) {
            return '<div class="ddv"></div>';
        },
        onExpandRow: function(index, row) {
            var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
            ddv.panel({
                border: false,
                cache: true,
                href: 'showAddCondition',
                onLoad: function() {
                    $('#dg').datagrid('fixDetailRowHeight', index);
                    $('#dg').datagrid('selectRow', index);
                    $('#dg').datagrid('getRowDetail', index).find('form').form('load', row);
                }
            });
            $('#dg').datagrid('fixDetailRowHeight', index);
        }
    });
});

function saveItem(index) {
    var row = $('#dg').datagrid('getRows')[index];
    var url = row.isNewRecord ? 'saveRewAndReq?id=' + <%=request.getAttribute("id") %>:'updateRewAndReq?id=' + <%=request.getAttribute("id") %>+'&row=' + row.id;
    $('#dg').datagrid('getRowDetail', index).find('form').form('submit', {
        url: url,
        onSubmit: function() {
            return $(this).form('validate');
        },
        success: function(data) {
            data = eval('(' + data + ')');
            data.isNewRecord = false;
            $('#dg').datagrid('collapseRow', index);
            $('#dg').datagrid('updateRow', {
                index: index,
                row: data
            });
            
            if ("undefined" != typeof(data["msg"])) 
		     	$.messager.alert("提醒",data["msg"],"error");
	     
        }
    });
}
function cancelItem(index) {
    var row = $('#dg').datagrid('getRows')[index];
    if (row.isNewRecord) {
        $('#dg').datagrid('deleteRow', index);
    } else {
        $('#dg').datagrid('collapseRow', index);
    }
}
function destroyItem() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('Confirm', '你确定要删除这项数据?',
        function(r) {
            if (r) {
                var index = $('#dg').datagrid('getRowIndex', row);
                $.post('destroyRewAndReq', {
                    row: row.id,
                    id: <%=request.getAttribute("id") %>
                },
                function() {
                    $('#dg').datagrid('deleteRow', index);
                });
            }
        });
    }
}
function newItem() {
    $('#dg').datagrid('appendRow', {
        isNewRecord: true
    });
    var index = $('#dg').datagrid('getRows').length - 1;
    $('#dg').datagrid('expandRow', index);
    $('#dg').datagrid('selectRow', index);
} 
</script>
</body>
</html>
