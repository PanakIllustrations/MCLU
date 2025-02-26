package com.tumult.mclu.client.gui.icons;

public enum IconUtils {
    INSTANCE;

    private static GuiIcons icon;
    private static Clickable clickable;

    public static GuiIcons getIcon() {
        if (icon == null) {
            icon = new GuiIcons();
        }
        return icon;
    }

    public Clickable getClickable() {
        if (clickable == null) {
            clickable = new Clickable();
        }
        return clickable;
    }
}