package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbDropExperienceBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;

/**
 * This class represents a {@link TestOreBlock} which is a type of {@link DumbDropExperienceBlock}.
 * This creates a block that also drops experience when broken, as specified by {@link DropExperienceBlock}
 * To ensure consistency throughout the project, all blocks extend our own abstract classes or implement our own interfaces.
 * It copies the properties of {@link Blocks#STONE} and requires the correct tool for drops.
 */
public class TestOreBlock extends DumbDropExperienceBlock {
    /**
     * Constructor for {@link TestOreBlock}.
     * It sets the experience drop range to be between 3 and 15 and
     * copies the properties of {@link Blocks#STONE} with a strength of 3f.
     */
    public TestOreBlock() {
        super(UniformInt.of(3, 15), Properties.ofFullCopy(Blocks.STONE).strength(3f).requiresCorrectToolForDrops());
    }
}