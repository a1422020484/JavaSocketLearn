<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="cn.saturn.web.controllers.activity.dao.PresetActivity"%>
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
<script type = "text/javascript">
function onSubmit()
{
	 $.messager.confirm('提醒', '是否提交?', function(r){
       if (r){
        	ajaxSub();
        }
     });
	return false;
}

function showOpenTime(){
	var value = $('#open_state').val();
	// 设置活动开启时间类型
	if(value == 0){
		$("#ordinaryTime").show();
		$("#openTime").hide();
	}else{
		$("#ordinaryTime").hide();
		$("#openTime").show();
	}
}

function loadType(){
	var presetType31 = <%=PresetActivity.activeType_tian_tian%>;
	var url = 'getById2?id=' + $('#type').find('option:selected').attr('id');
	var id = $('#type').find('option:selected').attr('value');
	var ids = id.split(",");
	
    $.ajax({
        type: "GET",
        url: url,
        datatype: "json",
        success: function(data) {
            var obj = eval("[" + data + "]");
            if (obj.length > 0) {
              
                if(ids[0] == presetType31)
					$('#desc').html(obj[0].condition_desc+" 1 表示连续, 其他表示累计");
                else
              		$('#desc').html(obj[0].condition_desc);
                
   				loadAllGrid();
            }
        },
    });
    
    showPageByType(ids[0]);
}

function chanegType(){
	var url = 'getById?id=' + $('#type').find('option:selected').attr('id');
	var id = $('#type').find('option:selected').attr('value');
	var ids = id.split(",");

    $.ajax({
        type: "GET",
        url: url,
        datatype: "json",
        success: function(data) {
            var obj = eval("[" + data + "]");
            if (obj.length > 0) {
                $('#intro').val(obj[0].info);
                $('#tips').val(obj[0].tips);
                $('#speArg').attr("value", obj[0].activitySpeArgs);
                $('#icon').attr("value", obj[0].icon);
                $('#order').attr("value", obj[0].activityOrder);
                if(ids[0] == presetType31)
              	{
					$('#desc').html(obj[0].condition_desc+" 1 表示连续, 其他表示累计");
              	}else
              		$('#desc').html(obj[0].condition_desc);
                
                loadAllGrid();
            }
        },
    });
	
    showPageByType(ids[0]);
}

function chanegSpeArgType(){
	var id = $('#speArgType').find('option:selected').attr('id');
	
	var data = '';
	if(id == 1)
		data = [{field:'cz_1',title:'累计充值元',width:200},{field:'cs_1',title:'奖励次数',width:200}];
	else if(id == 2)
		data = [{field:'cz_2',title:'单笔充值元',width:200},{field:'cs_2',title:'奖励次数',width:200}];
	else if(id == 3)
		data = [{field:'item_id',title:'消耗道具',width:200},{field:'item_name',title:'消耗道具名称',width:200},{field:'cz_3',title:'消耗道具数量',width:200},{field:'cs_3',title:'奖励次数',width:200}];
	else
		data = [{field:'cz_4',title:'累计水晶10连抽次数',width:200},{field:'cs_4',title:'奖励次数',width:200}];
	$("#dg30_addTimes").datagrid({columns:[data]});
}

function loadAllGrid(){
	var selectId = $('#type').find('option:selected').attr('id');
    
	<!--- 非30号转盘类型 活动物品奖励,以及条件 --->
    $('#dg').datagrid({
        url: 'getRewAndReq?id=' + <%=request.getAttribute("id") %>+'&a_id=' + selectId
    });

    <!--- 30号转盘类型 ,转盘物品清单--->
    $('#dg30_item_lists').datagrid({
        url: 'getType30ItemList?id=' + <%=request.getAttribute("id") %>+'&a_id=' + selectId
    });
    
 	<!--- 30号转盘类型 ,转盘奖励次数--->
   	$('#dg30_addTimes').datagrid({ 
   	 url: 'getType30AddTimes?id=' + <%=request.getAttribute("id") %>+'&a_id=' + selectId
    }); 
}

