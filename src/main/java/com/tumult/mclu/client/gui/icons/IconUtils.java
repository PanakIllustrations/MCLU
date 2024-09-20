package com.tumult.mclu.client.gui.icons;

import com.google.common.base.Preconditions;
import com.tumult.mclu.McluConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum IconUtils {
    INSTANCE;

    private GuiSprite sprite;
    private GuiCursor cursor;

    public static GuiSprite getSprite() {
        if (INSTANCE.sprite == null) {
            initialize();
        }
        return INSTANCE.sprite;
    }

    public static GuiCursor getCursor() {
        if (INSTANCE.cursor == null) {
            initialize();
        }
        return INSTANCE.cursor;
    }

    public static void initialize() {
        Minecraft minecraft = Minecraft.getInstance();
        TextureManager textureManager = minecraft.getTextureManager();
        SpriteUploader spriteUploader = new SpriteUploader(textureManager);

        INSTANCE.sprite = new GuiSprite(spriteUploader);
        INSTANCE.cursor = new GuiCursor(INSTANCE.sprite);
    }

    static TextureAtlasSprite getMissingSprite() {
        return Minecraft.getInstance()
                .getTextureAtlas(McluConstants.MOD_ICONS_ATLAS)
                .apply(MissingTextureAtlasSprite.getLocation());
    }
}