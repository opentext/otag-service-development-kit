/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StringUtil {

    public static final String DEFAULT_SEPARATOR = ",";

    public static String toListString(Collection<String> stringList) {
        return toListString(stringList, false);
    }

    /**
     * Turn an array of Strings into a single comma separated String.
     *
     * @param stringList list of Strings
     * @param enclose    should we enclose the value in curly braces?
     * @return concatenated comma separated String
     */
    public static String toListString(Collection<String> stringList, boolean enclose) {
        if (stringList == null || stringList.isEmpty())
            return (enclose) ? "{}" : "";

        StringBuilder builder = new StringBuilder((enclose) ? "{" : "");
        Comma comma = new Comma();

        for (String string : stringList) {
            builder.append(comma).append(string);
        }
        if (enclose)
            builder.append("}");
        return builder.toString();
    }

    public static Set<String> toStringSet(String toSplit) {
        if (isNullOrEmpty(toSplit)) return new HashSet<>();
        return new HashSet<>(Arrays.asList(toSplit.split(DEFAULT_SEPARATOR)));
    }

    public static Set<String> toStringSet(String toSplit, String separator) {
        if (isNullOrEmpty(toSplit)) return new HashSet<>();
        return new HashSet<>(Arrays.asList(toSplit.split(separator)));
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty() || value.trim().length() == 0;
    }

    public static class Comma {
        boolean skip = true;

        @Override
        public String toString() {
            if (skip) {
                skip = false;
                return "";
            } else {
                return ", ";
            }
        }
    }

}