// 根据类型显示页面
function showPageByType(typeId){
	
	var presetType30 = <%=PresetActivity.activeType_zhuan_pan%>;
	var presetType31 = <%=PresetActivity.activeType_tian_tian%>;
	var presetType32 = <%=PresetActivity.activeType_shen_sou %>;
	var presetType50 = <%=PresetActivity.activeType_quanfu_huodong%>;
	
	$('#icon').removeAttr("disabled");
	$('#order').removeAttr("disabled");
	$('#clazz').removeAttr("disabled");
	showType30(false);
	
	if(typeId == presetType31)
  	{	
  		$('#presetTyp31').show();
  		$('#presetTyp32').hide();
  		$('#presetTyp0').hide();
  		$('#presetTyp0Value').show();
  		
  	}else if(typeId == presetType32)
  	{
  		$('#presetTyp32').show();
  		$('#presetTyp0').hide();
  		$('#presetTyp31').hide();
  		$('#presetTyp0Value').show();
  		
  		$('#icon').attr("disabled","disabled");
  		$('#order').attr("disabled","disabled");
  		$('#clazz').attr("disabled","disabled");
  	}else if(typeId == presetType30){
  		
  		$('#presetTyp32').hide();
  		$('#presetTyp31').hide();
  		
  		$('#icon').attr("disabled","disabled");
  		$('#order').attr("disabled","disabled");
  		$('#clazz').attr("disabled","disabled");
  		showType30(true);
  	}else if(typeId == presetType50){
  		
  		//$('#presetTyp30').hide();
  		$('#presetTyp32').hide();
  		$('#presetTyp31').hide();
  		
  		$('#presetTyp0').show();
  		$('#presetTyp0Value').hide();
  		$('div.presetTyp30').each(function(){
 	       $(this).hide();
 	 	});
  		$('#presetTyp30selId').hide();
  		$('.presetTyp50').show();
  		
  	}
  	else{
  		$('#presetTyp0').show();
  		$('#presetTyp32').hide();
  		$('#presetTyp31').hide();
  		$('#presetTyp0Value').show();
 
  	}
}

function showType30(isShow){
	$('#presetTyp0Value').show();
	$('#presetTyp0').hide();
	$('.presetTyp50').hide();
	
	$('div.presetTyp30').each(function(){
	       $(this).hide();
	 });
	if(isShow){
		$('#presetTyp0Value').hide();
		$('#presetTyp30').show();
  		$('#presetTyp0').show();
  		 $('div.presetTyp30').each(function(){
  		       $(this).show();
  		 });
	}
}

