package de.netview.data;

import de.netview.dao.IMobileUserDao;

public class WizardUserData {
    private String firstname;
    private String lastname;
    private String mobile;
    private String phone;
    private String mail;

    public String getFirstname() {
        return checkNull(firstname);
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return checkNull(lastname);
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return checkNull(mobile);
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return checkNull(phone);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return checkNull(mail);
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    private String checkNull(String value){
        return value == null ? "" : value;
    }
}
