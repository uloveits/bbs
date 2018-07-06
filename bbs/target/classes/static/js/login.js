$(function() {
	$('#login_name,#password,#getCode').on('keypress', function (e) {
	    if (e.keyCode === 13) {
	         $('#signin').click();
	     }
	 });
	//登录按钮事件
	$("#signin").click(function(){
		var login_name = $("#login_name").val();
		var password = $("#password").val();
		var code = $("#getCode").val();
		$.ajax({
		    url:"/login/doSignIn",
		    async:false,
		    data:{
		    	'login_name':login_name,
		    	'password':password,
		    	'code':code,
		    	},
		    type:"POST",
		    success:function(data){ 
		    if(data.resultCode == 1){
		    	window.location = "/index";
		    }else{
		    	$("#errSpan").html(data.message);
		    }
		    	
		    }
		    
		});
	})
	
	$("#signup").click(function(){
		window.location ="/register";
	})
	
});
function myReload(){
	var img = document.getElementById("code");
	img.src="/login/buildImage?rnd=" + Math.random();
}