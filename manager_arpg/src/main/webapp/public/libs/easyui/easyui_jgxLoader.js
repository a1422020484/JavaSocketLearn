/**
 * 页面加载等待页面
 * 
 * @author rodking
 * @date 2016/5/4
 * 
 */

//弹出加载层
 function load() {  
     $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
     $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载，请稍候...").appendTo("body").css({ display: "block",left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 160) / 2,"line-height": "0px"});  
 } 
 
 //取消加载层  
 function disLoad() {  
     $(".datagrid-mask").remove();  
     $(".datagrid-mask-msg").remove();  
 }