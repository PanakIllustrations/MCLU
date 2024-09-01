package com.tumult.mclu.client.gui;



import com.tumult.mclu.MCLU;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public abstract class testScreen extends Screen {

    public static final ResourceLocation TEXTURE =
            new ResourceLocation(MCLU.MODID, "textures/gui/example_menu.png");

    protected final int windowWidth;
    protected final int windowHeight;
    protected final ResourceLocation backgroundTexture;


    protected testScreen(Component title) {
        this(title, TEXTURE, 176, 166);
    };
    protected testScreen(Component title, ResourceLocation texture, int windowWidth, int windowHeight) {
        super(title);
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.backgroundTexture = texture;
    };

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        int centeredX = (this.width - this.windowWidth) / 2;
        int centeredY = (this.height - this.windowHeight) / 2;
        guiGraphics.blit(this.backgroundTexture, centeredX, centeredY, 0, 0, this.windowWidth, this.windowHeight);
        var poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(centeredX, centeredY, 0);
        this.renderContent(guiGraphics, mouseX, mouseY, partialTicks);
        poseStack.popPose();
        for(var renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTicks);
        }
    }

    protected void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY,
                                 float partialTicks) {}

    @Override
    public void tick() {
        super.tick();
        if (!this.minecraft.player.isAlive() || this.minecraft.player.isDeadOrDying()) {
            this.onClose();
        }
    }
}
