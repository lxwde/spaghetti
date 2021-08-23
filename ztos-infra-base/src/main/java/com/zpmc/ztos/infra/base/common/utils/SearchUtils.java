package com.zpmc.ztos.infra.base.common.utils;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;//import com.sun.istack.internal.Nullable;

import java.util.Scanner;

public class SearchUtils {

    public static final int MAX_SEARCHABLE_PRIMARY_KEY_SIZE = 1000;

    private SearchUtils() {
    }

    @Nullable
    public static String convertSQLToRegExPattern(@NotNull String string) {
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        char c = string.charAt(0);
        if (c == '%') {
            stringBuilder.append("(.*)");
        } else if (c == '_') {
            stringBuilder.append('.');
        } else {
            stringBuilder.append('^').append(c);
        }
        for (int i = 1; i < string.length(); ++i) {
            c = string.charAt(i);
            if (c == '%') {
                stringBuilder.append("(.*)");
                continue;
            }
            if (c == '_') {
                stringBuilder.append('.');
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.append('$').toString();
    }

    @Nullable
    public static Object convertSyntheticSearchStringToObject(String string, Class class_) {
        block33: {
            if (String.class.equals((Object)class_)) {
                return string;
            }
            if (Long.class.isAssignableFrom(class_)) {
                try (Scanner scanner = new Scanner(string);){
                    if (scanner.hasNextLong()) {
                        Long l = scanner.nextLong();
                        return l;
                    }
                    break block33;
                }
            }
            if (Boolean.class.isAssignableFrom(class_)) {
                try (Scanner scanner = new Scanner(string);){
                    if (scanner.hasNextBoolean()) {
                        Boolean bl = scanner.nextBoolean();
                        return bl;
                    }
                }
            }
        }
        return null;
    }

    public static Object convertInMemorySyntheticSearchString(String string, Class class_) {
        block32: {
            if (Long.class.isAssignableFrom(class_)) {
                try (Scanner scanner = new Scanner(string);){
                    if (scanner.hasNextLong()) {
                        Long l = scanner.nextLong();
                        return l;
                    }
                    break block32;
                }
            }
            if (Boolean.class.isAssignableFrom(class_)) {
                try (Scanner scanner = new Scanner(string);){
                    if (scanner.hasNextBoolean()) {
                        Boolean bl = scanner.nextBoolean();
                        return bl;
                    }
                }
            }
        }
        return string;
    }
}
