package org.example.adapter;

import lombok.Getter;
import org.example.model.Purchase;
import org.example.util.FileUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class PurchaseAdapter {
    String SPLITERATOR = null;
    private final FileUtil fileUtil = new FileUtil();

    public List<Purchase> toPurchaseList(String path) {

        List<String[]> strings = fileUtil.getStrings(path, SPLITERATOR);

        return strings.stream()
                .map((s) -> {
                    LocalDateTime date = LocalDateTime.parse(s[0]);
                    String name = s[1];
                    long sum = Long.parseLong(s[2]);
                    return new Purchase(date, name, sum);
                })
                .toList();
    }
}
