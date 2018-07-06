package src.main.utweb.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import src.main.utweb.server.dto.UserDto;

@Repository
public class UserDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;


	public List<UserDto> getUserInfoByEmail(String email) {
		String sql = "select * from user_info where delFlag = 1 and activeFlag = 1 and email = ?";
		RowMapper<UserDto> rm = new BeanPropertyRowMapper<UserDto>(UserDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(email);
		List<UserDto> list = jdbcTemplate.query(sql, rm, params.toArray());
		return list;
	}
	
	public List<UserDto> CheckLoginName(String login_name){
		String sql = " select * from user_info where delFlag = 1 and activeFlag = 1 and loginName = ? ";		
		RowMapper<UserDto> rm = new BeanPropertyRowMapper<UserDto>(UserDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(login_name);
		List<UserDto> list = jdbcTemplate.query(sql, rm, params.toArray());
		return list;
	}
	
	public List<UserDto> CheckPassword(String login_name, String password){
		String sql = " select * from user_info where delFlag = 1 and activeFlag = 1 and loginName = ? and password = ?";		
		RowMapper<UserDto> rm = new BeanPropertyRowMapper<UserDto>(UserDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(login_name);
		params.add(password);
		List<UserDto> list = jdbcTemplate.query(sql, rm, params.toArray());
		return list;
	}
	
	public List<UserDto> getUserInfoByLoginName(String login_name){
		String sql = " select * from user_info where delFlag = 1 and activeFlag = 1 and loginName = ?";		
		RowMapper<UserDto> rm = new BeanPropertyRowMapper<UserDto>(UserDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(login_name);
		List<UserDto> list = jdbcTemplate.query(sql, rm, params.toArray());
		return list;
	}
	
	public void saveUserInfo(String userName,String emailAddress,String loginName,String password) {
		String sql = "INSERT INTO user_info(loginName,userName,avatar,password,email,updateTime,activeFlag,delFlag) "
					+ "values(?,?,?,?,?,now(),0,1)";
		jdbcTemplate.update(sql, loginName,userName,"default", password, emailAddress);
	}

	//判断是否激活
	public List<UserDto> checkIsActivate(String email){
		String sql = " select * from user_info where delFlag = 1 and activeFlag = 0 and email = ? ";		
		RowMapper<UserDto> rm = new BeanPropertyRowMapper<UserDto>(UserDto.class);
		List<Object> params = new ArrayList<Object>();
		params.add(email);
		List<UserDto> list = jdbcTemplate.query(sql, rm, params.toArray());
		return list;
	}
	
	public void doActivate(String email) {
		String sql = "UPDATE user_info set activeFlag = 1 where email = ?";
		jdbcTemplate.update(sql,email);
	}
	
	public void ChangeUserImg(String img, String loginName) {
		String sql = "UPDATE user_info set avatar = ? where loginName = ?";
		jdbcTemplate.update(sql,img,loginName);
	}
	
	public List<UserDto> getAllUserInfo() {
		String sql = "select loginName,userName from user_info where delFlag = 1 and activeFlag = 1";
		RowMapper<UserDto> rm = new BeanPropertyRowMapper<UserDto>(UserDto.class);
		List<UserDto> list = jdbcTemplate.query(sql, rm);
		return list;
	}

	
}
