package com.tumult.mclu.events;

import com.tumult.mclu.client.CustomAttributeHudOverlay;
import com.tumult.mclu.client.CustomAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.tumult.mclu.MCLU;


public class ClientEvents {

    @Mod.EventBusSubscriber(modid = MCLU.MODID, value = Dist.CLIENT)
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
        }
    }

    @Mod.EventBusSubscriber(modid = MCLU.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("armor", CustomAttributeHudOverlay.CUSTOM_ARMOR_HUD);
            event.registerAboveAll("health", CustomAttributeHudOverlay.CUSTOM_HEALTH_HUD);
            event.registerAboveAll("imagination", CustomAttributeHudOverlay.IMAGINATION_HUD);
            event.registerAboveAll("u_level", CustomAttributeHudOverlay.U_LEVEL_HUD);
        }
    }
}
