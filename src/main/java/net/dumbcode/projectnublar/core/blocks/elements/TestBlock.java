package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbBlock;
import net.minecraft.world.level.block.Blocks;

public class TestBlock extends DumbBlock {

    public TestBlock() {
        super(Properties.ofFullCopy(Blocks.AMETHYST_BLOCK));
    }
}
