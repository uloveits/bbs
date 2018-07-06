package src.main.utweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.utweb.server.dao.NotificationDao;
import src.main.utweb.server.dao.UserDao;
import src.main.utweb.server.dto.NotificationDto;
import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.server.dto.UserDto;

@Service
public class CommonService {
	@Autowired
    private UserDao userDao;
	
	@Autowired
    private NotificationDao notificationDao;

	public ResultDto getAllUserInfo() {
		ResultDto resultDto = new ResultDto();
		List<UserDto> list = userDao.getAllUserInfo();
		if(list.size() > 0){
			resultDto.resultCode = 1;
			resultDto.message = "获取信息成功！";
			resultDto.userDtoList = list;
		}else {
			resultDto.resultCode = 0;
			resultDto.message = "没有获取到信息！";
		}
		return resultDto;
	}
	
	public ResultDto getNotification(String loginName) {
		ResultDto resultDto = new ResultDto();
		List<NotificationDto> list = notificationDao.getNotification(loginName);
		
		if(list.size() > 0){
			resultDto.resultCode = 1;
			resultDto.message = "获取信息成功！";
			resultDto.notificationDtoList = list;
		}else {
			resultDto.resultCode = 0;
			resultDto.message = "没有获取到信息！";
		}
		return resultDto;
	}
	
	public ResultDto changeNoticeUseFlag(String noticeId) {
		ResultDto resultDto = new ResultDto();
		notificationDao.changeNoticeUseFlag(noticeId);
		resultDto.resultCode = 1;
		resultDto.message = "已成功标为已读！";
		return resultDto;
	}
	public ResultDto deleteAllNotice(String loginName) {
		ResultDto resultDto = new ResultDto();
		notificationDao.deleteAllNotice(loginName);
		resultDto.resultCode = 1;
		resultDto.message = "已成功标为已读！";
		return resultDto;
	}
}
