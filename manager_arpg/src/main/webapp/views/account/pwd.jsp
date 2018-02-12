<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改密码</title>

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
	 $.messager.confirm('提醒', '是否修改密码?', function(r){
       if (r){
        	ajaxSub();
        }
     });
	return false;
}

function ajaxSub()
{
	 var subUrl = "${path}"+'/account/toChangePwd';
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
	<div class="div_from_aoto" style="width: 500px;">
		<form id="myfrom" target="_top">
			<div class="control-group">
				<label class="laber_from">旧密码</label>
				<div class="controls">
					<input class="input_from" type="password" name="password"
						placeholder="请输入旧密码" />
					<p class="help-block"></p>
				</div>
			</div>
			<div class="control-group">
				<label class="laber_from">新密码</label>
				<div class="controls">
					<input class="input_from" type="password" name="newPwd"
						placeholder="请输入新密码" />
					<p class="help-block"></p>
				</div>
			</div>
			<div class="control-group">
				<label class="laber_from">确认密码</label>
				<div class="controls">
					<input class="input_from" type="password" name="checkPwd"
						placeholder="请输入确认密码" />
					<p class="help-block"></p>
				</div>
			</div>
			<div class="control-group">
				<label class="laber_from"></label>
				<div class="controls">
					<button class="btn btn-success" onclick="return onSubmit();" style="width: 120px;">确认</button>
				</div>
			</div>
		</form>
	</div>

</body>



</html>
