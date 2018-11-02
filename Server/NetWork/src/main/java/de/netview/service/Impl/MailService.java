package de.netview.service.Impl;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import de.netview.service.IMailService;

@Service
public class MailService implements IMailService{

	@Autowired
    public JavaMailSender emailSender;
	
	@Override
	public void sendMessage(String from, String to, String subject, String massage, List content) throws MessagingException {
		
		MimeMessage message = emailSender.createMimeMessage();
	      
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	     
	    helper.setTo(to);
	    helper.setFrom(from);
	    helper.setSubject(subject);
	    helper.setText(massage);
	         
	    FileSystemResource file = new FileSystemResource(new File(""));
	    helper.addAttachment("Invoice", file);
	 
	    emailSender.send(message);
	}

}
