package com.rig.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {

    public final String BOOK_NOT_EXIST = "Book with specified id does not exist";
    public final String STOCK_INSUFFICIENT = "There is not enough stock for this items";
    public final String STOCK_CHANGED = "Stock information changed for this item, please try again";
    public final String ALLOWED_MINIMUM_ORDER_ERROR = "You should at least order 1 item";
    public final String AUTH_FAIL = "Authentication Failed";
    public final String CUSTOMER_NOT_FOUND = "Customer with related id could not be found";
}
