package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.blocks.DumbDoorBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.NotNull;

public class TestDoorBlock extends DumbDoorBlock {
    public TestDoorBlock(DumbBlocks.@NotNull Blocks block) {
        super(BlockSetType.IRON, Properties.ofFullCopy(block.getRegistry().block().get()));
    }
}
