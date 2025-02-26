package com.tumult.mclu.client.gui.frame.core.css;

import java.awt.*;

public class Theme {
    // Primary colors
    public static final Color PRIMARY = new Color(33, 150, 243);
    public static final Color PRIMARY_LIGHT = new Color(100, 181, 246);
    public static final Color PRIMARY_DARK = new Color(25, 118, 210);

    // Accent colors
    public static final Color ACCENT = new Color(255, 64, 129);
    public static final Color ACCENT_LIGHT = new Color(255, 110, 156);
    public static final Color ACCENT_DARK = new Color(216, 27, 96);

    // Neutral colors
    public static final Color BACKGROUND = new Color(250, 250, 250);
    public static final Color SURFACE = new Color(255, 255, 255);
    public static final Color ERROR = new Color(211, 47, 47);

    // Text colors
    public static final Color TEXT_PRIMARY = new Color(33, 33, 33);
    public static final Color TEXT_SECONDARY = new Color(117, 117, 117);
    public static final Color TEXT_DISABLED = new Color(189, 189, 189);

    // Semi-transparent backgrounds
    public static final Color OVERLAY = new Color(0, 0, 0, 128);
    public static final Color SCRIM = new Color(0, 0, 0, 160);

    // Common style presets
    public static Style button() {
        return new Style()
                .rect(0, 0, 100, 40)
                .backgroundColor(PRIMARY)
                .borderColor(PRIMARY_DARK)
                .radius(4)
                .border(1)
                .padding(8)
                .margin(4);
    }

    public static Style panel() {
        return new Style()
                .rect(0, 0, 200, 150)
                .backgroundColor(SURFACE)
                .borderColor(TEXT_DISABLED)
                .radius(8)
                .border(1)
                .padding(16)
                .margin(8);
    }

    public static Style toolbar() {
        return new Style()
                .rect(0, 0, 400, 56)
                .backgroundColor(PRIMARY)
                .display(Style.Display.flex)
                .justifyContent("flex-start")
                .alignItems("center")
                .padding(8);
    }

    public static Style icon() {
        return new Style()
                .rect(0, 0, 24, 24)
                .backgroundColor(Color.WHITE)
                .margin(4);
    }
}
