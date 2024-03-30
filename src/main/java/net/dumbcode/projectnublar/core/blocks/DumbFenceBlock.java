package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.world.level.block.FenceBlock;

public abstract class DumbFenceBlock extends FenceBlock implements IDumbBlock {
    protected DumbFenceBlock(Properties properties) {
        super(properties);
    }
}
