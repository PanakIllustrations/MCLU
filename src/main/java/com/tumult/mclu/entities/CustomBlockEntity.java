package com.tumult.mclu.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomBlockEntity extends BlockEntity {
    private float rotationAngle;

    public CustomBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROTATING_BLOCK_ENTITY.get(), pos, state);
    }

    public void tick() {
        if (level != null && !level.isClientSide()) {
            rotationAngle = (rotationAngle + 1.0F) % 360.0F;
            setChanged();  // Mark the block entity as changed so the renderer knows to update
        }
    }

    public float getRotationAngle() {
        return rotationAngle;
    }
}
