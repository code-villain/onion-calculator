package me.siyoon;

import java.util.Arrays;
import java.util.Objects;

public class StringCalculator {
    private static final String[] basicSeparators = new String[]{",", ":"};

    public int sum(String inputString) {
        final Integer[] numbers = convertStringToNumbers(inputString);

        if (hasNegativeNumbers(numbers)) {
            throw new RuntimeException();
        }

        return Arrays.stream(numbers)
                .reduce(0, Integer::sum);
    }

    private Integer[] convertStringToNumbers(String inputString) {
        return parseStringsToIntegers(split(inputString));
    }

    private boolean hasNegativeNumbers(Integer[] numbers) {
        return Arrays.stream(numbers)
                .anyMatch(n -> n < 0);
    }

    private Integer[] parseStringsToIntegers(String[] strings) {
        return Arrays.stream(strings)
                .map(s -> s.equals("") || s.equals(" ") ? "0" : s)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }

    private String[] split(String inputString) {
        if (Objects.isNull(inputString)) {
            return new String[]{};
        }

        if (hasCustomSeparator(inputString)) {
            String customSeparator = getCustomSeparator(inputString);
            inputString = subStringCustomSeparatorSyntax(inputString);
            return inputString.split(getSplitRegex(customSeparator));
        }

        return inputString.split(getSplitRegex());
    }

    private boolean hasCustomSeparator(String s) {
        return s.matches("\\A//.\n.*");
    }

    private String getCustomSeparator(String s) {
        return String.valueOf(s.charAt(2));
    }

    private String subStringCustomSeparatorSyntax(String s) {
        return s.substring(4);
    }

    private String getSplitRegex() {
        return String.join("|", basicSeparators);
    }

    private String getSplitRegex(String customSeparator) {
        return String.join("|", customSeparator, basicSeparators[0], basicSeparators[1]);
    }
}