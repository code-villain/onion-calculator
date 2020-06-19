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
        assertThat(stringCalculator.parseStringToInteger(new String[]{"1", "2"}), is(new Integer[]{1, 2}));
        assertThat(stringCalculator.parseStringToInteger(new String[]{""}), is(new Integer[]{0}));
        assertThat(stringCalculator.parseStringToInteger(new String[]{"1", " "}), is(new Integer[]{1, 0}));
    }

    @Test
    public void regexTest() {
        assertThat("//;\n".matches("\\A//.\n"), is(true));
        assertThat("//;\n1".matches("\\A//.\n."), is(true));
        assertThat("//;\n1;2;3".matches("\\A//.\n.*"), is(true));
    }

    private static class StringCalculator {
        private static final String[] basicSeparators = new String[]{",", ":"};

        public int sum(String inputString) {
            final Integer[] numbers = convertStringToNumbers(inputString);

            if (hasNegativeNumbers(numbers)) {
                throw new RuntimeException();
            }

            return Arrays.stream(numbers)
                    .reduce(0, Integer::sum);
        }

        private boolean hasNegativeNumbers(Integer[] numbers) {
            return Arrays.stream(numbers)
                    .anyMatch(n -> n < 0);
        }

        private Integer[] convertStringToNumbers(String inputString) {
            return parseStringToInteger(split(inputString));
        }

        private String[] split(String inputString) {
            if (Objects.isNull(inputString)) {
                return new String[]{};
            }

            if (hasCustomSeparator(inputString)) {
                String customSeparator = getCustomSeparator(inputString);
                inputString = subStringCustomSeparatorSyntax(inputString);
                return inputString.split(getSplitRegex(customSeparator));
            }

            return inputString.split(getSplitRegex());
        }

        private String subStringCustomSeparatorSyntax(String s) {
            return s.substring(4);
        }

        private String getCustomSeparator(String s) {
            return String.valueOf(s.charAt(2));
        }

        private String getSplitRegex() {
            return String.join("|", basicSeparators);
        }

        private String getSplitRegex(String customSeparator) {
            return String.join("|", customSeparator, basicSeparators[0], basicSeparators[1]);
        }

        private boolean hasCustomSeparator(String s) {
            return s.matches("\\A//.\n.*");
        }

        private Integer[] parseStringToInteger(String[] strings) {
            return Arrays.stream(strings)
                    .map(s -> s.equals("") || s.equals(" ") ? "0" : s)
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
        }
    }
}