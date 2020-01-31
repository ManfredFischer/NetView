package de.netview.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LDAPGROUP")
public class LDAPGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, precision = 10)
    private long lgid;
    private String groupname;
    private String groupdn;

    public long getLgid() {
        return lgid;
    }

    public void setLgid(long lgid) {
        this.lgid = lgid;
    }

    public String getGroupdn() {
        return groupdn;
    }

    public void setGroupdn(String groupdn) {
        this.groupdn = groupdn;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
