package me.siyoon;

import java.util.*;

public class StringCalculator {
    private final StringConverter stringConverter;

    public StringCalculator() {
        stringConverter = new StringConverter();
    }

    public int sum(String inputString) {
        final Integer[] numbers = stringConverter.convertStringToNumbers(inputString);

        if (hasNegativeNumbers(numbers)) {
            throw new NegativeNumberExistException();
        }

        return Arrays.stream(numbers)
                .reduce(0, Integer::sum);
    }


    private boolean hasNegativeNumbers(Integer[] numbers) {
        return Arrays.stream(numbers)
                .anyMatch(n -> n < 0);
    }
}