package com.ecust.touhouairline.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "luggage", schema = "flight", catalog = "")
public class LuggageEntity {
    private String luggageNo;
    private String detailNo;
    private int weight;
    private int shippingFee;

    @Id
    @Column(name = "luggageNo")
    public String getLuggageNo() {
        return luggageNo;
    }

    public void setLuggageNo(String luggageNo) {
        this.luggageNo = luggageNo;
    }

    @Basic
    @Column(name = "detailNo")
    public String getDetailNo() {
        return detailNo;
    }

    public void setDetailNo(String detailNo) {
        this.detailNo = detailNo;
    }

    @Basic
    @Column(name = "weight")
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "shippingFee")
    public int getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(int shippingFee) {
        this.shippingFee = shippingFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LuggageEntity that = (LuggageEntity) o;
        return weight == that.weight &&
                shippingFee == that.shippingFee &&
                Objects.equals(luggageNo, that.luggageNo) &&
                Objects.equals(detailNo, that.detailNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(luggageNo, detailNo, weight, shippingFee);
    }

}
