package src.main.utweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.service.SubjectContentService;

@Controller
public class SubjectContentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubjectContentController.class);

	@Autowired
    private SubjectContentService subjectContentService;
	
	
	@RequestMapping(value = "/subjectContent.do")
	public String creatNewSubject(){
		LOGGER.info("主题内容页面加载");
		return "subjectContent";
	}
	
	@RequestMapping(value = "/getSubjectInfo")
	@ResponseBody
	public ResultDto getSubjectInfo(String contentId){
		LOGGER.info("==========getSubjectInfo_start======");
		System.out.println(contentId);
		ResultDto resultDto = new ResultDto();
		resultDto = subjectContentService.getSubjectInfoById(contentId);
		LOGGER.info("==========getSubjectInfo_end======");
		return resultDto;
	}
	
	@RequestMapping(value = "/getReplyInfo")
	@ResponseBody
	public ResultDto getReplyInfo(String id){
		LOGGER.info("==========getReplyInfo_start======");
		ResultDto resultDto = new ResultDto();
		resultDto = subjectContentService.getReplyInfo(id);
		LOGGER.info("==========getReplyInfo_end======");
		return resultDto;
	}
	
	@RequestMapping(value = "/addReplyContent")
	@ResponseBody
	public ResultDto addReplyContent(String contentId,String replyContent,String replyTo,String loginName,String userName,String noticeLoginN,String noticeUserN){
		LOGGER.info("==========addReplyContent_start======");
		ResultDto resultDto = new ResultDto();
		resultDto = subjectContentService.addReplyContent(contentId,replyContent,replyTo,loginName,userName,noticeLoginN,noticeUserN);
		LOGGER.info("==========addReplyContent_end======");
		return resultDto;
	}
	
}
