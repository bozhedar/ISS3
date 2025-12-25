package org.example.service;

import org.example.adapter.PurchaseTxtAdapter;
import org.example.adapter.factory.PurchaseAdapterFactory;
import org.example.model.Purchase;
import org.example.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    FileUtil fileUtil;
    @Mock
    PurchaseAdapterFactory adapterFactory;

    @InjectMocks
    PurchaseService service;

    @Test
    public void test(){
        Purchase purchase1 = new Purchase(LocalDateTime.parse("2021-02-09T08:42:59"), "Power Engineer", 17480);
        Purchase purchase2 = new Purchase(LocalDateTime.parse("2021-02-09T09:50:10"), "Fossil", 19600);
        Purchase purchase3 = new Purchase(LocalDateTime.parse("2021-02-10T08:53:25"), "Power Engineer", 24600);

        final double cost = 10;
        final int discountPercent = 50;
        final int discountStep = 5;
        PurchaseTxtAdapter txtAdapter = new PurchaseTxtAdapter();
        String dataPath = "D:/data_iss/test/discount_day.txt";
        String resultPath = "D:/data_iss/test/result.txt";
        List<Purchase> purchaseList = List.of(purchase1, purchase2, purchase3);

        Mockito.when(adapterFactory.createAdapter(dataPath)).thenReturn(txtAdapter);
        when(fileUtil.getStrings(dataPath, txtAdapter.getSPLITERATOR())).thenReturn(List.of(
                new String[]{"2021-02-09T08:42:59", "Power Engineer", "17480"},
                new String[]{"2021-02-09T09:50:10", "Fossil", "19600"},
                new String[]{"2021-02-10T08:53:25", "Power Engineer", "24600"}
        ));
        Mockito.when(txtAdapter.toPurchaseList(dataPath)).thenReturn(purchaseList);

        service.saveResultInTextFile(resultPath, dataPath, cost, discountPercent, discountStep);

        Map<String, Double> sumMap = new HashMap<>();
        sumMap.put("Power Engineer", 110700.0);
        sumMap.put("Fossil", 78400.0);

        Mockito.verify(fileUtil).save(sumMap, resultPath);

    }

}
