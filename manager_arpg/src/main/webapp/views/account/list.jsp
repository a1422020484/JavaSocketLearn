<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.saturn.web.controllers.login.dao.UserModel"%>
<%@ include file="../index.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<!-- 调用CSS，JS -->

<!-- 
<link href="${css}/login/invalid.css" rel="stylesheet" type="text/css" />
<link href="${css}/login/reset.css" rel="stylesheet" type="text/css" />
<link href="${css}/login/style.css" rel="stylesheet" type="text/css" />
<link href="${css}/server/base.css" rel="stylesheet" type="text/css" />
 -->

<link href="${css}/server/formui.css" rel="stylesheet" type="text/css" />
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
	<div id="server-content">
		<table class="tb" id="top">
			<!-- 表头部分 -->
			<thead>
				<tr>
					<th>账号ID</th>
					<th>账号</th>
					<th>权限</th>
					<th>备注</th>
					<th><a href="javascript:void;" target="_top" onclick="center_iframe('${path}/account/add?mainView=true');">新增</a></th>
				</tr>
			</thead>

			<!-- 表内容部分 -->
			<tbody>
			<c:forEach items="${list}" var="ls">
			 <tr>
			 <td>${ls.getId()}</td>
			 <td>${ls.getUsername()}</td>
			 <td>${ls.getAuthorityStr()}</td>
			 <td>${ls.getRemark()}</td>
			 <td><a href="javascript:void;" target="_top" onclick="center_iframe('account/edit?id=${ls.getId()}&mainView=true');" >编辑</a>&nbsp;|&nbsp;
			 	<a target="_top" href="javascript:void;" 
			 	onclick="javascript:return onSubmit('toRemove?id=${ls.getId()}&mainView=true')">删除</a>
			 </td>
			 </tr>
			</c:forEach>
			</tbody>

			<!-- 表尾 -->
			<tfoot>

			</tfoot>

		</table>


	</div>
</body>



</html>
