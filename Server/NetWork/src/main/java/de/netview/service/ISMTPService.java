package de.netview.service;

import org.apache.commons.mail.EmailException;

public interface ISMTPService {

    void sendMessage(String msg, String eMail, String subject) throws EmailException;
}
