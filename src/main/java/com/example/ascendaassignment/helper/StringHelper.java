package com.example.ascendaassignment.helper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StringHelper{

    // Trim and format values in a list
    public static List<String> toTrimmedLowerAndFormat(List<String> input)
    {
        return input.stream().filter(Objects::nonNull)
                .map(StringHelper::toTrimmedLowerAndFormat)
                .collect(Collectors.toList());
    }

    // Trim and format text
    // Example : "BathTub" -> "bath tub"
    public static String toTrimmedLowerAndFormat(String input)
    {
        String formatted = input.replaceAll("([a-z])([A-Z])", "$1 $2")
                .replaceAll("([A-Z])([A-Z][a-z])", "$1 $2");
        return formatted.trim().toLowerCase();
    }
}
