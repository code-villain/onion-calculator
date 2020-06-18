package calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringCalculatorTest {

    StringCalculator stringCalculator;

    @Before
    public void before() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void _01_빈_문자열_또는_null() {
        assertEquals(0, stringCalculator.add(""));
        assertEquals(0, stringCalculator.add(null));
    }

    @Test
    public void _02_숫자_하나() {
        assertEquals(1, stringCalculator.add("1"));
    }

    @Test
    public void _03_숫자_두개_쉼표() {
        assertEquals(3, stringCalculator.add("1,2"));
    }

    @Test
    public void _04_쉼표랑_콜론() {
        assertEquals(6, stringCalculator.add("1,2:3"));
    }

    @Test
    public void _05_커스텀_구분자() {
        assertEquals(6, stringCalculator.add("//;\n1;2;3"));
    }

    @Test(expected = RuntimeException.class)
    public void _06_음수_예외처리() {
        assertEquals(4, stringCalculator.add("-1,2,3"));
    }

}
