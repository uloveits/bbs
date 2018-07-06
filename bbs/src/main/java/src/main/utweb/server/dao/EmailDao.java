package src.main.utweb.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import src.main.utweb.server.dto.EmailDto;

@Repository
public class EmailDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void saveEmail(String id,String emailAddress) {
		String sql = "INSERT INTO email_info(id,email,sendFlag) "
					+ "values(?,?,0)";
		jdbcTemplate.update(sql, id,emailAddress);
	}

	public List<EmailDto> getEmailInfo() {
		String sql = "select * from email_info where sendFlag = 0 ";
		RowMapper<EmailDto> rm = new BeanPropertyRowMapper<EmailDto>(EmailDto.class);
		List<EmailDto> list = jdbcTemplate.query(sql, rm);
		return list;
	}
	
	public List<EmailDto> getEmailInfoById(String id) {
		String sql = "select * from email_info where sendFlag = 1 and id = ?";
		RowMapper<EmailDto> rm = new BeanPropertyRowMapper<EmailDto>(EmailDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		List<EmailDto> list = jdbcTemplate.query(sql, rm,params.toArray());
		return list;
	}
	
	public void changeEmailSendFlag(String emailAddress) {
		String sql = "UPDATE email_info set sendFlag = 1  WHERE email = ?";
		jdbcTemplate.update(sql,emailAddress);
	}

	
}
