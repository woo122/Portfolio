package com.springsecurity.example1.entity;

public enum OrderStatus {
    ORDER("주문완료"),
    CANCEL("주문취소"),
    PAYMENT_COMPLETE("결제완료"),
    PREPARING("상품준비중"),
    DELIVERY("배송중"),
    COMPLETE("배송완료");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
