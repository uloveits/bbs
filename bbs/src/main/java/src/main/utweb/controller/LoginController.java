package src.main.utweb.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import src.main.utweb.server.dto.ResultDto;
import src.main.utweb.service.LoginService;
import src.main.utweb.util.PassWordEncrypt;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
    private LoginService loginService;
	
	@RequestMapping(value = "")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value = "/buildImage")
	public void buildImage(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException{
		int width = 80;
        int height = 40;
        Random random = new Random();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        BufferedImage image = new BufferedImage(width, height, 1);
        Graphics g = image.getGraphics();
        g.setColor(this.getRandColor(200, 250));
        g.setFont(new Font("Times New Roman",0,28));
        g.fillRect(0, 0, width, height);
        for(int i=0;i<40;i++){
            g.setColor(this.getRandColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }
        String strCode = "";
        for(int i=0;i<4;i++){
            String rand = String.valueOf(random.nextInt(10));
            strCode = strCode + rand;
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(rand, 17*i+6, 28);
        }
        session.setAttribute("strCode", strCode);
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
        response.getOutputStream().flush();
	}
	
	@RequestMapping(value = "doSignIn")
	@ResponseBody
	public ResultDto doSignIn(HttpServletRequest request,HttpServletResponse response,String login_name, String password, String code) throws IOException {
		HttpSession session = request.getSession();
		ResultDto resultDto = new ResultDto();
		String strCode = session.getAttribute("strCode").toString();
		String passwordMd5 = PassWordEncrypt.md5Encode(password);
		resultDto = loginService.checkSignIn(login_name, passwordMd5);
		
		//如果登录成功，将用户信息存入session
		if(resultDto.resultCode == 1) {
			if(!strCode.equals(code)){
				resultDto.resultCode = 0;
				resultDto.message = "您输入的验证码不正确！";
				return resultDto;
			}
			session.setAttribute("loginName", resultDto.userDto.loginName);
			session.setAttribute("userName", resultDto.userDto.userName);
			session.setAttribute("avatar", resultDto.userDto.avatar);
			session.setAttribute("email", resultDto.userDto.email);
		}
		return resultDto;
	}
	
	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc < 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}