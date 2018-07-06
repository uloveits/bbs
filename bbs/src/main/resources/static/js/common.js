var currentSession;
$(function(){
	
  
	$(document).on("click",".user_img",function() {
		var loginName = $(this).find("input").val();
		window.location = "/personalCenter.do?loginName="+loginName;
	})
	//获得地理位置
	$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function(_result) {
         if (remote_ip_info.ret == '1') {
        	 $(".user_address_txt").html(remote_ip_info.country+" " + remote_ip_info.province+""+remote_ip_info.city);        
        	 //获得天气信息
   /*     	 citytq = remote_ip_info.city ;
        	 var url = "http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&city=" + citytq + "&day=0&dfc=3";
        	 $.ajax({
        		 url: url,
        	     dataType: "script",
        	     scriptCharset: "gbk",
        	     success: function (data) {
        	       var _w = window.SWther.w[citytq][0];
        	       var _f = _w.f1 + "_0.png";
        	       if (new Date().getHours() > 17) {
        	         _f = _w.f2 + "_1.png";
        	       }
        	       var img = "<img width='34px' height='34px' src='http://i2.sinaimg.cn/dy/main/weather/weatherplugin/wthIco/20_20/" + _f
        	    + "' />";
        	       var tq = img + " " + _w.s1 + " " + _w.t1 + "℃～" + _w.t2 + "℃ " + _w.d1 + _w.p1 + "级";
        	       $('#weather').html(tq);
        	      }
        	  });*/
         } else {
        	 $(".user_address_txt").html("没有找到匹配的IP地址信息");
         }
     });
	
	GetSessionInfo();
	  
    //得到通知信息
    GetNoticeInfo();
    
    $("#head_SignUp").click(function(){
    	window.location = "/register"
    })
    
    $("#head_SignIn").click(function(){
    	window.location = "/login"
    })
    //退出登录
    $("#head_SignOut").click(function(){
    	bootStrapDialog.confirm("确定注销本次登录并返回登录页面吗？","noImg",function(){
    		$.ajax({
        	    url:"/delSession",
        	    async:false,
        	    data:{},
        	    type:"POST",
        	    success:function(data){
        	    	if(data == "true"){
        	    		currentSession = "";
            	    	window.location = "/login"
        	    	}	
        	    }
        	    
        	});
		}) 	
    })
    
    //通知展开按钮点击事件
    $("#noticeDownBtn").click(function(){
    	$(this).css("display","none");
    	$("#noticeUpBtn").css("display","inline-block");
    	if($(".noticeContent").css('display') == 'none'){
    		$(".noticeContent").css('display','block');
    	}else {
    		$(".noticeContent").css('display','none');
    	}
    	
    })
    //通知折叠按钮点击事件
    $("#noticeUpBtn").click(function(){
    	$(this).css("display","none");
    	$("#noticeDownBtn").css("display","inline-block");
    	if($(".noticeContent").css('display') == 'none'){
    		$(".noticeContent").css('display','block');
    	}else {
    		$(".noticeContent").css('display','none');
    	}
    	
    })
    $("#deleteAllBtn").click(function(){
    	bootStrapDialog.confirm("确定要清空未读消息吗？","noImg",function(){
    		$.ajax({
         	    url:"/deleteAllNotice",
         	    async:false,
         	    data:{
         	    	loginName:currentSession.loginName,
         	    },
         	    type:"POST",
         	    success:function(data){
         	    	if(data.resultCode == 1) {
         	    		GetNoticeInfo();
        	    		$(".noticeContent").css('display','block');
         	    	}
         	    }
             })
    	})
    	 
    })
    //未读消息点击事件
    $(document).on("click",".noticeC_c",function(){
    	var noticeId = $(this).find(".noticeId").val();
    	var subjectId = $(this).find(".subjectId").val();

        $.ajax({
    	    url:"/changeNoticeUseFlag",
    	    async:false,
    	    data:{
    	    	noticeId:noticeId,
    	    },
    	    type:"POST",
    	    success:function(data){
    	    	if(data.resultCode == 1) {
    	    		window.location  = "/subjectContent.do?contentId=" + subjectId;
    	    	}
    	    }
        })
    	
    })
    //未读消息单个删除事件
    $(document).on("click",".deleteBtn",function(){
    	var id = $(this).parent().parent().find(".noticeId").val();
    	$.ajax({
    	    url:"/changeNoticeUseFlag",
    	    async:false,
    	    data:{
    	    	noticeId:id,
    	    },
    	    type:"POST",
    	    success:function(data){
    	    	if(data.resultCode == 1) {
    	    		GetNoticeInfo();
    	    		$(".noticeContent").css('display','block');
    	    	}
    	    }
        })
    })
  
})

