package src.main.utweb.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.utweb.server.dao.EmailDao;
import src.main.utweb.server.dao.UserDao;
import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.server.dto.UserDto;
import src.main.utweb.util.UUIDTool;

@Service
public class RegisterService {
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
    private EmailDao emailDao;

	public ResultDto doSignUp(String userName,String emailAddress,String loginName,String password) {
		ResultDto resultDto = new ResultDto();
		//检查用户名是否存在
		List<UserDto> listByLoginName = userDao.getUserInfoByLoginName(loginName);
		if(listByLoginName.size() > 0) {
			resultDto.resultCode = 0;
			resultDto.message = "该登录帐号已存在！！";
			return resultDto;
		}
		//检查邮箱是否存在
		List<UserDto> listByEmail = userDao.getUserInfoByEmail(emailAddress);
		if(listByEmail.size() > 0) {
			resultDto.resultCode = 0;
			resultDto.message = "该邮箱地址已存在！！";
			return resultDto;
		}
		
		//保存用户信息
		userDao.saveUserInfo(userName, emailAddress, loginName, password);
		
		//保存邮箱
		String emailId = UUIDTool.creatUUID();
		emailDao.saveEmail(emailId,emailAddress);
		
		resultDto.resultCode = 1;
		resultDto.message = "注册成功！请登录邮箱激活！";
		return resultDto;
	}

}
