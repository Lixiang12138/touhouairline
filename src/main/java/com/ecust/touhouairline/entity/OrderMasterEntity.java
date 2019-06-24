package com.ecust.touhouairline.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "ordermaster", schema = "flight", catalog = "")
public class OrderMasterEntity {
    private String orderNo;
    private String flightNo;
    private String userNo;
    private Date orderDate;
    private String ticketClass;
    private int sum;
    private String state;
    private Integer usedCredit;
    private Collection<OrderDetailEntity> orderdetailsByOrderNo;
    private FlightEntity flightByFlightNo;
    private UserEntity userByUserNo;

    @Id
    @Column(name = "orderNo")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "flightNo")
    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
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
    @Column(name = "orderDate")
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Basic
    @Column(name = "ticketClass")
    public String getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(String ticketClass) {
        this.ticketClass = ticketClass;
    }

    @Basic
    @Column(name = "sum")
    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
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
    @Column(name = "usedCredit")
    public Integer getUsedCredit() {
        return usedCredit;
    }

    public void setUsedCredit(Integer usedCredit) {
        this.usedCredit = usedCredit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderMasterEntity that = (OrderMasterEntity) o;
        return sum == that.sum &&
                Objects.equals(orderNo, that.orderNo) &&
                Objects.equals(flightNo, that.flightNo) &&
                Objects.equals(userNo, that.userNo) &&
                Objects.equals(orderDate, that.orderDate) &&
                Objects.equals(ticketClass, that.ticketClass) &&
                Objects.equals(state, that.state) &&
                Objects.equals(usedCredit, that.usedCredit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo, flightNo, userNo, orderDate, ticketClass, sum, state, usedCredit);
    }

    @OneToMany(mappedBy = "ordermasterByOrderNo")
    public Collection<OrderDetailEntity> getOrderdetailsByOrderNo() {
        return orderdetailsByOrderNo;
    }

    public void setOrderdetailsByOrderNo(Collection<OrderDetailEntity> orderdetailsByOrderNo) {
        this.orderdetailsByOrderNo = orderdetailsByOrderNo;
    }

    @ManyToOne
    @JoinColumn(name = "flightNo", referencedColumnName = "flightNo", nullable = false)
    public FlightEntity getFlightByFlightNo() {
        return flightByFlightNo;
    }

    public void setFlightByFlightNo(FlightEntity flightByFlightNo) {
        this.flightByFlightNo = flightByFlightNo;
    }

    @ManyToOne
    @JoinColumn(name = "userNo", referencedColumnName = "userNo", nullable = false)
    public UserEntity getUserByUserNo() {
        return userByUserNo;
    }

    public void setUserByUserNo(UserEntity userByUserNo) {
        this.userByUserNo = userByUserNo;
    }
}
