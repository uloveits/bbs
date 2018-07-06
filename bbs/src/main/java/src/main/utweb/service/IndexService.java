package src.main.utweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.main.utweb.server.dao.SubjectDao;
import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.server.dto.SubjectDto;


@Service
public class IndexService {
	@Autowired
    private SubjectDao subjectDao;


	public ResultDto getNewSubject(String subject_type,String search_content) {
		ResultDto resultDto = new ResultDto();
		String str = search_content;
		String newStr = "%";
		int count = str.length();
		for(int i=0;i<count;i++){
			newStr = newStr+str.substring(i,i+1) +"%";		
		}
		List<SubjectDto> subjectDtoList = subjectDao.getNewSubject(subject_type,newStr);
		if(subjectDtoList.size() > 0) {
			resultDto.resultCode = 1;
			resultDto.message = "获取主题列表成功！！";
			resultDto.subjectDtoList = subjectDtoList;
		}else {
			resultDto.resultCode = 0;
			resultDto.message = "该节点没有帖子！！";
		}
		return resultDto;
	}
}
