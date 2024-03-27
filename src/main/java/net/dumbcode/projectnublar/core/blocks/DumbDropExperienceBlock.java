package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.DropExperienceBlock;

/**
 * This abstract class is used to ensure consistency throughout the project.
 * You should always extend our abstract classes or implement our interfaces when creating new types (in this case
 * blocks) to ensure that the project is easier to maintain and understand.
 */
public abstract class DumbDropExperienceBlock extends DropExperienceBlock implements IDumbBlock {
    /**
     * Constructor for the {@link DumbDropExperienceBlock} class.
     *
     * @param experience An instance of {@link IntProvider} that represents the experience provided by the block. Must not be null.
     * @param properties An instance of {@link Properties} that represents the properties of the block. Must not be null.
     */
    protected DumbDropExperienceBlock(IntProvider experience, Properties properties) {
        super(experience, properties);
    }
}
