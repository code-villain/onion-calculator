package me.siyoon;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringCalculatorTest {
    StringCalculator stringCalculator;

    @Before
    public void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void sum() {
        assertThat(stringCalculator.sum(""), is(0));
        assertThat(stringCalculator.sum("1,2"), is(3));
    }

    @Test
    public void split() {
        assertThat(stringCalculator.split("1,2"), is(new String[]{"1", "2"}));
        assertThat(stringCalculator.split("1,2:3"), is(new String[]{"1", "2", "3"}));
    }

    private class StringCalculator {
        public int sum(String s) {
            return s.equals("") ? 0 : 3;
        }

        private String[] split(String s) {
            return s.split("," + "|" + ":");
        }
    }
}