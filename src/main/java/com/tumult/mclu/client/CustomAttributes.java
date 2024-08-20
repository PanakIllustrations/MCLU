package com.tumult.mclu.client;

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
    public static final RegistryObject<Attribute> CURRENT_ARMOR = ATTRIBUTES.register("current_armor",
            () -> new RangedAttribute("attribute.mclu.armor", 0.0D, 0.0D, 64.0D).setSyncable(true));
    public static final RegistryObject<Attribute> CURRENT_IMAGINATION = ATTRIBUTES.register("current_imagination",
            () -> new RangedAttribute("attribute.mclu.current_imagination", 6.0D, 0.0D, 64.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MAX_IMAGINATION = ATTRIBUTES.register("max_imagination",
            () -> new RangedAttribute("attribute.mclu.max_imagination", 6.0D, 0.0D, 64.0D).setSyncable(true));
    public static final RegistryObject<Attribute> U_SCORE = ATTRIBUTES.register("u_score",
            () -> new RangedAttribute("attribute.mclu.u_score", 0.0D, 0.0D, 1000000.0D).setSyncable(true));
    public static final RegistryObject<Attribute> U_LEVEL = ATTRIBUTES.register("u_level",
            () -> new RangedAttribute("attribute.mclu.u_level", 0.0D, 0.0D, 45.D).setSyncable(true));
    public static final RegistryObject<Attribute> U_COIN = ATTRIBUTES.register("u_coin",
            () -> new RangedAttribute("attribute.mclu.u_coin", 0.0D, 0.0D, 99999999.0D).setSyncable(true));
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, CURRENT_ARMOR.get());
        event.add(EntityType.PLAYER, CURRENT_IMAGINATION.get());
        event.add(EntityType.PLAYER, MAX_IMAGINATION.get());
        event.add(EntityType.PLAYER, U_SCORE.get());
        event.add(EntityType.PLAYER, U_LEVEL.get());
        event.add(EntityType.PLAYER, U_COIN.get());
    }
}
