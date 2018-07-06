package src.main.utweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.utweb.server.dao.SubjectDao;
import src.main.utweb.server.dao.UserDao;
import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.server.dto.SubjectDto;
import src.main.utweb.server.dto.SubjectReplyDto;
import src.main.utweb.server.dto.UserDto;


@Service
public class PersonalCenterService {
	@Autowired
    private UserDao userDao;
	
	@Autowired
    private SubjectDao subjectDao;


	public ResultDto changeUserImg(String img,String loginName) {
		ResultDto resultDto = new ResultDto();
		userDao.ChangeUserImg(img, loginName);
		resultDto.resultCode = 1;
		resultDto.message = "更新成功！";
		return resultDto;
		
	}
	
	public ResultDto getUserInfoByLoginName(String loginName) {
		ResultDto resultDto = new ResultDto();
		List<UserDto> list = userDao.CheckLoginName(loginName);
		if(list.size() > 0 ){
			resultDto.resultCode = 1;
			resultDto.message = "获取信息成功！";
			resultDto.userDto = list.get(0);
		}else {
			resultDto.resultCode = 0;
			resultDto.message = "获取信息失败！";
		}
		
		return resultDto;	
	}
	
	public ResultDto getPersonalSubject(String loginName) {
		ResultDto resultDto = new ResultDto();
		List<SubjectDto> subjectDtoList = subjectDao.getPersonalSubject(loginName);
		if(subjectDtoList.size() > 0 ){
			resultDto.resultCode = 1;
			resultDto.message = "获取个人主题信息成功！";
			resultDto.subjectDtoList = subjectDtoList;
		}else {
			resultDto.resultCode = 0;
			resultDto.message = "获取个人主题信息失败！";
		}
		
		return resultDto;	
	}
	
	public ResultDto getPersonalReply(String loginName) {
		ResultDto resultDto = new ResultDto();
		List<SubjectReplyDto> subjectReplyDtoList = subjectDao.getPersonalReply(loginName);
		if(subjectReplyDtoList.size() > 0 ){
			resultDto.resultCode = 1;
			resultDto.message = "获取个人回复信息成功！";
			resultDto.subjectReplyDtoList = subjectReplyDtoList;
		}else {
			resultDto.resultCode = 0;
			resultDto.message = "获取个人回复信息失败！";
		}
		
		return resultDto;	
	}
}
