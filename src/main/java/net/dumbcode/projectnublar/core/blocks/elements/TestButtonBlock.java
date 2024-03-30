package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.blocks.DumbButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.NotNull;

public class TestButtonBlock extends DumbButtonBlock {
    public TestButtonBlock(DumbBlocks.@NotNull Blocks block) {
        super(BlockSetType.IRON, 20, Properties.ofFullCopy(block.getRegistry().block().get()));
    }
}
