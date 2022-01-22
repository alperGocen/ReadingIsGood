package com.rig.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book extends AbstractDateEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default true")
    private boolean isActive = Boolean.TRUE;

    @Column(name = "NUMBER_IN_STOCK")
    private int numberInStock;

    @Version
    @Column(name = "version", columnDefinition = "int default 0")
    private int version;
}
