package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.frame.ClickableIcon;
import com.tumult.mclu.client.gui.frame.DrawableIcon;
import net.minecraft.resources.ResourceLocation;

public class IconFactory {
    public static DrawableIcon createDrawableIcon(String name, int width, int height, int textureWidth, int textureHeight) {
        return new DrawableIcon(new ResourceLocation(McluConstants.MOD_ID, "/textures/gui/" + name + ".png"), width, height, textureWidth, textureHeight);
    }
    public static Clickable.ClickableIcon createClickableIcon(DrawableIcon icon, int width, int height, int textureWidth, int textureHeight){
        return new ClickableIcon(icon);
    }

}
