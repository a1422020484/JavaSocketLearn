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
<script type="text/javascript">
	function onSubmit() {
		$.messager.confirm('提醒', '是否提交?', function(r) {
			if (r) {
				ajaxSub();
			}
		});
		return false;
	}

	function ajaxSub() {
		var subUrl = "${sendUrl}";
		$.ajax({
					type : "POST",
					url : subUrl,
					data : $('#myfrom').serialize(),
					async : false,
					datatype : "json",
					success : function(data) {
						var obj = eval("[" + data + "]");
						if (obj.length > 0) {
							$.messager.alert(obj[0].title, obj[0].context,
									obj[0].type);
							if (obj[0].redUrl != '')
								center_iframe(obj[0].redUrl);
						}
					},
				});
	}
	
	function showOpenTime(){
		var value = $('#open_state').val();
		// 设置活动开启时间类型
		if(value == 0){
			$("#ordinaryTime").show();
			$("#openTime").hide();
		}else{
			$("#ordinaryTime").hide();
			$("#openTime").show();
		}
	}
</script>
</head>
<body>
	<div id="body2"
		style="width: 98%; margin: 0 auto; text-align: center; overflow: auto">
		<div style="width: 90%; text-align: center;">
			<form id="myfrom" target="_top">
				<input type="hidden" value="0" id="id" name="id" />
			
				<table border="1">
					<tr>
						<td style="background-color: rgb(184, 204, 228)">邮件发送 </td>
					</tr>
				</table>
				
				<br /> <br />
	
				
				</br></br>
				<table>
					<tr></tr>
					<tr>
						<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
							height="50" width="200">备注</td>
						<td rowspan="2"><input name='remark' id='remark'
							class="easyui-textbox" data-options="multiline:true"
							value="${remark}" style="width: 1044px; height: 80px"></td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
				<br /> <br />
				
			<br/><br/>

				<div class="control-group"
					style="widrh: 100%; margin: 0 auto; text-align: center">
					<button type="submit" class="btn btn-success"
						onclick="return onSubmit();" style="width: 120px;">${btnStr} & 邮件发送</button>
				</div>
			</form>
		</div>
	</div>

	<script type="text/javascript">
	
	
	
	
	$(document).ready(function() {
		showOpenTime();
		$('#open_state').change(function(){showOpenTime(); });
		var h = document.documentElement.clientHeight;
		$('#body2').css('height', h * 0.9);
		
	});
	</script>
</body>
</html>
