package org.example.service;

import org.example.adapter.PurchaseTxtAdapter;
import org.example.adapter.factory.PurchaseAdapterFactory;
import org.example.exception.PurchaseServiceException;
import org.example.model.Purchase;
import org.example.util.FileUtil;
import org.junit.jupiter.api.Assertions;
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

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    FileUtil fileUtil;
    @Mock
    PurchaseAdapterFactory adapterFactory;
    @Mock
    PurchaseTxtAdapter txtAdapter;

    @InjectMocks
    PurchaseService service;

    Purchase purchase1;
    Purchase purchase2;
    Purchase purchase3;

    String dataPath = "D:/data_iss/test/discount_day.txt";
    String resultPath = "D:/data_iss/test/result.txt";
    double cost = 10;
    int discountPercent = 50;
    int discountStep = 5;

    @BeforeEach
    public void setUp() {
        purchase1 = new Purchase(
                LocalDateTime.parse("2021-02-09T08:42:59"),
                "Power Engineer",
                17480);
        purchase2 = new Purchase(
                LocalDateTime.parse("2021-02-09T09:50:10"),
                "Fossil",
                19600);
        purchase3 = new Purchase(
                LocalDateTime.parse("2021-02-10T08:53:25"),
                "Power Engineer",
                24600);
    }


    @Test
    public void saveResultInTextFile_testWithOrdinaryArguments(){

        List<Purchase> purchaseList = List.of(purchase1, purchase2, purchase3);

        Mockito.when(adapterFactory.createAdapter(dataPath)).thenReturn(txtAdapter);

        Mockito.when(txtAdapter.toPurchaseList(dataPath)).thenReturn(purchaseList);

        service.saveResultInTextFile(resultPath, dataPath, cost, discountPercent, discountStep);

        Map<String, Double> sumMap = new HashMap<>();
        sumMap.put("Power Engineer", 235000.0);
        sumMap.put("Fossil", 107800.0);

        Mockito.verify(fileUtil).save(sumMap, resultPath);
    }

    @Test
    public void saveResultInTextFile_testWithZeroDiscount(){

        discountPercent = 0;

        List<Purchase> purchaseList = List.of(purchase1, purchase2, purchase3);

        Mockito.when(adapterFactory.createAdapter(dataPath)).thenReturn(txtAdapter);

        Mockito.when(txtAdapter.toPurchaseList(dataPath)).thenReturn(purchaseList);

        service.saveResultInTextFile(resultPath, dataPath, cost, discountPercent, discountStep);

        Map<String, Double> sumMap = new HashMap<>();
        sumMap.put("Power Engineer", 420800.0);
        sumMap.put("Fossil", 196000.0);

        Mockito.verify(fileUtil).save(sumMap, resultPath);
    }

    @Test
    public void saveResultInTextFile_nullDataPath_throwsException(){
        dataPath = null;

        Assertions.assertThrows(NullPointerException.class, () -> service.saveResultInTextFile(
                resultPath,
                dataPath,
                cost,
                discountPercent,
                discountStep));

    }

    @Test
    public void saveResultInTextFile_nullResultPath_throwsNullPointerException(){
        resultPath = null;

        Assertions.assertThrows(NullPointerException.class, () -> service.saveResultInTextFile(
                resultPath,
                dataPath,
                cost,
                discountPercent,
                discountStep));

    }

    @Test
    public void saveResultInTextFile_costLessThenZero_throwsPurchaseServiceException(){
        cost = -1;

        Assertions.assertThrows(PurchaseServiceException.class, () -> service.saveResultInTextFile(
                resultPath,
                dataPath,
                cost,
                discountPercent,
                discountStep));

    }

    @Test
    public void saveResultInTextFile_discountPercentLessThenZero_throwsPurchaseServiceException(){
        discountPercent = -1;

        Assertions.assertThrows(PurchaseServiceException.class, () -> service.saveResultInTextFile(
                resultPath,
                dataPath,
                cost,
                discountPercent,
                discountStep));

    }

}
