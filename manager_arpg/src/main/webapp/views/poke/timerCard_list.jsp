<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.saturn.web.controllers.server.dao.ServerModel"%>
<%@ page import="cn.saturn.web.utils.AuthorityUtils"%>
<%@ include file="../index.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- 调用CSS，JS -->
<script src="${libs}/layer/layer.js"></script>


<link rel="stylesheet" href="${libs}/bsgrid/merged/bsgrid.all.min.css" />
<script type="text/javascript"
	src="${libs}/bsgrid/js/lang/grid.zh-CN.min.js"></script>
<script type="text/javascript"
	src="${libs}/bsgrid/merged/bsgrid.all.min.js"></script>
<script type="text/javascript" src="${js}/easyui_iframe.js"></script>


<style type="text/css">
</style>

<script type = "text/javascript">
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
}
</script>
</head>
<body>
	<table id="searchTable" style="width: 98%">
		<!-- 表头部分  getCheckedIds()-->
		<tr>
			<th w_index="id" width="1%;">ID</th>
			<th w_index="pid" width="1%;">PID</th>
			<th w_index="timeCardName" width="5%;">标题</th>
			<th w_index="selectSrvIds" width="5%;">选择服务器</th>
			<th w_index="openTime" width="5%;">开启时间</th>
			<th w_index="openTimeShow" width="5%;">展示时间</th>
			<th w_index="remark" width="5%;">备注</th>
			<th w_render="operate" width="5%;">
			<a href="javascript:void;" target="_top" onclick="center_iframe('${addUrl}?mainView=true');">新建</a>&nbsp;
				<!-- |&nbsp<a href="javascript:void;" target="_top" onclick="center_iframe('${checkUrl}?mainView=true');">查看</a>
				 -->
				</th>
		</tr>
	</table>

	<script type="text/javascript">
		var gridObj;
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
				$('#searchTable thead tr th:eq(0) input[type=checkbox]')
						.change(
								function() {
									var checked = $.bsgrid.adaptAttrOrProp(
											$(this), 'checked') ? true : false;
									$.bsgrid
											.adaptAttrOrProp(
													$('#searchTable tbody tr td:nth-child(1)>input[type=checkbox]'),
													'checked', checked);
								});
			}
		});

		//额外操作界面
		function operate(record, rowIndex, colIndex, options) {
			var id = gridObj.getRecordIndexValue(record, 'id')
			var deleteUrl = "${toDeleteUrl}?id=" + id;
			var editUrl = "${editUrl}?id=" + id+"&mainView=true";
			//var sendUrl = "${sendUrl}?id=" + id+"&mainView=true";

			//String
			//sendStr = '<a href="javascript:void;" target="_top" onclick="center_iframe(\''
			//		+ sendUrl
			//		+ '\');" >发送</a>';

			String
			editStr = '<a href="javascript:void;" target="_top" onclick="center_iframe(\''
					+ editUrl
					+ '\');" >编辑</a>';

			String
			deleteStr ='<a href="javascript:void;"'
				+ '" target="_top" '
				+ ' onclick="javascript:return onSubmit(\''+deleteUrl+'\')" >删除</a>';

			//return sendStr + "&nbsp;|&nbsp;" + editStr + "&nbsp;|&nbsp;"
			//		+ deleteStr;
			
			return  editStr + "&nbsp;|&nbsp;" + deleteStr;
		}
	</script>



</body>



</html>
