package com.tumult.mclu.client.gui.frame.core.css;

import java.awt.*;

public class StyleBuilder {
    private final Style style = new Style();

    public static StyleBuilder create() {
        return new StyleBuilder();
    }

    public StyleBuilder display(Style.Display display) {
        style.display(display);
        return this;
    }

    public StyleBuilder size(float width, float height) {
        style.width(width).height(height);
        return this;
    }

    public StyleBuilder position(float x, float y) {
        style.rect(x, y, style.getRect()[Style.WIDTH], style.getRect()[Style.HEIGHT]);
        return this;
    }

    public StyleBuilder background(Color color) {
        style.backgroundColor(color);
        return this;
    }

    public StyleBuilder border(float width, Color color) {
        style.border(width).borderColor(color);
        return this;
    }

    public StyleBuilder rounded(float radius) {
        style.radius(radius);
        return this;
    }

    public StyleBuilder padding(float padding) {
        style.padding(padding);
        return this;
    }

    public StyleBuilder margin(float margin) {
        style.margin(margin);
        return this;
    }

    public StyleBuilder text(String text, float fontSize) {
        style.text(text).fontSize(fontSize);
        return this;
    }

    public StyleBuilder flexLayout(String justifyContent, String alignItems) {
        style.display(Style.Display.flex)
                .justifyContent(justifyContent)
                .alignItems(alignItems);
        return this;
    }

    public Style build() {
        return style;
    }
}
