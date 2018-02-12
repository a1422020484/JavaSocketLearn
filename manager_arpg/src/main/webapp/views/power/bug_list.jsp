<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="proto.ProtocolWeb.AccountInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- 调用CSS，JS -->
<script src="${libs}/layer/layer.js"></script>


<link rel="stylesheet" href="${libs}/bsgrid/merged/bsgrid.all.min.css" />
<script type="text/javascript"
	src="${libs}/bsgrid/js/lang/grid.zh-CN.min.js"></script>
<script type="text/javascript"
	src="${libs}/bsgrid/merged/bsgrid.all.min.js"></script>
<script type="text/javascript" src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${js}/easyui_iframe.js"></script>
<script>
	function showPromptBug(title, srvId, id) {
		//alert(title + ": " + srvId + "," + id);
		var input = prompt(title, "");
		if (input != null && input != "") {
			var subUrl = "${editUrl}&id=" + id + "&disStr="
					+ input;
			//window.location.reload(); //刷新页面
			ajaxSub(subUrl);

			return input;
		}
		return null;
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
		<!-- 表头部分 -->
		<tr>
			<th w_index="id" width="5%;">bugID</th>
			<th w_index="playerId" width="5%;">玩家ID</th>
			<th w_index="playerName" width="5%;">玩家名称</th>
			<th w_index="bugStr" width="3%;">bug内容</th>
			<th w_index="time" width="5%;">提交时间</th>
			<th w_index="disStr" width="5%;">处理记录</th>
			<th w_index="disTime" width="5%;">处理时间</th>
			<!-- 
			<th w_index="dispose0" width="5%;">是否处理</th>
			 -->
			<th w_render="operate" width="5%;">处理</th>
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
				pageSize : 10,
				displayBlankRows : false, //是否显示空白行
				//stripeRows : true, //隔行变色
				pageIncorrectTurnAlert : false, //是否提示无法翻页提示
			});
		});

		function operate(record, rowIndex, colIndex, options) {
			var id = gridObj.getRecordIndexValue(record, 'id')
			var dispose = gridObj.getRecordIndexValue(record, 'dispose')
			//var btnStr = 
			var deleteUrl = "${deleteUrl}&id=" + id;
			var deleteStr = "<a href=\"" + deleteUrl + "\" target=\"_top\">删除</a>";

			//处理字段
			var editStr = "";
			if (dispose == 0) {
				//var editUrl = "${editUrl}&id=" + id;
				//editStr = "<a href=\"" + editUrl + "\" target=\"_top\">处理</a>";
				var actionStr = "showPromptBug(\'输入处理结果\', ${srvId}, " + id
						+ ")"
				editStr = "<a href=\"javascript:;\" onclick=\""+actionStr+"\" target=\"_top\" >处理</a>";
			} else {
				editStr = "已处理";
			}

			return editStr + "&nbsp;|&nbsp;" + deleteStr;
		}
	</script>

</body>



</html>
