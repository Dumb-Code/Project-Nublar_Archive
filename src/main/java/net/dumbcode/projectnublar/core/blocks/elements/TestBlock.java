package net.dumbcode.projectnublar.core.blocks.elements;

import net.dumbcode.projectnublar.core.blocks.DumbBlock;
import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.blocks.entity.DumbBlockEntities;
import net.dumbcode.projectnublar.core.blocks.entity.DumbEntityBlock;
import net.dumbcode.projectnublar.core.blocks.entity.elements.TestBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;

/**
 * This class represents a TestBlock which is a type of DumbEntityBlock.
 * It copies the properties of an amethyst block.
 *
 * This is an example of how to create a {@link Block} with a linked {@link EntityBlock}
 * The linked entity is {@link TestBlockEntity} as linked by {@link DumbBlocks.Metadata#associatedEntity()}
 */
public class TestBlock extends DumbEntityBlock {
    /**
     * Constructor for {@link TestBlock}.
     * Note that this takes in a parameter of {@link DumbBlockEntities.Entities},
     * which is different from a normal {@link DumbBlock} as the block requires
     * a linked entity.
     *
     * @param entity The entity associated with this block.
     */
    public TestBlock(DumbBlockEntities.Entities entity) {
        super(entity, Properties.ofFullCopy(Blocks.AMETHYST_BLOCK));
    }
}