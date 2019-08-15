package de.netview.service.Impl;

import de.netview.service.ISMTPService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.stereotype.Service;

@Service
public class SMTPService implements ISMTPService {

    private MultiPartEmail email = null;
    String mailserver = "smtp.gmail.com";
    String username = "manfred.fischer@reservix.de";
    String password = "k1ngSize";

    private void setMailServer() throws EmailException {
        email = new MultiPartEmail();
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setSSLOnConnect(true);
        email.setHostName(mailserver);
        email.setFrom("");
        email.setCharset("UTF-8");
    }

    @Override
    public void sendMessage(String msg, String to, String subject) throws EmailException {
        setMailServer();
        email.addTo(to);
        email.setSubject(subject);
        email.setMsg(msg);
        email.send();
    }
}
