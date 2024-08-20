package com.tumult.mclu.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Entity.class)
public class EntityMixin {
    /**
     * @author Boanerges
     * @Reason Disables fall damage to balance modded damage mechanics.
     */
    @Overwrite
    protected void checkFallDamage(double fallDistance, boolean onGround, BlockState blockState, BlockPos blockPos) {
        // Skip fall damage processing
    }

    /**
     * @author Boanerges
     * @Reason Disables fall damage to balance modded damage mechanics.
     */
    @Overwrite
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        // Skip fall damage
        return false;
    }

}
