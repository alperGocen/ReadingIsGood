package com.rig.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class StatisticsDto {

    private int month;
    private int year;
    private long totalOrderCount;
    private long totalBookCount;
    private BigDecimal totalPurchasedAmount;
}
