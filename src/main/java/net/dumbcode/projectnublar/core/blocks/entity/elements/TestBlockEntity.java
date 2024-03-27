package net.dumbcode.projectnublar.core.blocks.entity.elements;

import net.dumbcode.projectnublar.core.blocks.entity.DumbBlockEntities;
import net.dumbcode.projectnublar.core.blocks.entity.DumbBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

/**
 * This is a test block entity to showcase how creating one works.
 */
public class TestBlockEntity extends DumbBlockEntity {

    /**
     * This is a nested class {@link Renderer} that extends {@link GeoBlockRenderer}.
     * It is used to render the {@link TestBlockEntity} in the game.
     * It is advised you follow this pattern when creating a custom renderer for a block entity as
     * it ensures consistency throughout the project. Furthermore, it is highly unlikely that you will need to
     * use this renderer for other block entities.
     *
     * @param entity The {@link DumbBlockEntities.Entities} enum constant that represents the type of the block entity.
     * @param context The context for the block entity renderer provider.
     */
    public static class Renderer extends GeoBlockRenderer<DumbBlockEntity> {
        public Renderer(DumbBlockEntities.@NotNull Entities entity, BlockEntityRendererProvider.Context context) {
            super(entity.getModel());
        }
    }

    /**
     * This is the constructor for the {@link TestBlockEntity} class.
     * It takes three arguments: an {@link DumbBlockEntities.Entities} enum constant, a {@link BlockPos}, and a {@link BlockState}.
     *
     * @param entity The {@link DumbBlockEntities.Entities} enum constant that represents the type of the block entity.
     * @param pPos The position of the block entity in the world.
     * @param pBlockState The state of the block in the world.
     */
    public TestBlockEntity(DumbBlockEntities.Entities entity, BlockPos pPos, BlockState pBlockState) {
        super(entity, pPos, pBlockState);
    }

    /**
     * This method is used to register the animation controllers for the block entity.
     * It takes a {@link AnimatableManager.ControllerRegistrar} as an argument, which is used to register the animation controllers.
     *
     * @param controllers The registrar for the animation controllers.
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }
}