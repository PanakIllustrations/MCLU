package com.tumult.mclu.client.gui.frame.core.review;

import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import com.tumult.mclu.client.gui.frame.core.geometry.Rect;

import java.awt.*;


public class Registry {
    public final Node hud;

    public final Node backpack;
    public final Node map;
    public final Node passport;
    public final Node mouse_cursor;

    Registry(){
        mouse_cursor = registerGuiIcon(new Rect(9,17, 1), "mouse_cursor", null);
        hud = registerGuiIcon(new Rect(0,0,255,255,0), "hud", null);

        backpack = registerGuiIcon(new Rect(16, 16, 2), "backpack", hud);
        map = registerGuiIcon( new Rect(16, 16, 2), "map", hud);
        passport = registerGuiIcon( new Rect(16,16, 2), "passport", Color.CYAN, hud);
    }

    public Node registerGuiIcon(IRect rect, String name, Color color, Node parent) {
        float[] wh = new float[2]; rect.getWH(wh);
        int size = defaultCanvasSize(wh);
        return new Node(rect, name, size, color, parent);
    }

    public Node registerGuiIcon(IRect rect, String name, Node parent) {
        float[] wh = new float[2]; rect.getWH(wh);
        int size = defaultCanvasSize(wh);
        return new Node(rect, name, size, Color.WHITE, parent);
    }

    public static int defaultCanvasSize(float[] wh) {
        int intValue = (int) Math.ceil(Math.max(wh[0], wh[1]));
        intValue--;
        intValue |= intValue >> 1;
        intValue |= intValue >> 2;
        intValue |= intValue >> 4;
        intValue |= intValue >> 8;
        intValue |= intValue >> 16;
        return intValue + 1;
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
