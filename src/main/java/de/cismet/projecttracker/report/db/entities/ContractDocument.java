package de.cismet.projecttracker.report.db.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contract_document", schema = "public")
public class ContractDocument extends BasicHibernateEntity {

    private Contract contract;
    private String documentname;
    private String mimetype;
    private byte[] document;

    public ContractDocument() {
    }

    public ContractDocument(long id) {
        this.id = id;
    }

    public ContractDocument(long id, Contract contract, byte[] document, String documentname, String mimetype) {
        this.id = id;
        this.contract = contract;
        this.document = document;
        this.documentname = documentname;
        this.mimetype = mimetype;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contractid")
    public Contract getContract() {
        return this.contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "document", nullable = false)
    public byte[] getDocument() {
        return this.document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    @Column(name = "documentname")
    public String getDocumentname() {
        return this.documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    @Column(name = "mimetype")
    public String getMimetype() {
        return this.mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
}


