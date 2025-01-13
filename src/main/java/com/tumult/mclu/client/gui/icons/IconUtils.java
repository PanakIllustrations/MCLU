package com.tumult.mclu.client.gui.icons;

public enum IconUtils { // thread-safe singleton
    INSTANCE;

    private static GuiIcons icon;

    public static GuiIcons getIcon() { // globalized access point
        if (icon == null) { // lazy initialization
            icon = new GuiIcons();
        }
        return icon;
    }
}