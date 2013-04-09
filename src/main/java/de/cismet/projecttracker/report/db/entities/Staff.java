/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.db.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "staff",
    schema = "public"
)
public class Staff extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private String firstname;
    private String name;
    private int permissions;
    private String username;
    private String email;
    private byte[] password;
    private Set<Contract> contracts = new HashSet<Contract>(0);
    private Set<Activity> activities = new HashSet<Activity>(0);
    private Date lastmodification;
    private Profile profile;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Staff object.
     */
    public Staff() {
    }

    /**
     * Creates a new Staff object.
     *
     * @param  id                DOCUMENT ME!
     * @param  firstname         DOCUMENT ME!
     * @param  name              DOCUMENT ME!
     * @param  permissions       DOCUMENT ME!
     * @param  username          DOCUMENT ME!
     * @param  email             DOCUMENT ME!
     * @param  password          DOCUMENT ME!
     * @param  lastmodification  DOCUMENT ME!
     */
    public Staff(final long id,
            final String firstname,
            final String name,
            final int permissions,
            final String username,
            final String email,
            final byte[] password,
            final Date lastmodification) {
        this.id = id;
        this.firstname = firstname;
        this.name = name;
        this.permissions = permissions;
        this.username = username;
        this.password = password;
        this.email = email;
        this.lastmodification = lastmodification;
    }

    /**
     * Creates a new Staff object.
     *
     * @param  id                DOCUMENT ME!
     * @param  firstname         DOCUMENT ME!
     * @param  name              DOCUMENT ME!
     * @param  permissions       DOCUMENT ME!
     * @param  username          DOCUMENT ME!
     * @param  email             DOCUMENT ME!
     * @param  password          DOCUMENT ME!
     * @param  lastmodification  DOCUMENT ME!
     * @param  contracts         DOCUMENT ME!
     * @param  activities        DOCUMENT ME!
     */
    public Staff(final long id,
            final String firstname,
            final String name,
            final int permissions,
            final String username,
            final String email,
            final byte[] password,
            final Date lastmodification,
            final Set<Contract> contracts,
            final Set<Activity> activities) {
        this.id = id;
        this.firstname = firstname;
        this.name = name;
        this.permissions = permissions;
        this.username = username;
        this.password = password;
        this.contracts = contracts;
        this.activities = activities;
        this.email = email;
        this.lastmodification = lastmodification;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "firstname",
        nullable = false,
        length = 50
    )
    public String getFirstname() {
        return this.firstname;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  firstname  DOCUMENT ME!
     */
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "name",
        nullable = false,
        length = 50
    )
    public String getName() {
        return this.name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  name  DOCUMENT ME!
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "permissions",
        nullable = false
    )
    public int getPermissions() {
        return this.permissions;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  permissions  DOCUMENT ME!
     */
    public void setPermissions(final int permissions) {
        this.permissions = permissions;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "username",
        nullable = false,
        length = 50
    )
    public String getUsername() {
        return this.username;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  username  DOCUMENT ME!
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "email",
        nullable = true,
        length = 100
    )
    public String getEmail() {
        return this.email;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  email  DOCUMENT ME!
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(
        name = "password",
        nullable = true
    )
    public byte[] getPassword() {
        return this.password;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  password  DOCUMENT ME!
     */
    public void setPassword(final byte[] password) {
        this.password = password;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name = "lastmodification",
        nullable = true,
        length = 29
    )
    public Date getLastmodification() {
        return lastmodification;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lastmodification  DOCUMENT ME!
     */
    public void setLastmodification(final Date lastmodification) {
        this.lastmodification = lastmodification;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        mappedBy = "staff"
    )
    public Set<Contract> getContracts() {
        return this.contracts;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  contracts  DOCUMENT ME!
     */
    public void setContracts(final Set<Contract> contracts) {
        this.contracts = contracts;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "staff"
    )
    public Set<Activity> getActivities() {
        return this.activities;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  activities  DOCUMENT ME!
     */
    public void setActivities(final Set<Activity> activities) {
        this.activities = activities;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "profileid",
        nullable = true
    )
    public Profile getProfile() {
        return profile;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  profile  DOCUMENT ME!
     */
    public void setProfile(final Profile profile) {
        this.profile = profile;
    }
}
