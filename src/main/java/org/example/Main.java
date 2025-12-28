package org.example;


import org.example.adapter.factory.PurchaseAdapterFactory;
import org.example.service.PurchaseService;
import org.example.util.FileUtil;

public class Main {
    public static void main(String[] args) {
        final String resultPath = "D:/data_iss/result.txt";
        final String dataPath = "D:/data_iss/discount_day.txt";
        final double cost = 10;
        final int discountPercent = 50;
        final int discountStep = 5;

        PurchaseAdapterFactory purchaseAdapterFactory = new PurchaseAdapterFactory();
        FileUtil fileUtil = new FileUtil();

        PurchaseService service = new PurchaseService(purchaseAdapterFactory, fileUtil);

        service.saveResultInTextFile(
                resultPath,
                dataPath,
                cost,
                discountPercent,
                discountStep);
        }
    }

    