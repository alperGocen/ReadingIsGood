package com.rig.model.entity;

import com.rig.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
public class Order extends AbstractDateEntity {

    @Column(name = "ORDER_NUMBER")
    private String orderNumber;

    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "ORDERED_ITEM",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ORDER_ITEM_ID"))
    private Set<OrderItem> orderItems = new HashSet<>();


    public void addOrderItem(final OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }
}
