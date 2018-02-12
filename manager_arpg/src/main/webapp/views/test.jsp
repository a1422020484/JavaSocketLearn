<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="index.jsp"%>
<!doctype html>
<html>



<head>
<title>开始使用layer</title>

<script src="${libs}/jquery/jquery-1.11.3.min.js"></script>
<script src="${libs}/layer/layer.js"></script>
</head>





<body>
	<button id="test1">小小提示层</button>
	<button id="parentIframe">小小提示层</button>

	你必须先引入jQuery1.8或以上版本

	<script>
		$('#test1').on('click', function() {
			layer.msg('Hello layer');
		});

		$('#parentIframe').on('click', function() {
			layer.open({
				type : 2,
				title : 'iframe父子操作',
				maxmin : true,
				shadeClose : true, //点击遮罩关闭层
				area : [ '800px', '520px' ],
				content : 'test/iframe.html'
			});
		});
	</script>
	当然，你也可以写在外部的js文件
</body>
</html>