//将毫秒转换成时间格式
function toDateTime(time) {
	var formart = new Date(time);
	return formart.getFullYear() + "/" + (formart.getMonth() + 1) + "/" + formart.getDate() + "/ " + formart.getHours() + ":" + formart.getMinutes() + ":" + formart.getSeconds();
};


function LimitName(Name) {
     var str = Name;
     if(str.length > 5){
    	 str = str.substr(0,5) + '...' ;
    	 return "<span title='"+ Name +"'>"+str+"</span>"
     }else {
    	 return Name;
     }
}

function LimitContent(Name) {
    var str = Name;
    if(str.length > 15){
   	 str = str.substr(0,15) + '...' ;
   	 return "<span title='"+ Name +"'>"+str+"</span>"
    }else {
   	 return Name;
    }
}

function GetNoticeList(noticeName) {
	var list = [];
	list = noticeName.split("@");
	list.shift()
	return list;
}

function NoticeType(type) {
	if(type == 0) {
		return " 回复了你  ";
	}else {
		return " 提到了你  ";
	}
}
//取url参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

function GetSessionInfo(){
	$.ajax({
	    url:"/getSession",
	    async:false,
	    data:{},
	    type:"POST",
	    success:function(data){
	    	currentSession = data;
	    	
            
            
	    	var notSignIn_Html = "<div id='head_SignUp' class='head_notLogin'>注册</div>" + 
								"<div id='head_or'  class='head_notLogin'>|</div>" + 
								"<div id='head_SignIn'  class='head_notLogin'>登录</div>";
	    	
	    	if(data.userName != null){
	    		var aleradySignIn_Html ="<div id='head_welcome' class='head_Login'>" 
					+"<div data-toggle='dropdown' aria-expanded='true'  style='cursor: pointer'>"
					+LimitName(data.userName)+
					"</div>"
					+" <ul class='dropdown-menu' role='menu' style='top: 38px!important;right:0px;left:auto;min-width:106px;padding:0 0;background-color: bisque;'>"
		            +"<li><a href='/personalCenter.do?loginName="+data.loginName+"'>"
	                +"<span><img style='width: 20px;height: 20px;' src='/image/personal.png'></span>"
	                +"<span class='text'>个人中心</span>"
	                +"</a></li>"
	                +"<li class='divider' style='margin:1px 0;'></li>"
	                +"<li><a id='head_SignOut'>"
	                +"<span><img style='width: 20px;height: 20px;' src='/image/loginOut.png'></span>"
	                +"<span class='text'>退出登录</span>"
	                +"</a></li>"
	                +"</ul></div>"
	                +"<div style='display:inline-block;float: right;margin-right: 11px;margin-top: 13px;font-size: 16px;'>欢迎</div>";
	    		$("#common_head").append(aleradySignIn_Html);
	    	}else {
	    		$("#common_head").append(notSignIn_Html);
	    	}
	    }
	    
	});
}

function GetNoticeInfo(){
	 $.ajax({
		    url:"/getNotification",
		    async:false,
		    data:{
		    	loginName:currentSession.loginName,
		    },
		    type:"POST",
		    success:function(data){
		    	notificationList = data.notificationDtoList;

		    	if(notificationList.length > 0) {
		    		$("#notice_CC").empty();
		    		$("#noticeCount").html(notificationList.length+ " 条未读消息！！");
		    		for(var i= 0;i< notificationList.length;i++ ){  	
			    		var notice_Html = "<div class='noticeContent'>"+
											"<div class='noticeC_t'>"+
											"<a href='/personalCenter.do?loginName="+notificationList[i].noticeFrom+"'>"+
											LimitName(notificationList[i].noticeFromName)+"</a>" + "  在  "+
											"<a href='/subjectContent.do?contentId="+notificationList[i].subjectId+"'>"+ 
											LimitName(notificationList[i].title) + "</a>" + NoticeType(notificationList[i].type) +
											toDateTime(notificationList[i].noticeTime)+ 
											"<img class='deleteBtn' src='/image/delete.png'/>"+
											"</div>" +
											"<div class='noticeC_c'>"+
											notificationList[i].noticeContent + 
											"<input type='hidden' class='noticeId' value='"+notificationList[i].id+"'>"+
											"<input type='hidden' class='subjectId' value='"+notificationList[i].subjectId+"'>"+"</div>"+"</div>";
			    		$("#notice_CC").append(notice_Html);
		    		}
		    	}else {
		    		$(".notifications").css("display","none");
		    	}
		    	
		    }
		    
		});
}


