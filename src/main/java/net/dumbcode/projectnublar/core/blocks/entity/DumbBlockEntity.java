package net.dumbcode.projectnublar.core.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * It represents a block entity that can be animated via GeckoLib.
 */
public abstract class DumbBlockEntity extends BlockEntity implements GeoBlockEntity {

    /**
     * An instance of {@link AnimatableInstanceCache} that represents the cache of animatable instances.
     * This cache is used to store the animation data for the block entity.
     */
    protected final AnimatableInstanceCache instanceCache = GeckoLibUtil.createInstanceCache(this);

    /**
     * Constructor for the {@link DumbBlockEntity} class.
     *
     * @param entity An instance of {@link DumbBlockEntities.Entities} that represents the entity associated with the block entity. Must not be null.
     * @param pPos An instance of {@link BlockPos} that represents the position of the block entity. Must not be null.
     * @param pBlockState An instance of {@link BlockState} that represents the state of the block entity. Must not be null.
     */
    protected DumbBlockEntity(DumbBlockEntities.Entities entity, BlockPos pPos, BlockState pBlockState) {
        super(entity.getRegistry().blockEntityType().get(), pPos, pBlockState);
    }

    /**
     * Returns the cache of animatable instances.
     *
     * @return the cache of animatable instances.
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return instanceCache;
    }
}
