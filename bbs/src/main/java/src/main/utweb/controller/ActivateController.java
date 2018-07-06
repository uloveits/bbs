package src.main.utweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.service.ActivateService;


@Controller
public class ActivateController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivateController.class);
	

	@Autowired
    private ActivateService activateService;
	
	@RequestMapping(value = "/activate")
	public String activate(){
		LOGGER.info("加载激活页面！");
		return "activate";
	}
	
	@RequestMapping(value = "/doActivate")
	@ResponseBody
	public ResultDto doActivate(String activateId,String mail){
		LOGGER.info("==========isActivate_start======");
		
		ResultDto resultDto = new ResultDto();
		resultDto = activateService.doActivate(activateId,mail);
		
		LOGGER.info("==========isActivate_end======");
		return resultDto;
	}
}
