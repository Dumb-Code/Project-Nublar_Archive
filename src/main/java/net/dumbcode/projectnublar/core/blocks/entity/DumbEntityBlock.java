package net.dumbcode.projectnublar.core.blocks.entity;

import net.dumbcode.projectnublar.core.blocks.DumbBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class DumbEntityBlock extends DumbBlock implements EntityBlock {
    protected DumbEntityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
