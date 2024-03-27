package net.dumbcode.projectnublar.core.blocks.entity.elements;

import net.dumbcode.projectnublar.core.blocks.entity.DumbBlockEntities;
import net.dumbcode.projectnublar.core.blocks.entity.DumbBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TestBlockEntity extends DumbBlockEntity {
    public static class Renderer extends GeoBlockRenderer<DumbBlockEntity> {
        public Renderer(DumbBlockEntities.@NotNull Entities entity, BlockEntityRendererProvider.Context context) {
            super(entity.getModel());
        }
    }

    public TestBlockEntity(DumbBlockEntities.Entities entity, BlockPos pPos, BlockState pBlockState) {
        super(entity, pPos, pBlockState);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }
}
