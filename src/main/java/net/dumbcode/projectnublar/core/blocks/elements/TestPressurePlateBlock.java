package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.blocks.DumbPressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.NotNull;

public class TestPressurePlateBlock extends DumbPressurePlateBlock {
    public TestPressurePlateBlock(DumbBlocks.@NotNull Blocks block) {
        super(BlockSetType.IRON, Properties.ofFullCopy(block.getRegistry().block().get()));
    }
}
