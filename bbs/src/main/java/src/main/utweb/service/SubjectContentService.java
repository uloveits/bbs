package src.main.utweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.utweb.server.dao.NotificationDao;
import src.main.utweb.server.dao.SubjectDao;
import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.server.dto.SubjectDto;
import src.main.utweb.server.dto.SubjectReplyDto;
import src.main.utweb.util.UUIDTool;

@Service
public class SubjectContentService {
	@Autowired
    private SubjectDao subjectDao;
	
	@Autowired
    private NotificationDao notificationDao;


	public ResultDto getSubjectInfoById(String contentId) {
		ResultDto resultDto = new ResultDto();
		List<SubjectDto> subjectDtoList = subjectDao.getSubjectInfoById(contentId);
		System.out.println(subjectDtoList.get(0).title);
		if(subjectDtoList.size() > 0) {
			resultDto.resultCode = 1;
			resultDto.message = "获取当前帖子内容成功！！";
			resultDto.subjectDtoList = subjectDtoList;
		}
		return resultDto;
	}
	
	public ResultDto getReplyInfo(String id) {
		ResultDto resultDto = new ResultDto();
		List<SubjectReplyDto> subjectReplyDtoList = subjectDao.getReplyInfo(id);
		if(subjectReplyDtoList.size() > 0) {
			resultDto.resultCode = 1;
			resultDto.message = "获取主题回复列表成功！！";
			resultDto.subjectReplyDtoList = subjectReplyDtoList;
		}
		return resultDto;
	}
	
	//添加回复列表
	public ResultDto addReplyContent(String contentId,String replyContent,String replyTo,String loginName,String userName,String noticeLoginN,String noticeUserN) {
		ResultDto resultDto = new ResultDto();
		//将回复内容存进去
		subjectDao.addReplyContent(contentId,replyContent,loginName,noticeLoginN,noticeUserN);
		
		//找到该主题的回复数量
		List<SubjectDto> subjectDtoList = subjectDao.getSubjectInfoById(contentId);
		int newCount = Integer.parseInt(subjectDtoList.get(0).replyCount) + 1;
		String strNewCount = Integer.toString(newCount);
		
		//插入最新的回复条数
		subjectDao.addReplyCount(contentId,strNewCount,userName);
		
		//插入回复的notice
		int type = 0;//type=0表示回复的提醒
		String id = UUIDTool.creatUUID();
		
		if(!replyTo.equals(loginName) ){
			notificationDao.AddReplyNotice(id,replyTo,loginName,replyContent,contentId,type);
		}
		
		//插入@别人的notice
		if(noticeLoginN != "") {
			 String[] arrReplyTo = noticeLoginN.split("@");
			 int type1 = 1;//type=1表示@别人的提醒
			 for (int i = 1; i < arrReplyTo.length; i++) {
				 id = UUIDTool.creatUUID();
				 notificationDao.AddReplyNotice(id,arrReplyTo[i],loginName,replyContent,contentId,type1); 
			}  
		};
		resultDto.resultCode = 1;
		resultDto.message = "回复成功！！";
		return resultDto;
	}
	
	
}
