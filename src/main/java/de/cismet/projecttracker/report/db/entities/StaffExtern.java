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
    name = "staff_extern",
    schema = "public"
)
public class StaffExtern extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private String firstname;
    private String name;
    private String username;
    private String email;
    private byte[] password;
    private Set<WorkPackage> workpackages = new HashSet<WorkPackage>(0);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Staff object.
     */
    public StaffExtern() {
    }

    /**
     * Creates a new Staff object.
     *
     * @param  id         DOCUMENT ME!
     * @param  firstname  DOCUMENT ME!
     * @param  name       DOCUMENT ME!
     * @param  username   DOCUMENT ME!
     * @param  email      DOCUMENT ME!
     * @param  password   DOCUMENT ME!
     */
    public StaffExtern(final long id,
            final String firstname,
            final String name,
            final String username,
            final String email,
            final byte[] password) {
        this.id = id;
        this.firstname = firstname;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
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
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "staff_extern_workpackage",
        schema = "public",
        joinColumns = {
                @JoinColumn(
                    name = "staffexternid",
                    nullable = false,
                    updatable = false
                )
            },
        inverseJoinColumns = {
                @JoinColumn(
                    name = "workpackageid",
                    nullable = false,
                    updatable = false
                )
            }
    )
    public Set<WorkPackage> getWorkpackages() {
        return this.workpackages;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workpackage  projects contracts DOCUMENT ME!
     */
    public void setWorkpackages(final Set<WorkPackage> workpackage) {
        this.workpackages = workpackage;
    }
}
