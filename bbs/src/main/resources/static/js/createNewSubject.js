$(function(){
	$("#CancelSubjectBtn").click(function(){
		bootStrapDialog.confirm("确定放弃本次发帖吗？","noImg",function(){
			window.location="/index"
		})
		
	})
	
	$("#createSubjectBtn").click(function(){
		var subject_user = currentSession.loginName;
		var subject_title = $("#subject_title").val();
		var subject_content = $("#subject_content").val();
		var subject_type = $("#subject_type").val();
		$.ajax({
		    url:"/creatNewSubject",
		    async:false,
		    data:{
		    	'subject_user':subject_user,
		    	'subject_title':subject_title,
		    	'subject_content':subject_content,
		    	'subject_type':subject_type,
		    	},
		    type:"POST",
		    success:function(data){ 
		    	if(data.resultCode == 1){
		    		bootStrapDialog.info(data.message,'success',function(){
		    			window.location="/index"
		    		});
		    	}	
		    }    
		});
		
	})
})