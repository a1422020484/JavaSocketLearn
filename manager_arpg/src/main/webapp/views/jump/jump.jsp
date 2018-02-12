
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>跳转提示</title>

<style type="text/css">
* {
	padding: 0;
	margin: 0;
}

body {
	background: #fff;
	font-family: '微软雅黑';
	color: #333;
	font-size: 16px;
}

.message {
	width: 400px;
	height: 150px;
	margin: auto;
	border: 1px solid #1B8F24;
	margin-top: 30px;
}

.head {
	width: 100%;
	height: 30px;
	background: rgb(222, 245, 194);
	text-align: center;
	padding-top: 5px;
}

.content {
	height: 120px;
	width: 100%;
}

.success, .error {
	text-align: center;
	margin-top: 30px;
}

.jump {
	text-align: center;
	margin-top: 20px;
}
</style>





</head>







<body >
	<div class="message">
		<div class="head">
			<span>Ace Admin提示信息:</span>
		</div>
		<div class="content">

			<%
				String msg = (String) request.getAttribute("message");
				String err = (String) request.getAttribute("error");

				if (msg != null) {
					out.print("<p class=\"success\">:)" + msg + "</p>");
				} else {
					out.print("<p class=\"error\">:(" + err + "</p>");
				}
			%>



			<p class="detail"></p>
			<p class="jump">
				<a id="href" href="${jumpUrl}" target="_top">如果你的浏览器没有自动跳转，请点击这里...
				</a> <br /> 等待时间： <b id="wait"> ${waitSecond} </b>
			</p>
		</div>
	</div>




	<script type="text/javascript">
		function time_jump() {
			var wait = document.getElementById('wait');
			var href = document.getElementById('href').href;
			function timer() {
				var time = --wait.innerHTML;
				if (time <= 0) {
					window.top.location.href = href;
					clearInterval(interval);
				}
			}
			var interval = setInterval(timer, 1000);
		}

		time_jump();
	</script>

</body>

</html>
