package me.siyoon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringConverter {
    private static final List<String> basicSeparators = Arrays.asList(",", ":");

    public Integer[] convertStringToNumbers(String inputString) {
        return parseStringsToIntegers(split(inputString));
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
            List<String> separators = includeCustomSeparator(inputString);
            inputString = subStringCustomSeparatorSyntax(inputString);
            return inputString.split(getSplitRegex(separators));
        }

        return inputString.split(getSplitRegex());
    }

    private List<String> includeCustomSeparator(String inputString) {
        String customSeparator = getCustomSeparator(inputString);
        List<String> includeCustomSeparator = new ArrayList<>(basicSeparators);
        includeCustomSeparator.add(customSeparator);
        return includeCustomSeparator;
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
        return getSplitRegex(basicSeparators);
    }

    private String getSplitRegex(List<String> separators) {
        return String.join("|", separators);
    }
}