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
		var subUrl = "${editUrl}";
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
				<c:if test="${isNew}">
					<input type="hidden" value="0" id="id" name="id" />
				</c:if>
				<c:if test="${!isNew}">
					<input type="hidden" value="${id}" id="id" name="id" />
				</c:if>
				<table border="1">
					<tr>
						<td style="background-color: rgb(184, 204, 228)" width="15%">ID:</td>
						<td><input class="input_from" name="cid" value="${cid}"
							style="width: 80px;" ${cidStr} type="text" /></td>
						
						<td style="background-color: rgb(184, 204, 228)">活动时间 </td>
						<td>类型 <select name="open_state" id="open_state">
						<option id="0" value="0" <c:if test="${open_state == 0}">selected="true"</c:if> >普通时间</option>
						<option id="1" value="1" <c:if test="${open_state == 1}">selected="true"</c:if>>开服时间</option>
						</select>
						</td>
						<td>
						<div id="ordinaryTime">
						<input style="width: 150px;" name="openTime" type="date" value="${openTime}" />
						</div>
						<div id="openTime">
						<input class="input_from" name="openDate" type="text" value="${openDate}" style="width: 80px;" />
						</div></td>	
					</tr>
				</table>
				
				<br /> <br />
				<table border="1">
					<tr>
					</tr>
					<tr rowspan="2">
						<td style="background-color: rgb(184, 204, 228)" width="10%">闪光精灵一</td>
						<td align="left" width="5%">
						<input class="easyui-combobox" id="first" name="first"
								data-options="valueField:'id',textField:'name'" value="${first}">
						</td>
						<td align="left" width="85%"></td>
					</tr>
				</table>
				<br /> <br />
				
				<table border="1">
					<tr>
					</tr>
					<tr rowspan="2">
						<td style="background-color: rgb(184, 204, 228)" width="10%">闪光精灵二</td>
						<td align="left" width="5%">
						<input class="easyui-combobox" id="second" name="second"
								data-options="valueField:'id',textField:'name'" value="${second}">
						</td>
						<td align="left" width="85%"></td>
					</tr>
				</table>
				<br /> <br />
				
				<table border="1">
					<tr >
					</tr>
					<tr >
						<td style="background-color: rgb(184, 204, 228)" width="10%">闪光精灵三</td>
						<td align="left" width="5%">
						<input class="easyui-combobox" id="third" name="third"
								data-options="valueField:'id',textField:'name'" value="${third}" >
						</td>
						<td align="left" width="85%"></td>
					</tr>
				</table>
				<br /> <br />
				
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

				<div class="control-group"
					style="widrh: 100%; margin: 0 auto; text-align: center">
					<button type="submit" class="btn btn-success"
						onclick="return onSubmit();" style="width: 120px;">${btnStr} & 发送</button>
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
				
		$.ajax({
			type : "POST",
			url : "../Exchange/getSelect2EasyUi?type=1",
			datatype : "json",
			success : function(data) {
			var obj = eval(data);
			if (obj.length > 0) {
			$('#first').combobox('loadData', obj);
							
			$('#first').combobox({enable:true  });
			}
		},
		});
				
		$.ajax({
			type : "POST",
			url : "../Exchange/getSelect2EasyUi?type=2",
			datatype : "json",
			success : function(data) {
				var obj = eval(data);
				if (obj.length > 0) {
					$('#second').combobox('loadData', obj);
				}
			},
		});
				
		$.ajax({
			type : "POST",
			url : "../Exchange/getSelect2EasyUi?type=3",
			datatype : "json",
			success : function(data) {
				var obj = eval(data);
				if (obj.length > 0) {
				$('#third').combobox('loadData', obj);
				}
			},
		});
	});

	</script>
</body>
</html>
