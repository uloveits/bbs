package src.main.utweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.service.NewSubjectService;

@Controller
public class NewSubjectController {

	
	@Autowired
    private NewSubjectService newSubjectService;
	
	@RequestMapping(value = "/newSubject.do")
	public String creatNewSubject(){
		return "createNewSubject";
	}
	
	@RequestMapping(value = "/creatNewSubject")
	@ResponseBody
	@Transactional
	public ResultDto creatNewSubject(String subject_user, String subject_title,String subject_content, String subject_type) {
		System.out.println("==========creatNewSubject_start======");
		ResultDto resultDto = new ResultDto();
		resultDto = newSubjectService.saveNewSubject(subject_user,subject_title, subject_content,subject_type);
		System.out.println("==========creatNewSubject_end======");
		return resultDto;
	}
	
}