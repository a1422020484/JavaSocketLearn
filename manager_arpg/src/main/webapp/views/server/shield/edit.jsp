<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../index.jsp"%>
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
	 var subUrl = "${path}"+'/server/shield/toEdit';
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
			<input type="hidden" value="${id}" name="id" />
			<div class="control-group">
				<label class="laber_from">版本</label>
				<div class="controls">
					<input class="input_from" name="version" type="text" value="${version}" />
					<p class="help-block"></p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="laber_from">关闭子渠道(1,2,3)</label>
				<div class="controls">
					<input class="input_from" name="closedSubPlatform" type="text" value="${closedSubPlatform}" />
					<p class="help-block"></p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="laber_from">兑换系统</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="redeemSys"
						type="checkbox" <c:if test="${redeemSys == 1}">checked</c:if>  value="1" />
					<p class="help-block"></p>
				</div>
			</div>

			<div class="control-group">
				<label class="laber_from">官网</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="webSite"
						type="checkbox"  <c:if test="${webSite == 1}">checked</c:if> value="1" />
					<p class="help-block"></p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="laber_from">联系我们</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="contact"
						type="checkbox" <c:if test="${contact == 1}">checked</c:if> value="1" />
					<p class="help-block"></p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="laber_from">排行榜</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="rankingSys"
						type="checkbox" <c:if test="${rankingSys == 1}">checked</c:if> value="1" />
					<p class="help-block"></p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="laber_from">月卡</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="monthCard"
						type="checkbox" <c:if test="${monthCard == 1}">checked</c:if> value="1" />
					<p class="help-block"></p>
				</div>
			</div>
			<div class="control-group">
				<label class="laber_from">非苹果审核</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="silentDownloadRes"
						type="checkbox" <c:if test="${silentDownloadRes == 1}">checked</c:if> value="1" />
					<p class="help-block"></p>
				</div>
			</div>
			<div class="control-group">
				<label class="laber_from">facebook分享</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="fbShare"
						type="checkbox" <c:if test="${fbShare == 1}">checked</c:if> value="1" />
					<p class="help-block"></p>
				</div>
			</div>
            <div class="control-group">
				<label class="laber_from">爱贝支付</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="abPay"
						type="checkbox" <c:if test="${abPay == 1}">checked</c:if> value="1" />
					<p class="help-block"></p>
				</div>
			</div>
			
			 <div class="control-group">
				<label class="laber_from">微信</label>
				<div class="controls">
					<input style="width: 20px; height: 20px" name="weixin"
						type="checkbox" <c:if test="${weixin == 1}">checked</c:if> value="1" />
					<p class="help-block"></p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="laber_from"></label>
				<div class="controls">
					<button class="btn btn-success" onclick="return onSubmit();" style="width: 120px;">${btn}</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
