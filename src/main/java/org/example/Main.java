package org.example;

import org.example.service.PurchaseService;

public class Main {
    public static void main(String[] args) {
        final String resultPath = "D:/data_iss/result.txt";
        final String dataPath = "D:/data_iss/discount_day.txt";
        final double cost = 10;
        final int discountPercent = 50;
        final int discountStep = 5;

        PurchaseService service = new PurchaseService();

        service.saveResultInTextFile(
                resultPath,
                dataPath,
                cost,
                discountPercent,
                discountStep);
        }
    }

    