package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.blocks.DumbTrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.NotNull;

public class TestTrapDoorBlock extends DumbTrapDoorBlock {
    public TestTrapDoorBlock(DumbBlocks.@NotNull Blocks block) {
        super(BlockSetType.IRON, Properties.ofFullCopy(block.getRegistry().block().get()));
    }
}
