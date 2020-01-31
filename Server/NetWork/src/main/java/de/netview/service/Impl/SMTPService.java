package de.netview.service.Impl;

import de.netview.data.WizardUserData;
import de.netview.function.IFileUtil;
import de.netview.function.impl.StringOwnUtils;
import de.netview.service.ISMTPService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Service
public class SMTPService implements ISMTPService {

    @Autowired
    private IFileUtil fileUtil;
    private MultiPartEmail email = null;
    String mailserver = "mail.gmx.net";
    String username = "m.fischer1983@gmx.de";
    String password = "k1ngSize";

    private void setMailServer() throws EmailException {
        email = new HtmlEmail();
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setSSLOnConnect(true);
        email.setHostName(mailserver);
        email.setCharset("UTF-8");
    }

    @Override
    public void sendMessage(String msg, String to, String subject) throws EmailException {
        setMailServer();
        email.addTo(to);
        email.setFrom("m.fischer1983@gmx.de","Manfred Fischer");
        email.setSubject(subject);
        email.setMsg(msg);
        email.send();
    }



    @Override
    public void sendUserInformation(WizardUserData wizardUserData){
        try {
            File file = fileUtil.getFileFromResources("de/netview/template/EMailAddUser.html");
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String fileData = "";
            String fileRow = fileReader.readLine();
            while (fileRow != null){
                fileData += fileRow;
                fileRow = fileReader.readLine();
            }

            fileData = StringOwnUtils.ersetze("firstname",wizardUserData.getFirstname(),fileData);
            fileData = StringOwnUtils.ersetze("lastname",wizardUserData.getLastname(),fileData);
            fileData = StringOwnUtils.ersetze("mail",wizardUserData.getMail(),fileData);
            fileData = StringOwnUtils.ersetze("phone",wizardUserData.getPhone(),fileData);
            fileData = StringOwnUtils.ersetze("mobile",wizardUserData.getMobile(),fileData);

            sendMessage(fileData,"manfred.fischer@reservix.de","Neuer Mitarbeiter - "+wizardUserData.getFirstname()+" "+wizardUserData.getLastname());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
