package com.tumult.mclu.client.gui.frame.core.css;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Style {
    // Enumerations for CSS-like properties
    public enum Display { inline, flex, grid, table, none }
    public enum Align { top, center, bottom, left, right }
    public enum Position { stationary, absolute, fixed, relative, sticky }
    public enum JustifyContent { flexStart, center, flexEnd }
    public enum VerticalAlign { baseline, length, percent, sub, top, middle, bottom }
    public enum JustifyItems { normal, stretch, start, left, center, end, right }
    public enum JustifySelf { auto, normal, stretch, start, left, center, end, right }
    public enum TextJustify { auto, inter_word, inter_character, none }
    public enum FontWeight { normal, bold, italic }

    // Constants for rectangle properties
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int WIDTH = 2;
    public static final int HEIGHT = 3;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;

    // Base properties stored as packed integers for performance
    private float[] rect = new float[4];
    private float[] padding = new float[4];
    private float[] margin = new float[4];
    private int borderColor = 0xFFFFFFFF; // Packed ARGB
    private int backgroundColor = 0xFFFFFFFF; // Packed ARGB

    // Layout and text properties
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
    private boolean dirty = true;

    // Transitions
    private final Map<String, String> transitions = new HashMap<>();

    // Property setters (fluent API)
    public Style display(Display value) { this.display = value; markDirty(); return this; }
    public Style rect(float w, float h) {rect[WIDTH] = w; rect[HEIGHT] = h; markDirty(); return this; }
    public Style rect(float x, float y, float w, float h) { rect[LEFT] = x; rect[TOP] = y; rect[WIDTH] = w; rect[HEIGHT] = h; markDirty(); return this; }
    public Style width(float w) { rect[WIDTH] = w; markDirty(); return this; }
    public Style height(float h) { rect[HEIGHT] = h; markDirty(); return this; }
    public Style border(float v) { border = v; markDirty(); return this; }
    public Style radius(float v) { radius = v; markDirty(); return this; }
    public Style depth(float z) { depth = z; markDirty(); return this; }
    public Style scale(float v) { scale = v; markDirty(); return this; }
    public Style opacity(float value) { opacity = value; markDirty(); return this; }
    public Style visibility(boolean visible) { this.visibility = visible; markDirty(); return this; }

    public Style padding(float v) {
        padding[LEFT] = padding[TOP] = padding[RIGHT] = padding[BOTTOM] = v;
        markDirty();
        return this;
    }

    public Style margin(float v) {
        margin[LEFT] = margin[TOP] = margin[RIGHT] = margin[BOTTOM] = v;
        markDirty();
        return this;
    }

    // Use packed colors for efficiency
    public Style backgroundColor(Color color) {
        this.backgroundColor = packColor(color);
        markDirty();
        return this;
    }

    public Style borderColor(Color color) {
        this.borderColor = packColor(color);
        markDirty();
        return this;
    }

    public Style fontSize(float size) { fontSize = size; markDirty(); return this; }
    public Style text(String value) { text = value; markDirty(); return this; }
    public Style justifyContent(String value) { justifyContent = value; markDirty(); return this; }
    public Style alignItems(String value) { alignItems = value; markDirty(); return this; }

    public Style pointerEvents(boolean enabled) { pointerEvents = enabled; markDirty(); return this; }

    public Style transition(String property, float duration, String easing) {
        transitions.put(property, duration + "s " + easing);
        return this;
    }

    // Getters
    public Display getDisplay() { return display; }
    public float[] getRect() { return rect; }
    public float[] getPadding() { return padding; }
    public float[] getMargin() { return margin; }
    public float getRadius() { return radius; }
    public float getBorder() { return border; }
    public float getDepth() { return depth; }
    public float getScale() { return scale; }
    public float getOpacity() { return opacity; }
    public boolean isVisible() { return visibility; }
    public String getText() { return text; }
    public float getFontSize() { return fontSize; }
    public boolean hasPointerEvents() { return pointerEvents; }
    public Map<String, String> getTransitions() { return transitions; }

    // Color access with unpacking for rendering
    public Color getBackgroundColor() { return unpackColor(backgroundColor); }
    public Color getBorderColor() { return unpackColor(borderColor); }

    // Efficient color manipulation methods
    private static int packColor(Color color) {
        return ((color.getAlpha() & 0xFF) << 24) |
                ((color.getRed() & 0xFF) << 16) |
                ((color.getGreen() & 0xFF) << 8) |
                (color.getBlue() & 0xFF);
    }

    private static Color unpackColor(int packed) {
        return new Color(
                (packed >> 16) & 0xFF,
                (packed >> 8) & 0xFF,
                packed & 0xFF,
                (packed >> 24) & 0xFF
        );
    }

    // Float array color conversion for rendering
    public void getBackgroundColorArray(int color, float[] out) {
        out[0] = ((color >> 16) & 0xFF) / 255f;
        out[1] = ((color >> 8) & 0xFF) / 255f;
        out[2] = (color & 0xFF) / 255f;
        out[3] = ((color >> 24) & 0xFF) / 255f * opacity;
    }

    // Dirty state management
    public boolean isDirty() { return dirty; }
    public void markDirty() { dirty = true; }
    public void clearDirty() { dirty = false; }
}