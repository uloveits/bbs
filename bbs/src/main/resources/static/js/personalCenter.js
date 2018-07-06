var PAGE_CONTENT_C = 10;
$(function(){
	var img;
	var urlLoginName= GetQueryString("loginName");
	if(currentSession.loginName == urlLoginName){
		$(".user_img_left").html("<img src='/image/user/"+currentSession.avatar+".png' />")
		$(".user_name").html( currentSession.userName);		
	}else {
		$.ajax({
		    url:"/getUserInfoByLoginName",
		    async:false,
		    data:{
		    	'loginName': urlLoginName,
		    },
		    type:"POST",
		    success:function(data){
		    	if(data.resultCode == 1){
		    		$(".user_img_left").html("<img src='/image/user/"+data.userDto.avatar+".png' />"+ "<input type='hidden' value='"+data.userDto.loginName+"'>")
					$(".user_name").html( data.userDto.userName);
		    		$(".user_img_left").removeAttr("data-target");
			    	$("#editInfo").css("display","none")
		    	}	
		    }
		})    
	}
	//TA创建的所有主题
	GetPersonalSubject(urlLoginName);
	//TA最近回复了
	GetPersonalReply(urlLoginName);
	
	$(".chooseImg").click(function(){
		$(".chooseImg").removeClass("curChoose");
		$(this).addClass("curChoose");
		img = $(this).find("input").val();
	})
	
	$("#changeImgBtn").click(function(){
		var loginName = currentSession.loginName;
		$.ajax({
		    url:"/changeUserImg",
		    async:false,
		    data:{
		    	'img': img,
		    	'loginName': loginName,
		    },
		    type:"POST",
		    success:function(data){
		    	bootStrapDialog.info(data.message,'success',function(){
		    		window.location = "/personalCenter.do?loginName="+loginName;
	    		});
		    	
		    }
		})    
	})
	
	$(document).on("click",".tab_content",function(){
		var id = $(this).find(".content_id").val();
		window.location = "/subjectContent.do?contentId="+id;
	})
})
//TA创建的所有主题
function GetPersonalSubject(loginName){
	$.ajax({
	    url:"/getPersonalSubject",
	    async:false,
	    data:{
	    	'loginName': loginName,
	    },
	    type:"POST",
	    success:function(data){
	    	$(".p_creatSubject").empty();
	    	if(data.resultCode == 1){
	    		var subjectDtoList = data.subjectDtoList;
	    		var pageCount = Math.ceil(subjectDtoList.length/PAGE_CONTENT_C); 
		    	//当前显示多少页
		    	var currentPage = pageCount; 
               
		    	var startC =0;
               
          	 	var endC = ((startC + PAGE_CONTENT_C) < subjectDtoList.length) ? (startC + PAGE_CONTENT_C) : subjectDtoList.length;
          	 
	    		//一页显示的回复数量
	    		var pageContentCount = subjectDtoList.length > PAGE_CONTENT_C ? PAGE_CONTENT_C :subjectDtoList.length;    	
	    		
	    		//回复内容加载
	    		LoadSubjectContent(subjectDtoList, startC, endC);
		    
	    		var options = {
                    bootstrapMajorVersion: 2, //版本
                    currentPage: 1, //当前页数
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
                    	$(".p_creatSubject").empty();               	 
                   	 	var startC =(page-1)*PAGE_CONTENT_C;
                   	 	var endC = ((startC + PAGE_CONTENT_C) < subjectDtoList.length) ? (startC + PAGE_CONTENT_C) : subjectDtoList.length;
                   	 	LoadSubjectContent(subjectDtoList, startC, endC);        	
                    }
	    		};
	    		if(subjectDtoList.length > PAGE_CONTENT_C) {
               	  	$('.pageSubject').bootstrapPaginator(options);
                } 
	    	}
	    	 
	    }
	})    
}

