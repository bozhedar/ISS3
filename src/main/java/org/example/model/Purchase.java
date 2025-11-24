package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Purchase {
    private LocalDateTime date;
    private String customerName;
    private long sum;
    private static double discountPercent = 50;

    public void applyDiscount() {
        if (discountPercent > 0) {
            sum -= (long) (sum * (discountPercent / 100));
            discountPercent -= 5;
        }
    }
}
