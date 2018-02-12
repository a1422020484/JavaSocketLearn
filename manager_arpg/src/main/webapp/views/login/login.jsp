<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />




<link rel="stylesheet" href="public/css/login/reset.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="public/css/login/style.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="public/css/login/invalid.css"
	type="text/css" media="screen" />






<script>
	
</script>

<title>${title}</title>

</head>





<body id="login">

	<div id="login-wrapper" class="png_bg">
		<div id="login-top">
			<h1>{$title}</h1>
			<img id="logo" src="public/images/login/logo.png" alt="{$title}" />
		</div>
		<!-- Form表单 -->
		<div id="login-content">
			<form action="login/tologin" method="POST">
				<p>
					<label>用户名</label> <input class="text-input" type="text"
						name="username" />
				</p>
				<div class="clear"></div>
				<p>
					<label>密码</label> <input class="text-input" type="password"
						name="password" />
				</p>

				<div class="clear"></div>
				<p>
					<input class="button" type="submit" value="登录" />
				</p>

			</form>
		</div>

	</div>
</body>
</html>