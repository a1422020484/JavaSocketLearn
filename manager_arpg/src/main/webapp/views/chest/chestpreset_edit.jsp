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
		var subUrl = "${eidtUrl}";
		$
				.ajax({
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

		refTable();
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
					<input type="hidden" value='${requires}' id="requires"
						name="requires" />
					<input type="hidden" value='${awards}' id="awards" name="awards" />
				</c:if>
				<c:if test="${!isNew}">
					<input type="hidden" value="${id}" id="id" name="id" />
				</c:if>
				<table border="1">
					<tr>
						<td style="background-color: rgb(184, 204, 228)" width="15%">ID:</td>
						<td><input class="input_from" name="cid" value="${cid}"
							style="width: 80px;" ${cidStr} type="text" /></td>
						<td style="background-color: rgb(184, 204, 228)" width="15%">名称</td>
						<td><input class="input_from" name="name" type="text"
							value="${chestName}" /></td>
					</tr>
				</table>
				<br /> <br />
				<table border="1">
					<tr>
						<td style="background-color: rgb(184, 204, 228)">品阶</td>
						<td align="left" width="15%"><select name="quility"
							id="quility">
								<option id="-1" value="-1">--请选择--</option>
								<c:forEach var="entity" items="${quils}">
									<option id="${entity.key}" value="${entity.key}"
										<c:if test="${quility == entity.key}">selected="true"</c:if>>${entity.value}</option>
								</c:forEach>
						</select></td>
						<td style="background-color: rgb(184, 204, 228)">类型</td>
						<td align="left" width="15%"><select name="type" id="type">
								<option id="-1" value="-1">--请选择--</option>
								<c:forEach var="entity" items="${types}">
									<option id="${entity.key}" value="${entity.key}"
										<c:if test="${type == entity.key}">selected="true"</c:if>>${entity.value}</option>
								</c:forEach>
						</select></td>
						<td style="background-color: rgb(184, 204, 228)">随机产出物品种类</td>
						<td align="left" width="15%"><input class="input_from"
							name="item_num" id="item_num" value="${item_num}"
							style="width: 80px;" type="text" /></td>
					</tr>
				</table>
				<br /> <br />

				<table>
					<tr></tr>
					<tr>
						<td
							style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
							height="50" width="200">描述</td>
						<td rowspan="2"><input name='desc' id='desc'
							class="easyui-textbox" data-options="multiline:true"
							value="${chestDesc}" style="width: 1044px; height: 80px"></td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
				<br /> <br />
				<table>
					<tr></tr>
					<tr>
						<td
							style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
							height="50" width="200">备注</td>
						<td rowspan="2"><input name='remark' id='remark'
							class="easyui-textbox" data-options="multiline:true"
							value="${remark}" style="width: 1044px; height: 80px"></td>
					</tr>
					<tr>
						<td></td>
					</tr>
				</table>
				<br /> <br /> <br />
				<div id="new_hident" name="new_hident"
					<c:if test="${isNew}"> style="visibility: hidden;display: none" </c:if>>
					<table id="dg" title="宝箱物品" style="height: 500px"
						toolbar="#toolbar" pagination="true" fitColumns="true"
						singleSelect="true">
						<thead>
							<tr>
								<th field="id" width="50">id</th>
								<th field="item_id" width="50">物品ID</th>
								<th field="item_name" width="50">物品名称</th>
								<th field="weight" width="50">物品权重</th>
								<th field="num" width="50">物品固定数量</th>
								<th field="randomListId" width="50">物品随机数量</th>
							</tr>
						</thead>
					</table>
					<div id="toolbar">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-add" plain="true" onclick="newItem()">添加</a> <a
							href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-remove" plain="true" onclick="destroyItem()">删除</a>
					</div>
					<br /> <br />
				</div>
				<div class="control-group"
					style="widrh: 100%; margin: 0 auto; text-align: center">
					<button type="submit" class="btn btn-success"
						onclick="return onSubmit();" style="width: 120px;">${btnStr}</button>
				</div>
			</form>
		</div>
	</div>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var h = document.documentElement.clientHeight;
							$('#body2').css('height', h * 0.9);

							var selectId = $('#type').find('option:selected')
									.attr('id');
							$('#dg')
									.datagrid(
											{
												url : 'getChestRew?id='
														+
	<%=request.getAttribute("id")%>
		+ '&a_id=' + selectId
											});

							$('#type')
									.change(
											function() {
												var url = 'getById?id='
														+ $('#type')
																.find(
																		'option:selected')
																.attr('id');
												$
														.ajax({
															type : "GET",
															url : url,
															datatype : "json",
															success : function(
																	data) {
																var obj = eval("["
																		+ data
																		+ "]");
																if (obj.length > 0) {
																	//var parsedJson = $.parseJSON(obj); 
																	//alert(obj);
																	$('#intro')
																			.textbox(
																					'setValue',
																					obj[0].info);
																	$('#tips')
																			.textbox(
																					"setValue",
																					obj[0].tips);
																	$('#speArg')
																			.attr(
																					"value",
																					obj[0].activitySpeArgs);
																	$('#icon')
																			.attr(
																					"value",
																					obj[0].icon);
																	$('#order')
																			.attr(
																					"value",
																					obj[0].activityOrder);

																	console
																			.log($('#dg'));
																	$('#dg')
																			.datagrid(
																					{
																						url : 'getChestRew?id='
																								+
	<%=request.getAttribute("id")%>
		+ '&a_id='
																								+ obj[0].id
																					});
																	//refTable();
																}
															},
														});
											});

						});

		function refTable() {
			var row = $('#dg').datagrid('getRows');
			// 修改后的数据放到
			$('#dg').datagrid('loadData', row);
		}
		<!-- - dg - -->
		$(function() {
			$('#dg').datagrid(
					{
						view : detailview,
						detailFormatter : function(index, row) {
							return '<div class="ddv"></div>';
						},
						onExpandRow : function(index, row) {
							var ddv = $(this).datagrid('getRowDetail', index)
									.find('div.ddv');
							ddv.panel({
								border : false,
								cache : true,
								href : 'showCondition',
								onLoad : function() {
									$('#dg').datagrid('fixDetailRowHeight',
											index);
									$('#dg').datagrid('selectRow', index);
									$('#dg').datagrid('getRowDetail', index)
											.find('form').form('load', row);
								}
							});
							$('#dg').datagrid('fixDetailRowHeight', index);
						}
					});
		});

		function saveItem(index) {
			var row = $('#dg').datagrid('getRows')[index];
			var type = $('#type').val();
			console.log(type);
			var url = row.isNewRecord ? 'saveChestRew?id='
					+
	<%=request.getAttribute("id")%>
		: 'updateChestRew?id='
					+
	<%=request.getAttribute("id")%>
		+ '&row=' + row.id;
			url = url + '&type=' + type;
			$('#dg').datagrid('getRowDetail', index).find('form').form(
					'submit', {
						url : url,
						onSubmit : function() {
							return $(this).form('validate');
						},
						success : function(data) {
							data = eval('(' + data + ')');
							//data.isNewRecord = false;

							if ("undefined" != typeof (data["msg"])) {
								if (row.isNewRecord) {
									$('#dg').datagrid('deleteRow', index);
								}
								$.messager.alert("提醒", data["msg"], "error");
							} else {
								$('#dg').datagrid('collapseRow', index);
								$('#dg').datagrid('updateRow', {
									index : index,
									row : data
								});
							}
							refTable();
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

			refTable();
		}
		function destroyItem() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$.messager.confirm('Confirm', '你确定要删除这项数据?', function(r) {
					if (r) {
						var index = $('#dg').datagrid('getRowIndex', row);
						$.post('destroyChestReq', {
							row : row.id,
							id :
	<%=request.getAttribute("id")%>
		}, function() {
							$('#dg').datagrid('deleteRow', index);
						});
					}
				});
			}

			refTable();
		}
		function newItem() {
			$('#dg').datagrid('appendRow', {
				isNewRecord : true
			});

			var index = $('#dg').datagrid('getRows').length - 1;
			$('#dg').datagrid('expandRow', index);
			$('#dg').datagrid('selectRow', index);
		}
	</script>
</body>
</html>
