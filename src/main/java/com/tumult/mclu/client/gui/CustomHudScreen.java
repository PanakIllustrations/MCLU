package com.tumult.mclu.client.gui;

import com.tumult.mclu.client.gui.widgets.AttributeWidget;

import java.awt.*;

public class CustomHudScreen extends Screen {
    private AttributeWidget healthWidget;

    protected CustomHudScreen() {
        super(new TextComponent("Custom HUD"));
    }

    @Override
    protected void init() {
        super.init();
        int x = this.width / 2 - 50;
        int y = this.height / 2 - 10;

        healthWidget = new HealthWidget(x,y,100,10,20);
        this.addRenderableWidget(new healthWidget);
    }
}
