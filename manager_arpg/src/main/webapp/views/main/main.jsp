<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.saturn.web.controllers.main.dao.Menu"%>
<%@ page import="cn.saturn.web.utils.AuthorityUtils"%>
<%@ include file="../index.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>管理页面</title>

<!-- 调用CSS，JS -->
<link href="${css}/style.css" rel="stylesheet" type="text/css" />
<link href="${res}/index.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/color.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/demo/demo.css" />

<script type="text/javascript" src="${js}/main/jquery.min.js"></script>
<script type="text/javascript" src="${js}/main/tendina.min.js"></script>
<script type="text/javascript" src="${js}/main/common.js"></script>
<script type="text/javascript" src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${libs}/easyui/datagrid-detailview.js"></script>

<script type="text/javascript">
$(document).ready(function() {
    var h = document.documentElement.clientHeight;
    $('#tb').css('height', h * 0.9);

    $('#tt').tree({
        url: 'main/menuList',
        lines :  true 
    });

    $('#tt').tree({
        onClick: function(node) {
        	var childSize = $('#tt').tree('getChildren',node.target).length;
        	if(childSize >=0)
        	{
        		// 点击后折叠还是展开
        		var isOpen = (node.state == 'closed' ? true : false);
            	if(isOpen)
            		$('#tt').tree('expand',node.target);
            	else
            		$('#tt').tree('collapse',node.target);
        	}

        	if ('undefined' == typeof node.url || node.url == '')
            {
            	return;
            }
            var tt = $('#tb');
            if (tt.tabs('exists', node.text)) {
                tt.tabs('select', node.text);
                var tab = tt.tabs('getSelected');
                tt.tabs('update', {
                    tab: tab,
                    options: {
                        title: tab.title,
                        content: '<iframe scrolling="auto"  id="' + tab.title + '"  frameborder="0"  src="' + node.url + '" style="width:100%;height:100%;" ></iframe>',
                        closable: true
                    }
                });
            } else {
                tt.tabs('add', {
                    title: node.text,
                    content: '<iframe scrolling="auto" id="' + node.text + '" frameborder="0"  src="' + node.url + '" style="width:100%;height:100%;" ></iframe>',
                    closable: true
                });

            }

        }
    });

});
</script>
</head>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
		<!-- north -->
		<div data-options="region:'north'" style="height: 5%">
			<div style="float: left">
				<span
					style="font-size: 16px; line-height: 45px; padding-left: 20px; color: #8d8d8d">
					游戏管理后台 ${version} </span>
			</div>
		</div>
		<!-- west -->
		<div data-options="region:'west',split:true" title="菜单栏"
			style="width: 15%">
			<ul id='tt'></ul>
		</div>
		<!--center-->
		<div data-options="region:'center',split:true" id="tb" class="easyui-tabs" style="width: 100%;height:auto">
			<div title="首页" style="padding: 20px; display: none;">tab1</div>
		</div>
		
		<!-- south -->
		<div data-options="region:'south',split:true" style="height: 5%;">
			<p style="text-align: center;">Copyright © 2015 - 土星网络</p>
		</div>
	</div>

</body>
</html>