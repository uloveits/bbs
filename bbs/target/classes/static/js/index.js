$(function(){
	showContentList("技术");
	if(currentSession.userName){
		$("#user_info_c").html("<a id='userName' style='font-size:22px;'>"+currentSession.userName+"</a>");
		$("#myself_img").html("<img src='/image/user/"+currentSession.avatar+".png' />" + "<input type='hidden' value='"+currentSession.loginName+"'>")
	}else {
		$("#user_info_i").css("display","none");
		$("#create_title").css("display","none");
		$("#user_info_c").html("<a href='/login' style='font-size:20px;'>请登录...</a>");
	}
	//时钟设置
	AnalogClock("clock",new AnalogClockOption(200, "#eee", "#333"));
	  
	//////////////节点的选择///////////////////
	$(".chooseTab").click(function(){
		var type = $(this).html();
		$(".chooseTab").removeClass("tab").removeClass("tab_Cur").addClass("tab");
		$(this).removeClass("tab").addClass("tab_Cur");
		showContentList(type);
		
	})
	
	//帖子详细点击事件
	$(document).on("click",".content_title_top",function() {
		var id = $(this).find(".content_id").val();
		window.location = "/subjectContent.do?contentId="+id;
	})

})

//动态的显示话题
function showContentList(type){
	var search_content = $("#search_content").val();
	$.ajax({
	    url:"/index/content_list",
	    async:false,
	    data:{
	    	'type':type,
	    	'search_content':search_content,
	    },
	    type:"POST",
	    success:function(data){
	    	$("#content_list").empty();
	    	if(data.resultCode == 1){
	    		var subjectDtoList = data.subjectDtoList;
		    	for(var i=0; i<subjectDtoList.length; i++){	
		    		 var contentList_Html = "<div class='tab_content'>"+
		    			"<div class='user_img'><a><img src='/image/user/"+subjectDtoList[i].avatar+".png' /></a>" +
		    			"<input type='hidden'  value='" +subjectDtoList[i].loginName+"'/>" +			
		    			"</div>"+
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
		    		 $("#content_list").append(contentList_Html);

		    	}	 
	    	}
	    	 
	    }
	    
	});
}
