package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public abstract class DumbFenceGateBlock extends FenceGateBlock implements IDumbBlock {
    protected DumbFenceGateBlock(WoodType woodType, Properties properties) {
        super(woodType, properties);
    }
}
