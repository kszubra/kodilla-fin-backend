package com.kodilla.kodillafinalbackend.enumeration;

public enum PaymentStatus {

    AWAITING("AWAITING"),
    PAID("PAID"),
    REJECTED("REJECTED");

    private String description;

    PaymentStatus(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.getDescription();
    }
}
