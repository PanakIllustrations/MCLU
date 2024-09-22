package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.McluConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class SpriteUploader extends TextureAtlasHolder {
    static final ResourceLocation GUI_ATLAS = new ResourceLocation(McluConstants.MOD_ID,"gui");

    public SpriteUploader(TextureManager textureManager) {
        super(textureManager, McluConstants.LOCATION_GUI_TEXTURE_ATLAS, GUI_ATLAS);
    }

    @Override
    public TextureAtlasSprite getSprite(ResourceLocation location) {
        return super.getSprite(location);
    }

    public TextureAtlas getAtlas() {
        return super.textureAtlas;
    }
}
