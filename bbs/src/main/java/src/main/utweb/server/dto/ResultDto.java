package src.main.utweb.server.dto;

import java.util.ArrayList;
import java.util.List;

public class ResultDto {
	public int resultCode;
	public String message;
	
	public UserDto userDto = new UserDto();
	
	public List<UserDto> userDtoList= new ArrayList<UserDto>();
	
	public List<SubjectDto> subjectDtoList= new ArrayList<SubjectDto>();
	
	public List<SubjectReplyDto> subjectReplyDtoList= new ArrayList<SubjectReplyDto>();
	
	public List<NotificationDto> notificationDtoList= new ArrayList<NotificationDto>();
	
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
