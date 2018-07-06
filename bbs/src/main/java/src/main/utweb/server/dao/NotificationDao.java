package src.main.utweb.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import src.main.utweb.server.dto.NotificationDto;


@Repository
public class NotificationDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void AddReplyNotice(String id,String replyTo,String loginName,String replyContent,String contentId,int type) {
		String sql = "INSERT INTO notification(id,noticeTo,noticeFrom,noticeContent,subjectId,noticeTime,type,useFlag) "
				+ "values(?,?,?,?,?,now(),?,0)";
		jdbcTemplate.update(sql, id,replyTo,loginName,replyContent, contentId, type);
	}
	
	public List<NotificationDto> getNotification(String loginName) {
		String sql = " select N.id as id,"+
				" noticeTo,"+
				" U_T.userName as noticeToName,"+
				" noticeFrom,"+
				" U_F.userName as noticeFromName,"+
				" noticeContent,"+
				" noticeTime,"+
				" N.`type` as type,"+
				" subjectId,"+
				" S.title"+
				" from notification N"+
				" left join user_info U_T"+
				" on (N.noticeTo = U_T.loginName)"+
				" left join user_info U_F"+
				" on (N.noticeFrom = U_F.loginName)"+
				" left join subject S"+
				" on (N.subjectId = S.id)"+
				" where useFlag = 0 and noticeTo = ? ";
		RowMapper<NotificationDto> rm = new BeanPropertyRowMapper<NotificationDto>(NotificationDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(loginName);
		List<NotificationDto> list = jdbcTemplate.query(sql, rm, params.toArray());
		return list;
	}
	
	public void changeNoticeUseFlag(String noticeId) {
		String sql = "UPDATE notification set useFlag = 1 where id = ?";
		jdbcTemplate.update(sql, noticeId);
	}
	
	public void deleteAllNotice(String loginName) {
		String sql = "UPDATE notification set useFlag = 1 where noticeTo = ?";
		jdbcTemplate.update(sql, loginName);
	}
}
