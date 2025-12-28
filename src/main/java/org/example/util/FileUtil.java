package org.example.util;


import org.example.exception.IORuntimeException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class FileUtil {
    public List<String[]> getStrings(String path, String spliterator) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(path))) {

            while (bufferedReader.ready()) {
                String[] strings = bufferedReader.readLine().split(spliterator);
                data.add(strings);
            }

        } catch (IOException e) {
            throw new IORuntimeException("Failed to read file.");
        }
        return data;
    }

    public <K, V>  void save (Map<K, V> map, String resultPath) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(resultPath))) {

            Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                writer.write(entry.getKey() + " - " + entry.getValue());
                if (iterator.hasNext()) {
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new IORuntimeException("Failed to save file.");
        }
    }
}
