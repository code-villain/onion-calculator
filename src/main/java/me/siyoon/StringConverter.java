package me.siyoon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class StringConverter {
    private static final List<String> BASIC_SEPARATORS = Arrays.asList(",", ":");
    private static final List<String> STRINGS_TO_CHANGE_TO_ZERO = Arrays.asList("", " ");
    private static final int INDEX_OF_CUSTOM_SEPARATOR = 2;
    private static final int LAST_INDEX_OF_CUSTOM_SEPARATOR_SYNTAX = 4;
    private static final Pattern PATTERN = Pattern.compile("\\A//.\n.*");
    private static final String[] EMPTY_STRING_ARRAY = {};

    public Integer[] convertStringToNumbers(String inputString) {
        return parseStringsToIntegers(split(inputString));
    }

    private Integer[] parseStringsToIntegers(String[] strings) {
        return Arrays.stream(strings)
                .map(s -> STRINGS_TO_CHANGE_TO_ZERO.contains(s) ? "0" : s)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }

    private String[] split(String inputString) {
        if (Objects.isNull(inputString)) {
            return EMPTY_STRING_ARRAY;
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
        List<String> includeCustomSeparator = new ArrayList<>(BASIC_SEPARATORS);
        includeCustomSeparator.add(customSeparator);
        return includeCustomSeparator;
    }

    private boolean hasCustomSeparator(String s) {
        return PATTERN.matcher(s).matches();
    }

    private String getCustomSeparator(String s) {
        return String.valueOf(s.charAt(INDEX_OF_CUSTOM_SEPARATOR));
    }

    private String subStringCustomSeparatorSyntax(String s) {
        return s.substring(LAST_INDEX_OF_CUSTOM_SEPARATOR_SYNTAX);
    }

    private String getSplitRegex() {
        return getSplitRegex(BASIC_SEPARATORS);
    }

    private String getSplitRegex(List<String> separators) {
        return String.join("|", separators);
    }
}