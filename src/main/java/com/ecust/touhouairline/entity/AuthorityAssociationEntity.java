package com.ecust.touhouairline.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authorityassociation", schema = "flight", catalog = "")
@IdClass(AuthorityAssociationEntityPK.class)
public class AuthorityAssociationEntity {
    private String characterNo;
    private String auhtorityNo;
    private CharacterEntity characterByCharacterNo;
    private AuthorityEntity authorityByAuhtorityNo;

    @Id
    @Column(name = "characterNo")
    public String getCharacterNo() {
        return characterNo;
    }

    public void setCharacterNo(String characterNo) {
        this.characterNo = characterNo;
    }

    @Id
    @Column(name = "auhtorityNo")
    public String getAuhtorityNo() {
        return auhtorityNo;
    }

    public void setAuhtorityNo(String auhtorityNo) {
        this.auhtorityNo = auhtorityNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityAssociationEntity that = (AuthorityAssociationEntity) o;
        return Objects.equals(characterNo, that.characterNo) &&
                Objects.equals(auhtorityNo, that.auhtorityNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterNo, auhtorityNo);
    }

    @ManyToOne
    @JoinColumn(name = "characterNo", referencedColumnName = "characterNo", nullable = false)
    public CharacterEntity getCharacterByCharacterNo() {
        return characterByCharacterNo;
    }

    public void setCharacterByCharacterNo(CharacterEntity charactertableByCharacterNo) {
        this.characterByCharacterNo = charactertableByCharacterNo;
    }

    @ManyToOne
    @JoinColumn(name = "auhtorityNo", referencedColumnName = "auhtorityNo", nullable = false)
    public AuthorityEntity getAuthorityByAuhtorityNo() {
        return authorityByAuhtorityNo;
    }

    public void setAuthorityByAuhtorityNo(AuthorityEntity authoritytableByAuhtorityNo) {
        this.authorityByAuhtorityNo = authoritytableByAuhtorityNo;
    }
}
