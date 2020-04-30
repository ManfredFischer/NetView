package de.netview.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.netview.model.Hardware;
import de.netview.model.LDAPUser;
import de.netview.model.Lizenz;

public class UserDetails implements Serializable {

    private static final long serialVersionUID = -320454294025384012L;
    private Long luid;
    private String username;
    private String token;
    private String description;
    private String pin;
    private String puk;
    private List<LizenzData> lizenz = new ArrayList<LizenzData>();
    private List<HardwareInformation> hardwarerent = new ArrayList<>();

    public UserDetails(LDAPUser ldapUser) {

        if (ldapUser != null) {
            setLuid(ldapUser.getLuid());
            setToken(ldapUser.getToken());
            setDescription(ldapUser.getDescription());
            setPin(ldapUser.getPin());
            setPuk(ldapUser.getPuk());
            setUsername(ldapUser.getUsername());
            for (Lizenz lizenz : ldapUser.getLizenz()) {
                this.lizenz.add(new LizenzData(lizenz));
            }

            for (Hardware hardwareData : ldapUser.getHardwarerent()) {
                this.hardwarerent.add(new HardwareInformation(hardwareData));
            }
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLuid() {
        return luid;
    }

    public void setLuid(Long luid) {
        this.luid = luid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPuk() {
        return puk;
    }

    public void setPuk(String puk) {
        this.puk = puk;
    }

    public List<LizenzData> getLizenz() {
        return lizenz;
    }

    public void setLizenz(List<LizenzData> lizenz) {
        this.lizenz = lizenz;
    }

    public List<HardwareInformation> getHardwarerent() {
        return hardwarerent;
    }

    public void setHardwarerent(List<HardwareInformation> hardwarerent) {
        this.hardwarerent = hardwarerent;
    }


}
