package org.example.adapter;

import org.example.model.Purchase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PurchaseAdapter {
    public List<Purchase> toPurchaseList(List<String[]> strings) {
        List<Purchase> purchases = new ArrayList<>();

        for (String[] s : strings) {
            LocalDateTime date = LocalDateTime.parse(s[0]);
            String name = s[1];
            long sum = Long.parseLong(s[2]);

            purchases.add(new Purchase(date, name, sum));
        }
        return purchases;
    }
}