//TA最近的回复
function GetPersonalReply(loginName){
	$.ajax({
	    url:"/getPersonalReply",
	    async:false,
	    data:{
	    	'loginName': loginName,
	    },
	    type:"POST",
	    success:function(data){
	    	$(".p_replyContent").empty();
	    	if(data.resultCode == 1){
	    		var subjectReplyList = data.subjectReplyDtoList;
	    		//一共分多少页
		    	var pageCount = Math.ceil(subjectReplyList.length/PAGE_CONTENT_C); 
		    	//当前显示多少页
		    	var currentPage = pageCount; 
               
		    	var startC =0;
               
          	 	var endC = ((startC + PAGE_CONTENT_C) < subjectReplyList.length) ? (startC + PAGE_CONTENT_C) : subjectReplyList.length;
          	 
	    		//一页显示的回复数量
	    		var pageContentCount = subjectReplyList.length > PAGE_CONTENT_C ? PAGE_CONTENT_C :subjectReplyList.length;    	
	    		
	    		//回复内容加载
	    		LoadReplyContent(subjectReplyList, startC, endC);
		    
	    		var options = {
                    bootstrapMajorVersion: 2, //版本
                    currentPage: 1, //当前页数
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
                    	$(".p_replyContent").empty();               	 
                   	 	var startC =(page-1)*PAGE_CONTENT_C;
                   	 	var endC = ((startC + PAGE_CONTENT_C) < subjectReplyList.length) ? (startC + PAGE_CONTENT_C) : subjectReplyList.length;
                   	 	LoadReplyContent(subjectReplyList, startC, endC);        	
                    }
	    		};
	    		if(subjectReplyList.length > PAGE_CONTENT_C) {
               	  	$('.pageReply').bootstrapPaginator(options);
                } 
	    	}
	    }
	})  
}
//创建的所有主题
function LoadSubjectContent(subjectDtoList, startC, endC){
	for(var i=startC; i<endC;i++){	
		 var contentList_Html = "<div class='tab_content'>"+
			"<div class='content_title'>"+
			"<div class='content_title_top'><a>"+subjectDtoList[i].title+"</a>" +
			"<input type='hidden' class='content_id' value='" +subjectDtoList[i].id+"'/>" +		
			"</div>"+
			"<div class='content_title_botton'><span>"
			+ subjectDtoList[i].type+" · " 
			+ "<a>"+subjectDtoList[i].userName+"</a> · "
			+ toDateTime(subjectDtoList[i].replyTime)
			+ isReplyName(subjectDtoList[i].replyName)
			+"</span></div>" +
			"</div>"+
			 isReplyCount(subjectDtoList[i].replyCount) +
			"</div>";
		 $(".p_creatSubject").append(contentList_Html);

	}	
}
//最近回复了
function LoadReplyContent(subjectReplyList, startC, endC){
	for(var i=startC; i< endC; i++){
		var noticeUserN = subjectReplyList[i].noticeUserN;		  		    		
		var noticeLoginN = subjectReplyList[i].noticeLoginN;
		var replyContent = subjectReplyList[i].content;		    				
		if(noticeUserN != "" && noticeUserN != null && noticeLoginN != "" && noticeLoginN != null) {	
			var noticeUserNList = GetNoticeList(noticeUserN);
			var noticeLoginNList = GetNoticeList(noticeLoginN);
			for(var j in noticeUserNList) {
				var replaceStr = "@"+noticeUserNList[j];
				replyContent = replyContent.replace(replaceStr,"@<a href='personalCenter.do?loginName="+noticeLoginNList[j]+"'>"+noticeUserNList[j] +"</a>");
			}	
		}	
		var p_replyContent_Html = "<div class='replyContent'>" 
    		+"<div class='replyContent_top'>回复了"
    		+"<span><a href='/personalCenter.do?loginName="+subjectReplyList[i].loginName+"'>"+subjectReplyList[i].userName+"</a></span>"
    		+"	创建的主题  > "
    		+subjectReplyList[i].type+" > "
    		+"<span><a href='subjectContent.do?contentId="+subjectReplyList[i].id+"'>"+LimitContent(subjectReplyList[i].title)+"</a></span>"
    		+"<span class='replyTime'>"+toDateTime(subjectReplyList[i].replyTime)+"</span></div>"
    		+"<div class='replyContent_botton'>"+replyContent+"</div>"
    		+"</div>";
		
		$(".p_replyContent").append(p_replyContent_Html);
	}	 
}
