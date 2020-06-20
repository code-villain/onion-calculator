package calculator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

	private Calculator cal;

	@BeforeEach
	void setUp() {
		cal = new Calculator();
		System.out.println("before");
	}

	@Test
	void add() {
		assertThat(cal.add(6, 3)).isEqualTo(9);
		System.out.println("add");
	}

	@Test
	void subtract() {
		assertThat(cal.subtract(6, 3)).isEqualTo(3);
		System.out.println("subtract");
	}

	@Test
	void multiply() {
		assertThat(cal.multiply(2, 6)).isEqualTo(12);
		System.out.println("multiply");
	}

	@Test
	void divide() {
		assertThat(cal.divide(8, 4)).isEqualTo(2);
		System.out.println("divide");
	}

	@AfterEach
	void tearDown() {
		System.out.println("teardown");
	}
}