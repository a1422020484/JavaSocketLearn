<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
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

function ajaxSub()
{
	 var subUrl = "${eidtUrl}";
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
	 	style="width: 100%; margin: 0 auto; text-align: center;overflow:auto " >
		<form id="myfrom">
		
		<table border="1" align="center">
		<tr  height="100px">
			<td width="20%" style="background-color: rgb(184, 204, 228);" >配置服务器</td>
			<td width="60%">
				<%
					//pageContext.getOut().println("<label class=\"laber_from\">服务器</label>");
					//WebUtils.createServerSelected(pageContext, "srvId", "input_select");

					WebUtils.createServerCheckBoxs(pageContext, "srvId", "controls", null, null);
				%>
			</td>
			<td width="20%">此处为服务器选择，可进行单服选择操作，全服选择操作</td>
		</tr>
		<tr height="80px">
			<td width="20%" style="background-color: rgb(184, 204, 228);" >跑马灯内容</td>
			<td width="80%" colspan="2">
			<input name='msgStr' id='msgStr'
						class="easyui-textbox" data-options="multiline:true"
						value="${msg}" style="width: 1044px; height: 80px">
			</td>
		</tr>
		<!-- <tr height="80px">
			<td width="20%" style="background-color: rgb(184, 204, 228);" >跑马灯时间</td>
			<td colspan="2">
			<div>
				<span>选择开始时间  </span>
				<input class="easyui-datebox" name="startDate" value="${startDate}" data-options="formatter:myformatter,parser:myparser"></input>
				<span>选择结束时间 </span>
				<input class="easyui-datebox" name="endDate" value="${endDate}" data-options="formatter:myformatter,parser:myparser"></input>
			</div>
			</td>
		</tr> -->
		
		<tr>
			<td width="20%" style="background-color: rgb(184, 204, 228);">开始时刻</td>
			<td><input class="easyui-datetimebox" name="startDate" value="${startDate}" data-options="formatter:myformatter,parser:myparser"></input></td>
			<td> 跑马灯开始时间 </td>
		</tr>
		<tr>
			<td width="20%" style="background-color: rgb(184, 204, 228);">结束时刻</td>
			<td ><input class="easyui-datetimebox" name="endDate" value="${endDate}" data-options="formatter:myformatter,parser:myparser"></input></td>
			<td> 跑马灯结束时间 </td>
		</tr>
		<tr>
			<td width="20%" style="background-color: rgb(184, 204, 228);">重复间隔</td>
			<td align="left"><input class="input_from" name="intervalTime" type="text"
						value="${intervalTime}" onchange="numberInput(this)" /></td>
			<td> 秒,不支持浮点数 </td>
		</tr>
		<tr>
			<td width="20%" style="background-color: rgb(184, 204, 228);">单次显示次数</td>
			<td align="left" colspan="2"><input class="input_from" name="loopCount" type="text"
						value="${loopCount}" onchange="numberInput(this)" /></td>
		</tr>
		</table>	
		<div class="control-group">
				<label class="laber_from"></label>
				<div class="controls">
					<button class="btn btn-success"
						onclick="return onSubmit();"
						style="width: 120px;">发送</button>
				</div>
			</div>
		</form>


	</div>
<script type = "text/javascript"> 
	<!---rew type 01--->
	$(document).ready(function() {
		var h = document.documentElement.clientHeight;		
		$('#body2').css('height', h * 0.9);
	});
	
	function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        var h = date.getHours();
        var min = date.getMinutes(); 
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min);
    }
    function myparser(s){
        if (!s) return new Date();
        var reg=/[^0-9]/ 
        var ss = (s.split(reg)); 
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        var d = parseInt(ss[2],10);
        var h = parseInt(ss[3],10);
        var min = parseInt(ss[4],10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h)&& !isNaN(min)){
            return new Date(y,m-1,d,h,min);
        } else {
            return new Date();
        }
    }
</script>

</body>
</html>
