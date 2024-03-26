package net.dumbcode.projectnublar.core.entities;

import net.dumbcode.projectnublar.core.entities.elements.DinosaurEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public enum DumbEntities {
    DINOSAUR(EntityType.Builder.of(DinosaurEntity::new, MobCategory.CREATURE));

    private final EntityType<?> nativeType;
    DumbEntities(EntityType.Builder<?> of) {
        this.nativeType = of.build(this.name().toLowerCase());
    }

    public EntityType<?> getNativeType() {
        return nativeType;
    }
}
