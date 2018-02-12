<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
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
<script type="text/javascript">
	function onSubmit() {
		$.messager.confirm('提醒', '是否提交?', function(r) {
			if (r) {
				ajaxSub();
			}
		});
		return false;
	}

	function ajaxSub() {
		var subUrl = "${editUrl}";
		$.ajax({
					type : "POST",
					url : subUrl,
					data : $('#myfrom').serialize(),
					async : false,
					datatype : "json",
					success : function(data) {
						var obj = eval("[" + data + "]");
						if (obj.length > 0) {
							$.messager.alert(obj[0].title, obj[0].context,
									obj[0].type);
							if (obj[0].redUrl != '')
								center_iframe(obj[0].redUrl);
						}
					},
				});
	}
	
	function showOpenTime(){
		var value = $('#open_state').val();
		// 设置活动开启时间类型
		if(value == 0){
			$("#ordinaryTime").show();
			$("#openTime").hide();
			$("#ordinaryTimeShows").show();
			$("#openTimeShows").hide();
		}else{
			$("#ordinaryTime").hide();
			$("#openTime").show();
			$("#ordinaryTimeShows").hide();
			$("#openTimeShows").show();
		}
	}
	
</script>
</head>
<body>
	<div id="body2"
		style="width: 98%; margin: 0 auto; text-align: center; overflow: auto">
		<div style="width: 90%; text-align: center;">
			<form id="myfrom" target="_top">
				<c:if test="${isNew}">
					<input type="hidden" value="0" id="id" name="id" />
				</c:if>
				<c:if test="${!isNew}">
					<input type="hidden" value="${id}" id="id" name="id" />
				</c:if>
				<table border="1">
					<tr>
						<td style="background-color: rgb(184, 204, 228)">活动标题</td>
						<td><input class="input_from" name="timeCardName" type="text" value="${timeCardName}" /></td>
					
						<td style="background-color: rgb(184, 204, 228)" width="15%">PID:</td>
						<td><input class="input_from" name="cid" value="${cid}"
							style="width: 80px;" ${cidStr} type="text" /></td>
					</tr>	
						
						
				</table>
				
				</br></br>
				
				<table border="1">
						<tr>
						<td style="background-color: rgb(184, 204, 228)">活动时间 </td>
				
				
						<td>类型 <select name="open_state" id="open_state">
						<option id="0" value="0" <c:if test="${open_state == 0}">selected="true"</c:if> >普通时间</option>
						<option id="1" value="1" <c:if test="${open_state == 1}">selected="true"</c:if>>开服时间</option>
						</select>
						</td>
						<td>
						<div id="ordinaryTime">
						<input style="width: 150px;" name="openTime" type="date" value="${openTime}" />至
						<input style="width: 150px;" name="endTime" type="date" value="${endTime}" />
						</div>
						<div id="openTime">
						<input class="input_from" name="openDate" type="text" value="${openDate}" style="width: 80px;" />至
						<input class="input_from" name="endDate" type="text" value="${endDate}" style="width: 80px;" />
						</div>
						</td>	
						
						
						<td style="background-color: rgb(184, 204, 228)">活动展示时间 </td>
						
						<td>
						<div id="ordinaryTimeShows">
						<input style="width: 150px;" name="openTimeShow" type="date" value="${openTimeShow}" />至
						<input style="width: 150px;" name="endTimeShow" type="date" value="${endTimeShow}" />
						</div>
						<div id="openTimeShows">
						<input class="input_from" name="openDateShow" type="text" value="${openDateShow}" style="width: 80px;" />至
						<input class="input_from" name="endDateShow" type="text" value="${endDateShow}" style="width: 80px;" />
						</div>
						</td>
						
					</tr>
					
				</table>
				</br></br>
				
				
				<table>
					<tr></tr>
					<tr>
						<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
							height="50" width="200">备注</td>
						<td rowspan="2"><input name='remark' id='remark'
							class="easyui-textbox" data-options="multiline:true"
							value="${remark}" style="width: 1044px; height: 80px"></td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
				<br /> <br />
				
				<table>
				<tr height="100">
					<td style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
						width="120">配置服务器：</td>
						<td width="75%" height="30px"
							style="border: rgb(144, 144, 144) solid 1px">
							<%
							 	int[] srvIds = (int[]) request.getAttribute("selectSrvIds");
								WebUtils.createServerCheckBoxs(pageContext, "srvIds", "controls", null, srvIds);
							%>
						</td>
					<td style="border: rgb(144, 144, 144) solid 1px">此处为服务器选择，可进行单服选择操作，全服选择操作</td>
					</tr>
			</table>
			<br/><br/>
			
			
					<div id="new_hident" name="new_hident"
				<c:if test="${isNew}"> style="visibility: hidden;display: none" </c:if>>
				<table id="dg" title="限时卡包选择栏(不能添加重复ID)" style="height: 500px" toolbar="#toolbar"
							pagination="true" fitColumns="true" singleSelect="true">
					<thead>
						<tr>
						<th field="cardId" width="50">限时卡包ID选择</th>
						<th field="cardName" width="50">限时卡包</th>
						</tr>
					</thead>
				</table>
				<div id="toolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="newItem()">添加</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true" onclick="destroyItem()">删除</a>
				</div>
				</div>
			

				<div class="control-group"
					style="width: 100%; margin: 0 auto; text-align: center">
					<button type="submit" class="btn btn-success"
						onclick="return onSubmit();" style="width: 120px;">${btnStr} & 保存</button>
				</div>
			</form>
		</div>
	</div>

	<script type="text/javascript">
	
	$(document).ready(function() {
		showOpenTime();
		$('#open_state').change(function(){showOpenTime(); });
		var h = document.documentElement.clientHeight;
		$('#body2').css('height', h * 0.9);
		$('#dg').datagrid({
	        url: 'getRewAndReq?id=' + <%=request.getAttribute("id") %>
	    });
		
		
	});
	
	$(function() {
	    $('#dg').datagrid({
	        view: detailview,
	        detailFormatter: function(index, row) {
	            return '<div class="ddv"></div>';
	        },
	        onExpandRow: function(index, row) {
	            var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
	            ddv.panel({
	                border: false,
	                cache: true,
	                href: 'showAddCondition',
	                onLoad: function() {
	                    $('#dg').datagrid('fixDetailRowHeight', index);
	                    $('#dg').datagrid('selectRow', index);
	                    $('#dg').datagrid('getRowDetail', index).find('form').form('load', row);
	                }
	            });
	            $('#dg').datagrid('fixDetailRowHeight', index);
	        }
	    });
	});


	function saveItem(index) {
	    var row = $('#dg').datagrid('getRows')[index];
	    var url = row.isNewRecord ? 'saveRewAndReq?id=' + <%=request.getAttribute("id") %>:'updateRewAndReq?id=' + <%=request.getAttribute("id") %>+'&row=' + row.id+'&cardId=' + row.cardId;
	    $('#dg').datagrid('getRowDetail', index).find('form').form('submit', {
	        url: url,
	        onSubmit: function() {
	            return $(this).form('validate');
	        },
	        success: function(data) {
	            data = eval('(' + data + ')');
	            data.isNewRecord = false;
	            $('#dg').datagrid('collapseRow', index);
	            $('#dg').datagrid('updateRow', {
	                index: index,
	                row: data
	            });
	            
	            if ("undefined" != typeof(data["msg"])) 
			     	$.messager.alert("提醒",data["msg"],"error");
		     
	        }
	    });
	}


	function cancelItem(index) {
	    var row = $('#dg').datagrid('getRows')[index];
	    if (row.isNewRecord) {
	        $('#dg').datagrid('deleteRow', index);
	    } else {
	        $('#dg').datagrid('collapseRow', index);
	    }
	}

	function destroyItem() {
	    var row = $('#dg').datagrid('getSelected');
	    if (row) {
	        $.messager.confirm('Confirm', '你确定要删除这项数据?',
	        function(r) {
	            if (r) {
	                var index = $('#dg').datagrid('getRowIndex', row);
	                $.post('destroyRewAndReq', {
	                    row: row.id,
	                    id: <%=request.getAttribute("id") %>,
	                    timerCardId:row.cardId,
	                    timerCardName:row.cardName
	                },
	                function() {
	                    $('#dg').datagrid('deleteRow', index);
	                });
	            }
	        });
	    }
	}


	function newItem() {
	    $('#dg').datagrid('appendRow', {
	        isNewRecord: true
	    });
	    var index = $('#dg').datagrid('getRows').length - 1;
	    $('#dg').datagrid('expandRow', index);
	    $('#dg').datagrid('selectRow', index);
	}
	
	</script>
</body>
</html>
