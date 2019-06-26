package com.ecust.touhouairline.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "flight", catalog = "")
public class UserEntity implements Serializable {
  @Id
  private String userName;
  private String password;
  private String userPhone;
  private String email;
  private String nickName;
  private Integer credit;
  private CharacterEntity characterByUserNo;
  private Collection<OrderMasterEntity> ordermastersByUserNo;
  private Collection<PassengerEntity> passengersByUserNo;

  @Id
  @Column(name = "userno")
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Basic
  @Column(name = "password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Basic
  @Column(name = "userphone")
  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  @Basic
  @Column(name = "email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Basic
  @Column(name = "nickname")
  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  @Basic
  @Column(name = "credit")
  public Integer getCredit() {
    return credit;
  }

  public void setCredit(Integer credit) {
    this.credit = credit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserEntity that = (UserEntity) o;
    return Objects.equals(userName, that.userName) &&
            Objects.equals(password, that.password) &&
            Objects.equals(userPhone, that.userPhone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(nickName, that.nickName) &&
            Objects.equals(credit, that.credit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, nickName, password, userPhone, email, credit);
  }

  @ManyToOne
  @JoinColumn(name = "characterno", referencedColumnName = "characterno", nullable = false)
  public CharacterEntity getCharacterByUserNo() {
    return characterByUserNo;
  }

  public void setCharacterByUserNo(CharacterEntity characterByUserNo) {
    this.characterByUserNo = characterByUserNo;
  }

  @OneToMany(mappedBy = "userNo")
  public Collection<OrderMasterEntity> getOrdermastersByUserNo() {
    return ordermastersByUserNo;
  }

  public void setOrdermastersByUserNo(Collection<OrderMasterEntity> ordermastersByUserNo) {
    this.ordermastersByUserNo = ordermastersByUserNo;
  }

  @OneToMany(mappedBy = "userNo")
  public Collection<PassengerEntity> getPassengersByUserNo() {
    return passengersByUserNo;
  }

  public void setPassengersByUserNo(Collection<PassengerEntity> passengersByUserNo) {
    this.passengersByUserNo = passengersByUserNo;
  }

  @Override
  public String toString() {
    return "UserEntity{" +
            "userName='" + userName + '\'' +
            ", password='" + password + '\'' +
            ", userPhone='" + userPhone + '\'' +
            ", email='" + email + '\'' +
            ", nickName='" + nickName + '\'' +
            ", credit=" + credit +
            ", characterByUserNo=" + characterByUserNo +
            ", ordermastersByUserNo=" + ordermastersByUserNo +
            ", passengersByUserNo=" + passengersByUserNo +
            '}';
  }
}
