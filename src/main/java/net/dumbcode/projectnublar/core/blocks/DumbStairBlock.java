package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.world.level.block.StairBlock;
import org.jetbrains.annotations.NotNull;

public abstract class DumbStairBlock extends StairBlock implements IDumbBlock {
    protected DumbStairBlock(DumbBlocks.@NotNull Blocks block, Properties properties) {
        super(block.getRegistry().block().get().defaultBlockState(), properties);
    }

    protected DumbStairBlock(DumbBlocks.@NotNull Blocks block) {
        this(block, Properties.ofFullCopy(block.getRegistry().block().get()));
    }
}
