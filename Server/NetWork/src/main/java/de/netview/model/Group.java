package de.netview.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mf on 13.08.2016.
 */
@Entity
@Table(name = "User")
public class Group {

    private Long gid;
    private String groupname;
    private String right;
    private List<User> user = new ArrayList<User>();

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Id
    @GeneratedValue
    @Column(name = "gid", nullable = false)
    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="uid")
    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
