package com.tumult.mclu.client.gui;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import com.tumult.mclu.MCLU;


@Mod.EventBusSubscriber(modid = MCLU.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MCLU.MODID);
    // RangedAttribute(string name, double default, double minimum, double maximum)
    public static final RegistryObject<Attribute> CUSTOM_ARMOR_CURRENT = ATTRIBUTES.register("custom_armor_current",
            () -> new RangedAttribute("attribute.mclu.armor_current", 0.0D, 0.0D, 64.0D).setSyncable(true));
    public static final RegistryObject<Attribute> CUSTOM_HEALTH_MAX = ATTRIBUTES.register("custom_health_max",
            () -> new RangedAttribute("attribute.mclu.custom_health_max", 4.0D, 4.0D, 64.0D).setSyncable(true));
    public static final RegistryObject<Attribute> IMAGINATION_CURRENT = ATTRIBUTES.register("imagination_current",
            () -> new RangedAttribute("attribute.mclu.current_imagination", 6.0D, 0.0D, 64.0D).setSyncable(true));
    public static final RegistryObject<Attribute> IMAGINATION_MAX = ATTRIBUTES.register("imagination_max",
            () -> new RangedAttribute("attribute.mclu.max_imagination", 6.0D, 0.0D, 64.0D).setSyncable(true));
    public static final RegistryObject<Attribute> U_SCORE_CURRENT = ATTRIBUTES.register("u_score_current",
            () -> new RangedAttribute("attribute.mclu.u_score_current", 0.0D, 0.0D, 100.0D).setSyncable(true));
    public static final RegistryObject<Attribute> U_SCORE_NEEDED = ATTRIBUTES.register("u_score_needed",
            () -> new RangedAttribute("attribute.mclu.u_score_needed", 100.0D, 1.0D, 1000000.0D).setSyncable(true));
    public static final RegistryObject<Attribute> U_LEVEL = ATTRIBUTES.register("u_level",
            () -> new RangedAttribute("attribute.mclu.u_level", 0.0D, 0.0D, 45.0D).setSyncable(true));
    public static final RegistryObject<Attribute> U_COIN = ATTRIBUTES.register("u_coin",
            () -> new RangedAttribute("attribute.mclu.u_coin", 0.0D, 0.0D, 99999999.0D).setSyncable(true));
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, CUSTOM_ARMOR_CURRENT.get());
        event.add(EntityType.PLAYER, CUSTOM_HEALTH_MAX.get());
        event.add(EntityType.PLAYER, IMAGINATION_CURRENT.get());
        event.add(EntityType.PLAYER, IMAGINATION_MAX.get());
        event.add(EntityType.PLAYER, U_SCORE_CURRENT.get());
        event.add(EntityType.PLAYER, U_SCORE_NEEDED.get());
        event.add(EntityType.PLAYER, U_LEVEL.get());
        event.add(EntityType.PLAYER, U_COIN.get());
    }
}
