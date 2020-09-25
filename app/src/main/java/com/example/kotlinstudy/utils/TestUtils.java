package com.example.kotlinstudy.utils;

public class TestUtils {
    public static boolean isAdult(String date) {
        return DateUtils.INSTANCE.isAdult(date);
    }
}
