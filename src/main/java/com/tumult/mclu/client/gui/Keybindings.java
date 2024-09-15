package com.tumult.mclu.client.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.tumult.mclu.MCLU;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public final class Keybindings {
    public static final Keybindings INSTANCE = new Keybindings();

    private Keybindings() {}

    private static final String MCLU_CATEGORY = "key.categories." + MCLU.MODID;

    public final KeyMapping RELEASE_MOUSE = new KeyMapping(
            "key." + MCLU.MODID + ".release_mouse",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_F, -1),
            MCLU_CATEGORY
    );
}
