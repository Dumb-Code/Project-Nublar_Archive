package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.blocks.DumbSlabBlock;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class TestSlabBlock extends DumbSlabBlock {
    public TestSlabBlock(DumbBlocks.@NotNull Blocks block) {
        super(Properties.ofFullCopy(block.getRegistry().block().get()));
    }
}
