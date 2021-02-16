package com.ayla.eventhubtesting;

import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class Commons {

    public static final String NULL_STR = "null";

    private Commons() {
    }

    public static boolean isNullBlank(String in) {
        if (in == null) {
            return true;
        }
        return isTrimEmpty(in) || in.trim()
                .toLowerCase(Locale.getDefault())
                .equals(NULL_STR)
                || in.trim()
                .toLowerCase(Locale.getDefault())
                .equals("none");
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static <T> boolean isNullBlank(T[] arr) {
        return arr == null || arr.length == 0 || (arr.length == 1 && arr[0] == null);
    }

    public static <T> boolean notNullBlank(T[] arr) {
        return !isNullBlank(arr);
    }

    public static <T> boolean notNullBlank(Collection<T>[] graphs) {
        return graphs != null && graphs.length > 0 && isNullBlank(graphs[0]);
    }


    private static boolean isTrimEmpty(final String in) {
        if (in == null) {
            return true;
        }
        for (int i = 0; i < in.length(); i++) {
            if (!Character.isWhitespace(in.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlank(final String in) {
        return in == null || in.length() == 0;
    }

    public static boolean notNull(Object in) {
        return !isNull(in);
    }

    public static boolean notBlank(String in) {
        return !isBlank(in);
    }

    public static boolean isBlank(Collection<? extends Object> col) {
        return col == null || col.isEmpty();
    }

    public static boolean notBlank(Collection<? extends Object> col) {
        return !isBlank(col);
    }

    public static <K, V> boolean isBlank(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNullBlank(Collection<? extends Object> col) {
        return col == null || col.isEmpty() || (col.size() == 1 && getFirst(col) == null);
    }

    public static boolean notNullBlank(Collection<? extends Object> col) {
        return !isNullBlank(col);
    }

    public static boolean notBlank(List<? extends Object> col) {
        return !isBlank(col);
    }

    public static <T> boolean isBlank(T[] array) {
        return array == null || array.length == 0;
    }


    public static <T> boolean notBlank(T[] array) {
        return !isBlank(array);
    }

    public static boolean notBlank(Map<? extends Object, ? extends Object> map) {
        return !isBlank(map);
    }

    public static String str(Object o) {
        return o == null ? "null" : o.toString();
    }

    public static String str(Object o, Throwable... e) {
        return o == null ? "null" : o.toString();
    }

    public static <T> T getFirst(Collection<T> col) {
        if (isBlank(col)) {
            return null;
        }

        return getFirst(col, null);
    }

    public static <T> T getFirst(Collection<T> col, T defaultVal) {
        if (isBlank(col)) {
            return defaultVal;
        }

        return Iterables.getFirst(col, defaultVal);
    }


    public static boolean notNullBlank(String val) {
        return !isNullBlank(val);
    }


    public static boolean notNullBlank(Map<String, Object> map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isBoolean(Object isEnabled) {
        return (isEnabled instanceof Boolean);
    }


    public static String trim(String in) {
        return in == null ? null : in.trim();
    }


}
