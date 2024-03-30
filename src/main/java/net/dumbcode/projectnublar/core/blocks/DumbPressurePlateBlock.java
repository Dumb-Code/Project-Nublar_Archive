package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public abstract class DumbPressurePlateBlock extends PressurePlateBlock implements IDumbBlock {
    protected DumbPressurePlateBlock(BlockSetType setType, Properties properties) {
        super(setType, properties);
    }
}
