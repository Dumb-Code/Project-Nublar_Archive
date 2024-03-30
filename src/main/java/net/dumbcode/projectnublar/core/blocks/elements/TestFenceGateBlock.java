package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.blocks.DumbFenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

public class TestFenceGateBlock extends DumbFenceGateBlock {
    public TestFenceGateBlock(DumbBlocks.@NotNull Blocks block) {
        super(WoodType.OAK, Properties.ofFullCopy(block.getRegistry().block().get()));
    }
}
