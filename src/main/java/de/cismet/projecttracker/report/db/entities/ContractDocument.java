/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.projecttracker.report.db.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
@Entity
@Table(
    name = "contract_document",
    schema = "public"
)
public class ContractDocument extends BasicHibernateEntity {

    //~ Instance fields --------------------------------------------------------

    private Contract contract;
    private String documentname;
    private String mimetype;
    private byte[] document;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ContractDocument object.
     */
    public ContractDocument() {
    }

    /**
     * Creates a new ContractDocument object.
     *
     * @param  id  DOCUMENT ME!
     */
    public ContractDocument(final long id) {
        this.id = id;
    }

    /**
     * Creates a new ContractDocument object.
     *
     * @param  id            DOCUMENT ME!
     * @param  contract      DOCUMENT ME!
     * @param  document      DOCUMENT ME!
     * @param  documentname  DOCUMENT ME!
     * @param  mimetype      DOCUMENT ME!
     */
    public ContractDocument(final long id,
            final Contract contract,
            final byte[] document,
            final String documentname,
            final String mimetype) {
        this.id = id;
        this.contract = contract;
        this.document = document;
        this.documentname = documentname;
        this.mimetype = mimetype;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contractid")
    public Contract getContract() {
        return this.contract;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  contract  DOCUMENT ME!
     */
    public void setContract(final Contract contract) {
        this.contract = contract;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(
        name = "document",
        nullable = false
    )
    public byte[] getDocument() {
        return this.document;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  document  DOCUMENT ME!
     */
    public void setDocument(final byte[] document) {
        this.document = document;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(name = "documentname")
    public String getDocumentname() {
        return this.documentname;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  documentname  DOCUMENT ME!
     */
    public void setDocumentname(final String documentname) {
        this.documentname = documentname;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Column(name = "mimetype")
    public String getMimetype() {
        return this.mimetype;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mimetype  DOCUMENT ME!
     */
    public void setMimetype(final String mimetype) {
        this.mimetype = mimetype;
    }
}
