package org.m1z1na.Searcher;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* Класс предназначен для парсинга файла с возможностью выбора строк по маске */
public class SearcherCsv implements Searcher {


    private final static String DELIMITER = ",";
    private final String filePath;
    private final CheckLine checkLine;
    private long timeSpent;

    public SearcherCsv(String filePath) {

        this.filePath = filePath;
        this.checkLine = new CheckLine();
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public List<String> searchDataByName(List<String> list, String searchTerm) {
        List<String> newList = new ArrayList<>();
        timeSpent = System.nanoTime();
        if (searchTerm == null || list.isEmpty()) {
            newList = list;
        } else {
            for (String line : list
            ) {
                var rowsArray = line.split(DELIMITER);
                if (rowsArray[2].toUpperCase().contains(searchTerm.toUpperCase(Locale.ROOT))) {
                    newList.add(line);
                }
            }
        }
        timeSpent = Duration.ofNanos(System.nanoTime() - timeSpent).toMillis();
        return newList;
    }

    public List<String> searchData(String searchTerm) {
        List<String> list = new ArrayList<>();
        String searchExpression;
        timeSpent = System.nanoTime();
        try (var bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(
                ClassLoader.getSystemResourceAsStream(filePath)), StandardCharsets.UTF_8))) {

            var row = bufferedReader.readLine();
            while (row != null) {
                var rowsArray = row.split(DELIMITER);

                searchExpression = searchTerm;
                String columName;
                for (int i = 0; i < 13; ++i) {
                    columName = "column[" + (i + 1) + "]";
                    searchExpression = searchExpression.replace(columName, rowsArray[i]);
                }

                if (checkLine.check(searchExpression)) {
                    list.add(row);

                }
                row = bufferedReader.readLine();
            }
            timeSpent = Duration.ofNanos(System.nanoTime() - timeSpent).toMillis();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтения файла", e);
        }

        return list;

    }


}
