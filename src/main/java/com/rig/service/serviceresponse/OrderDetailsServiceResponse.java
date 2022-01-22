package com.rig.service.serviceresponse;

import com.rig.model.entity.Book;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class OrderDetailsServiceResponse {

    private String orderNumber;
    private LocalDateTime orderCreationDate;
    private Set<Book> orderItemList;
    private BigDecimal totalPrice;
}
