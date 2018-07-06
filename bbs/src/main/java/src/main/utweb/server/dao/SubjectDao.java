package src.main.utweb.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import src.main.utweb.server.dto.SubjectDto;
import src.main.utweb.server.dto.SubjectReplyDto;

@Repository
public class SubjectDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void saveNewSubject(String subject_id,String subject_user,String subject_title,
												String subject_content, String subject_type) {
		String sql = "INSERT INTO subject(id,loginName,title,content,type,replyCount,replyName,createTime,replyTime,delFlag) "
				+ "values(?,?,?,?,?,0, '',now(),now(),1)";
		jdbcTemplate.update(sql, subject_id,subject_user,subject_title, subject_content, subject_type);
	}
	
	public List<SubjectDto> getNewSubject(String subject_type,String search_content) {
		String sql = "select id ,"
				+ " S.loginName,"
				+ " userName,"
				+ " title,"
				+ " content,"
				+ " type,"
				+ " avatar,"
				+ " replyCount,"
				+ " replyName,"
				+ " createTime,"
				+ " replyTime "
				+ " from subject S"
				+ " left join user_info U"
				+ " on (S.loginName = U.loginName)"
				+ " where S.delFlag = 1 "
				+ " and type = ? "
				+ " and (title like ? "
				+ " or content like ?) "
				+ " order by replyTime desc , createTime desc";
		RowMapper<SubjectDto> rm = new BeanPropertyRowMapper<SubjectDto>(SubjectDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(subject_type);
		params.add("%"+ search_content +"%");
		params.add("%"+ search_content +"%");
		List<SubjectDto> subjectDtoList = jdbcTemplate.query(sql, rm, params.toArray());
		return subjectDtoList;
	}
	
	public List<SubjectDto> getSubjectInfoById(String contentId) {
		String sql = "select id ,"
				+ " S.loginName,"
				+ " userName,"
				+ " title,"
				+ " content,"
				+ " type,"
				+ " avatar,"
				+ " replyCount,"
				+ " replyName,"
				+ " createTime,"
				+ " replyTime "
				+ " from subject S"
				+ " left join user_info U"
				+ " on (S.loginName = U.loginName)"
				+ " where S.delFlag = 1 "
				+ " and S.id = ? ";
		RowMapper<SubjectDto> rm = new BeanPropertyRowMapper<SubjectDto>(SubjectDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(contentId);
		List<SubjectDto> subjectDtoList = jdbcTemplate.query(sql, rm, params.toArray());
		return subjectDtoList;
	}
	
	public List<SubjectReplyDto> getReplyInfo(String id) {
		String sql = "select id ,"
				+ " S.loginName,"
				+ " U.userName,"
				+ " content,"
				+ " noticeLoginN,"
				+ " noticeUserN,"
				+ " avatar,"
				+ " replyTime "
				+ " from subject_reply S"
				+ " left join user_info U"
				+ " on (S.loginName = U.loginName)"
				+ " where S.delFlag = 1 "
				+ " and S.id = ? "
				+ " and U.delFlag = 1 "
				+ " order by replyTime ASC";
		RowMapper<SubjectReplyDto> rm = new BeanPropertyRowMapper<SubjectReplyDto>(SubjectReplyDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		List<SubjectReplyDto> subjectReplyDtoList = jdbcTemplate.query(sql, rm, params.toArray());
		return subjectReplyDtoList;
	}
	
	public void addReplyContent(String contentId,String replyContent,String loginName,String noticeLoginN,String noticeUserN) {
		String sql = "INSERT INTO subject_reply(id,loginName,content,noticeLoginN,noticeUserN,replyTime,delFlag) "
				+ "values(?,?,?,?,?,now(),1)";
		jdbcTemplate.update(sql, contentId,loginName,replyContent,noticeLoginN,noticeUserN);
	}
	
	
	public void addReplyCount(String contentId,String newCount,String userName) {
		String sql = "UPDATE subject set replyCount = ? , replyName = ? ,replyTime = now()  WHERE id = ?";
		jdbcTemplate.update(sql,newCount,userName,contentId);
	}
	
	public List<SubjectDto> getPersonalSubject(String loginName) {
		String sql = "select id ,"
				+ " S.loginName,"
				+ " userName,"
				+ " title,"
				+ " content,"
				+ " type,"
				+ " avatar,"
				+ " replyCount,"
				+ " replyName,"
				+ " createTime,"
				+ " replyTime "
				+ " from subject S"
				+ " left join user_info U"
				+ " on (S.loginName = U.loginName)"
				+ " where S.delFlag = 1 "
				+ " and S.loginName = ? "
				+ " and U.delFlag = 1 "
				+ " order by replyTime desc , createTime desc";
		RowMapper<SubjectDto> rm = new BeanPropertyRowMapper<SubjectDto>(SubjectDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(loginName);
		List<SubjectDto> subjectDtoList = jdbcTemplate.query(sql, rm, params.toArray());
		return subjectDtoList;
	}
	
	public List<SubjectReplyDto> getPersonalReply(String loginName) {
		String sql = " select R.id,"+
				" R.content,"+
				" R.noticeLoginN,"+
				" R.noticeUserN,"+
				" R.replyTime,"+
				" S.loginName,"+
				" S.title,"+
				" S.`type`,"+
				" U.userName"+
				" from subject_reply R"+
				" left join subject S"+
				" on (R.id = S.id)"+
				" left join user_info U"+
				" on (S.loginName = U.loginName)"+
				" where R.loginName = ?"+
				" and R.delFlag = 1"+
				" and S.delFlag = 1"+
				" and U.delFlag = 1"+
				" order by R.replyTime desc";
		RowMapper<SubjectReplyDto> rm = new BeanPropertyRowMapper<SubjectReplyDto>(SubjectReplyDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(loginName);
		List<SubjectReplyDto> subjectReplyDtoList = jdbcTemplate.query(sql, rm, params.toArray());
		return subjectReplyDtoList;
	}
	
}
