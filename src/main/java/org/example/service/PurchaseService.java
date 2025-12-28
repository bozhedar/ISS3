package org.example.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.adapter.PurchaseAdapter;
import org.example.adapter.factory.PurchaseAdapterFactory;
import org.example.exception.PurchaseServiceException;
import org.example.model.Purchase;
import org.example.util.FileUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseAdapterFactory purchaseAdapterFactory;
    private final FileUtil fileUtil;

    private Map<String, Double> getTotalSum(String dataPath, double cost,
                                                int discountPercent, int discountStep) {
        Map<String, Double> sumMap = new HashMap<>();
        List<Purchase> purchases = new ArrayList<>(getPurchasesFromFile(dataPath));

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



    public void saveResultInTextFile(@NonNull String resultPath, @NonNull String dataPath, double cost,
                                            int discountPercent, int discountStep) {
        if (cost <= 0) {
            throw new PurchaseServiceException("Cost must be more then zero");
        } else if (discountPercent < 0) {
            throw new PurchaseServiceException("Discount percent must be equal zero or more");
        }

        Map<String, Double> sumMap = getTotalSum(dataPath, cost, discountPercent, discountStep);
        fileUtil.save(sumMap, resultPath);
    }


    private List<Purchase> getPurchasesFromFile(String path) {
        PurchaseAdapter adapter = purchaseAdapterFactory.createAdapter(path);
        return adapter.toPurchaseList(path);
    }
}
