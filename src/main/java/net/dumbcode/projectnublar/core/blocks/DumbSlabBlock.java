package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.world.level.block.SlabBlock;

public abstract class DumbSlabBlock extends SlabBlock implements IDumbBlock {
    protected DumbSlabBlock(Properties properties) {
        super(properties);
    }
}
