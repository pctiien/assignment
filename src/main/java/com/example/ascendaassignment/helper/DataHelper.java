package com.example.ascendaassignment.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


// Using this helper to handle null values of any fields in a map
// If not null , parse it to String or any type data
// If null , just return null
public class DataHelper {

    public static String getString(Map<String, Object> sourceData, String key) {
        return Optional.ofNullable(sourceData.get(key))
                .map(Object::toString)
                .orElse(null);
    }

    public static Long getLong(Map<String, Object> sourceData, String key) {
        return Optional.ofNullable(sourceData.get(key))
                .map(Object::toString)
                .filter(value -> !value.isEmpty())
                .map(Long::valueOf)
                .orElse(null);
    }

    public static Double getDouble(Map<String, Object> sourceData, String key) {
        return Optional.ofNullable(sourceData.get(key))
                .map(Object::toString)
                .filter(value-> !value.isEmpty())
                .map(Double::valueOf)
                .orElse(null);
    }

    public static ArrayList getList(Map<String, Object> sourceData, String key) {
        return Optional.ofNullable(sourceData.get(key))
                .filter(val -> val instanceof List)
                .map(ArrayList.class::cast)
                .orElse(new ArrayList<>());
    }

    public static Map getMap(Map<String, Object> sourceData, String key) {
        return Optional.ofNullable(sourceData.get(key))
                .filter(val -> val instanceof Map)
                .map(Map.class::cast)
                .orElse(null);
    }

}