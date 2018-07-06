package src.main.utweb.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import src.main.utweb.server.dao.EmailDao;
import src.main.utweb.server.dto.EmailDto;
import src.main.utweb.service.MailService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class MailBatch {
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	@Autowired
	MailService mailService = new MailService();
	
	@Autowired
    private EmailDao emailDao;
	
    //每3秒执行一次
    @Scheduled(fixedRate = 30000)
    public void timerRate() throws Exception {
    
       
    }
    //第一次延迟3秒执行，当执行完后一分钟再执行
    @Scheduled(initialDelay = 3000, fixedDelay = 60000)
    public void timerInit() {
    	//找到需要发送的邮件
    	List<EmailDto> emailList = emailDao.getEmailInfo();
    	Date now = new Date();
    	long now_ms = now.getTime();
    	if(emailList.size() > 0) {
    		for(int i= 0; i< emailList.size(); i++) {
            	StringBuffer sb=new StringBuffer("点击下面链接激活账号，24小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
            	sb.append("<a href=\"http://localhost:8080/activate?activateId=");
            	sb.append(emailList.get(0).id); 
            	sb.append("&mail="); 
            	sb.append(emailList.get(0).email);
               	sb.append("&now="); 
            	sb.append(now_ms); 
              	sb.append("\">http://localhost:8080/activate?activateId="); 
              	sb.append(emailList.get(0).id);
              	sb.append("&mail="); 
            	sb.append(emailList.get(0).email); 
             	sb.append("&now="); 
            	sb.append(now_ms); 
              	sb.append("</a>");
              	mailService.sendHtmlMail(emailList.get(0).email,"用户激活",sb.toString());
        		emailDao.changeEmailSendFlag(emailList.get(0).email);	
        	}
    	}
    	
    }

    //每天23点27分50秒时执行
    @Scheduled(cron = "50 27 23 * * ?")
    public void timerCron() {
    	
    }
}
