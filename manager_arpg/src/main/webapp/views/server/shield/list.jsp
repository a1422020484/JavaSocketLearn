<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.saturn.web.controllers.server.dao.ServerModel"%>
<%@ include file="../../index.jsp"%>
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
		<!-- 表头部分 -->
		<tr>
			<th w_index="id" width="1%;">ID</th>
			<th w_index="version" width="5%;">版本</th>
			<th w_index="redeemSys" width="5%;">兑换系统</th>
			<th w_index="webSite" width="5%;">官网</th>
			<th w_index="contact" width="5%;">联系我们</th>
			<th w_index="rankingSys" width="5%;">排行榜</th>
			<th w_index="monthCard" width="5%;">月卡</th>
			<th w_index="silentDownloadRes" width="5%;">非苹果审核</th>
			<th w_index="fbShare" width="5%;">facebook分享</th>
			<th w_index="abPay" width="5%;">爱贝支付</th>
			<th w_index="weixin" width="5%;">微信</th>
			<th w_render="operate" width="5%;"><a 
			href="javascript:void;" target="_top" onclick="center_iframe('${addUrl}?mainView=true');">新建</a></th>
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
			var deleteUrl = "${toDeleteUrl}?id=" + id;
			var editUrl = "${editUrl}?id=" + id+"&mainView=true";

			String
			editStr = '<a href="javascript:void;" target="_top" onclick="center_iframe(\''
					+ editUrl
					+ '\');"  >编辑</a>';

			String
			deleteStr = '<a href="javascript:void;"'
					+ '" target="_top" '
					+ ' onclick="javascript:return onSubmit(\''+deleteUrl+'\')" >删除</a>';

			return editStr + "&nbsp;|&nbsp;" + deleteStr;
		}
	</script>
</body>
</html>
