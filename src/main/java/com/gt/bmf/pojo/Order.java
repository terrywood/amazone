package com.gt.bmf.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 15-9-21.
 */
@Entity
@Table(name="order_entity")
public class Order implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String  id;
    private Date createTime;
    private Date orderTime;
    private String orderName;
    private Integer totalItem;

    @Transient
    private String orderLink;

    @Transient
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public String getOrderLink() {
        return orderLink;
    }

    public void setOrderLink(String orderLink) {
        this.orderLink = orderLink;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}
