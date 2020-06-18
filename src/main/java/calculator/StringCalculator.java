package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    int add(String text) {
        if (!isEmpty(text)) {
            return sum(getInts(getStrings(text)));
        }
        return 0;
    }

    // int 배열 더하기
    private int sum(int[] numbers) {
        int sum = 0;
        for (int i = 0; i <numbers.length ; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    // string 배열을 int 배열로 변환
    private int[] getInts(String[] strings) {
        int[] numbers = new int[strings.length];
        for (int i = 0; i <strings.length ; i++) {
            numbers[i] = Integer.parseInt(strings[i]);
            if (numbers[i] < 0) throw new RuntimeException("음수 예외 처리");
        }
        return numbers;
    }

    // 구분자를 기준으로 String 배열 생성
    private String[] getStrings(String text) {
        String[] strings;
        Matcher m = Pattern.compile("//(.)\n(.*)").matcher(text);
        if (m.find()) {
            String customDelimeter = m.group(1);
            strings = m.group(2).split(customDelimeter);
        } else {
            strings = text.split(",|:");
        }
        return strings;
    }

    // 텍스트가 비었는지 판단
    private boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }
}
