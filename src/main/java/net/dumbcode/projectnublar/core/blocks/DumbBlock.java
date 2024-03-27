package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.world.level.block.Block;

/**
 * This abstract class is used to ensure consistency throughout the project.
 * You should always extend our abstract classes or implement our interfaces when creating new types (in this case
 * blocks) to ensure that the project is easier to maintain and understand.
 */
public abstract class DumbBlock extends Block implements IDumbBlock {
    /**
     * Constructor for {@link DumbBlock}.
     * It takes a {@link Properties} object as a parameter.
     *
     * @param properties The properties of the block.
     */
    protected DumbBlock(Properties properties) {
        super(properties);
    }
}