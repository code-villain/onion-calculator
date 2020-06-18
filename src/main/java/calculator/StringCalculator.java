package calculator;

public class StringCalculator {
    int add(String text) {
        if (isEmpty(text)) {
            return 0;
        }

        String[] strings = text.split(",|:");

        int[] numbers = new int[strings.length];
        for (int i = 0; i <strings.length ; i++) {
            numbers[i] = Integer.parseInt(strings[i]);
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
