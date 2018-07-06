$(function(){
	var activateId =  GetQueryString("activateId");
	var mail = GetQueryString("mail");
	var emailTime = GetQueryString("now");
	
	if(activateId == ""){
		//window.location = "/index"
	}
	//判断是否过了24小时
	var nowTime = (new Date()).getTime();
	if((nowTime - emailTime) > 86400000) {
		$("#active_img").html("<img src='/image/cry.png' />");
		$("#active_text").html("激活失败,已经超过了24小时。请重新注册！");
		$("#active_href").html("<a href='/register'>>> 继续注册</a>");
	} 
	//判断该用户是否已经激活
	$.ajax({
	    url:"/doActivate",
	    async:false,
	    data:{
	    	'activateId':activateId,
	    	'mail':mail,
	    	},
	    type:"POST",
	    success:function(data){ 
	    	if(data.resultCode == 0){
	    		$("#active_img").html("<img src='/image/cry.png' />");
	    		$("#active_href").html("<a href='/register'>>> 继续注册</a>");
	    	}else {
	    		$("#active_img").html("<img src='/image/success.png' />");
	    		$("#active_href").html("<a href='/login'>>> 现在登录</a>");
	    	}
	    	$("#active_text").html(data.message);
	    }
	    
	});
})

function GetQueryString(name) {
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}