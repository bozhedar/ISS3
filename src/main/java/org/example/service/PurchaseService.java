package org.example.service;

import lombok.NonNull;
import org.example.adapter.PurchaseAdapter;
import org.example.model.Purchase;
import org.example.util.FileUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class PurchaseService {

    private final PurchaseAdapter purchaseAdapter = new PurchaseAdapter();
    private final FileUtil fileUtil = new FileUtil();

    public Map<String, Double> getTotalSum(String dataPath, double cost,
                                                int discountPercent, int discountStep) {
        Map<String, Double> sumMap = new HashMap<>();
        ArrayList<Purchase> purchases = getPurchasesFromFile(dataPath);

        purchases.sort(Comparator.comparing(Purchase::getDate));

        for (Purchase p : purchases) {
            double totalSum = p.getQuantity() * cost;

            if (discountPercent > 0) {
                double percent = (double) discountPercent / 100;
                totalSum -= totalSum * percent;
                discountPercent -= discountStep;
            }

            sumMap.merge(p.getCustomerName(), totalSum, Double::sum);
        }


        return sumMap;
    }



    public void saveResultInTextFile(String resultPath, String dataPath, double cost,
                                            int discountPercent, int discountStep) {

        Map<String, Double> sumMap = getTotalSum(dataPath, cost, discountPercent, discountStep);
        fileUtil.save(sumMap, resultPath);
    }


    public ArrayList<Purchase> getPurchasesFromFile(@NonNull String path) {
        return purchaseAdapter.toPurchaseList(
                fileUtil.getStrings(path, "\\|"));
    }
}
