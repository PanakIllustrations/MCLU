package com.tumult.mclu.client.gui.frame.core.css;

import java.util.HashMap;
import java.util.Map;

public class StyleSheet {
    private static final Map<String, Style> styles = new HashMap<>();

    static {
        styles.put("button", new Style()
                .rect(0, 0, 30, 40)
                .margin(4)
        );
    }

    public static Style getStyle(String style) {
        return styles.get(style);
    }
}
