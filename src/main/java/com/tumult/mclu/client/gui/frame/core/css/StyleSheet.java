package com.tumult.mclu.client.gui.frame.core.css;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StyleSheet {
    private static final Map<String, Style> styles = new HashMap<>();

    static {
        // Initialize common styles
        styles.put("button", Theme.button());

        styles.put("button.primary", Theme.button()
                .backgroundColor(Theme.PRIMARY));

        styles.put("button.accent", Theme.button()
                .backgroundColor(Theme.ACCENT));

        styles.put("button.danger", Theme.button()
                .backgroundColor(Theme.ERROR));

        styles.put("panel", Theme.panel());

        styles.put("toolbar", Theme.toolbar());

        styles.put("icon", Theme.icon()
                .backgroundColor(Theme.PRIMARY));

        styles.put("container", new Style()
                .rect(0, 0, 300, 200)
                .backgroundColor(new Color(33, 33, 33, 200))
                .radius(8)
                .border(1)
                .borderColor(Theme.PRIMARY)
                .padding(16));
    }

    public static Style getStyle(String selector) {
        Style style = styles.get(selector);
        return style != null ? style : new Style();
    }

    public static void addStyle(String selector, Style style) {
        styles.put(selector, style);
    }
}
