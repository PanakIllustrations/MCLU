package com.tumult.mclu.client.gui.icons;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum IconUtils {
    INSTANCE;
    private GuiIcon icon;
    public GuiIcon getIcon() {
        if (INSTANCE.icon == null) {
            INSTANCE.icon = new GuiIcon();
        }
        return INSTANCE.icon;
    }
}