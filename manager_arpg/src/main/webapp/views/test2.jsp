<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="index.jsp"%>
<!-- Put IE into quirks mode -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=gb2312" />
<title>自适应满屏高度的布局-建站学(www.Cuoxin.com)</title>
<style type="text/css">
html {
	height: 100%;
	max-height: 100%;
	padding: 0;
	margin: 0;
	border: 0;
	background: #fff;
	font-size: 76%;
	font-family: georgia, palatino linotype, times new roman, serif, "宋体";
	/* hide overflow:hidden from IE5/Mac */ /* \*/
	overflow: hidden; /* */
}

html {
	scrollbar-face-color: orange;
	scrollbar-highlight-color: #fff;
	scrollbar-shadow-color: #c00;
	scrollbar-3dlight-color: #c00;
	scrollbar-arrow-color: #000;
	scrollbar-track-color: #c0c0c0;
	scrollbar-darkshadow-color: #fff;
}

body {
	height: 100%;
	max-height: 100%;
	overflow: hidden;
	padding: 0;
	margin: 0;
	border: 0;
}

#content {
	overflow: auto;
	position: absolute;
	z-index: 3;
	top: 100px;
	width: 96%;
	bottom: 80px;
	left: 2%;
	background: #666;
}

* html #content {
	top: 0;
	left: 2%;
	height: 100%;
	max-height: 100%;
	width: 96%;
	overflow: auto;
	position: absolute;
	z-index: 3;
	border-top: 100px solid #fff;
	border-bottom: 80px solid #fff;
}

#head {
	position: absolute;
	margin: 0;
	top: 0;
	left: 1px;
	display: block;
	width: 100%;
	height: 90px;
	background: orange;
	background-position: 0 0;
	background-repeat: no-repeat;
	font-size: 4em;
	z-index: 5;
	overflow: hidden;
	color: #fff;
	border-bottom: 1px #c0c0c0 solid;
	text-align: center;
}

#foot {
	position: absolute;
	margin: 0;
	bottom: 0;
	left: 2%;
	display: block;
	width: 96%;
	height: 70px;
	font-size: 1em;
	z-index: 5;
	overflow: hidden;
	color: #fff;
	text-align: center;
}

#content p, h2 {
	padding: 10px;
	font-size: 12px;
	color: #fff;
}

#content p.bold {
	font-size: 1.2em;
	font-weight: bold;
	color: red;
}

#logo {
	height: 75px;
	line-height: 75px;
	text-align: center;
	display: block;
}

.footleft {
	width: 20%;
	height: 70px;
	line-height: 70px;
	float: left;
	border: 1px #c0c0c0 solid;
	background: blue;
}

.footmid {
	width: 58%;
	height: 70px;
	line-height: 70px;
	margin-left: 1%;
	float: left;
	border: 1px #c0c0c0 solid;
	background: blue;
}

.footright {
	width: 20%;
	height: 70px;
	line-height: 70px;
	float: right;
	border: 1px #c0c0c0 solid;
	background: blue;
}

a:link, a:visited {
	color: #fff;
}
</style>
</head>
<body>
	<div id="head">
		<span id="logo">100%高度全屏自适应布局</span>
	</div>
	<div id="foot">
		<div class="footleft">
			<a href="http://www.Cuoxin.com/">网站制作教程:建站学</a>
		</div>
		<div class="footmid">
			<a href="http://www.Cuoxin.com/">网站制作教程:建站学</a>
		</div>
		<div class="footright">
			<a href="http://www.Cuoxin.com/">网站制作教程:建站学</a>
		</div>
	</div>
	<div id="content">
		<h2>此布局惟一的遗憾是在IE6以下浏览器中显示为怪异模式,因为HTML代码的头一句加了一个注释,如果删除后在IE中滚动条高度显示不全.</h2>
		<p class="bold">此布局在以下浏览器中测试通过: IE5.5,IE6,IE7,TT,Opera
			9.63,Firefox3.0,谷歌，IE8未作测试。</p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
		<p> </p>
	</div>
</body>
</html>