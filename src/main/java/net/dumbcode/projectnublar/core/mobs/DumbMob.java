package net.dumbcode.projectnublar.core.mobs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

public abstract class DumbMob extends Mob implements GeoEntity, DumbEntity {
    protected DumbMob(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
}
