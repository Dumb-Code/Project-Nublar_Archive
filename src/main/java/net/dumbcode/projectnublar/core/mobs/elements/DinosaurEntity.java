package net.dumbcode.projectnublar.core.mobs.elements;

import net.dumbcode.projectnublar.core.mobs.DumbAnimal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DinosaurEntity extends DumbAnimal {
    protected AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

    public DinosaurEntity(EntityType<? extends Entity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return ageableMob;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Head", 5, this::headAnimationController));
    }

    private PlayState headAnimationController(AnimationState<DinosaurEntity> entity) {
        // if entity is attacking, play specific animation by using
        // event.setAndContinue(animation)
        return PlayState.STOP;
    }
}
