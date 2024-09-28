package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class UIWindow extends UIComponent {
    private String title;
    private List<UIComponent> children = new ArrayList<>();

    public UIWindow(ResourceLocation resourceLocation, Vector2DPoint iconDimensions, Vector2DPoint textureDimensions) {
        super(resourceLocation, iconDimensions, textureDimensions);
    }
    public void addChild(UIComponent child) {
        children.add(child);
    }
    public List<UIComponent> getChildren() {
        return children;
    }
}
