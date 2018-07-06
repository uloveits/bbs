package src.main.utweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.service.PersonalCenterService;

@Controller
public class personalCenterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(personalCenterController.class);
	
	@Autowired
    private PersonalCenterService personalCenterService;

	@RequestMapping(value = "/personalCenter.do")
	public String personalCenter(){
		return "personalCenter";
	}
	
	@RequestMapping(value = "/changeUserImg")
	@ResponseBody
	@Transactional
	public ResultDto changeUserImg(HttpServletRequest request,String img,String loginName){
		LOGGER.info("==========changeUserImg_start======");
		HttpSession session = request.getSession();
		ResultDto resultDto = new ResultDto();
		resultDto = personalCenterService.changeUserImg(img,loginName);
		session.setAttribute("avatar", img);
		LOGGER.info("==========changeUserImg_end======");
		return resultDto;
	}
	
	@RequestMapping(value = "/getUserInfoByLoginName")
	@ResponseBody
	public ResultDto getUserInfoByLoginName(String loginName){
		LOGGER.info("==========getUserInfoByLoginName_start======");
		ResultDto resultDto = new ResultDto();
		resultDto = personalCenterService.getUserInfoByLoginName(loginName);
		LOGGER.info("==========getUserInfoByLoginName_end======");
		return resultDto;
	}
	
	
	@RequestMapping(value = "/getPersonalSubject")
	@ResponseBody
	public ResultDto getPersonalSubject(String loginName){
		LOGGER.info("==========getPersonalSubject_start======");
		ResultDto resultDto = new ResultDto();
		resultDto = personalCenterService.getPersonalSubject(loginName);
		LOGGER.info("==========getPersonalSubject_end======");
		return resultDto;
	}
	
	
	@RequestMapping(value = "/getPersonalReply")
	@ResponseBody
	public ResultDto getPersonalReply(String loginName){
		LOGGER.info("==========getPersonalSubject_start======");
		ResultDto resultDto = new ResultDto();
		resultDto = personalCenterService.getPersonalReply(loginName);
		LOGGER.info("==========getPersonalSubject_end======");
		return resultDto;
	}
}