//将毫秒转换成时间格式
function toDateTime(time) {
	var formart = new Date(time);
  return formart.getFullYear() + "/" + (formart.getMonth() + 1) + "/" + formart.getDate() + "/ " + formart.getHours() + ":" + formart.getMinutes() + ":" + formart.getSeconds();
};

//是否有人回复
function isReplyName(replyName) {
	 if(replyName != "" && replyName != null){	
		 return " · 最后回复来自：<a>" + replyName +"</a>";
	 }	
	 return "";
}
//没有人回复时后，颜色变灰
function isReplyCount(replyCount) {
	if(replyCount == "0") {
		return "<div class='views' style='background-color: grey;'><span>"+replyCount+"</span></div>"
	}
	return "<div class='views'><span>"+replyCount+"</span></div>"
}

function bootStrapDialog() {
    var me = this;
    me.info = function(msg,type,okCallback){
        if(type == 'success') {
            $("#myModal_info").modal('show');
           // $("#myModal_info .modal-body").find('img').attr('src','/images/icon/success_icon.png');
            $("#myModal_info .modal-body").find('span').html(msg);
            $("#myModal_info .btn-primary").click(function(){
                if (okCallback && typeof okCallback === 'function') {
                    okCallback();
                    //移除当前的confirm方法
                    $(this).unbind('click');
                }
            })
        }else if(type == 'wrong') {
            $("#myModal_info").modal('show');
            //$("#myModal_info .modal-body").find('img').attr('src','/images/icon/shanchu_icon.png');
            $("#myModal_info .modal-body").find('span').html(msg);
            $("#myModal_info .btn-primary").click(function(){
                if (okCallback && typeof okCallback === 'function') {
                    okCallback();
                    //移除当前的confirm方法
                    $(this).unbind('click');
                }
            })
        }else if(type == 'info') {
            $("#myModal_info").modal('show');
           // $("#myModal_info .modal-body").find('img').attr('src','/images/icon/info_icon.png');
            $("#myModal_info .modal-body").find('span').html(msg);
            $("#myModal_info .btn-primary").click(function(){
                if (okCallback && typeof okCallback === 'function') {
                    okCallback();
                    //移除当前的confirm方法
                    $(this).unbind('click');
                }
            })
        }

    };
    me.confirm = function(msg,type,okCallback){
        if(type == 'choose') {
            $("#myModal_confirm").modal('show');
            $("#myModal_confirm .modal-body").find('img').attr('src','/images/icon/why_icon.png');
            $("#myModal_confirm .modal-body").find('span').html(msg);
            $("#myModal_confirm .btn-primary").click(function(){
                if (okCallback && typeof okCallback === 'function') {
                    okCallback();
                    //移除当前的confirm方法
                    $(this).unbind('click');
                }
            });
            $("#myModal_confirm .btn-default").click(function(){
                //移除当前的confirm方法
                $("#myModal_confirm .btn-primary").unbind('click');
            })
        }else if(type == 'noImg') {
            $("#myModal_confirm").modal('show');
            $("#myModal_confirm .modal-body").find('img').css('display','none');
            $("#myModal_confirm .modal-body").find('span').html(msg);
            $("#myModal_confirm .btn-primary").click(function(){
                if (okCallback && typeof okCallback === 'function') {
                    okCallback();
                    //移除当前的confirm方法
                    $(this).unbind('click');
                }
            });
            $("#myModal_confirm .btn-default").click(function(){
                //移除当前的confirm方法
                $("#myModal_confirm .btn-primary").unbind('click');
            })
        }

    };

    me.error = function(msg,ele,ele_label) {
        $(ele).addClass('has-error');
        $(ele_label).addClass('has-error-label');
        $(ele).attr('data-toggle','tooltip');
        $(ele).attr('data-placement','top');
        //$(ele).attr('aria-required',true);
       // $(ele).attr(' data-trigger','manual');
        $(ele).attr('data-original-title',msg);
        $(ele).tooltip({
            trigger:'manual'
        });
        $(ele).tooltip('show');
       /* $(ele).focus();*/
        $(ele).change(function(){
            $(ele).removeClass('has-error');
            $(ele_label).removeClass('has-error-label');
            $(ele).tooltip('destroy');

        })
    };
}
var bootStrapDialog = new bootStrapDialog();