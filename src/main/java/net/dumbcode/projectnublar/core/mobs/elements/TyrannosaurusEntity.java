package net.dumbcode.projectnublar.core.mobs.elements;

import net.dumbcode.projectnublar.core.mobs.DumbMobs;
import net.dumbcode.projectnublar.core.mobs.DumbPathfinderMob;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TyrannosaurusEntity extends DumbPathfinderMob {
    protected AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

    @SuppressWarnings("unchecked")
    public TyrannosaurusEntity(EntityType<? extends Entity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static class Renderer extends GeoEntityRenderer<DumbPathfinderMob> {
        public Renderer(DumbMobs.@NotNull Mobs mob, EntityRendererProvider.Context renderManager) {
            super(renderManager, mob.getModel());
        }
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }
}
