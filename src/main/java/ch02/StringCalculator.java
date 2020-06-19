package ch02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    int add(String text) {
        if (isEmpty(text)) {
            return 0;
        }

        return sum(stringsToInt(split(text)));
    }

    private String[] split(String text) {
        Matcher m = Pattern.compile("//(.)\n(.*)").matcher(text);
        if (m.find()) {
            String customDelimeter = m.group(1);
            return m.group(2).split(customDelimeter);
        }

        return text.split(",|:");
    }

    private int[] stringsToInt(String[] values){
        int[] numbers = new int[values.length];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = onlyPositive(values[i]);
        }

        return numbers;
    }

    private int onlyPositive(String value) {
        int number = Integer.parseInt(value);
        if (number < 0) {
            throw new RuntimeException("양수만 입력 가능합니다.");
        }
        return number;
    }

    private int sum(int[] numbers) {
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return sum;
    }

    private boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }
}
