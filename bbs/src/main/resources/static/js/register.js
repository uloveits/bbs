$(function(){
	
	$("#signUpBtn").click(function(){
		var userName = $("#userName").val();
		var emailAddress = $("#emailAddress").val();
		var loginName = $("#loginName").val();
		var password = $("#password").val();
		var confirmPassword = $("#confirmPassword").val();
		
		if(userName == "") {
			$("#errSpan").html("姓名不能为空！");
			return;
		}
		
		if(emailAddress == "") {
			$("#errSpan").html("邮箱不能为空！");
			return;
		}
		if(!isEmail(emailAddress)) {
			$("#errSpan").html("邮箱格式不正确！	");
			return;
		}
		
		if(loginName == "") {
			$("#errSpan").html("登录帐号不能为空！");
			return;
		}
		
		if(password == "") {
			$("#errSpan").html("密码不能为空！");
			return;
		}
		
		if(password != confirmPassword) {
			$("#errSpan").html("两次密码输入的不一致！");
			return;
		}
		
		$.ajax({
		    url:"/register/doSignUp",
		    async:false,
		    data:{
		    	'userName':userName,
		    	'emailAddress':emailAddress,
		    	'loginName':loginName,
		    	'password':password,
		    	},
		    type:"POST",
		    success:function(data){
		    	alert(data.message);
		    	window.location = "/login";
		    }
		});
		
		
	})
	$("#cancelBtn").click(function(){
		window.location = "/index"; 
	})
});
//判断是否是email格式
function isEmail(str){ 
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
	return reg.test(str); 
} 

function clearAllInput(){
	$('#info1').val('');
	$('#info2').val('');
	$('#info3').val('');
	$('#info4').val('');
}
function registerUser(){
	$('#tip').html('');
	var phone = $('#info1').val();
	if(phone == ''){
		$('#tip').html('手机号不能为空！');
		return;
	}
	if(isNaN(phone) || phone.length != 11){
		$('#tip').html('手机号格式不正确！');
		return;
	}
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if (!myreg.test(phone)) {
		$('#tip').html('手机号格式不正确！');
		return false;
	}
	var pswd = $('#info2').val();
	if(pswd == ''){
		$('#tip').html('密码不能为空！');
		return;
	}
	var surepswd = $('#info3').val();
	if(surepswd == ''){
		$('#tip').html('确认密码不能为空！');
		return;
	}
	var nickname = $('#info4').val();
	if(nickname == ''){
		$('#tip').html('昵称不能为空！');
		return;
	}
	if(pswd != surepswd){
		$('#tip').html('两次密码不一致！');
		return;
	}
	var userexist = '';
	$.ajax({
	    url:"/register/ifuserexist",
	    async:false,
	    data:{'phone':phone},
	    type:"POST",
	    success:function(data){
	    	userexist = data;
	    }
	});
	if(userexist == 'true'){
		$('#tip').html('此手机号已被注册！');
		return;
	}
	$.ajax({
	    url:"/register/regedit",
	    async:false,
	    data:{'phone':phone,'pswd':pswd,'nickname':nickname},
	    type:"POST",
	    success:function(data){
	    }
	});
}