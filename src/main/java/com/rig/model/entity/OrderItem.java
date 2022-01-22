package com.rig.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem extends AbstractDateEntity {

    @OneToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @Column(name = "NUMBER_IN_BASKET")
    private int numberInBasket;
}
