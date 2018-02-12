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
	<div class="div_from_aoto" style="width: 500px;">
		<div class="control-group">
			<input type="hidden" value="${curSrvId}" name="srvId" id="srvId" />
			<div class="controls">
				<label class="laber_from">当前服务器:</label> <label class="laber_from">${srvCode}</label>

				<%
					Integer srvId0 = (Integer) request.getAttribute("curSrvId");
					int srvId = (srvId0 != null) ? srvId0 : 0;
					WebUtils.createServerSelected(pageContext, "selectSrvId",
							"input_select", srvId, null);
				%>
				<button class="btn btn-success" style="width: 120px;"
					onclick="changeServer('${resetUrl}');">切换</button>

				<p class="help-block"></p>
			</div>
		</div>
	</div>



	<table id="searchTable" style="width: 98%">
		<!-- 表头部分  getCheckedIds()-->
		<tr>
			<th w_index="id" width="1%;">ID</th>
			<th w_index="msgStr" width="5%;">内容</th>
			<th w_index="startTime" width="5%;">发送时间</th>
			<th w_index="endTime" width="5%;">失效时间</th>
			<th w_index="awardStr" width="5%;">奖励</th>
			<th w_render="operate" width="5%;"><a href="javascript:void;" target="_top"
				onclick="center_iframe('${backUrl}?mainView=true');" >返回</a></th>
		</tr>
	</table>

	<script type="text/javascript">
		var gridObj;
		
		function changeServer(url)
		{
			var text = $('#selectSrvId').find("option:selected").val();
			var urlStr = url +'?srvId='+text+'&mainView=true';
			center_iframe(urlStr);
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
		});

		//额外操作界面
		function operate(record, rowIndex, colIndex, options) {
			var id = gridObj.getRecordIndexValue(record, 'id')
			var deleteUrl = "${toDeleteUrl}?id=" + id + "&srvId=${srvId}";

			String
			deleteStr = '<a href="javascript:void;"'
				+ '" target="_top" '
				+ ' onclick="javascript:return onSubmit(\''+deleteUrl+'\')" >删除</a>';

			return deleteStr;
		}
	</script>



</body>



</html>