function ajaxSub()
{
	 var subUrl = "${eidtUrl}";
	   $.ajax({
	    type: "POST",
	    url: subUrl,
	    data:$('#myfrom').serialize(),
	    async: false,
	    datatype: "json",
	    success: function(data) {
	      var obj = eval("[" + data + "]");
	      if (obj.length > 0) {
	        $.messager.alert(obj[0].title,obj[0].context,obj[0].type);
	        if(obj[0].redUrl!='')
		      center_iframe(obj[0].redUrl);
	        }
	     },
	   });
}
</script>
</head>
<body>
	<div id="body2" 
	 	style="width: 98%; margin: 0 auto; text-align: center;overflow:auto " >
	 	<div style="width: 90%; text-align: center;">
		<form id="myfrom" target="_top">
			<c:if test="${isNew}">
				<input type="hidden" value="0"  id="id" name="id" />
				<input type="hidden" value='${requires}'  id="requires" name="requires" />
				<input type="hidden" value='${awards}'  id="awards" name="awards" />
				<input type="hidden" value='${itemList}'  id="itemList" name="itemList" />
				<input type="hidden" value='${turnTableCsAwards}'  id="turnTableCsAwards" name="turnTableCsAwards" />
			</c:if>
			 <c:if test="${!isNew}">
				<input type="hidden" value="${id}"  id="id" name="id" />
			</c:if>
			<table border="1">
				<tr>
					<td style="background-color: rgb(184, 204, 228)">活动标题</td>
					<td><input class="input_from" name="tital" type="text" value="${tital}" /></td>
					<td style="background-color: rgb(184, 204, 228)">活动ID:</td>
					<td><input class="input_from" name="pid" type="text" value="${pid}" style="width: 100px;" /></td>
					<td> <input class="input_from" name="cid" value="${cid}" style="width: 80px;" ${cidStr} type="text" /></td>
					
				</tr>
			</table>
			<br /> <br />
			<table border="1">
				<tr>
					<td style="background-color: rgb(184, 204, 228)">活动时间 </td>
					<td>类型 
					<select name="open_state" id="open_state">
						<option id="0" value="0" <c:if test="${open_state == 0}">selected="true"</c:if> >普通时间</option>
						<option id="1" value="1" <c:if test="${open_state == 1}">selected="true"</c:if>>开服时间</option>
					</select>
					</td>
					<td>
						<div id="ordinaryTime">
						
						<input style="width: 150px;" name="startTime" type="date" value="${startTime}" /> 
						<input style="width: 150px;" name="endTime" type="date" value="${endTime}" />
						
						<!---
						<input class="easyui-datetimebox" name="startTime"     
        					data-options="required:true,showSeconds:true"   value="${startTime}" style="width:150px">  
						
						<input class="easyui-datetimebox" name="endTime"     
        					data-options="required:true,showSeconds:true"  value="${endTime}" style="width:150px">  
						--->
						</div>
						<div id="openTime">
						<input class="input_from" name="openDate" type="text" value="${openDate}" style="width: 80px;" />
						<input class="input_from" name="closeDate" type="text" value="${closeDate}" style="width: 80px;" />
						</div>
					</td>
					<td style="background-color: rgb(184, 204, 228)">开启时段</td>
					<td>
					    <input class="input_from"
							name="period" id="period" value="${period}" style="width: 150px;"
							type="text" /></div>
					</td>
					<td style="background-color: rgb(184, 204, 228)">广告图 </td>
					<td>
					    <input class="input_from"
							name="winPhoto" id="winPhoto" value="${winPhoto}" style="width: 200px;"
							type="text" /></div>
					</td>
					<td style="background-color: rgb(184, 204, 228)">广告图排序 </td>
					<td>
					    <input class="input_from"
							name="winPhotoIndex" id="winPhotoIndex" value="${winPhotoIndex}" style="width: 100px;"
							type="text" /></div>
					</td>
				</tr>
			</table>
			
			<br /> <br />
			
			<table border="1">
				<tr>
					<td style="background-color: rgb(184, 204, 228);" width="80">活动类型</td>
					<td align="left" width="10%">
					<select name="type" id="type">
						<option id="-1" value="-1,-1" <c:if test="${typeId == 0}">selected="true"</c:if>>--请选择--</option>
						<c:forEach items="${actModel}" var="act">
							<option id="${act.getId()}" value="${act.getType()},${act.getId()}"
							<c:if test="${typeId == act.getId()}">selected="true"</c:if>>${act.getName()}</option>
						</c:forEach>
					</select>
					<td style="background-color: rgb(184, 204, 228);" width="80">活动分类</td>
					<td align="left" width="10%">
							<%
								//状态
								Integer clazzObj = (Integer) request.getAttribute("clazz");
								int clazz = (clazzObj != null) ? clazzObj : 0;
								WebUtils.createSelected(pageContext, "clazz", "input_select", PresetActivity.clazzStrs, clazz, false);
							%>
					</td>
						<td style="background-color: rgb(184, 204, 228);" width="80">活动优先级</td>
						<td align="left" width="10%"><input class="input_from"
							name="order" id="order" value="${order}" style="width: 80px;"
							type="text" /></td>
							
						<td style="background-color: rgb(184, 204, 228);" width="80">活动图标</td>
						<td align="left" width="10%"><input class="input_from"
							name="icon" id="icon" type="text" value="${icon}" /></td>
						<td style="background-color: rgb(184, 204, 228);" width="80">
						<div id="presetTyp31" name="presetTyp31">充值金额</div>
						<div id="presetTyp0" name="presetTyp0">特殊参数</div>
						<div id="presetTyp32" name="presetTyp32">展示神兽图片</div>
						</td>
						<td align="left" width="10%">
						<div id="presetTyp0Value" name="presetTyp0Value">
						<input class="input_from"
							name="speArg" id="speArg" value="${speArg}" style="width: 80px;"
							type="text" /></div>
						<div class="presetTyp30"  id=presetTyp30selId>
						<select name="speArgType" id="speArgType">
							<option id="1" value="1" <c:if test="${speArg == 1}">selected="true"</c:if>>累计充值达到 x元,增加 N 次机会</option>
							<option id="2" value="2" <c:if test="${speArg == 2}">selected="true"</c:if>>单笔充值到达 x元,增加 N 次机会</option>
							<option id="3" value="3" <c:if test="${speArg == 3}">selected="true"</c:if>>累计消耗 x 个 y 道具,增加 N 次机会 </option>
							<option id="4" value="4" <c:if test="${speArg == 4}">selected="true"</c:if>>累计水晶10 连抽 x 次,增加 N 次机会</option>
						</select>
						</div>
						
						<div class="presetTyp50" >
						<select name="speArgTypequanfu" id="speArgType">
							<option id="1" value="1" <c:if test="${speArg == 1}">selected="true"</c:if>>所选定的服务器在活动期间内充值金额总共达到X元</option>
							<option id="2" value="2" <c:if test="${speArg == 2}">selected="true"</c:if>>所选定的服务器在活动期间内总共有进行了Y笔金额为Z的充值</option>
							<option id="3" value="3" <c:if test="${speArg == 3}">selected="true"</c:if>>所选定的服务器在活动期间内总共有Y人进行了Z的充值 </option>
							<option id="4" value="4" <c:if test="${speArg == 4}">selected="true"</c:if>>所选定的服务器在活动期间内总共消耗数量为X的水晶</option>
							<option id="5" value="5" <c:if test="${speArg == 5}">selected="true"</c:if>>所选定的服务器在活动期间内总共进行了X次水晶扭蛋</option>
							<option id="6" value="6" <c:if test="${speArg == 6}">selected="true"</c:if>>所选定的服务器在活动期间内总共击杀了X只精灵</option>
							<option id="7" value="7" <c:if test="${speArg == 7}">selected="true"</c:if>>所算定的服务器在活动期间内总共击杀了ID为X的Y只精灵</option>
							<option id="8" value="8" <c:if test="${speArg == 8}">selected="true"</c:if>>所选定的服务器在活动期间内总共进行了X场 皇家对战/血战到底/世界联赛 的游戏</option>
						</select>
						</div>
						
						
						</td>
				</tr>
			</table>		
			<br /> <br />
			<table border="1">
				<tr>
					<td style="background-color: rgb(184, 204, 228);" width="80">额外参数</td>
					<td><input class="input_from" name="extraParams" type="text" value="${extraParams}" style="width: 500px;" /></td>
				</tr>
			</table>
			
			<br />
			<div class="presetTyp30" >
			<table border="1">
				<tr>
				<td style="background-color: rgb(184, 204, 228);" width="80">每次转盘消耗道具</td>
				<td align="left" width="10%"><input class="easyui-combobox" id="useItemId" name="useItemId"
				data-options="valueField:'id',textField:'name'">
				</td>
				<td style="background-color: rgb(184, 204, 228);" width="80">道具消耗数量</td>
				<td align="left" width="10%"><input class="input_from" name="useNum" id="useNum" type="text" value="${useNum}" /></td>
				<td style="background-color: rgb(184, 204, 228);" width="80">每日免费转盘次数</td>
				<td align="left" width="10%"><input class="input_from" name="freeNum" id="freeNum" type="text" value="${freeNum}" /></td>
				<td style="background-color: rgb(184, 204, 228);" width="80">是否开启排行榜奖励</td>
				<td><select name="openRanking" id="openRanking">
							<option id="0" value="0" <c:if test="${openRanking == 0}">selected="true"</c:if>>关闭</option>
							<option id="1" value="1" <c:if test="${openRanking == 1}">selected="true"</c:if>>开启</option>
					</select>
				</td>		
			</table>
			</div>
			<br/>
			<table>
				<tr></tr>
				<tr>
					<td
						style="background-color: rgb(184, 204, 228); border: rgb(144, 144, 144) solid 1px"
						height="50" width="200">活动介绍</td>
					<td rowspan="2">
					<textarea name='intro' id='intro' style="width: 1044px; height: 80px">${intro}</textarea>
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
						height="50" width="200">活动提醒</td>
					<td rowspan="2">
					<textarea name='tips' id='tips' style="width: 1044px; height: 80px">${tips}</textarea>
				</tr>
				<tr>
					<td></td>
				</tr>
			</table>
			<br />
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
			<br />
				
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
			<br/>
			<div id="new_hident" name="new_hident"
			<c:if test="${isNew}"> style="visibility: hidden;display: none" </c:if>>
				
				<div class="presetTyp30" >
				<br></br>
				<table id="dg30_item_lists" title="轮盘物品清单配置" style="height: 500px"
				 toolbar="#toolbar30_item_lists" pagination="true" fitColumns="true" singleSelect="true">
					<thead>
						<tr>
							<th field="id" width="50">序号</th>
							<th field="item_id" width="50">物品id</th>
							<th field="item_name" width="50">物品名称</th>
							<th field="item_num" width="50">物品数量</th>
							<th field="item_order" width="50">物品排序</th>
							<th field="item_weight" width="50">道具权重</th>
							<th field="item_min_cs" width="50">最少保底数</th>
							<th field="item_max_cs" width="50">必出次数</th>
						</tr>
					</thead>
				</table>
				<div id="toolbar30_item_lists">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="newItem('#dg30_item_lists')">添加</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="destroyItem('#dg30_item_lists','delType30ItemList')">删除</a>
				</div>
				</div>
				<div class="presetTyp30" >
				<br></br>
				<div id="dg30_addTimes" title="轮盘次数奖励配置" style="height: 500px" 
				toolbar="#toolbardg30_addTimes" pagination="true" fitColumns="true" singleSelect="true">
				</div>
				<div id="toolbardg30_addTimes">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="newItem('#dg30_addTimes')">添加</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="destroyItem('#dg30_addTimes','delType30AddTimes')">删除</a>
				</div>
				</div>
				<br /> <br />
				
			<div id="desc" name="desc"></div>
				<table id="dg" title="活动奖励  (或者 转盘排行榜奖励) "  style="height: 500px" toolbar="#toolbar"
					pagination="true" fitColumns="true" singleSelect="true">
					<thead>
						<tr>
							<th field="id" width="50">序号</th>
							<th field="req" width="50">完成条件</th>
							<th field="name" width="50">成就名称</th>
							<th field="item_id_01" width="50">物品id_01</th>
							<th field="item_name_01" width="50">物品名称_01</th>
							<th field="item_num_01" width="50">物品数量_01</th>
							<th field="item_id_02" width="50">物品id_02</th>
							<th field="item_name_02" width="50">物品名称_02</th>
							<th field="item_num_02" width="50">物品数量_02</th>
							<th field="item_id_03" width="50">物品id_03</th>
							<th field="item_name_03" width="50">物品名称_03</th>
							<th field="item_num_03" width="50">物品数量_03</th>
							<th field="item_id_04" width="50">物品id_04</th>
							<th field="item_name_04" width="50">物品名称_04</th>
							<th field="item_num_04" width="50">物品数量_04</th>
						</tr>
					</thead>
				</table>
			<div id="toolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="newItem('#dg')">添加</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="destroyItem('#dg','delAwardAndReq')">删除</a>
			</div>		
			</div>
			
			<br/><br/>
			<div class="control-group"
				style="widrh: 100%; margin: 0 auto; text-align: center">
				<button type="submit" class="btn btn-success"
					onclick="return onSubmit();" style="width: 120px;">${btnStr}</button>
			</div>
		</form>
		</div>
	</div>

	<script type = "text/javascript"> 
	<!---rew type 01--->
	$(document).ready(function() {
		$('#dg').datagrid({
	         pageSize:250,
	         pageList: [20, 100, 250,500],
	 })
	
	var h = document.documentElement.clientHeight;
	loadType();
	showOpenTime();
	chanegSpeArgType();
	
	// 设置更改 open_state 选项点击事件
	$('#open_state').change(function(){showOpenTime(); });
    // 设置更改 type 选项点击事件
    $('#type').change(function() {chanegType();});
    
    // 设置更改 speArgType 选项点击事件
    $('#speArgType').change(function() {chanegSpeArgType();});
    
   	$.ajax({type : "POST",
    	url : "../item/getSelect2EasyUiAll",
    	datatype : "json",
    	success : function(data) {
    	var obj = eval(data);
    	if (obj.length > 0) {
    		$('#useItemId').combobox('loadData', obj);
    	}
    	},
    });
   	
   	$('#useItemId').combobox('setValue', '${useItemId}');
    
	$('#body2').css('height', h * 0.9);
});
	

