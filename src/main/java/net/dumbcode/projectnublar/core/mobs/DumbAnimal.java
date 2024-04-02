package net.dumbcode.projectnublar.core.mobs;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

public abstract class DumbAnimal extends Animal implements DumbEntity {
    @SuppressWarnings("unchecked")
    protected DumbAnimal(EntityType<? extends Entity> pEntityType, Level pLevel) {
        super((EntityType<? extends Animal>) pEntityType, pLevel);
    }
}
