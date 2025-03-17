package com.tumult.mclu.client.gui.core.util.parsing;

import java.util.HashMap;
import java.util.Map;

public class StyleSheet {
    private final Map<String, Style> styles = new HashMap<>();

    public void addStyle(String selector, Style style) {
        styles.put(selector, style);
    }

    public Style getStyle(String selector) {
        return styles.get(selector);
    }
}

class Style {
    private final Map<String, String> properties = new HashMap<>();
    public Style property(String name, String value) {
        properties.put(name, value);
        return this;
    }
    public Map<String, String> getProperties() {
        return properties;
    }
    public String getProperty(String name) {
        return properties.get(name);
    }
}
