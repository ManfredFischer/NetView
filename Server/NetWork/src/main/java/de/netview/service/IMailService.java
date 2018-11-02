package de.netview.service;

import java.util.List;

import javax.mail.MessagingException;

public interface IMailService {
	
	public void sendMessage(String from, String to, String subject, String massage, List content) throws MessagingException;

}
