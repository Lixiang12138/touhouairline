package com.ecust.touhouairline.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "flight", schema = "flight", catalog = "")
public class FlightEntity {
    private String flightNo;
    private String planeNo;
    private Timestamp departTime;
    private Timestamp arrivedTime;
    private String departPlace;
    private String destination;
    private int economyPrice;
    private int premiumPrice;
    private int firstPrice;
    private String boardingGate;
    private String flightState;
    private Integer mileage;
    private PlaneEntity planeByPlaneNo;
    private Collection<OrderMasterEntity> ordermastersByFlightNo;

    @Id
    @Column(name = "flightNo")
    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    @Basic
    @Column(name = "planeNo")
    public String getPlaneNo() {
        return planeNo;
    }

    public void setPlaneNo(String planeNo) {
        this.planeNo = planeNo;
    }

    @Basic
    @Column(name = "departTime")
    public Timestamp getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Timestamp departTime) {
        this.departTime = departTime;
    }

    @Basic
    @Column(name = "arrivedTime")
    public Timestamp getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(Timestamp arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    @Basic
    @Column(name = "departPlace")
    public String getDepartPlace() {
        return departPlace;
    }

    public void setDepartPlace(String departPlace) {
        this.departPlace = departPlace;
    }

    @Basic
    @Column(name = "destination")
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "economyPrice")
    public int getEconomyPrice() {
        return economyPrice;
    }

    public void setEconomyPrice(int economyPrice) {
        this.economyPrice = economyPrice;
    }

    @Basic
    @Column(name = "premiumPrice")
    public int getPremiumPrice() {
        return premiumPrice;
    }

    public void setPremiumPrice(int premiumPrice) {
        this.premiumPrice = premiumPrice;
    }

    @Basic
    @Column(name = "firstPrice")
    public int getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(int firstPrice) {
        this.firstPrice = firstPrice;
    }

    @Basic
    @Column(name = "boardingGate")
    public String getBoardingGate() {
        return boardingGate;
    }

    public void setBoardingGate(String boardingGate) {
        this.boardingGate = boardingGate;
    }

    @Basic
    @Column(name = "flightState")
    public String getFlightState() {
        return flightState;
    }

    public void setFlightState(String flightState) {
        this.flightState = flightState;
    }

    @Basic
    @Column(name = "mileage")
    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightEntity that = (FlightEntity) o;
        return economyPrice == that.economyPrice &&
                premiumPrice == that.premiumPrice &&
                firstPrice == that.firstPrice &&
                Objects.equals(flightNo, that.flightNo) &&
                Objects.equals(planeNo, that.planeNo) &&
                Objects.equals(departTime, that.departTime) &&
                Objects.equals(arrivedTime, that.arrivedTime) &&
                Objects.equals(departPlace, that.departPlace) &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(boardingGate, that.boardingGate) &&
                Objects.equals(flightState, that.flightState) &&
                Objects.equals(mileage, that.mileage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNo, planeNo, departTime, arrivedTime, departPlace, destination, economyPrice, premiumPrice, firstPrice, boardingGate, flightState, mileage);
    }

    @ManyToOne
    @JoinColumn(name = "planeNo", referencedColumnName = "planeNo", nullable = false)
    public PlaneEntity getPlaneByPlaneNo() {
        return planeByPlaneNo;
    }

    public void setPlaneByPlaneNo(PlaneEntity planeByPlaneNo) {
        this.planeByPlaneNo = planeByPlaneNo;
    }

    @OneToMany(mappedBy = "flightByFlightNo")
    public Collection<OrderMasterEntity> getOrdermastersByFlightNo() {
        return ordermastersByFlightNo;
    }

    public void setOrdermastersByFlightNo(Collection<OrderMasterEntity> ordermastersByFlightNo) {
        this.ordermastersByFlightNo = ordermastersByFlightNo;
    }
}
