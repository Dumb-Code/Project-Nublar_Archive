package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.world.level.block.Block;

public abstract class DumbBlock extends Block implements IDumbBlock {
    public DumbBlock(Properties properties) {
        super(properties);
    }
}
