package com.mycompany.customer;

public class CustomerNotFoundException extends Throwable {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
