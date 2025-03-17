package com.tumult.mclu.client.gui.core;

import com.tumult.mclu.client.gui.core.geometry.Circle;
import com.tumult.mclu.client.gui.core.geometry.Rect;
import com.tumult.mclu.client.gui.core.geometry.RoundRect;

import java.awt.*;

public class Registry {
    // Define UI elements that will be accessible
    //public final GuiContainer mainContainer;
    //public final GuiContainer toolbar;
    public final GuiSprite backpack;
    public final GuiSprite map;
    public final GuiSprite passport;
    //public final GuiSprite mouse_cursor;
    public final GuiShape rect;
    public final GuiShape circle;

    Registry(){
        // Create cursor
//        mouse_cursor = registerGuiSprite(new Rect(9,17), "mouse_cursor", 32);
//
//        // Create a main container with grid layout
//        mainContainer = new GuiContainer(
//                new RoundRect(50, 50, 300, 200, 1, 10),
//                new Color(0, 0, 0, 200)
//        );
////        mainContainer.setLayoutStrategy(new GridLayout(3, 10, 10));
//
//        // Create a toolbar with flex layout
//        toolbar = new GuiContainer(
//                new RoundRect(50, 260, 300, 40, 1, 5),
//                new Color(33, 33, 33, 200)
//        );
//        toolbar.setLayoutStrategy(new FlexLayout(
//                Style.JustifyContent.center,
//                Style.Align.center,
//                5 // Gap between items
//        );

        // Create icons
        backpack = registerGuiSprite(new Rect(16, 16), "backpack", 16);
        map = registerGuiSprite(new Rect(16, 16), "map", 16);
        passport = registerGuiSprite(new Rect(16,16), "passport", Color.RED, 16);
        rect = registerGuiShape(new RoundRect(20, 20, 30, 30, 2, 5), Color.RED);
        circle = registerGuiShape(new Circle(40, 20, 1, 20), Color.CYAN);

        // Add icons to toolbar
//        toolbar.addChild(backpack);
//        toolbar.addChild(passport);
//        toolbar.addChild(map);
//
//        // Add shapes to main container
//        mainContainer.addChild(rect);
//        mainContainer.addChild(circle);

        // Add toolbar to main container (this will position it properly based on layout)
        // mainContainer.addChild(toolbar.rect); // Uncomment if you want nested containers
    }

    public GuiSprite registerGuiSprite(Rect rect, String name, Color color, int size) {
        return new GuiSprite(rect, name, size, color);
    }

    public GuiSprite registerGuiSprite(Rect rect, String name, int size) {
        return new GuiSprite(rect, name, size, Color.WHITE);
    }

    public GuiShape registerGuiShape(RoundRect rect, Color color) {
        return new GuiShape(rect, color);
    }

    public enum icon { // thread-safe singleton
        INSTANCE;
        private static Registry icon;
        public static Registry get() { // globalized access point
            if (icon == null) { // lazy initialization
                icon = new Registry();
            }
            return icon;
        }
    }
}
//
//    int defaultCanvasSize(float width, float height) {
//        int intValue = (int) Math.ceil(Math.max(width, height));
//        intValue--;
//        intValue |= intValue >> 1;
//        intValue |= intValue >> 2;
//        intValue |= intValue >> 4;
//        intValue |= intValue >> 8;
//        intValue |= intValue >> 16;
//        return intValue + 1;
//    }
