package com.example.calculator;

import junit.framework.TestCase;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class MainActivityTest {


    @Test
    public void onClickMr() {
        assertEquals("2", MainActivity.onClickMr("2", 0, "2"));
        assertEquals("55+55", MainActivity.onClickMr("55+55", 3, "55"));
    }

    @Test
    public void onClickMemory() {
        assertEquals("2", MainActivity.onClickMemory("2", 0, 0, "+"));
        assertEquals("2", MainActivity.onClickMemory("2", 0, 0, "-"));
        assertEquals("4", MainActivity.onClickMemory("2+", 2, 2, "+"));
    }

    @Test
    public void onClickPercent() {
        assertEquals("0.49", MainActivity.onClickPercent(2, "7+7"));
        assertEquals("0.07", MainActivity.onClickPercent(0, "7"));

    }

    @Test
    public void onClickDot() {
        assertEquals("2.", MainActivity.onClickDot("2", 0));
        assertEquals("2+2.", MainActivity.onClickDot("2+2", 2));
    }

    @Test
    public void onClickNumbers() {
        assertEquals("2", MainActivity.onClickNumbers("0", "2", 0));
        assertEquals("23", MainActivity.onClickNumbers("2", "3", 0));
        assertEquals("2+2", MainActivity.onClickNumbers("2+0", "2", 2));
    }

    @Test
    public void minPlus() {

        assertEquals(new ArrayList<String>() {{
            add("2+-3");
            add("2");
        }}, MainActivity.minPlus("2+3", 2));

        assertEquals(new ArrayList<String>() {{
            add("-2+");
            add("3");
        }}, MainActivity.minPlus("2+", 2));

        assertEquals(new ArrayList<String>() {{
            add("-2.3");
            add("0");
        }}, MainActivity.minPlus("2.3", 0));

        assertEquals(new ArrayList<String>() {{
            add("-2.3+");
            add("5");
        }}, MainActivity.minPlus("2.3+", 4));


    }

    @Test
    public void processingOperation() {

        assertEquals(new ArrayList<String>() {{
            add("2+");
            add("2");
        }}, MainActivity.processingOperation("+", "2", 0));

        assertEquals(new ArrayList<String>() {{
            add("2+");
            add("2");
        }}, MainActivity.processingOperation("+", "2+", 2));

        assertEquals(new ArrayList<String>() {{
            add("2-");
            add("2");
        }}, MainActivity.processingOperation("-", "2+", 2));


    }

    @Test
    public void validationNumber() {


        assertEquals("2", MainActivity.validationNumber("2.0000000000"));
        assertEquals("2.000002", MainActivity.validationNumber("2.0000020000"));
        assertEquals("2", MainActivity.validationNumber("2."));
        assertEquals("0", MainActivity.validationNumber("0.000"));
        assertEquals("0.0001", MainActivity.validationNumber("0.0001"));



    }

    @Test
    public void result() {
        assertEquals("4", MainActivity.result("2+2", 2));
        assertEquals("2", MainActivity.result("4-2", 2));
        assertEquals("4", MainActivity.result("2*2", 2));
        assertEquals("2+2.", MainActivity.onClickDot("2+2", 2));

    }
}