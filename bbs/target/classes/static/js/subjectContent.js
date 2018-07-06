var PAGE_CONTENT_C = 50;
var m_noticeLoginN = "";
var m_noticeUserN = "";
$(function(){
	if(currentSession.userName){
		$("#user_info_c").html("<a id='userName' style='font-size:22px;'>"+currentSession.userName+"</a>");
		$("#myself_img").html("<img src='/image/user/"+currentSession.avatar+".png' />"+ "<input type='hidden' value='"+currentSession.loginName+"'>")
	}else {
		$("#user_info_i").css("display","none");
		$("#create_title").css("display","none");
		$("#user_info_c").html("<a href='/login' style='font-size:20px;'>请登录...</a>");
	}
	
	var contentId = GetQueryString("contentId");
	showSubjectInfo(contentId);
	
	$(document).on("click",".user_img_left",function(){
		var loginName = $(this).find("input").val();
		window.location = "/personalCenter.do?loginName="+loginName;
	})
	
	//@人的回复按钮
	$(document).on("click",".replyNoticeBtn",function(){
		var userName = "@"+$(this).find(".hidUserName").val();
		var loginName = "@"+$(this).find(".hidLoginName").val();
		m_noticeLoginN = m_noticeLoginN + loginName;
		m_noticeUserN = m_noticeUserN + userName
		$("#add_reply_c").val($("#add_reply_c").val()+userName);
		$("#add_reply_c").focus();
		
	})
	$.ajax({
	    url:"/getAllUserInfo",
	    async:false,
	    data:{},
	    type:"POST",
	    success:function(data){
	    	if(data.resultCode == 1) {
	    
	    		var userInfoList = data.userDtoList;
	    		var userInfoList = $.map(userInfoList,function(value,i) {
	    		      return {'name':value.userName,'loginName':value.loginName};
	    		    });	    	    
	    		var at_config = {
	    			      at: "@",
	    			      data:userInfoList,
	    			      insertTpl: '@${name}',
	    			      displayTpl: "<li class='select'><span>${name}</span> <small style='display:none'>${loginName}</small></li>",
	    			      startWithSpace: false,
	    				}
	    		$inputor = $('#add_reply_c').atwho(at_config);
	    		$inputor.on('keyup.atwhoInner', function(e) {
	    			 if (e.keyCode === 13) {
	    				 var userName = "@"+$(".atwho-view-ul").find(".cur").find('span').html();
	    				 var loginName = "@"+$(".atwho-view-ul").find(".cur").find('small').html();
	    				 m_noticeLoginN = m_noticeLoginN + loginName;
	    				 m_noticeUserN = m_noticeUserN + userName;
	    		     }
	    			
	    		})
	    		    
	    	}		    	
	    }
	})
	
    $(document).on('click','.select',function() {
    	var userName = "@"+$(this).find('span').html();
		var loginName = "@"+$(this).find('small').html();
    	m_noticeLoginN = m_noticeLoginN + loginName;
		m_noticeUserN = m_noticeUserN + userName;
    })

    $('#add_reply_c').on('keypress', function (e) {
	    if (e.keyCode === 13) {
	         $('#replyBtn').click();
	     }
	 });
	
	//回复按钮
	$("#replyBtn").click(function(){
		var replyContent = $("#add_reply_c").val();
		if(replyContent == ""){
			bootStrapDialog.info('请写点内容再回复吧！','success',function(){
				$("#add_reply_c").focus();
    		});
			return;
		}
		//check艾特的人有没有问题
		var noticeUserNList = GetNoticeList(m_noticeUserN);
		var noticeLoginNList = GetNoticeList(m_noticeLoginN);
		var noticeUserN = "";
		var noticeLoginN = "";
		for(var i in noticeUserNList) {
			if(replyContent.indexOf("@"+noticeUserNList[i]) != -1) {
				
				noticeUserN = noticeUserN + "@"+ noticeUserNList[i];
				noticeLoginN = noticeLoginN + "@"+ noticeLoginNList[i];
			}
		}
		$.ajax({
		    url:"/addReplyContent",
		    async:false,
		    data:{
		    	'contentId':contentId,
		    	'replyContent':replyContent,
		    	'replyTo':$("#hidLoginName").val(),
		    	'loginName':currentSession.loginName,
		    	'userName':currentSession.userName,
		    	'noticeLoginN':noticeLoginN,
		    	'noticeUserN':noticeUserN,
		    },
		    type:"POST",
		    success:function(data) {
		    	if(data.resultCode == 1) {
		    		$(".reply_c").empty();
		    		$("#add_reply_c").val("");
		    		m_noticeLoginN = "";
		    		m_noticeUserN = "";
		    		showSubjectInfo(contentId);
		    	}		    	
		    }
		})
	})
	
	$("#back_top").click(function(){
		scrollTo(0,0);
	})
})

