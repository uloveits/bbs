package src.main.utweb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.service.IndexService;


@Controller
@RequestMapping(value = "/index")
public class IndexController {
	
	@Autowired
    private IndexService indexService;
	
	
	@RequestMapping(value = "")
	public String login(){
		return "index";
	}
	
	@RequestMapping(value = "/content_list")
	@ResponseBody
	public ResultDto content_list(String type,String search_content){
		System.out.println("==========content_list_start======");
		System.out.println(type);
		ResultDto resultDto = new ResultDto();
		resultDto = indexService.getNewSubject(type,search_content);
		System.out.println("==========content_list_end======");
		return resultDto;
	}
}