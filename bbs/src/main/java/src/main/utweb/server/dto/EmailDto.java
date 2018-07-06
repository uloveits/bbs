package src.main.utweb.server.dto;

public class EmailDto {
	public String id;
	public String email;
	public int sendFlag;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(int sendFlag) {
		this.sendFlag = sendFlag;
	}
}
