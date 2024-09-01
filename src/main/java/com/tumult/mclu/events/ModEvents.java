package com.tumult.mclu.events;

import com.tumult.mclu.client.gui.CustomAttributes;
import com.tumult.mclu.client.gui.ModifyPlayerHealth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
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
        public static boolean isHealthInitialized = false;
        private static final ModifyPlayerHealth modifyPlayerHealth = new ModifyPlayerHealth();
        private static final Map<UUID, Integer> playerCurrentArmor = new HashMap<>();

        @SubscribeEvent
        public static void onWorldLoad(LevelEvent.Load event) {
            if (event.getLevel() instanceof ServerLevel serverLevel) {
                serverLevel.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).set(true, serverLevel.getServer());
            }
        }

        @SubscribeEvent
        public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
            isHealthInitialized = false;
            Player player = event.getEntity();
            AttributeInstance maxHealthAttr = player.getAttribute(CustomAttributes.CUSTOM_HEALTH_MAX.get());
            if (maxHealthAttr != null) {
                double customMaxHealth = maxHealthAttr.getValue();
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(customMaxHealth);
                player.setHealth((float) customMaxHealth);
            }
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
                player.getAttribute(CustomAttributes.CUSTOM_ARMOR_CURRENT.get()).setBaseValue(maxArmorValue);
            }
        }


        @SubscribeEvent
        public static void onPlayerDamage(LivingDamageEvent event) {
            if (event.getEntity() instanceof Player player) {
                double currentArmorValue = player.getAttributeValue(CustomAttributes.CUSTOM_ARMOR_CURRENT.get());
                float damageTaken = event.getAmount();

                if (currentArmorValue > 0) {
                    double damageAbsorbedByArmor = Math.min(damageTaken, currentArmorValue);
                    double newArmorValue = Math.max(currentArmorValue - damageAbsorbedByArmor, 0);
                    player.getAttribute(CustomAttributes.CUSTOM_ARMOR_CURRENT.get()).setBaseValue(newArmorValue);
                    if (newArmorValue <= 0) {
                        player.playSound(SoundEvents.ITEM_BREAK, 1.0f, 1.0f);
                    }
                    float remainingDamage = (float) (damageTaken - damageAbsorbedByArmor);
                    event.setAmount(remainingDamage);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerDeath(LivingDeathEvent event) {
            if (event.getEntity() instanceof Player player) {
                UUID playerId = player.getUUID();
                int currentArmorValue = (int) player.getAttributeValue(CustomAttributes.CUSTOM_ARMOR_CURRENT.get());
                playerCurrentArmor.put(playerId, currentArmorValue); // Store the armor value
            }
        }

        @SubscribeEvent
        public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
            Player player = event.getEntity();
            UUID playerId = player.getUUID();

            Integer currentArmorValue = playerCurrentArmor.get(playerId);
            AttributeInstance maxHealthAttr = player.getAttribute(CustomAttributes.CUSTOM_HEALTH_MAX.get());

            if (maxHealthAttr != null) {
                double customMaxHealth = maxHealthAttr.getValue();
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(customMaxHealth);
                player.setHealth((float) customMaxHealth);
            }
            player.getServer().execute(() -> {
                //player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0);
                player.getAttribute(CustomAttributes.CUSTOM_ARMOR_CURRENT.get()).setBaseValue(currentArmorValue);
                // Player is spawned without armor, so current armor must be loaded by value stored on death
            });
        }

    }
}
