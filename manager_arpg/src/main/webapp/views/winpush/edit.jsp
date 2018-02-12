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
			<table border="1">
				<tr>
					<td style="background-color: rgb(184, 204, 228)">发布时间 </td>
					<td>类型 
					<select name="open_state" id="open_state">
						<option id="0" value="0" <c:if test="${open_state == 0}">selected="true"</c:if> >普通时间</option>
						<option id="1" value="1" <c:if test="${open_state == 1}">selected="true"</c:if>>开服时间</option>
					</select>
					</td>
					<td>
						<div id="ordinaryTime">
						<input style="width: 150px;" name="normalTime" type="date" value="${normalTime}" /> 
						</div>
						<div id="openTime">
						<input class="input_from" name="openTime" type="text" value="${openTime}" style="width: 80px;" />
						</div>
					</td>
				</tr>
			</table>
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
						height="50" width="200">备注</td>
					<td rowspan="2"><input name='remark' id='remark'
						class="easyui-textbox" data-options="multiline:true"
						value="${remark}" style="width: 1044px; height: 80px"></td>
				</tr>
				<tr>
					<td></td>
				</tr>
			</table>
			<table border="1">
				<tr>
					<td style="background-color: rgb(184, 204, 228)">弹窗配置</td>
					<td width="75%">
					闪光来袭:
					<select name="winpush1" id="winpush1">
					  <option id="0" value="0" <c:if test="${winpush1 == 0}">selected="true"</c:if>>关</option>
					  <option id="1" value="1" <c:if test="${winpush1 == 1}">selected="true"</c:if>>开</option>
				    </select><br>
					轮盘活动:
					<select name="winpush2" id="winpush2">
					  <option id="0" value="0" <c:if test="${winpush2 == 0}">selected="true"</c:if>>关</option>
					  <option id="1" value="1" <c:if test="${winpush2 == 1}">selected="true"</c:if>>开</option>
				    </select><br><br>
					限时神兽:
					<select name="winpush3" id="winpush3">
					  <option id="0" value="0" <c:if test="${winpush3 == 0}">selected="true"</c:if>>关</option>
					  <option id="1" value="1" <c:if test="${winpush3 == 1}">selected="true"</c:if>>开</option>
				    </select><br><br>
					天天活动:
					<select name="winpush4" id="winpush4">
					  <option id="0" value="0" <c:if test="${winpush4 == 0}">selected="true"</c:if>>关</option>
					  <option id="1" value="1" <c:if test="${winpush4 == 1}">selected="true"</c:if>>开</option>
				    </select><br><br>
					</td>
					<td width="75%">
					弹窗顺序:
					<input class="input_from" name="pushorder1" type="text" value="${pushorder1}" style="width: 180px;" /> <br>
					<input class="input_from" name="pushorder2" type="text" value="${pushorder2}" style="width: 180px;" /> <br>
					<input class="input_from" name="pushorder3" type="text" value="${pushorder3}" style="width: 180px;" /> <br>
					<input class="input_from" name="pushorder4" type="text" value="${pushorder4}" style="width: 180px;" /> <br>
					</td>
				</tr>
			</table>
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
</script>
</body>
</html>
