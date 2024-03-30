package net.dumbcode.projectnublar.core.mobs;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

public abstract class DumbPathfinderMob extends PathfinderMob implements GeoEntity, DumbEntity {
    @SuppressWarnings("unchecked")
    protected DumbPathfinderMob(EntityType<? extends Entity> pEntityType, Level pLevel) {
        super((EntityType<? extends PathfinderMob>) pEntityType, pLevel);
    }
}
