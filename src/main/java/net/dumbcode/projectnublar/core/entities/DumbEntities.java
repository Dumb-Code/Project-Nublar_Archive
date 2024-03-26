package net.dumbcode.projectnublar.core.entities;

import net.dumbcode.projectnublar.core.entities.elements.DinosaurEntity;
import net.dumbcode.projectnublar.core.exceptions.UtilityClassException;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.NotNull;

public final class DumbEntities {
    public enum Entities {
        DINOSAUR(EntityType.Builder.of(DinosaurEntity::new, MobCategory.CREATURE));

        private final EntityType<? extends Entity> nativeType;
        Entities(EntityType.@NotNull Builder<? extends Entity> of) {
            this.nativeType = of.build(this.name().toLowerCase());
        }

        @SuppressWarnings("unchecked")
        public <T extends Entity> EntityType<T> getNativeType() {
            return (EntityType<T>) nativeType;
        }
    }

    private DumbEntities() {
        throw new UtilityClassException();
    }
}
