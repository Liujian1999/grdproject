package com.ecommerce.demo.utils;

import java.util.UUID;

public class OrderUtils {

    public static String generateOrderNumber() {
       String uuid  = UUID.randomUUID().toString();
       return  uuid;
    }
}
