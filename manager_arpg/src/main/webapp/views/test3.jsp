<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="index.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>

<head>

<title>enter</title>
<script>
	function keypress1() {
		var text1 = document.getElementById("mytext1").value;
		//alert("value " + text1);
		var len = 15 - text1.length;
		var show = "你还可以输入" + len + "个字";
		document.getElementById("name").innerText = show;
	}
	function keypress2() {
		var text1 = document.getElementById("myarea").value;
		var len;//记录剩余字符串的长度
		if (text1.length >= 30) {
			document.getElementById("myarea").value = text1.substr(0, 30);
			len = 0;
		} else {
			len = 30 - text1.length;
		}
		var show = "你还可以输入" + len + "个字";
		document.getElementById("pinglun").innerText = show;
	}
</script>
</head>

<body>
	<div style="text-align: left;">
		昵称: <input type="text" id="mytext1" maxlength=15 onKeyUp="keypress1()" />
		<font color="gray"> <label id="name">你还可以输入15个字</label></font>

	</div>
</body>

</html>