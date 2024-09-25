package com.tumult.mclu.client.gui.icons;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum IconUtils {
    INSTANCE;

    private GuiIcon icon;
    private Clickable clickable;

    public GuiIcon getIcon() {
        if (icon == null) {
            icon = new GuiIcon();
        }
        return icon;
    }

    public Clickable getClickable() {
        if (clickable == null) {
            clickable = new Clickable();
        }
        return clickable;
    }
}