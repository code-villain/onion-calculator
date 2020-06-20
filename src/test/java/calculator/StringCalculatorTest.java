package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringCalculatorTest {

	private StringCalculator stringCalculator;

	@BeforeEach
	void setUp() {
		stringCalculator = new StringCalculator();
	}

	@NullAndEmptySource
	@ParameterizedTest
	void emptyOrNullTextReturnZero(String text) {
		// when
		int nullOrEmptyText = stringCalculator.add(text);

		// then
		assertThat(nullOrEmptyText).isEqualTo(0);
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
	void minusThrowsRuntimeException() {
		// when & then
		RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> stringCalculator.add("-1"));
		assertThat(runtimeException.getMessage()).isEqualTo("음수는 계산할 수 없습니다.");
	}

	@DisplayName("구분자를 포함한 인자에 음수가 포함된 경우 RuntimeException 예외 처리")
	@Test
	void numbersContainingMinusThrowsRuntimeException() {
		// when & then
		RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> stringCalculator.add("3:-2,6"));
		assertThat(runtimeException.getMessage()).isEqualTo("음수는 계산할 수 없습니다.");
	}
}