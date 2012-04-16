package de.cismet.projecttracker.report.db.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "staff", schema = "public")
public class Staff extends BasicHibernateEntity {
    private String firstname;
    private String name;
    private int permissions;
    private String username;
    private String email;
    private byte[] password;
    private Set<Contract> contracts = new HashSet<Contract>(0);
    private Set<Activity> activities = new HashSet<Activity>(0);

    public Staff() {
    }

    public Staff(long id, String firstname, String name, int permissions, String username, String email, byte[] password) {
        this.id = id;
        this.firstname = firstname;
        this.name = name;
        this.permissions = permissions;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Staff(long id, String firstname, String name, int permissions, String username, String email, byte[] password, Set<Contract> contracts, Set<Activity> activities) {
        this.id = id;
        this.firstname = firstname;
        this.name = name;
        this.permissions = permissions;
        this.username = username;
        this.password = password;
        this.contracts = contracts;
        this.activities = activities;
        this.email = email;
    }

    @Column(name = "firstname", nullable = false, length = 50)
    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "permissions", nullable = false)
    public int getPermissions() {
        return this.permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "email", nullable = true, length = 100)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = true)
    public byte[] getPassword() {
        return this.password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "staff")
    public Set<Contract> getContracts() {
        return this.contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "staff")
    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }
}


