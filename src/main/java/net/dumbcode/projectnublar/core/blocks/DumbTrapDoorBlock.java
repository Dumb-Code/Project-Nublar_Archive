package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public abstract class DumbTrapDoorBlock extends TrapDoorBlock implements IDumbBlock {
    protected DumbTrapDoorBlock(BlockSetType setType, Properties properties) {
        super(setType, properties.noOcclusion());
    }
}
