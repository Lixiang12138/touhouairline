package com.ecust.touhouairline.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "orderdetail", schema = "flight", catalog = "")
public class OrderDetailEntity {
    private String detailNo;
    private String orderNo;
    private String passengerName;
    private String passengerType;
    private String identity;
    private String passport;
    private String phone;
    private Integer fee;
    private String state;
    private String seat;
    private Collection<LuggageEntity> luggagesByDetailNo;
    private OrderMasterEntity ordermasterByOrderNo;

    @Id
    @Column(name = "detailNo")
    public String getDetailNo() {
        return detailNo;
    }

    public void setDetailNo(String detailNo) {
        this.detailNo = detailNo;
    }

    @Basic
    @Column(name = "orderNo")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
    @Column(name = "identity")
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
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
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "fee")
    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "seat")
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailEntity that = (OrderDetailEntity) o;
        return Objects.equals(detailNo, that.detailNo) &&
                Objects.equals(orderNo, that.orderNo) &&
                Objects.equals(passengerName, that.passengerName) &&
                Objects.equals(passengerType, that.passengerType) &&
                Objects.equals(identity, that.identity) &&
                Objects.equals(passport, that.passport) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(fee, that.fee) &&
                Objects.equals(state, that.state) &&
                Objects.equals(seat, that.seat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detailNo, orderNo, passengerName, passengerType, identity, passport, phone, fee, state, seat);
    }

    @OneToMany(mappedBy = "orderdetailByDetailNo")
    public Collection<LuggageEntity> getLuggagesByDetailNo() {
        return luggagesByDetailNo;
    }

    public void setLuggagesByDetailNo(Collection<LuggageEntity> luggagesByDetailNo) {
        this.luggagesByDetailNo = luggagesByDetailNo;
    }

    @ManyToOne
    @JoinColumn(name = "orderNo", referencedColumnName = "orderNo", nullable = false)
    public OrderMasterEntity getOrdermasterByOrderNo() {
        return ordermasterByOrderNo;
    }

    public void setOrdermasterByOrderNo(OrderMasterEntity ordermasterByOrderNo) {
        this.ordermasterByOrderNo = ordermasterByOrderNo;
    }
}
