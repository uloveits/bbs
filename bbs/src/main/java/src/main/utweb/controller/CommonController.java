package src.main.utweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import src.main.utweb.server.dto.CurrentSessionDto;
import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.service.CommonService;


@Controller
public class CommonController {
	@Autowired
    private CommonService commonService;
	
	@RequestMapping(value = "/getSession")
	@ResponseBody
	public CurrentSessionDto getSession(HttpServletRequest request, HttpServletResponse response){
		CurrentSessionDto currentSessionDto = new CurrentSessionDto();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("userName");
		if(username != null) {
			currentSessionDto.userName = username;
			currentSessionDto.loginName = (String) session.getAttribute("loginName");
			currentSessionDto.avatar = (String) session.getAttribute("avatar");
			currentSessionDto.email = (String) session.getAttribute("email");
		}
		
		return currentSessionDto;
	}
	
	@RequestMapping(value = "/delSession")
	@ResponseBody
	public String delSession(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		session.removeAttribute("loginName");
		session.removeAttribute("userName");
		session.removeAttribute("avatar");
		session.removeAttribute("email");
		return "true";
	}
	
	//获得所有用户信息
	@RequestMapping(value = "/getAllUserInfo")
	@ResponseBody
	public ResultDto getAllUserInfo(){
		ResultDto resultDto = new ResultDto();
		resultDto = commonService.getAllUserInfo();
		return resultDto;
	}
	
	@RequestMapping(value = "/getNotification")
	@ResponseBody
	public ResultDto getNotification(String loginName){
		ResultDto resultDto = new ResultDto();
		
		resultDto = commonService.getNotification(loginName);
		return resultDto;
	}
	
	@RequestMapping(value = "/changeNoticeUseFlag")
	@ResponseBody
	public ResultDto changeNoticeUseFlag(String noticeId){
		ResultDto resultDto = new ResultDto();	
		resultDto = commonService.changeNoticeUseFlag(noticeId);
		return resultDto;
	}
	
	@RequestMapping(value = "/deleteAllNotice")
	@ResponseBody
	public ResultDto deleteAllNotice(String loginName){
		ResultDto resultDto = new ResultDto();	
		resultDto = commonService.deleteAllNotice(loginName);
		return resultDto;
	}
}
