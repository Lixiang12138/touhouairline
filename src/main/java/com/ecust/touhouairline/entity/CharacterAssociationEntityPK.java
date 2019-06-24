package com.ecust.touhouairline.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CharacterAssociationEntityPK implements Serializable {
    private String userNo;
    private String characterNo;

    @Column(name = "userNo")
    @Id
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Column(name = "characterNo")
    @Id
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
        CharacterAssociationEntityPK that = (CharacterAssociationEntityPK) o;
        return Objects.equals(userNo, that.userNo) &&
                Objects.equals(characterNo, that.characterNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNo, characterNo);
    }
}
