package org.m1z1na;

import org.m1z1na.Searcher.SearcherCsv;
import org.m1z1na.Technical.TechnicalMessage;

import java.util.Scanner;

public class Main {


    private final static String END_COMMAND = "!quit";
    private final static String FILE_PATH = "airports.csv";

    public static void main(String[] args) {
        SearcherCsv reader = new SearcherCsv(FILE_PATH);
        var scanner = new Scanner(System.in);
        TechnicalMessage.printInputMessage();

        while (true) {
            var input = scanner.nextLine().trim();
            if (END_COMMAND.equals(input)) {
                break;
            } else if (!input.isEmpty()) {
                var foundData = reader.searchData(input);
                TechnicalMessage.printFoundData(foundData, reader.getTimeSpent());
                if (foundData.isEmpty()) {
                    TechnicalMessage.printInputMessage();
                } else {
                    TechnicalMessage.printInputNameMessage();
                    input = scanner.nextLine().trim();
                    foundData = reader.searchDataByName(foundData, input);
                    TechnicalMessage.printFoundData(foundData, reader.getTimeSpent());
                }

            } else {
                TechnicalMessage.printInputMessage();
            }
        }
        scanner.close();
        TechnicalMessage.printEndMessage();
    }

}
