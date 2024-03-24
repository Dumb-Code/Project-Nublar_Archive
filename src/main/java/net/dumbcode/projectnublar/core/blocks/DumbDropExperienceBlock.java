package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.DropExperienceBlock;

public abstract class DumbDropExperienceBlock extends DropExperienceBlock implements IDumbBlock {
    protected DumbDropExperienceBlock(IntProvider experience, Properties properties) {
        super(experience, properties);
    }
}
