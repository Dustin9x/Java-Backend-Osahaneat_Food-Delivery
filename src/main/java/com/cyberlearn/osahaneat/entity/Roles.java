package com.cyberlearn.osahaneat.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "role_name")
    private String roleName;

    @Column(name = "created_date")
    private Date createdDate;

    @OneToMany(mappedBy = "roles")
    Set<Users> lstUsers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Users> getLstUsers() {
        return lstUsers;
    }

    public void setLstUsers(Set<Users> lstUsers) {
        this.lstUsers = lstUsers;
    }
}
