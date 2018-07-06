package src.main.utweb.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.utweb.server.dao.SubjectDao;
import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.util.UUIDTool;


@Service
public class NewSubjectService {
	@Autowired
    private SubjectDao subjectDao;

	public ResultDto saveNewSubject(String subject_user,String subject_title, String subject_content, String subject_type) {
		String subject_id = UUIDTool.creatUUID();
		ResultDto resultDto = new ResultDto();
		subjectDao.saveNewSubject(subject_id,subject_user,subject_title,subject_content,subject_type);
		resultDto.resultCode = 1;
		resultDto.message = "创建成功！！";
		return resultDto;
	}
}
