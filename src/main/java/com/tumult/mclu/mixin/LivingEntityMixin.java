package com.tumult.mclu.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    /**
     * @author Boanerges
     * @Reason Overwrite the checkFallDamage method to prevent fall damage.
     */
    @Overwrite
    protected void checkFallDamage(double fallDistance, boolean onGround, BlockState blockState, BlockPos blockPos) {
        // Skip fall damage processing
    }
    /**
     * @author Boanerges
     * @Reason Overwrite the causeFallDamage method to prevent fall damage.
     */
    @Overwrite
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        // Skip fall damage
        return false;
    }

    /**
     * @author Boanerges
     * @Reason Overwrite calculateFallDamage to return zero damage.
     */
    @Overwrite
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        // Return 0 damage
        return 0;
    }
}
