package com.tumult.mclu.client.gui.frame.core.css;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Style {
    enum Display { inline, flex, grid, table, none }
    enum Align { left, center, right }
    enum Position { stationary, absolute, fixed, relative, sticky }
    enum JustifyContent { flexStart, center, flexEnd }
    enum VerticalAlign { baseline, length, percent, sub, top, middle, bottom, }
    enum JustifyItems { normal, stretch, start, left, center, end, right }
    enum JustifySelf { auto, normal, stretch, start, left, center, end, right }
    enum TextJustify { auto, inter_word, inter_character, none }
    enum fontWeight { normal, bold, italic }

    // Constants for rectangle properties
    static final int LEFT = 0;
    static final int TOP = 1;
    static final int WIDTH = 2;
    static final int HEIGHT = 3;
    static final int RIGHT = 2;
    static final int BOTTOM = 3;

    private final float[] rect = new float[4];
    private final float[] padding = new float[4];
    private final float[] margin = new float[4];
    private final float[] borderColor = new float[4];
    private final float[] backgroundColor = new float[4];

    private String text = "";
    private String justifyContent = "";
    private String alignItems = "";
    private float radius = 0, border = 0, depth = 0, scale = 1;
    private float opacity = 1, fontSize = 10;
    private float minHeight = 0, maxHeight = Float.MAX_VALUE;
    private float minWidth = 0, maxWidth = Float.MAX_VALUE;
    private int columnCount = 1, columnGap = 0, gap = 0;
    private Display display = Display.none;
    private boolean pointerEvents = true;
    private boolean visibility = true;

    private final Map<String, String> transitions = new HashMap<>();

    public Style display(Display value) { this.display = value; return this; }
    public Style rect(float x, float y, float w, float h) { rect[LEFT] = x; rect[TOP] = y; rect[WIDTH] = w; rect[HEIGHT] = h; return this; }
    public Style width(float w) { rect[WIDTH] = w; return this; }
    public Style height(float h) { rect[HEIGHT] = h; return this; }
    public Style border(float v) { border = v; return this; }
    public Style radius(float v) { radius = v; return this; }
    public Style depth(float z) { depth = z; return this; }
    public Style scale(float v) { scale = v; return this; }
    public Style opacity(float value) { opacity = value; return this; }
    public Style visibility(boolean visible) { opacity = visible ? 1 : 0; return this; }

    public Style padding(float v) { padding[LEFT] = padding[TOP] = padding[RIGHT] = padding[BOTTOM] = v; return this; }
    public Style margin(float v) { margin[LEFT] = margin[TOP] = margin[RIGHT] = margin[BOTTOM] = v; return this; }
    public Style backgroundColor(Color color) { setColor(backgroundColor, color); return this; }
    public Style borderColor(Color color) { setColor(borderColor, color); return this; }

    public Style fontSize(float size) { fontSize = size; return this; }
    public Style text(String value) { text = value; return this; }
    public Style justifyContent(String value) { justifyContent = value; return this; }
    public Style alignItems(String value) { alignItems = value; return this; }

    public Style pointerEvents(boolean enabled) { pointerEvents = enabled; return this; }
    public Style transition(String property, float duration, String easing) { transitions.put(property, duration + "s " + easing); return this; }

    private void setColor(float[] colorArray, Color color) {
        colorArray[0] = color.getRed() / 255.0f;
        colorArray[1] = color.getGreen() / 255.0f;
        colorArray[2] = color.getBlue() / 255.0f;
        colorArray[3] = color.getAlpha() / 255.0f;
    }

    public Map<String, String> getTransitions() { return transitions; }
}
