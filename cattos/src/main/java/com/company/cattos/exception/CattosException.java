package com.company.cattos.exception;

import java.util.Arrays;

public class CattosException extends RuntimeException {


    public CattosException(String message, Throwable cause) {
        super(message, cause);
    }

    public static CattosException of(String message, Object... tail) {
        var cause = extractCause(tail);
        var template = formatMessage(message, cause);

        throw new CattosException(template, cause);
    }

    private static Throwable extractCause(Object[] objects) {
        return objects.length > 0 && objects[objects.length - 1] instanceof Throwable throwable
                ? throwable
                : null;
    }

    private static Object[] extractStringArgs(Object[] objects) {
        return objects.length > 0 && objects[objects.length - 1] instanceof Throwable
                ? Arrays.copyOf(objects, objects.length - 1)
                : objects;
    }

    private static String formatMessage(String template, Object... tail) {
        var extractedStringArgs = extractStringArgs(tail);
        var stringFormatTemplate = template.replace("{}", "%s");
        return String.format(stringFormatTemplate, extractedStringArgs);
    }
}
