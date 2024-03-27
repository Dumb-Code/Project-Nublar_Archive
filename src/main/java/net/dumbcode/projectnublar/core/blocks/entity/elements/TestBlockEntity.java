package net.dumbcode.projectnublar.core.blocks.entity.elements;

import net.dumbcode.projectnublar.core.blocks.entity.DumbBlockEntities;
import net.dumbcode.projectnublar.core.blocks.entity.DumbBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TestBlockEntity extends DumbBlockEntity {
    private static final DumbBlockEntities.Entities ENTITY = DumbBlockEntities.Entities.TEST_BLOCK;

    public static class Renderer extends GeoBlockRenderer<DumbBlockEntity> {
        public Renderer(BlockEntityRendererProvider.Context context) {
            super(ENTITY.getModel());
        }
    }

    public TestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ENTITY, pPos, pBlockState);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }
}
