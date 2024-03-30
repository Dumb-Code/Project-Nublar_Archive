package net.dumbcode.projectnublar.core.mobs;

import net.dumbcode.projectnublar.core.exceptions.UtilityClassException;
import net.dumbcode.projectnublar.core.mobs.elements.DinosaurEntity;
import net.dumbcode.projectnublar.core.mobs.elements.TyrannosaurusEntity;
import net.dumbcode.projectnublar.core.registry.Registrar;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public final class DumbMobs {
    public enum Mobs {
        TYRANNOSAURUS(
            TyrannosaurusEntity::new,
            TyrannosaurusEntity.Renderer::new,
            builder -> builder
                .canSpawnFarFromPlayer()
                .fireImmune()
                .sized(0.6F, 1.8F),
            attributes -> attributes
                .add(Attributes.MAX_HEALTH, 40),
            metadata -> metadata
                .category(MobCategory.CREATURE)
                .turnsHead(false)
        );

        private final EntityType<?> entityType;
        private final EntityRendererProvider<? extends Entity> renderer;
        private final AttributeSupplier attributeSupplier;
        private final Registry registry = Registry.of(
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.ENTITY_TYPES.getRegistryKey(), MOD_ID)
        );
        private final DefaultedEntityGeoModel<DumbEntity> model;
        private final Metadata metadata;
        @SuppressWarnings("unchecked")
        Mobs(EntityType.EntityFactory<?> constructor, BiFunction<Mobs, EntityRendererProvider.Context, ? extends GeoEntityRenderer<?>> rendererConstructor, UnaryOperator<EntityType.Builder<?>> builder, UnaryOperator<AttributeSupplier.Builder> attributes, @NotNull UnaryOperator<Metadata.Builder> metadata) {
            this.metadata = metadata.apply(new Metadata.Builder()).build();
            EntityType.Builder<?> localBuilder = EntityType.Builder.of(constructor, this.metadata.category);
            localBuilder = builder.apply(localBuilder);
            this.entityType = localBuilder.build(this.name().toLowerCase());
            this.attributeSupplier = attributes.apply(LivingEntity.createLivingAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 4.0D)).build();
            this.renderer = context -> (EntityRenderer<Entity>) rendererConstructor.apply(this, context);
            this.model = new DefaultedEntityGeoModel<>(new ResourceLocation(MOD_ID, getRegisterName()), this.metadata.turnsHead);

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

        @SuppressWarnings("unchecked")
        public <T extends DumbEntity> DefaultedEntityGeoModel<T> getModel() {
            return (DefaultedEntityGeoModel<T>) model;
        }

        @SuppressWarnings("unchecked")
        public <T extends Entity> EntityRendererProvider<T> getRenderer() {
            return (EntityRendererProvider<T>) renderer;
        }

        public AttributeSupplier getAttributeSupplier() {
            return attributeSupplier;
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

    public record Metadata(MobCategory category, boolean turnsHead) {
        public static class Builder {
            private MobCategory category;
            private boolean turnsHead = false;

            public Builder category(MobCategory category) {
                this.category = category;
                return this;
            }

            public Builder turnsHead(boolean turnsHead) {
                this.turnsHead = turnsHead;
                return this;
            }

            public Metadata build() {
                return new Metadata(category, turnsHead);
            }
        }
    }

    private DumbMobs() {
        throw new UtilityClassException();
    }
}
