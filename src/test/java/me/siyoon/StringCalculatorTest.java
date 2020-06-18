package me.siyoon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringCalculatorTest {
    StringCalculator stringCalculator;

    @BeforeEach
    public void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    @DisplayName("기본구분자로 나누어져 있는 숫자들의 합을 계산한다.")
    public void basicCalcSum() {
        assertThat(stringCalculator.sum("1,2"), is(3));
        assertThat(stringCalculator.sum("1,2:3"), is(6));
    }

    @Test
    @DisplayName("빈 문자열이나 null이 전달된 경우 0을 반환한다.")
    public void emptyOrNull_return0() {
        assertThat(stringCalculator.sum(""), is(0));
        assertThat(stringCalculator.sum(null), is(0));
    }

    @Test
    @DisplayName("숫자가 아닌 문자열이 포함된다면 RuntimeException")
    public void nonNumberString_RuntimeException() {
        assertThrows(RuntimeException.class, () -> stringCalculator.sum("1,2,*"));
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

    private static class StringCalculator {
        public int sum(String s) {
            return Arrays.stream(parse(split(s)))
                    .reduce(0, Integer::sum);
        }

        private String[] split(String s) {
            if (Objects.isNull(s)) {
                return new String[]{};
            }

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