package com.ecust.touhouairline.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "passenger", schema = "flight", catalog = "")
public class PassengerEntity {
    private String passengerNo;
    private String userNo;
    private String passengerName;
    private String passengerType;
    private String passport;
    private String identity;
    private String sex;
    private String passengerPhone;

    @Id
    @Column(name = "passengerNo")
    public String getPassengerNo() {
        return passengerNo;
    }

    public void setPassengerNo(String passengerNo) {
        this.passengerNo = passengerNo;
    }

    @Basic
    @Column(name = "userNo")
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Basic
    @Column(name = "passengerName")
    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    @Basic
    @Column(name = "passengerType")
    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    @Basic
    @Column(name = "passport")
    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Basic
    @Column(name = "identity")
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Basic
    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "passengerPhone")
    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerEntity that = (PassengerEntity) o;
        return Objects.equals(passengerNo, that.passengerNo) &&
                Objects.equals(userNo, that.userNo) &&
                Objects.equals(passengerName, that.passengerName) &&
                Objects.equals(passengerType, that.passengerType) &&
                Objects.equals(passport, that.passport) &&
                Objects.equals(identity, that.identity) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(passengerPhone, that.passengerPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerNo, userNo, passengerName, passengerType, passport, identity, sex, passengerPhone);
    }

}
