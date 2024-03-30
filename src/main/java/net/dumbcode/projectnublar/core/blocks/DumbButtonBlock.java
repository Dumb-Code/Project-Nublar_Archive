package net.dumbcode.projectnublar.core.blocks;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public abstract class DumbButtonBlock extends ButtonBlock implements IDumbBlock {
    protected DumbButtonBlock(BlockSetType setType, int ticksToStayPressed, Properties properties) {
        super(setType, ticksToStayPressed, properties);
    }
}
