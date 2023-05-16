package org.m1z1na.Technical;

import java.util.List;
/* Служебный класс для вывода сообщений пользователю */
public class TechnicalMessage {

    private final static String MESSAGE_LOG = "\nКоличество найденных строк: %d, Время затрачено на поиск: %d мс\n";
    private final static String MESSAGE_NOT_FOUND = "\nДанные не найдены.\n";
    private final static String MESSAGE_INPUT = "Введите текст для поиска:";
    private final static String MESSAGE_INPUT_NAME = "Введите название аэропорта:";
    private final static String MESSAGE_EXIT = "\nВозращайся ещё";

    public static void printFoundData(List<String> fountData, long timeSpent) {
        if (fountData.size() > 0) {
            fountData.forEach(System.out::println);
            System.out.printf(MESSAGE_LOG, fountData.size(), timeSpent);
        } else {
            System.out.println(MESSAGE_NOT_FOUND);
        }
    }

    public static void printEndMessage() {
        System.out.println(MESSAGE_EXIT);
    }

    public static void printInputMessage() {
        System.out.println(MESSAGE_INPUT);
    }

    public static void printInputNameMessage() {
        System.out.println(MESSAGE_INPUT_NAME);
    }
}
