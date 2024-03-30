package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public abstract class DumbDoorBlock extends DoorBlock implements IDumbBlock {
    protected DumbDoorBlock(BlockSetType setType, Properties properties) {
        super(setType, properties.noOcclusion());
    }
}
