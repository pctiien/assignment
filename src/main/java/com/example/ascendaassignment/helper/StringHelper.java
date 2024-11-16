package com.example.ascendaassignment.helper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StringHelper{
    public static List<String> toTrimmedLowerAndFormat(List<String> input)
    {
        return input.stream().filter(Objects::nonNull)
                .map(StringHelper::toTrimmedLowerAndFormat)
                .collect(Collectors.toList());
    }
    public static String toTrimmedLowerAndFormat(String input)
    {
        String formatted = input.replaceAll("([a-z])([A-Z])", "$1 $2")
                .replaceAll("([A-Z])([A-Z][a-z])", "$1 $2");
        return formatted.trim().toLowerCase();
    }
}
