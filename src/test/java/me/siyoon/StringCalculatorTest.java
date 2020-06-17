package me.siyoon;

import com.sun.org.apache.bcel.internal.generic.ATHROW;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

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
        assertThat(stringCalculator.sum("1,2:3"), is(6));
    }

    @Test
    public void split() {
        assertThat(stringCalculator.split(""), is(new String[]{""}));
        assertThat(stringCalculator.split("1,2"), is(new String[]{"1", "2"}));
        assertThat(stringCalculator.split("1,2:3"), is(new String[]{"1", "2", "3"}));
    }

    @Test
    public void parsingStringToInt() {
        assertThat(stringCalculator.parse(new String[]{"1", "2"}), is(new Integer[]{1, 2}));
        assertThat(stringCalculator.parse(new String[]{""}), is(new Integer[]{0}));
        assertThat(stringCalculator.parse(new String[]{"1", " "}), is(new Integer[]{1, 0}));
    }

    @Test(expected = RuntimeException.class)
    public void parsingStringToInt_숫자가_아닌_값이_포함된경우() {
        stringCalculator.parse(new String[]{"1", "*"});
    }

    private class StringCalculator {
        public int sum(String s) {
            return Arrays.stream(parse(split(s)))
                    .reduce(0, Integer::sum);
        }

        private String[] split(String s) {
            return s.split("[" + "," + ":]");
        }

        private Integer[] parse(String[] strings) {
            return Arrays.stream(strings)
                    .map(s -> s.equals("") || s.equals(" ") ? "0" : s)
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
        }
    }
}