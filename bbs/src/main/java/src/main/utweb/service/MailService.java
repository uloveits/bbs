package src.main.utweb.service;



import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service 
public class MailService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class); 
     
	    @Autowired  
	    private JavaMailSender sender;  
	      
	    @Value("${spring.mail.username}")  
	    private String from;  
	      
	    /** 
	     * 发送纯文本的简单邮件 
	     * @param to 
	     * @param subject 
	     * @param content 
	     */  
	    public void sendSimpleMail(String to, String subject, String content){  
	        SimpleMailMessage message = new SimpleMailMessage();  
	        message.setFrom(from);  
	        message.setTo(to);  
	        message.setSubject(subject);  
	        message.setText(content);  
	  
	        try {  
	            sender.send(message);  
	            LOGGER.info("简单邮件已经发送。");  
	        } catch (Exception e) {  
	        	LOGGER.error("发送简单邮件时发生异常！", e);  
	        }  
	    }  
	      
	    /** 
	     * 发送html格式的邮件 
	     * @param to 
	     * @param subject 
	     * @param content 
	     */  
	    public void sendHtmlMail(String to, String subject, String content){  
	        MimeMessage message = sender.createMimeMessage();  
	  
	        try {  
	            //true表示需要创建一个multipart message  
	            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");  
	            helper.setFrom(from);  
	            helper.setTo(to);  
	            helper.setSubject(subject);  
	            helper.setText(content, true);  
	  
	            sender.send(message);  
	            LOGGER.info("html邮件已经发送。");  
	        } catch (MessagingException e) {  
	        	LOGGER.error("发送html邮件时发生异常！", e);  
	        }  
	    }  
	      
	    /** 
	     * 发送带附件的邮件 
	     * @param to 
	     * @param subject 
	     * @param content 
	     * @param filePath 
	     */  
	    public void sendAttachmentsMail(String to, String subject, String content, String filePath){  
	        MimeMessage message = sender.createMimeMessage();  
	  
	        try {  
	            //true表示需要创建一个multipart message  
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);  
	            helper.setFrom(from);  
	            helper.setTo(to);  
	            helper.setSubject(subject);  
	            helper.setText(content, true);  
	  
	            FileSystemResource file = new FileSystemResource(new File(filePath));  
	            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));  
	            helper.addAttachment(fileName, file);  
	              
	            sender.send(message);  
	            LOGGER.info("带附件的邮件已经发送。");  
	        } catch (MessagingException e) {  
	        	LOGGER.error("发送带附件的邮件时发生异常！", e);  
	        }  
	    }  
}
