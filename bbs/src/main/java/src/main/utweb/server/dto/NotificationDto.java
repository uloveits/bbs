package src.main.utweb.server.dto;

import java.util.Date;

public class NotificationDto {
	public String id;
	public String noticeTo;
	public String noticeFrom;
	public String noticeContent;
	public String subjectId;
	public Date noticeTime;
	public int type;
	public String useFlag;
	
	public String noticeToName;
	public String noticeFromName;
	public String title;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoticeToName() {
		return noticeToName;
	}
	public void setNoticeToName(String noticeToName) {
		this.noticeToName = noticeToName;
	}
	public String getNoticeFromName() {
		return noticeFromName;
	}
	public void setNoticeFromName(String noticeFromName) {
		this.noticeFromName = noticeFromName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNoticeTo() {
		return noticeTo;
	}
	public void setNoticeTo(String noticeTo) {
		this.noticeTo = noticeTo;
	}
	public String getNoticeFrom() {
		return noticeFrom;
	}
	public void setNoticeFrom(String noticeFrom) {
		this.noticeFrom = noticeFrom;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public Date getNoticeTime() {
		return noticeTime;
	}
	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
}
