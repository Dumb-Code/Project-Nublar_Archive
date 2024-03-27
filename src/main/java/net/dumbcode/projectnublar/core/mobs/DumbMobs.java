package net.dumbcode.projectnublar.core.mobs;

import net.dumbcode.projectnublar.core.exceptions.UtilityClassException;
import net.dumbcode.projectnublar.core.mobs.elements.DinosaurEntity;
import net.dumbcode.projectnublar.core.registry.Registrar;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public final class DumbMobs {
    public enum Mobs {
        DINOSAUR(
            DinosaurEntity::new,
            metadata -> metadata.category(MobCategory.CREATURE)
        );

        private final EntityType<? extends Entity> entityType;
        private final Registry registry = Registry.of(
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.ENTITY_TYPES.getRegistryKey(), MOD_ID)
        );
        private final Metadata metadata;
        Mobs(EntityType.EntityFactory<?> constructor, @NotNull UnaryOperator<Metadata.Builder> metadata) {
            this.metadata = metadata.apply(new Metadata.Builder()).build();
            this.entityType = EntityType.Builder.of(constructor, this.metadata.category).build(this.name().toLowerCase());
        }

        @SuppressWarnings("unchecked")
        public <T extends Entity> EntityType<T> getEntityType() {
            return (EntityType<T>) entityType;
        }

        public Registry getRegistry() {
            return registry;
        }

        public Metadata getMetadata() {
            return metadata;
        }

        public @NotNull String getRegisterName() {
            return this.name().toLowerCase();
        }
    }

    public record Registry(RegistryObject<EntityType<? extends Entity>> entityType) {
        public static @NotNull Registry of(RegistryObject<EntityType<? extends Entity>> entityType) {
            return new Registry(entityType);
        }
    }

    public record Metadata(MobCategory category) {
        public static class Builder {
            private MobCategory category;

            public Builder category(MobCategory category) {
                this.category = category;
                return this;
            }

            public Metadata build() {
                return new Metadata(category);
            }
        }
    }

    private DumbMobs() {
        throw new UtilityClassException();
    }
}
