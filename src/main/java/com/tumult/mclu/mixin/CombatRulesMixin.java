package com.tumult.mclu.mixin;

import net.minecraft.world.damagesource.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CombatRules.class)
public class CombatRulesMixin {
    /**
     * @author Boanerges
     * @Reason Applies custom armor mechanics.
     */
    @Overwrite
    public static float getDamageAfterAbsorb(float baseDamage, float armor, float toughness) {
        return baseDamage;
        /*
        Player player = Minecraft.getInstance().player;
        double currentArmorValue = player.getAttribute(CustomAttributes.ARMOR_CURRENT.get()).getValue();
        double damageAbsorbedByArmor = 0.0D;
        if (currentArmorValue > 0) {
            damageAbsorbedByArmor = Math.min(baseDamage, currentArmorValue);
            double newArmorValue = Math.max(currentArmorValue - damageAbsorbedByArmor, 0);
            player.getAttribute(CustomAttributes.ARMOR_CURRENT.get()).setBaseValue(newArmorValue);

            if (newArmorValue <= 0) {
                player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0F);
            }
        }
        if (damageAbsorbedByArmor >= baseDamage) {
            return 0.0F;
        }
        return (float)(baseDamage - damageAbsorbedByArmor);
        */
    }
}