function center_iframe(url) {
	var tt = top.jQuery('#tb');
	var h = document.documentElement.clientHeight;
	var tab = tt.tabs('getSelected');
	tab.css('width', h*0.9);
	
	tt.tabs('update', {
	 tab : tab,
	 options : {
		content : '<iframe scrolling="auto" frameborder="0"  src="'
				+ url + '" style="width: 100%;height:100%" ></iframe>',
		closable : true,
		selected : true
	 }
	});
}


function easyUI_alert(title,message){
    $.messager.alert(title,message);
}

function easyUI_prompt(title,message){
    $.messager.prompt(title, message, function(r){
        //if (r){
         //   alert('you type: '+r);
        //}
        return r;
    });
}