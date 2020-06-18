package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {

	private StringCalculator stringCalculator;

	@BeforeEach
	void setUp() {
		stringCalculator = new StringCalculator();
	}

	@DisplayName("빈 문자열 또는 null 값을 입력할 경우 0을 반환")
	@Test
	void emptyOrNullTextReturnZero() {
		// when
		int emptyText = stringCalculator.add("");
		int nullText = stringCalculator.add(null);

		// then
		assertThat(emptyText).isEqualTo(0);
		assertThat(nullText).isEqualTo(0);
	}

	@DisplayName("숫자 하나를 문자열로 입력할 경우 해당 숫자를 반환")
	@Test
	void oneNumericText() {
		// when
		int add = stringCalculator.add("1");

		// then
		assertThat(add).isEqualTo(1);
	}

	@DisplayName("숫자 두개를 쉼표 구분자(,)로 입력할 경우 두 숫자의 합을 반환")
	@Test
	void twoNumericTextWithComma() {
		// when
		int add = stringCalculator.add("1,2");

		// then
		assertThat(add).isEqualTo(3);
	}

	@DisplayName("구분자를 쉼표(,) 이외에 콜론(:)을 사용")
	@Test
	void commaAndColonSeparator() {
		// when
		int add = stringCalculator.add("1,2:3");

		// then
		assertThat(add).isEqualTo(6);
	}

	@DisplayName("'//'와 '\n' 문자 사이에 커스텀 구분자를 지정")
	@Test
	void customSeparator() {
		// when
		int add = stringCalculator.add("//;\n1;2;3");

		// then
		assertThat(add).isEqualTo(6);
	}

	@DisplayName("문자열 계산기에 음수를 전달하는 경우 RuntimeException 예외 처리")
	@Test
	void minusThrowRuntimeException() {
		// when & then
		assertThrows(RuntimeException.class, () -> stringCalculator.add("-1"));
	}
}