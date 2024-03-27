package net.dumbcode.projectnublar.core.mobs;

import net.dumbcode.projectnublar.core.exceptions.UtilityClassException;
import net.dumbcode.projectnublar.core.mobs.elements.DinosaurMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.NotNull;

public final class DumbMobs {
    public enum Mobs {
        DINOSAUR(EntityType.Builder.of(DinosaurMob::new, MobCategory.CREATURE));

        private final EntityType<? extends Entity> nativeType;
        Mobs(EntityType.@NotNull Builder<? extends Entity> of) {
            this.nativeType = of.build(this.name().toLowerCase());
        }

        @SuppressWarnings("unchecked")
        public <T extends Entity> EntityType<T> getNativeType() {
            return (EntityType<T>) nativeType;
        }
    }

    private DumbMobs() {
        throw new UtilityClassException();
    }
}
