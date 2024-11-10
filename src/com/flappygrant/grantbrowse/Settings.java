package com.flappygrant.grantbrowse;

import java.util.HashMap;
import java.util.Map;

public class Settings {
    private static final Map<String, Object> SETTINGS = new HashMap<>();

    static {
        SETTINGS.put("DEFAULT_TITLE", "Grantbrowse");
        SETTINGS.put("HOME_PAGE", "search.flappygrant.com");
        SETTINGS.put("URL_BAR_HEIGHT", 30);
        SETTINGS.put("HOME_BTN_WIDTH", 75);
        SETTINGS.put("SETTINGS_BTN_WIDTH", 125);
        SETTINGS.put("BASE_FONT_SIZE", 16);
        SETTINGS.put("PARAGRAPH_FONT_SIZE", 1);
        SETTINGS.put("HEADING1_FONT_SIZE", 2);
    }

    public static Object get(String key) {
        return SETTINGS.getOrDefault(key, null);
    }

    public static void set(String key, String value) {
        if (value == null) {
            return;
        }

        Object currentValue = SETTINGS.get(key);

        if (currentValue instanceof String) {
            SETTINGS.put(key, value);
        } else if (currentValue instanceof Integer) {
            try {
                SETTINGS.put(key, Integer.parseInt(value));
            } catch (NumberFormatException e) {
                System.err.println("Invalid integer value for " + key + ": " + value);
            }
        }
    }

    public static boolean contains(String key) {
        return SETTINGS.containsKey(key);
    }
}
