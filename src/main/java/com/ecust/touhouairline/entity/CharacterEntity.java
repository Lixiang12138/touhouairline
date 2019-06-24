package com.ecust.touhouairline.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "charactertable", schema = "flight", catalog = "")
public class CharacterEntity {
    private String characterNo;
    private String characterName;
    private Collection<AuthorityAssociationEntity> authorityAssociationsByCharacterNo;

    @Id
    @Column(name = "characterNo")
    public String getCharacterNo() {
        return characterNo;
    }

    public void setCharacterNo(String characterNo) {
        this.characterNo = characterNo;
    }

    @Basic
    @Column(name = "characterName")
    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterEntity that = (CharacterEntity) o;
        return Objects.equals(characterNo, that.characterNo) &&
                Objects.equals(characterName, that.characterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterNo, characterName);
    }

    @OneToMany(mappedBy = "charactertableByCharacterNo")
    public Collection<AuthorityAssociationEntity> getAuthorityAssociationsByCharacterNo() {
        return authorityAssociationsByCharacterNo;
    }

    public void setAuthorityAssociationsByCharacterNo(Collection<AuthorityAssociationEntity> authorityassociationsByCharacterNo) {
        this.authorityAssociationsByCharacterNo = authorityassociationsByCharacterNo;
    }

}
