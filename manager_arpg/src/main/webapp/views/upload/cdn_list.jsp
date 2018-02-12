<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
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
<link rel="stylesheet" type="text/css"
	href="${libs}/uploadify/uploadify.css" />
	
<script type="text/javascript" src="${js}/main/jquery.min.js"></script>
<script type="text/javascript" src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/datagrid-detailview.js"></script>
<script type="text/javascript"
	src="${libs}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${js}/easyui_iframe.js"></script>
<script type="text/javascript" src="${libs}/uploadify/jquery.uploadify.min.js"></script>

</head>
<body>
	<label for="Attachment_GUID">附件上传：</label>
	<input class="easyui-validatebox" type="hidden" id="Attachment_GUID" name="Attachment_GUID" />
	<div>
	<input id="file_upload" name="file_upload" type="file" multiple="multiple">
    <a href="javascript:void(0)" class="easyui-linkbutton" id="btnUpload" data-options="plain:true,iconCls:'icon-save'"
                                    onclick="javascript: $('#file_upload').uploadify('upload', '*')">上传</a>
   <a href="javascript:void(0)" class="easyui-linkbutton" id="btnCancelUpload" data-options="plain:true,iconCls:'icon-cancel'"
                                    onclick="javascript: $('#file_upload').uploadify('cancel', '*')">取消</a>
   <div id="fileQueue" class="fileQueue"></div>
   <div id="div_files"></div>
  </div>
  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:send();">发送</a>
<script type="text/javascript">
var subUrl = "${upLoadConfig}";
$(function(){
setTimeout(function(){
$('#file_upload').uploadify({
    'swf': '${libs}/uploadify/uploadify.swf', //FLash文件路径
    'buttonText': '浏  览',                       //按钮文本
    'uploader': subUrl,         			   //处理文件上传Action
    'queueID': 'fileQueue',                    //队列的ID
    'queueSizeLimit': 999,                     //队列最多可上传文件数量，默认为999
    'auto': false,                             //选择文件后是否自动上传，默认为true
    'multi': true,                             //是否为多选，默认为true
    'removeCompleted': true,                   //是否完成后移除序列，默认为true
    'fileSizeLimit': '10MB',                   //单个文件大小，0为无限制，可接受KB,MB,GB等单位的字符串值
    'fileTypeDesc': 'Image Files',             //文件描述
    'fileTypeExts': '**.jpg;*.gif;*.jpeg;*.png;*.bmp',  				   //上传的文件后缀过滤器
    'onQueueComplete': function (event, data) {//所有队列完成后事件
    	console.log(data);
        ShowUpFiles($("#Attachment_GUID").val(), "div_files");  //完成后更新已上传的文件列表
        $.messager.alert("提示", "上传完毕！");                                     //提示完成           
    },
    'onUploadStart' : function(file) {
        $("#file_upload").uploadify("settings", 'formData', { 'folder': '政策法规', 'guid': $("#Attachment_GUID").val() }); //动态传参数
    },
    'onUploadError': function (event, queueId, fileObj, errorObj) {
        //alert(errorObj.type + "：" + errorObj.info);
    }
});
},10)
});

function ShowUpFiles(guid,str){
	$('#'+str).html('test');
}

function send(){
	var url = '${send}';
	center_iframe(url);
}
</script>
</body>
</html>
