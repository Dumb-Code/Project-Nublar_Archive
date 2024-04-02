package net.dumbcode.projectnublar.core.mobs.elements;

import net.dumbcode.projectnublar.core.mobs.DumbAnimal;
import net.dumbcode.projectnublar.core.mobs.DumbEntity;
import net.dumbcode.projectnublar.core.mobs.DumbMobs;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public abstract class DinosaurEntity extends DumbAnimal {

    public static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(DinosaurEntity.class, EntityDataSerializers.INT);

    public DinosaurEntity(EntityType<? extends Entity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        // we're getting the value and then adding to it the amount of sub parts
        // so the ID of the entity is the x, and after this code runs it will be x + partsSize + 1
        // but since we're getting and then adding, we should add 1 to the main entity id ourselves
        this.setId(ENTITY_COUNTER.getAndAdd(getHitboxParts().size() + 1) + 1);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        // This is our ECS
        this.getEntityData().define(AGE, 1);
    }

    @Override
    public void setId(int pId) {
        super.setId(pId);
        List<DinosaurEntityPart> parts = getHitboxParts();
        for(int i = 0; i < parts.size(); i++) {
            parts.get(i).setId(pId + i + 1);
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return ageableMob;
    }

    // this method should always return same objects, but the holder can be different.
    public abstract List<DinosaurEntityPart> getHitboxParts();

    public boolean hurt(DinosaurEntityPart part, DamageSource source, float amount) {
        return true;
    }
}
