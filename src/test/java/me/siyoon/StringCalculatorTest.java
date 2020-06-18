package me.siyoon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class StringCalculatorTest {
    StringCalculator stringCalculator;

    @BeforeEach
    public void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void 기본_구분자로_계산하기() {
        assertThat(stringCalculator.sum(""), is(0));
        assertThat(stringCalculator.sum("1,2"), is(3));
        assertThat(stringCalculator.sum("1,2:3"), is(6));
    }

    @Test
    public void 문자열이_숫자가_아니라면_RuntimeException() {
        try {
            stringCalculator.sum("1,*:3");
        } catch (Exception e) {
            assertThat(e, instanceOf(RuntimeException.class));
            return;
        }
        fail("예외가 발생되어야 하지만 발생하지 않음");
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

    @Test
    public void parsingStringToInt_숫자가_아닌_값이_포함된경우() {
        assertThrows(RuntimeException.class, () -> stringCalculator.parse(new String[]{"1", "*"}));
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