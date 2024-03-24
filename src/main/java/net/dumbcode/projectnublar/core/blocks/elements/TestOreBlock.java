package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbDropExperienceBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;

public class TestOreBlock extends DumbDropExperienceBlock {
    public TestOreBlock() {
        super(UniformInt.of(3, 15), Properties.ofFullCopy(Blocks.STONE).strength(3f).requiresCorrectToolForDrops());
    }
}
