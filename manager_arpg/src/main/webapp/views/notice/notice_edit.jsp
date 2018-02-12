<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="cn.saturn.web.controllers.activity.dao.PresetActivity"%>
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
			<br /><br />
			<table>
				<tr height="100">
					<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
						width="120">配置服务器：</td>
					<td width="75%" height="30px"
						style="border: rgb(144, 144, 144) solid 1px">
						<%
						 	int[] srvIds = (int[]) request.getAttribute("selectSrvIds");
							WebUtils.createServerCheckBoxs(pageContext, "srvIds", "controls", null, srvIds);
						%>
					</td>
					<td style="border: rgb(144, 144, 144) solid 1px">此处为服务器选择，可进行单服选择操作，全服选择操作</td>
					</tr>
			</table>
			<br/><br/>
			<table>
				<tr></tr>
				<tr>
					<td
						style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
						height="50" width="200">公告内容</td>
					<td rowspan="2"><textarea name='notice' id='notice' style="width: 1044px; height: 80px">${notice}</textarea>
					</td>
				</tr>
			</table>
			<table>
			    <tr>
					<td
						style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
						height="50" width="200">公告图片</td>
					<td rowspan="2"><textarea name='imgs' id='imgs' style="width: 1044px; height: 80px">${imgs}</textarea>
					</td>
				</tr>
			</table>
			<table>
			    <tr>
					<td>
					<label class="laber_from">启用</label>
					<div class="controls">
					<input style="width: 20px; height: 20px" name="enable"
						type="checkbox" ${enableStr}></input>
					<p class="help-block"></p>
					</div>
					</td>
				</tr>
			</table>
			<br /><br />
			<br />
			<div class="control-group"
				style="widrh: 100%; margin: 0 auto; text-align: center">
				<button type="submit" class="btn btn-success"
					onclick="return onSubmit();" style="width: 120px;">创建</button>
			</div>
		</form>
		</div>
	</div>
<script type="text/javascript">

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

$(document).ready(function() {
	var h = document.documentElement.clientHeight;
	showOpenTime();
	// 设置更改 open_state 选项点击事件
	$('#open_state').change(function(){showOpenTime(); });
	
	$('#body2').css('height', h * 0.9);	
});

function onSubmit()
{
	 $.messager.confirm('提醒', '是否修改?', function(r){
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
          if (obj.length > 0) {
            $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
            if(obj[0].redUrl!='')
	               	center_iframe(obj[0].redUrl);
          }
	  },
	});
}
</script>
</body>
</html>
