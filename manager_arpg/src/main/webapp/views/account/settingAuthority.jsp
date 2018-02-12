<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../index.jsp"%>
<%@ page import="proto.ProtocolWeb.AccountInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- 调用CSS，JS -->
<link href="${css}/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${css}/main/add.css" type="text/css" />
<link rel="stylesheet" href="${res}/utilLib/bootstrap.min.css" type="text/css" />
	
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/themes/color.css" />
<link rel="stylesheet" type="text/css" href="${libs}/easyui/demo/demo.css" />
		
<script type="text/javascript" src="${js}/main/jquery.min.js"></script>
<script type="text/javascript" src="${libs}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${libs}/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="${libs}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${js}/easyui_iframe.js"></script>

<style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
        .fitem input{
            width:160px;
        }
    </style>
</head>


<body>
<div id="cc" class="easyui-layout" style="width:90%;">
  <div id="northHead" data-options="region:'north'" style="height:35px;background-color:#E7F0FF;"> 
  <div  class="easyui-panel" data-options="fit:true,border:false"  id="settingAuthorityPanel"> </div></div>
    <div data-options="region:'east',title:'菜单',split:true,collapsible:false" style="width:49%;">   
    <ul id="memuTree" class="easyui-tree" data-options="url:'${memuTableUrl}',method:'get',checkbox:true"></ul>   
    </div>
    <div data-options="region:'west',title:'权限',split:true,collapsible:false" style="width:49%;" >
    <ul id="authorityTree" class="easyui-tree" data-options="url:'${authorityTableUrl}',method:'get',checkbox:true"></ul>    
    </div>
</div>

    <div id="dlg" class="easyui-dialog" style="width:400px;height:180px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons" data-options="modal:true">
        <form id="fm"  method="post" >
            <div class="fitem">
                <label>权限名称:</label>
                <input name="name" class="easyui-textbox"required="true">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="insertAuthority()" style="width:90px">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
    </div>
    
   
<script type="text/javascript">
    var checkedNode = null;
    
    var h = document.documentElement.clientHeight;
    $('#cc').css('height', h * 0.9);
    
    var width =document.getElementById("northHead").style.width
    var height =document.getElementById("northHead").offsetHeight
    
    $('#settingAuthorityPanel').panel({
    	     width:width,
    	    height:height,
    	    title:'权限-菜单设置',
    	    tools:[{
    	    iconCls:'icon-add',
    	    handler:function(){openInsertDailog();}
    	    },{
    	    iconCls:'icon-save',
    	    handler:function(){
    	    	saveAuthority();
    	    }
    	    }]
    	}); 

    
    $('#authorityTree').tree({

    	onBeforeCheck:function(node,checked)
    	{			
    		var menuNodes = $('#memuTree').tree('getChecked', ['checked','indeterminate']);
			if(menuNodes!=null)
			{
			for(var item in menuNodes) 
			{
				$('#memuTree').tree('uncheck', menuNodes[item].target);
			}			
			}    		    	
    	}
    });
    
    
     $('#authorityTree').tree({

    	onCheck:function(node,checked){
    		
    		if(checked==true){
    			
    			if(checkedNode!=null)
    			{
        			if(checkedNode===node){
        				//alert(checkedNode===node);
        			}
        			else{
        			$('#authorityTree').tree('uncheck', checkedNode.target);
        			}
                }	
    			
    			var timestamp = Date.parse(new Date()); 
    		$.get('${getSelectedMenu}',{id:node['id'],timestamp:timestamp},function(data,textStatus) //清除浏览器缓存
    				{
    			       var array= eval("("+data+")");
    			       for(var item in array)
    			       {    				
    			    	   var node = $('#memuTree').tree('find', array[item]);    				
    			    	   if(node!=null&&node.children==null)
    			    	   {    				
    			    		   $('#memuTree').tree('check', node.target);    				
    			    		   var pnode =  $('#memuTree').tree('getParent',node.target);    				
    			    		   if(pnode!=null)
    			    		   {
    				
    			    			   $('#memuTree').tree('update', {target: pnode.target,state: 'open'});
    				
    			    		   }
    				
    			    	   }
    			
    			       }
    		
    				});
    		
    		checkedNode= node;    		
    		
    		}    		    		    	
    	}    
     });
    
    
  function saveAuthority(){
	  
	  var authorityNodes = $('#authorityTree').tree('getChecked');
	  var menuNodes = $('#memuTree').tree('getChecked', ['checked','indeterminate']);
	  if(menuNodes==null||menuNodes.length==0){
		  $.messager.alert('提醒','请至少选择一项菜单数据！','info');
		  return ;
	  }
	  if(authorityNodes==null||authorityNodes.length==0||authorityNodes.length>1){
		  $.messager.alert('提醒','请选择一项权限数据！','info');
		     return ;
	  }
	  
	  var arrayMenus = new Array();
	  for(var item in menuNodes ){
		  arrayMenus[item] =menuNodes[item].id
	  }
	  var authorityNodesId=authorityNodes[0].id
	   
	 var strjsonMenus = JSON.stringify(arrayMenus); //后台不接收数组，所以 转json。再转String 
	  
	  $.post('${updateOrInsertAuthority}',{authorityNodesId:authorityNodesId,authorityNodesIds:strjsonMenus},function(data,textStatus){
		  
		  if(data!=null){
			  data =  eval("("+data+")" );
		  }
		  $.messager.alert(data.title,data.context,data.type);
		  
		 // alert(data);
});
	  
	  
  }
  
  function openInsertDailog(){
      $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增权限');
      $('#fm').form('clear');
  }
  
  
  $('#fm').form({
	     url:'${insertAuthority}',
	      success:function(data){
	    	  if(data!=null){
				  data =  eval("("+data+")" );
			  }
	    	  $.messager.alert(data.title,data.context,data.type);
	    	  $('#fm').form('clear');
	    	  $('#dlg').dialog('close'); 
	      }
	  });

  
  function insertAuthority(){
	  $('#fm').submit();
	  
  }
    
</script>

</body>



</html>