//显示帖子的信息
function showSubjectInfo(contentId){
	$.ajax({
	    url:"/getSubjectInfo",
	    async:false,
	    data:{
	    	'contentId':contentId,
	    },
	    type:"POST",
	    success:function(data){
	    	var subjectInfo = data.subjectDtoList[0];
	    	$("#hidLoginName").val(subjectInfo.loginName);
	    	$(".navigation").find("span").html(" > " + subjectInfo.type);
	    	$(".content_title_top").html(subjectInfo.title);
	    	$(".content_title_botton").html("<span>"
	    									+ subjectInfo.type+" · " 
	    									+ "<a>"+subjectInfo.userName+"</a> · "
	    									+ toDateTime(subjectInfo.createTime)
	    									+ "</span>");
	    	if(subjectInfo.content == "" ) {
	    		$(".content_detail").css('display','none');
	    	}else{
	    	    //创建实例
	    	    var converter = new showdown.Converter();
	    	    //进行转换
	    	    var html = converter.makeHtml(subjectInfo.content);
	    	    //展示到对应的地方  result便是id名称
	    	    document.getElementById("content_detail_c").innerHTML = html;

	    		//$("#content_detail_c").html();
	    	}
	    	$(".user_img").html("<a><img src='/image/user/"+subjectInfo.avatar+".png'/></a>"+"<input type='hidden'  value='" +subjectInfo.loginName+"'/>"	);
	    	
	    	if(subjectInfo.replyCount > 0) {
	    		$(".replyCount").html(subjectInfo.replyCount);
	    		$(".reply_content").css("display","block");
	    		$(".no_reply_content").css("display",'none')
	    		showReplyContent(subjectInfo.id);
	    	}else {
	    		$(".reply_content").css("display","none");
	    		$(".no_reply_content").css("display",'block')
	    	}
	    	
	    }
	    
	});
	
}
//显示回复的内容
function showReplyContent(id) {
	$.ajax({
	    url:"/getReplyInfo",
	    async:false,
	    data:{
	    	'id':id,
	    },
	    type:"POST",
	    success:function(data){
	    	if(data.resultCode == 1){
	    		var subjectReplyDtoList = data.subjectReplyDtoList;
	    		var count = subjectReplyDtoList.length - 1;
	    		$(".replyTime").html(toDateTime(subjectReplyDtoList[count].replyTime));

	    		//一共分多少页
		    	 var pageCount = Math.ceil(subjectReplyDtoList.length/PAGE_CONTENT_C); 
		    	//当前显示多少页
                var currentPage = pageCount; 
                
                var startC =(currentPage-1)*PAGE_CONTENT_C;
                
           	 	var endC = ((startC + PAGE_CONTENT_C) < subjectReplyDtoList.length) ? (startC + PAGE_CONTENT_C) : subjectReplyDtoList.length;
           	 
	    		//一页显示的回复数量
	    		var pageContentCount = subjectReplyDtoList.length > PAGE_CONTENT_C ? PAGE_CONTENT_C :subjectReplyDtoList.length;    	
	    		
	    		//回复内容加载
	    		LoadReplyContent(subjectReplyDtoList, startC, endC);
		    
                var options = {
                     bootstrapMajorVersion: 2, //版本
                     currentPage: currentPage, //当前页数
                     totalPages: pageCount, //总页数
                     itemTexts: function (type, page, current) {
                         switch (type) {
                             case "first":
                                 return "<<";
                             case "prev":
                                 return "<";
                             case "next":
                                 return ">";
                             case "last":
                                 return ">>";
                             case "page":
                                 return page;
                         }
                     },
                     //点击事件，用于通过Ajax来刷新整个list列表
                     onPageClicked: function (event, originalEvent, type, page) {
                    	 $(".reply_c").empty();               	 
                    	 var startC =(page-1)*PAGE_CONTENT_C;
                    	 var endC = ((startC + PAGE_CONTENT_C) < subjectReplyDtoList.length) ? (startC + PAGE_CONTENT_C) : subjectReplyDtoList.length;
                    	 LoadReplyContent(subjectReplyDtoList, startC, endC);        
        		    		
                     }
                 };
                 if(subjectReplyDtoList.length > PAGE_CONTENT_C){
                	  $('.page').bootstrapPaginator(options);
                 }               
	    	}	    	
	    }	    
	});
}

//回复的内容加载
function LoadReplyContent(subjectReplyDtoList, startC, endC) {
	for(var i=startC; i< endC; i++){
		//回复的内容有没有艾特其他人
		var noticeUserN = subjectReplyDtoList[i].noticeUserN;		  		    		
		var noticeLoginN = subjectReplyDtoList[i].noticeLoginN;
		var replyContent = subjectReplyDtoList[i].content;		    				
		if(noticeUserN != "" && noticeUserN != null && noticeLoginN != "" && noticeLoginN != null) {	
			var noticeUserNList = GetNoticeList(noticeUserN);
			var noticeLoginNList = GetNoticeList(noticeLoginN);
			for(var j in noticeUserNList) {
				var replaceStr = "@"+noticeUserNList[j];
				replyContent = replyContent.replace(replaceStr,"@<a href='personalCenter.do?loginName="+noticeLoginNList[j]+"'>"+noticeUserNList[j] +"</a>");
			}	
		}		    		
		 var replyList_Html = "<div class='reply_content_list'>"+
					"<div class='user_img_left'><img src='/image/user/"+subjectReplyDtoList[i].avatar+".png' />" +
					"<input type='hidden' value='"+ subjectReplyDtoList[i].loginName+"'/>"+
					"</div>"+
					"<div class='reply_content_title'>"+
					"<div class='reply_content_title_top'>"+
					subjectReplyDtoList[i].userName+
					"  " + toDateTime(subjectReplyDtoList[i].replyTime)+
					"</div>"+
					"<div class='reply_content_title_botton'>"+ replyContent +"</div>"+
					"</div>"+	
					"<div class='replyNoticeBtn'><img src='/image/replyBtn.png' />" +
					"<input type='hidden' class='hidUserName' value='"+ subjectReplyDtoList[i].userName +"'/>" +
					"<input type='hidden' class='hidLoginName' value='"+ subjectReplyDtoList[i].loginName +"'/>" +
					"</div>"+
					"</div>";
		 $(".reply_c").append(replyList_Html);
	}	
	
}
