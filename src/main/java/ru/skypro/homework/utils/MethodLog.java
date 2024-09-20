package ru.skypro.homework.utils;

/**
 * Класс для получения имени метода
 */
public class MethodLog {
    public static String getMethodName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        return stackTraceElements[2].getMethodName();
    }
}
