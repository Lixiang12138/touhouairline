package com.ecust.touhouairline.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "characterassociation", schema = "flight", catalog = "")
@IdClass(CharacterAssociationEntityPK.class)
public class CharacterAssociationEntity {
    private String userNo;
    private String characterNo;
    private UserEntity userByUserNo;
    private CharacterEntity characterByCharacterNo;

    @Id
    @Column(name = "userNo")
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Id
    @Column(name = "characterNo")
    public String getCharacterNo() {
        return characterNo;
    }

    public void setCharacterNo(String characterNo) {
        this.characterNo = characterNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterAssociationEntity that = (CharacterAssociationEntity) o;
        return Objects.equals(userNo, that.userNo) &&
                Objects.equals(characterNo, that.characterNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNo, characterNo);
    }

    @ManyToOne
    @JoinColumn(name = "userNo", referencedColumnName = "userNo", nullable = false)
    public UserEntity getUserByUserNo() {
        return userByUserNo;
    }

    public void setUserByUserNo(UserEntity userByUserNo) {
        this.userByUserNo = userByUserNo;
    }

    @ManyToOne
    @JoinColumn(name = "characterNo", referencedColumnName = "characterNo", nullable = false)
    public CharacterEntity getCharactertableByCharacterNo() {
        return characterByCharacterNo;
    }

    public void setCharacterByCharacterNo(CharacterEntity charactertableByCharacterNo) {
        this.characterByCharacterNo = charactertableByCharacterNo;
    }
}
