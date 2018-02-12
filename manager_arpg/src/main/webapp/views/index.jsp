<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.saturn.web.utils.WebUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<%
	String path = request.getContextPath(); //包路径
	String res = path + "/public"; //资源路径
	String libs = path + "/public/libs"; //资源路径
	String images = path + "/public/images"; //图片路径
	String css = path + "/public/css"; //图片路径
	String js = path + "/public/js"; //js路径
	String views = path + "/views"; //视图路径

	//写入request
	request.setAttribute("path", path);
	request.setAttribute("res", res);
	request.setAttribute("libs", libs);
	request.setAttribute("images", images);
	request.setAttribute("css", css);
	request.setAttribute("js", js);
	request.setAttribute("views", views);
%>
<script src="${libs}/jquery/jquery-1.11.3.min.js"></script>
<script src="${libs}/layer/layer.js"></script>
<script>
	//读取值
	function getValue(id) {
		var obj = document.getElementById(id);
		var value = (obj != null) ? obj.value : null;
		//alert("id:" + id + " value:" + value);
		return value;
	}

	//调用java函数处理
	function callJava(url, value) {
		//alert("callJava: " + url + "-" + value);
		//创建HttpRequest对象
		var httpRequest = null;
		if (window.XMLHttpRequest) {
			httpRequest = new XMLHttpRequest();
		} else {
			httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}
		//设置回调函数
		//httpRequest.onreadystatechange = _finish;
		//alert("value " + value);

		//生成url. PS:value不一定是字符串, 不能判断value.length>0
		var urlStr = url;
		if (value != null) {
			var index = urlStr.indexOf('?');
			if (index >= 0) {
				urlStr += "&value=" + value;
			} else {
				urlStr += "?value=" + value;
			}
		}
		//alert("urlStr " + urlStr);

		/**
		var urlStr = url;
		var argStr = null;
		var index = urlStr.indexOf('?');
		if (index >= 0) {
			urlStr = url.slice(0, index);
			argStr = url.slice(index, url.lenght);
		} else {
			urlStr = url;
			argStr = null;
		}
		//添加参数
		if (value != null) {
			if (argStr == null) {
				argStr = "?value=" + value;
			} else {
				argStr += "&value=" + value;
			}
		}
		 **/
		//alert("urlStr: " + urlStr + " argStr:" + argStr);
		//回调函数
		//function callback() {
		//	alert("result123 " + httpRequest.status);
		//	if (httpRequest.readyState == 4) {
		//		if (httpRequest.status == 200) {
		//			resultStr = httpRequest.responseText;
		//		}
		//	}
		//}
		//httpRequest.onreadystatechange = callback; //回调

		//初始化httpRequest
		httpRequest.open("GET", urlStr, false); //访问方式, url, 是否异步
		//发送请求
		var result = httpRequest.send(null);

		var resultStr = false;
		if (httpRequest.status == 200) {
			resultStr = httpRequest.responseText;
		}
		//alert("result " + resultStr);
		return resultStr;
	}

	//确定窗口
	function dialog(msg) {
		var msg = (msg == null) ? "是否确定？\n\n请确认！" : msg;
		if (confirm(msg) == true) {
			return true;
		} else {
			return false;
		}
	}

	//数字输入事件
	function numberInput(input) {
		//alert("value " + input.value);
		var pattern = /[^0-9]/g;
		if (pattern.test(input.value)) {
			input.value = input.value.replace(pattern, "");
		}
	}

	//用户信息获取
	function findUserInfo(idName, inputName, textName, srvId) {
		//alert("idName:" + idName);
		var idObj = document.getElementById(idName);
		var inputObj = document.getElementById(inputName);
		var textObj = document.getElementById(textName);
		//textObj.innerText = inputObj.value.length;

		//访问url 
		var url = "${path}/main/findUser?playerText=" + inputObj.value
				+ "&srvId=" + srvId;
		//var url = "${path}/main/findUser";
		var retStr = callJava(url, null);
		//var retStr = "1,2,3,4,";

		//解析返回值
		var args = getArgs(retStr);
		if (args != null && args.length >= 2) {
			//正常返回
			idObj.value = args[0];
			textObj.innerText = args[1];
			return;
		}
		textObj.innerText = retStr;
		textObj.value = retStr;
		idObj.value = null;
	}

	//解析参数, 把字符串格式为 a,b,c 的数据拆分成数组.
	function getArgs(valueStr) {
		if (valueStr == null || valueStr.lenght <= 0) {
			return null;
		}
		//返回数组
		var retArg = new Array();
		var id = 0;

		//遍历获取
		var strLen = valueStr.length;
		var startIndex = 0;
		while (true) {
			//查找参数
			var index = valueStr.indexOf(',', startIndex);
			if (index <= 0) {
				index = strLen;
			}
			//截取出参数
			var arg = valueStr.slice(startIndex, index)
			//alert(startIndex + "," + index + "," + arg);
			retArg[id++] = arg;

			startIndex = index + 1;
			//检测是否结束
			if (startIndex >= strLen) {
				break;
			}
		}
		return retArg;
	}

	//打开窗口
	function openWindow(href, width, height) {
		width = (width != null) ? width : 400;
		height = (height != null) ? height : 300;
		//alert("href:" + href + ", width:" + width + ", height:" + height);

		//打开窗口
		window.top.layer.open({
			type : 2,
			title : "添加黑名单",
			area : [ width + 'px', height + 'px' ],
			content : href
		});
	}

	//跳转
	function topJump(href) {
		window.top.location.href = href;
	}
</script>
</head>
<body>
</body>

</html>


