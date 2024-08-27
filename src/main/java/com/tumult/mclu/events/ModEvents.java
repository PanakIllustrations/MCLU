package com.tumult.mclu.events;

import com.tumult.mclu.client.CustomAttributes;
import com.tumult.mclu.client.ModifyPlayerHealth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import com.tumult.mclu.MCLU;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = MCLU.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ModBusEvents {
        private static final ModifyPlayerHealth modifyPlayerHealth = new ModifyPlayerHealth();
        private static final Map<UUID, Integer> playerCurrentArmor = new HashMap<>();

        @SubscribeEvent
        public static void onWorldLoad(LevelEvent.Load event) {
            if (event.getLevel() instanceof ServerLevel serverLevel) {
                serverLevel.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).set(true, serverLevel.getServer());
            }
        }

        @SubscribeEvent
        public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            Player player = event.getEntity();
            // Apply the health modifier when the player logs in
            modifyPlayerHealth.applyHealthModifier(player);
        }

        @SubscribeEvent
        public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
            if (event.getEntity() instanceof Player player) {
                FoodData foodStats = player.getFoodData();
                foodStats.setFoodLevel(10);
                foodStats.setSaturation(20.0f);
                foodStats.setExhaustion(0.0f);
            }
        }

        @SubscribeEvent
        public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                double maxArmorValue = player.getArmorValue();
                player.getAttribute(CustomAttributes.ARMOR_CURRENT.get()).setBaseValue(maxArmorValue);
            }
        }


        @SubscribeEvent
        public static void onPlayerDamage(LivingDamageEvent event) {
            if (event.getEntity() instanceof Player player) {
                double currentArmorValue = player.getAttributeValue(CustomAttributes.ARMOR_CURRENT.get());
                float damageTaken = event.getAmount();

                double newArmorValue = currentArmorValue - damageTaken;
                newArmorValue = Math.max(newArmorValue, 0); // Clamp the value to a minimum of 0

                AttributeInstance armorAttribute = player.getAttribute(CustomAttributes.ARMOR_CURRENT.get());
                if (armorAttribute != null) {
                    armorAttribute.setBaseValue(newArmorValue);
                    player.getAttribute(CustomAttributes.ARMOR_CURRENT.get()).setBaseValue(0);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerDeath(LivingDeathEvent event) {
            if (event.getEntity() instanceof Player player) {
                UUID playerId = player.getUUID();
                int currentArmorValue = (int) player.getAttributeValue(CustomAttributes.ARMOR_CURRENT.get());
                playerCurrentArmor.put(playerId, currentArmorValue); // Store the armor value
            }
        }

        @SubscribeEvent
        public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
            Player player = event.getEntity();
            UUID playerId = player.getUUID();
            Integer currentArmorValue = playerCurrentArmor.get(playerId);

            player.getServer().execute(() -> {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0);
                player.getAttribute(CustomAttributes.ARMOR_CURRENT.get()).setBaseValue(currentArmorValue);
                // Player is spawned without armor, so current armor must be loaded by value stored on death
            });
        }

    }
}