<!--- 非30号转盘类型 活动物品奖励,以及条件 --->
function refTable() {
    var row = $('#dg').datagrid('getRows');
    // 修改后的数据放到
    $('#dg').datagrid('loadData', row);
}

function easyUiLoadGrid(item,url){
	 $(item).datagrid({
	        view: detailview,
	        detailFormatter: function(index, row) {
	            return '<div class="ddv"></div>';
	        },
	        onExpandRow: function(index, row) {
	            var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
	            ddv.panel({
	                border: false,
	                cache: true,
	                href: url,
	                onLoad: function() {
	                    $(item).datagrid('fixDetailRowHeight', index);
	                    $(item).datagrid('selectRow', index);
	                    $(item).datagrid('getRowDetail', index).find('form').form('load', row);
	                }
	            });
	            $(item).datagrid('fixDetailRowHeight', index);
	        }
	    });
}

$(function() {
	<!--- 非30号转盘类型 活动物品奖励,以及条件 --->
	easyUiLoadGrid('#dg','showRewAndReq');
    
    <!--- 30号转盘类型 ,转盘物品清单--->
	easyUiLoadGrid('#dg30_item_lists','showType30ItemList');
	
	<!--- 30号转盘类型 ,转盘奖励次数--->
	easyUiLoadGrid('#dg30_addTimes','showType30AddTimes');
});

