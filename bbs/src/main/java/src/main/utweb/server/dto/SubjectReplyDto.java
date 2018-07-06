package src.main.utweb.server.dto;

import java.util.Date;

public class SubjectReplyDto {
	public String id;
	public String loginName;
	public String userName;
	public String content;
	public String noticeLoginN;
	public String noticeUserN;
	public String avatar;
	public Date replyTime;
	public String delFlag;
	public String title;
	public String type;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNoticeLoginN() {
		return noticeLoginN;
	}
	public void setNoticeLoginN(String noticeLoginN) {
		this.noticeLoginN = noticeLoginN;
	}
	public String getNoticeUserN() {
		return noticeUserN;
	}
	public void setNoticeUserN(String noticeUserN) {
		this.noticeUserN = noticeUserN;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}
