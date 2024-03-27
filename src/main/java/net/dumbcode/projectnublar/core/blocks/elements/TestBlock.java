package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.entity.DumbBlockEntities;
import net.dumbcode.projectnublar.core.blocks.entity.DumbEntityBlock;
import net.minecraft.world.level.block.Blocks;

public class TestBlock extends DumbEntityBlock {
    public TestBlock(DumbBlockEntities.Entities entity) {
        super(entity, Properties.ofFullCopy(Blocks.AMETHYST_BLOCK));
    }
}
