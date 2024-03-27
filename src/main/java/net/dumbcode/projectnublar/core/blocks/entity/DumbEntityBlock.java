package net.dumbcode.projectnublar.core.blocks.entity;

import net.dumbcode.projectnublar.core.blocks.DumbBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class DumbEntityBlock extends DumbBlock implements EntityBlock {
    private final DumbBlockEntities.Entities entity;
    protected DumbEntityBlock(DumbBlockEntities.Entities entity, Properties properties) {
        super(properties);
        this.entity = entity;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return entity.getBlockEntityConstructor().create(pPos, pState);
    }
}
