package src.main.utweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.utweb.server.dao.EmailDao;
import src.main.utweb.server.dao.UserDao;
import src.main.utweb.server.dto.EmailDto;
import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.server.dto.UserDto;

@Service
public class ActivateService {
	@Autowired
    private UserDao userDao;
	
	@Autowired
    private EmailDao emailDao;
	
	public ResultDto doActivate(String activateId,String email) {
		ResultDto resultDto = new ResultDto();
		List<UserDto> list = userDao.checkIsActivate(email);
		if(list.size() > 0) {
			//判断激活信息
			List<EmailDto> emailList = emailDao.getEmailInfoById(activateId);
			
			if(emailList.size() > 0) {
				userDao.doActivate(email);
				resultDto.resultCode = 1;
				resultDto.message = "激活成功！";
			}else {
				resultDto.resultCode = 0;
				resultDto.message = "激活信息不正确，请重新注册！";
			}	
		}else{
			resultDto.resultCode = 1;
			resultDto.message = "您已经激活，无需再次激活！";
		}
		return resultDto;
	}
}
