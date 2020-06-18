package ch02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringCalculatorTest {
    private StringCalculator sc;

    @BeforeEach
    void setup() {
        sc = new StringCalculator();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void _01_문자열이_비었니(String input) {
        assertEquals(0, sc.add(input));
    }

    @Test
    void _02_숫자하나입력() {
        assertEquals(1, sc.add("1"));
    }

    @Test
    void _03_숫자_두개_쉼표() {
        assertEquals(3, sc.add("1,2"));
    }

    @Test
    void _04_쉼표_이외_구분자() {
        assertEquals(6, sc.add("1,2:3"));
    }

    @Test
    void _05_문자사이_커스텀구분자() {
        assertEquals(6, sc.add("//;\n1;2;3"));
    }

    @Test
    void _06_음수_런타임에러() {
        assertThatThrownBy(() -> sc.add("1,-2,3")).isInstanceOf(RuntimeException.class);
    }
}