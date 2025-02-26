package com.tumult.mclu.client.gui.frame.core.css;

import java.util.HashMap;
import java.util.Map;

public class StyleSheet extends Palette {
    private static final Map<String, Style> styles = new HashMap<>();

    static {
        styles.put("inventory", new Style()
                .rect(20, 20)
                .backgroundColor(BLACK)
                .radius(4));

        styles.put("inventory_slot",  new Style()
                .rect(20, 20)
                .backgroundColor(DARK_GRAY)
                .radius(4));

        styles.put("toolbar", new Style()
                .display(Style.Display.grid)
                .rect(130, 30)
                .visibility(false));

        styles.put("toolbar_slot", new Style()
                .rect(20, 20)
                .backgroundColor(TOOLBAR)
                .radius(4)
                .padding(2));
    }

    public static Style getStyle(String selector) {
        Style style = styles.get(selector);
        return style != null ? style : new Style();
    }

    public static void addStyle(String selector, Style style) {
        styles.put(selector, style);
    }
}
