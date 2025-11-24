package org.example.util;

import lombok.NonNull;
import org.example.model.Purchase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TotalSumCounterUtil {
    static final String RESULT_PATH = "D:/ISS/src/main/java/org/example/data/result.txt";
    static final String DATA_PATH = "D:/ISS/src/main/java/org/example/data/discount_day.txt";

    static public Map<String, Long> getTotalSum() {
        Map<String, Long> sumMap = new HashMap<>();
        ArrayList<Purchase> purchases = getPurchasesFromFile(DATA_PATH);

        purchases.sort(Comparator.comparing(Purchase::getDate));

        for (Purchase p : purchases) {
            p.applyDiscount();
            sumMap.merge(p.getCustomerName(), p.getSum(), Long::sum);
        }

        return sumMap;
    }

    public static void saveInTextFile() {
        Map<String, Long> sumMap = getTotalSum();

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(RESULT_PATH))) {

            for (Map.Entry<String, Long> entry : sumMap.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Failed to save file.");
        }
    }


    static public ArrayList<Purchase> getPurchasesFromFile(@NonNull String path) {
        ArrayList<Purchase> purchases = new ArrayList<>();

        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(path))) {

            while (bufferedReader.ready()) {
                String[] purchaseData = bufferedReader.readLine().split("\\|");

                LocalDateTime date = LocalDateTime.parse(purchaseData[0]);
                String name = purchaseData[1];
                long sum = Long.parseLong(purchaseData[2]);

                purchases.add(new Purchase(date, name, sum));
            }
        } catch (IOException e) {
            System.out.println("Failed to read file.");
        }
        return purchases;
    }
}
