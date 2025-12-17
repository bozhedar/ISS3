package org.example.adapter;

import org.example.model.Purchase;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PurchaseAdapter {
    public ArrayList<Purchase> toPurchaseList(ArrayList<String[]> strings) {
        ArrayList<Purchase> purchases = new ArrayList<>();

        for (String[] s : strings) {
            LocalDateTime date = LocalDateTime.parse(s[0]);
            String name = s[1];
            long sum = Long.parseLong(s[2]);

            purchases.add(new Purchase(date, name, sum));
        }
        return purchases;
    }
}
