package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.blocks.DumbWallBlock;
import org.jetbrains.annotations.NotNull;

public class TestWallBlock extends DumbWallBlock {
    public TestWallBlock(DumbBlocks.@NotNull Blocks block) {
        super(Properties.ofFullCopy(block.getRegistry().block().get()));
    }
}
