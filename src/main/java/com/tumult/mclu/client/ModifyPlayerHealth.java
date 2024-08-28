package com.tumult.mclu.client;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class ModifyPlayerHealth {
    // Make new AttributeModifier Instance
    private static final AttributeModifier HEALTH_MODIFIER = new AttributeModifier(
            UUID.fromString("b0e4b0b5-04f3-4a15-9348-91f5a7c8b2e0"),
            "max_health_modifier",
            -16,
            AttributeModifier.Operation.ADDITION
    );
    public void applyHealthModifier(Player player) {
        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);

        if (healthAttribute != null) {
            // Apply cached modifier to player attributes
            healthAttribute.addPermanentModifier(HEALTH_MODIFIER);
        }
    }
}

