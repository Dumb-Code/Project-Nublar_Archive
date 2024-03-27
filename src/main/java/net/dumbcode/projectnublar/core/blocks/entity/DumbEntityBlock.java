package net.dumbcode.projectnublar.core.blocks.entity;

import net.dumbcode.projectnublar.core.blocks.DumbBlock;
import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class is used as a parent class for entity blocks.
 * You should ensure any entity block classes extend this class to ensure consistency throughout the project.
 * Any block entities should be added to {@link DumbBlockEntities.Entities} to ensure they are registered correctly.
 */
public abstract class DumbEntityBlock extends DumbBlock implements EntityBlock {
    /**
     * An instance of {@link DumbBlockEntities.Entities} that is pretty much a circular reference to this.
     * This also allows access to its registry via {@link DumbBlockEntities.Entities#getRegistry()} for creating block entities with {@link #newBlockEntity(BlockPos, BlockState)}
     */
    private final DumbBlockEntities.Entities entity;

    /**
     * Constructor for the {@link DumbEntityBlock} class.
     *
     * @param entity An instance of {@link DumbBlockEntities.Entities} that represents this. Must not be null.
     * @param properties An instance of {@link Properties} that represents the properties of the block. Must not be null.
     */
    protected DumbEntityBlock(DumbBlockEntities.Entities entity, Properties properties) {
        super(properties);
        this.entity = entity;
    }

    /**
     * Returns the render shape of the block.
     *
     * @param pState An instance of {@link BlockState} that represents the state of the block. Must not be null.
     * @return the render shape of the block.
     */
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    /**
     * Creates a new block entity at the given position with the given state.
     *
     * @param pPos An instance of {@link BlockPos} that represents the position of the block. Must not be null.
     * @param pState An instance of {@link BlockState} that represents the state of the block. Must not be null.
     * @return a new instance of {@link BlockEntity} at the given position with the given state.
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return entity.getRegistry().blockEntityType().get().create(pPos, pState);
    }
}