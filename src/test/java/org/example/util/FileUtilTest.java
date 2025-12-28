package org.example.util;

import org.example.exception.IORuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

    private final FileUtil fileUtil = new FileUtil();

    @Test
    void getStrings_ShouldParseFileCorrectly(@TempDir Path tempDir) throws IOException {

        Path inputFile = tempDir.resolve("input.txt");
        String content = "Alice,100\nBob,200\nCharlie,300";
        Files.writeString(inputFile, content);

        List<String[]> result = fileUtil.getStrings(inputFile.toString(), ",");

        assertEquals(3, result.size());
        assertArrayEquals(new String[]{"Alice", "100"}, result.get(0));
        assertArrayEquals(new String[]{"Bob", "200"}, result.get(1));
        assertArrayEquals(new String[]{"Charlie", "300"}, result.get(2));
    }

    @Test
    void getStrings_ShouldHandleEmptyFile(@TempDir Path tempDir) throws IOException {
        Path inputFile = tempDir.resolve("empty.txt");
        Files.createFile(inputFile);

        List<String[]> result = fileUtil.getStrings(inputFile.toString(), ",");

        assertTrue(result.isEmpty());
    }

    @Test
    void getStrings_ShouldThrowIORuntimeException_WhenFileNotFound() {
        assertThrows(IORuntimeException.class, () ->
                fileUtil.getStrings("/non/existent/file.csv", ",")
        );
    }

    @Test
    void save_ShouldWriteMapToFileCorrectly(@TempDir Path tempDir) throws IOException {
        Path outputFile = tempDir.resolve("output.txt");
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("Alice", 150.0);
        map.put("Bob", 200.5);

        fileUtil.save(map, outputFile.toString());

        List<String> lines = Files.readAllLines(outputFile);
        assertEquals(2, lines.size());
        assertEquals("Alice - 150.0", lines.get(0));
        assertEquals("Bob - 200.5", lines.get(1));
    }

    @Test
    void save_ShouldHandleEmptyMap(@TempDir Path tempDir) throws IOException {
        Path outputFile = tempDir.resolve("empty_output.txt");
        Map<String, Integer> map = new HashMap<>();

        fileUtil.save(map, outputFile.toString());

        String content = Files.readString(outputFile);
        assertEquals("", content);
    }

    @Test
    void save_ShouldThrowIORuntimeException_WhenInvalidPath() {
        Map<String, String> map = Map.of("key", "value");
        assertThrows(IORuntimeException.class, () ->
                fileUtil.save(map, "/notExistingPath/file.txt")
        );
    }
}