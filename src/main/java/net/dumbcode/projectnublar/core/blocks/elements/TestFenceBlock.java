package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.blocks.DumbFenceBlock;
import org.jetbrains.annotations.NotNull;

public class TestFenceBlock extends DumbFenceBlock {
    public TestFenceBlock(DumbBlocks.@NotNull Blocks block) {
        super(Properties.ofFullCopy(block.getRegistry().block().get()));
    }
}
