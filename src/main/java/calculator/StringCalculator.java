package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	int add(String text) {
		if (text == null || text.isEmpty()) {
			return 0;
		}
		if (isNumeric(text)) {
			return textToNumberUnlessNegative(text);
		}
		Matcher matcher = Pattern.compile("//(.)\n(.*)").matcher(text);
		if (matcher.find()) {
			String customSeparator = matcher.group(1);
			String[] split = matcher.group(2).split(customSeparator);
			return sum(split);
		}
		return calculateWithCommaOrColon(text);
	}

	private boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			int i = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private int textToNumberUnlessNegative(final String text) {
		int number = Integer.parseInt(text);
		if (number < 0) {
			throw new RuntimeException("음수는 계산할 수 없습니다.");
		}
		return number;
	}

	private int calculateWithCommaOrColon(final String text) {
		String[] split = text.split("[,:]");
		return sum(split);
	}

	private int sum(final String[] numbers) {
		int sum = 0;
		for (String s : numbers) {
			int i = Integer.parseInt(s);
			sum += i;
		}
		return sum;
	}
}
