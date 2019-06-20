package com.ecust.touhouairline.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "authoritytable", schema = "flight", catalog = "")
public class AuthoritytableEntity {
    private String auhtorityNo;
    private String authorityName;
    private Collection<AuthorityassociationEntity> authorityassociationsByAuhtorityNo;

    @Id
    @Column(name = "auhtorityNo")
    public String getAuhtorityNo() {
        return auhtorityNo;
    }

    public void setAuhtorityNo(String auhtorityNo) {
        this.auhtorityNo = auhtorityNo;
    }

    @Basic
    @Column(name = "authorityName")
    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthoritytableEntity that = (AuthoritytableEntity) o;
        return Objects.equals(auhtorityNo, that.auhtorityNo) &&
                Objects.equals(authorityName, that.authorityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auhtorityNo, authorityName);
    }

    @OneToMany(mappedBy = "authoritytableByAuhtorityNo")
    public Collection<AuthorityassociationEntity> getAuthorityassociationsByAuhtorityNo() {
        return authorityassociationsByAuhtorityNo;
    }

    public void setAuthorityassociationsByAuhtorityNo(Collection<AuthorityassociationEntity> authorityassociationsByAuhtorityNo) {
        this.authorityassociationsByAuhtorityNo = authorityassociationsByAuhtorityNo;
    }
}
