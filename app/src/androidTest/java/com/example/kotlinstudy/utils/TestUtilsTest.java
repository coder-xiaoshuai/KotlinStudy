package com.example.kotlinstudy.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TestUtilsTest {

    private String date;

    public TestUtilsTest(String date) {
        this.date = date;
    }

    @Parameterized.Parameters
    public static List<String> data() {
        return Arrays.asList("2002-09-23", "2002-09-24", "2001-09-25", "2001-09-24", "2001-08-25", "2001-08-24", "2002-08-25");
    }

    @Test
    public void isAdult() {
        Assert.assertTrue(TestUtils.isAdult(date));
    }
}