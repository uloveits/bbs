package src.main.utweb.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.utweb.server.dao.UserDao;
import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.server.dto.UserDto;



@Service
public class LoginService {
	@Autowired
    private UserDao userDao;


	public ResultDto checkSignIn(String login_name, String password) {
		ResultDto resultDto = new ResultDto();
		//先check用户名存不存在
		List<UserDto> userListForName = userDao.CheckLoginName(login_name);
		if(userListForName.size() == 0){
			resultDto.resultCode = 0;
			resultDto.message = "用户名不存在！";
			return resultDto;
		}
		//check密码是否正确
		List<UserDto> userListForPwd = userDao.CheckPassword(login_name,password);
		if(userListForPwd.size() == 0){
			resultDto.resultCode = 0;
			resultDto.message = "您输入的密码不正确！";
			return resultDto;
		}else{
			resultDto.resultCode = 1;
			resultDto.message = "登录成功！";
			resultDto.userDto = userListForPwd.get(0);
			return resultDto;
		}
	}
}
