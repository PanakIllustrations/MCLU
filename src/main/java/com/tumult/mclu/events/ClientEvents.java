package com.tumult.mclu.events;

import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.icons.GuiSprite;
import com.tumult.mclu.client.gui.icons.IconUtils;
import com.tumult.mclu.client.gui.icons.SpriteUploader;
import com.tumult.mclu.client.gui.screens.CustomAttributeHudOverlay;
import com.tumult.mclu.client.gui.Keybindings;
import com.tumult.mclu.client.gui.icons.GuiCursor;
import com.tumult.mclu.client.gui.screens.GuiHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.tumult.mclu.MCLU;

public class ClientEvents {
    private static boolean wasKeyPressedLastTick = false;

    @Mod.EventBusSubscriber(modid = McluConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        // Forge-specific client-side event handlers here
        @SubscribeEvent
        public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
            ResourceLocation overlayId = event.getOverlay().id();
            // Compare the overlay with vanilla overlays like health, armor, and food
            if (overlayId.equals(VanillaGuiOverlay.PLAYER_HEALTH.id()) ||
                overlayId.equals(VanillaGuiOverlay.ARMOR_LEVEL.id()) ||
                overlayId.equals(VanillaGuiOverlay.FOOD_LEVEL.id()) ||
                overlayId.equals(VanillaGuiOverlay.AIR_LEVEL.id()) ||
                overlayId.equals(VanillaGuiOverlay.CHAT_PANEL.id()) ||
                overlayId.equals(VanillaGuiOverlay.HELMET.id())) {
                event.setCanceled(true); // Cancel the event to prevent rendering
            }
            if (overlayId.equals(VanillaGuiOverlay.HOTBAR.id())) {

            }
        }
        private static GuiCursor guiCursor = IconUtils.getCursor();
        private static float yaw = 0.0F;
        private static float pitch = 0.0F;

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;

            if (player != null) {

                boolean isKeyPressed = Keybindings.INSTANCE.RELEASE_MOUSE.isDown();

                if (isKeyPressed && !wasKeyPressedLastTick){
                    Keybindings.INSTANCE.RELEASE_MOUSE.consumeClick();
                    guiCursor.toggleCursorVisible();
                }

                if (!guiCursor.isCursorVisible()) {
                    yaw = mc.player.getViewXRot(1.0f);
                    pitch = mc.player.getViewYRot(1.0f);
                }

                wasKeyPressedLastTick = isKeyPressed;
            }
        }

        @SubscribeEvent
        public static void clientViewportEvent(ViewportEvent event) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;

            if (player != null && guiCursor.isCursorVisible()) {
                event.getCamera().getEntity().setYRot(pitch);
                event.getCamera().getEntity().setXRot(yaw);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = McluConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            //event.registerAboveAll("backpack", GuiHUD.BACKPACK_ICON);
            event.registerAboveAll("armor", CustomAttributeHudOverlay.CUSTOM_ARMOR_HUD);
            event.registerAboveAll("health", CustomAttributeHudOverlay.CUSTOM_HEALTH_HUD);
            event.registerAboveAll("imagination", CustomAttributeHudOverlay.IMAGINATION_HUD);
            event.registerAboveAll("u_level", CustomAttributeHudOverlay.U_LEVEL_HUD);
            event.registerAboveAll("cursor", GuiCursor.CURSOR_ICON);
        }

        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(Keybindings.INSTANCE.RELEASE_MOUSE);
        }

    }
}
