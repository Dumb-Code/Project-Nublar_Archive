package net.dumbcode.projectnublar.core.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class DumbBlockEntity extends BlockEntity implements GeoBlockEntity {
    protected final AnimatableInstanceCache instanceCache = GeckoLibUtil.createInstanceCache(this);

    protected DumbBlockEntity(DumbBlockEntities.Entities entity, BlockPos pPos, BlockState pBlockState) {
        super(entity.getRegistry().blockEntityType().get(), pPos, pBlockState);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return instanceCache;
    }
}
