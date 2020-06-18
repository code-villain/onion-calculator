package me.siyoon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringCalculatorTest {
    private StringCalculator stringCalculator;

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
    @DisplayName("구분자 사이에 숫자가 존재하지 않는 경우, 0으로 판단하여 계산한다.")
    public void emptyNum() {
        assertThat(stringCalculator.sum(",3:2"), is(5));
        assertThat(stringCalculator.sum(",,3:2"), is(5));
        assertThat(stringCalculator.sum("3, :2"), is(5));
    }

    @Test
    @DisplayName("문자열 제일 앞 //와 \n 사이에 char1 짜리 커스텀 구분자를 지정할 수 있다")
    public void customSeparator() {
        assertThat(stringCalculator.sum("//;\n1;2;3"), is(6));
        assertThat(stringCalculator.sum("//;\n"), is(0));
        assertThat(stringCalculator.sum("//;\n1,2:3;4"), is(10));
    }

    @Test
    @DisplayName("숫자가 아닌 문자열이 포함된다면 RuntimeException")
    public void nonNumberString_RuntimeException() {
        assertThrows(RuntimeException.class, () -> stringCalculator.sum("1,2,*"));
        assertThrows(RuntimeException.class, () -> stringCalculator.sum("&"));
    }

    @Test
    @DisplayName("음수인 숫자가 전달 된다면 RuntimeException")
    public void negativeNum_RuntimeException() {
        assertThrows(RuntimeException.class, () -> stringCalculator.sum("1,-2"));
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
    public void regexTest() {
        assertThat("//;\n".matches("\\A//.\n"), is(true));
        assertThat("//;\n1".matches("\\A//.\n."), is(true));
        assertThat("//;\n1;2;3".matches("\\A//.\n.*"), is(true));
    }

    private static class StringCalculator {
        private static final List<String> separators = new ArrayList<>(Arrays.asList(",", ":"));

        public int sum(String s) {
            return Arrays.stream(parse(split(s)))
                    .peek(i -> {
                        if (i < 0) {
                            throw new RuntimeException();
                        }
                    })
                    .reduce(0, Integer::sum);
        }

        private String[] split(String s) {
            if (Objects.isNull(s)) {
                return new String[]{};
            }


            if (hasCustomSeparator(s)) {
                addCustomSeparator(s);
                s = subStringCustomSeparatorSyntax(s);
            }

            return s.split(getSplitRegex());
        }

        private String subStringCustomSeparatorSyntax(String s) {
            return s.substring(4);
        }

        private void addCustomSeparator(String s) {
            final char customSeparator = s.charAt(2);
            separators.add(String.valueOf(customSeparator));
        }

        private String getSplitRegex() {
            return String.join("|", separators);
        }

        private boolean hasCustomSeparator(String s) {
            return s.matches("\\A//.\n.*");
        }

        private Integer[] parse(String[] strings) {
            return Arrays.stream(strings)
                    .map(s -> s.equals("") || s.equals(" ") ? "0" : s)
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
        }
    }
}