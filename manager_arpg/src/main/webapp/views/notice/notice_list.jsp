<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.saturn.web.controllers.server.dao.ServerModel"%>
<%@ page import="cn.saturn.web.utils.AuthorityUtils"%>
<%@ include file="../index.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- 调用CSS，JS -->
<script src="${libs}/layer/layer.js"></script>

<link rel="stylesheet" href="${res}/utilLib/bootstrap.min.css"
	type="text/css" />
<link rel="stylesheet" href="${libs}/bsgrid/merged/bsgrid.all.min.css" />
<script type="text/javascript"
	src="${libs}/bsgrid/js/lang/grid.zh-CN.min.js"></script>
<script type="text/javascript"
	src="${libs}/bsgrid/merged/bsgrid.all.min.js"></script>
<script type="text/javascript" src="${js}/easyui_iframe.js"></script>


<style type="text/css">
</style>

<script type = "text/javascript">
//获取悬赏的记录
function getCheckedRecords() {
	var records = new Array();
	$('#searchTable tbody tr').each(function() {
		if ($(this).find('td:eq(0)>input:checked').length == 1) {
			records[records.length] = gridObj.getRowRecord($(this));
		}
	});
	return records;
}

function sends(url){
	
	var records = getCheckedRecords();
	var recordCount = records.length;
	if (recordCount <= 0) {
		alert("请选择活动.");
		return;
	}
		
	//ID列表
	var ids = '';
	for (var i = 0; i < recordCount; i++) {
		ids += gridObj.getRecordIndexValue(records[i], 'id') + (i<recordCount-1 ? ',':'');
	}
		
	var subUrl = url +"&ids="+ids;
	console.log(subUrl);
	center_iframe(subUrl);
}


function onSubmit(url)
{
	var isSubmit = dialog('是否确定删除?');
	if(isSubmit)
	{
		ajaxSub(url);
	}
	return false;
}

function ajaxSub(url)
{
	 var subUrl = url;
	 $.ajax({
	         type: "get",
	         url: subUrl,
	         async: false,
	         datatype: "json",
	         success: function(data) {
	             var obj = eval("[" + data + "]");
	             if (obj.length > 0) {
	               alert(obj[0].title+obj[0].context);
	               
	               if(obj[0].redUrl!='')
	               	center_iframe(obj[0].redUrl);
	             }
	         }
	     });
	 setTimeout("window.location.reload()",2000);
}
</script>

</head>



<body>
	<table id="searchTable" style="width: 98%">
		<!-- 表头部分  getCheckedIds()-->
		<tr>
			<th w_render="checkbox" w_index="id" width="1%;"><input
				type="checkbox" /></th>
			<th w_index="id" width="1%;">ID</th>
			<th w_index="s_id" width="5%;">服务器列表</th>
			<th w_index="content" width="5%;">公告内容</th>
			<th w_index="imgs" width="5%;">公告图片</th>
			<th w_index="enable" width="5%;">是否开启</th>
			<th w_render="operate" width="5%;">
			<a href="javascript:void;" target="_top"
				onclick="center_iframe('${addUrl}?mainView=true');">新建</a>
			</th>
		</tr>
	</table>

	<script type="text/javascript">
		var gridObj;
		function onCheck()
		{
			$('#searchTable_pt_outTab').remove();
			$(function() {
				gridObj = $.fn.bsgrid.init('searchTable', {
					url : "${tableUrl}",
					//autoLoad: false,
					dataType : 'json',
					pageSizeSelect : true,
					pageSize : 20,
					displayBlankRows : false, //是否显示空白行
					//stripeRows : true, //隔行变色
					pageIncorrectTurnAlert : false, //是否提示无法翻页提示
				});
			});
		}
	
		
		$(function() {
			gridObj = $.fn.bsgrid.init('searchTable', {
				url : "${tableUrl}",
				// autoLoad: false,
				dataType : 'json',
				pageSizeSelect : true,
				pageSize : 20,
				displayBlankRows : false, //是否显示空白行
				//stripeRows : true, //隔行变色
				pageIncorrectTurnAlert : false, //是否提示无法翻页提示
			});

			//全选操作
			if ($('#searchTable thead tr th:eq(0) input[type=checkbox]').length == 1) {
				$('#searchTable thead tr th:eq(0) input[type=checkbox]').change(function() {var checked = $.bsgrid.adaptAttrOrProp(
					$(this), 'checked') ? true : false;
					$.bsgrid.adaptAttrOrProp($('#searchTable tbody tr td:nth-child(1)>input[type=checkbox]'),'checked', checked);});
			}
		});

		//额外操作界面
		function operate(record, rowIndex, colIndex, options) {
			var id = gridObj.getRecordIndexValue(record, 'id')
			var deleteUrl = "${toDeleteUrl}?id=" + id;
			var editUrl = "${editUrl}?id=" + id +"&mainView=true";
			
			//String
			//sendStr = '<a href="' + sendUrl + '" target="_top" >发送</a>';

			String
			editStr = '<a href="javascript:void;" target="_top" onclick="center_iframe(\''
					+ editUrl
					+ '\');" >编辑</a>';

			String
			deleteStr = '<a href="javascript:void;"'
				+ '" target="_top" '
				+ ' onclick="javascript:return onSubmit(\''+deleteUrl+'\')" >删除</a>';
			
			return  editStr + "&nbsp;|&nbsp;" + deleteStr+"&nbsp;";
			
		}
		
		//批量选择框
		function checkbox(record, rowIndex, colIndex, options) {
			return '<input type="checkbox" value="'
					+ gridObj.getColumnValue(rowIndex, gridObj
							.getColumnModel(colIndex).index) + '"/>';
		}
	</script>



</body>



</html>
