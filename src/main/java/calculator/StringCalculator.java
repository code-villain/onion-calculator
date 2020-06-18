package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    int add(String text) {
        if (isEmpty(text)) {
            return 0;
        }

        String[] strings;

        Matcher m = Pattern.compile("//(.)\n(.*)").matcher(text);
        if (m.find()) {
            String customDelimeter = m.group(1);
            strings = m.group(2).split(customDelimeter);
        } else {

            strings = text.split(",|:");
        }

        int[] numbers = new int[strings.length];
        for (int i = 0; i <strings.length ; i++) {
            numbers[i] = Integer.parseInt(strings[i]);
            if (numbers[i] < 0) throw new RuntimeException("음수 예외 처리");
        }

        int sum = 0;
        for (int i = 0; i <numbers.length ; i++) {
            sum += numbers[i];
        }

        return sum;
    }

    private boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }
}
