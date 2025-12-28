package org.example.exception;

public class PurchaseServiceException extends RuntimeException {
    public PurchaseServiceException(String message) {
        super(message);
    }
}