function saveItem(index,item,saveUrl,updateUrl) {
    var row = $(item).datagrid('getRows')[index];
    var selectId = $('#speArgType').find('option:selected').attr('id');
    var url = row.isNewRecord ? saveUrl+'?id=' + <%=request.getAttribute("id") %>+"&type="+selectId: updateUrl+'?id=' + <%=request.getAttribute("id") %>+'&row=' + row.id+"&type="+selectId;
    $(item).datagrid('getRowDetail', index).find('form').form('submit', {
        url: url,
        onSubmit: function() {
            return $(this).form('validate');
        },
        success: function(data) {
            data = eval('(' + data + ')');
            data.isNewRecord = false;
            $(item).datagrid('collapseRow', index);
            $(item).datagrid('updateRow', {
                index: index,
                row: data
            });
            
            if ("undefined" != typeof(data["msg"])) 
		     	$.messager.alert("提醒",data["msg"],"error");
	     
        }
    });
}
function cancelItem(index,item) {
    var row = $(item).datagrid('getRows')[index];
    if (row.isNewRecord) {
        $(item).datagrid('deleteRow', index);
    } else {
        $(item).datagrid('collapseRow', index);
    }
}
function destroyItem(item,delUrl) {
    var row = $(item).datagrid('getSelected');
    if (row) {
        $.messager.confirm('Confirm', '你确定要删除这项数据?',
        function(r) {
            if (r) {
                var index = $(item).datagrid('getRowIndex', row);
                $.post(delUrl, {row: row.id,id: <%=request.getAttribute("id") %>},
                function() {
                    $(item).datagrid('deleteRow', index);
                });
            }
        });
    }
}
function newItem(item) {
    $(item).datagrid('appendRow', {
        isNewRecord: true
    });
    var index = $(item).datagrid('getRows').length - 1;
    $(item).datagrid('expandRow', index);
    $(item).datagrid('selectRow', index);
} 



</script>
</body>
</html>
