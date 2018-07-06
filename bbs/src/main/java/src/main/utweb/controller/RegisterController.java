package src.main.utweb.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.service.RegisterService;
import src.main.utweb.util.PassWordEncrypt;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
    private RegisterService registerService;

	@RequestMapping(value = "")
	public String register(){
		LOGGER.info("加载注册页面！");
		return "register";
	}
	
	@RequestMapping(value = "/doSignUp")
	@ResponseBody
	@Transactional
	public ResultDto doSignUp(String userName,String emailAddress,String loginName,String password){
		LOGGER.info("==========doSignUp_start======");
		ResultDto resultDto = new ResultDto();
		String passwordMd5 = PassWordEncrypt.md5Encode(password);
		System.out.println(passwordMd5);
		resultDto = registerService.doSignUp(userName,emailAddress,loginName,passwordMd5);
		LOGGER.info("==========doSignUp_end======");
		return resultDto;
	}
	
}
