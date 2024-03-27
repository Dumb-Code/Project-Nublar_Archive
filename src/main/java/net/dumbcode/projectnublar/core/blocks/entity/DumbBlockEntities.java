package net.dumbcode.projectnublar.core.blocks.entity;

import net.dumbcode.projectnublar.ProjectNublar;
import net.dumbcode.projectnublar.core.blocks.entity.elements.TestBlockEntity;
import net.dumbcode.projectnublar.core.registry.Registrar;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.function.BiFunction;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

/**
 * This class represents the block entities in the mod {@link ProjectNublar}
 * It contains an enumeration of the different types of entities and a record for the registry.
 * Each entity type has its own block entity constructor, renderer, registry, and model.
 * The registry record is used to refer to the block entity type in the registry.
 */
public class DumbBlockEntities {
    /**
     * Each enum constant represents a different type of block entity.
     * Each enum constant has its own block entity constructor, renderer, registry, and model.
     * The block entity constructor is a function that creates a new instance of the block entity.
     * The renderer is a function that creates a new instance of the block entity renderer.
     * The registry is a record that contains a reference to the block entity type in the registry.
     * The model is a defaulted block geo model that represents the 3D model of the block entity.
     */
    public enum Entities {
        /**
         * TEST_BLOCK is an enum constant of the {@link Entities} enum.
         * It is used to showcase how to create a block entity with a custom renderer.
         * <p>
         * The first argument, {@code TestBlockEntity::new}, is a method reference to the constructor of {@link TestBlockEntity}.
         * This serves as the block entity constructor for TEST_BLOCK, which creates a new instance of {@link TestBlockEntity}.
         * <p>
         * The second argument, {@code TestBlockEntity.Renderer::new}, is a method reference to the constructor of the {@link TestBlockEntity.Renderer} class embedded in {@link TestBlockEntity}.
         * You should follow this pattern when creating a custom renderer for a block entity.
         * This serves as the renderer for TEST_BLOCK, which creates a new instance of the block entity renderer.
         */
        TEST_BLOCK(
            TestBlockEntity::new,
            TestBlockEntity.Renderer::new
        );

        /**
         * Supplier function that creates a new instance of a {@link DumbBlockEntity}.
         * It is a functional interface that takes a {@link BlockPos} and a {@link BlockState} as arguments and returns a new {@link DumbBlockEntity}.
         */
        private final BlockEntityType.BlockEntitySupplier<DumbBlockEntity> blockEntityConstructor;

        /**
         * The renderer is a provider function that creates a new instance of a {@link BlockEntityRenderer} for a {@link DumbBlockEntity}.
         * It is a functional interface that takes a {@link BlockEntityRendererProvider.Context} as an argument and returns a new {@link BlockEntityRenderer}.
         */
        private final BlockEntityRendererProvider<DumbBlockEntity> renderer;

        /**
         * The registry is an instance of the {@link Registry} record, created using the static method {@link Registry#of(RegistryObject)}
         * It contains a {@link RegistryObject} that represents the block entity type in the registry.
         * The {@link RegistryObject} is created using the static method {@link RegistryObject#create(ResourceLocation, ResourceKey, String)}.
         */
        private final Registry registry = Registry.of(
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.BLOCK_ENTITY_TYPES.getRegistryKey(), MOD_ID)
        );

        /**
         * The model is an instance of the {@link DefaultedBlockGeoModel} class, created using its constructor.
         * It represents the 3D model of the block entity.
         * The {@link DefaultedBlockGeoModel} is created using a {@link ResourceLocation} that represents the location of the model in the mod's resources.
         */
        private final DefaultedBlockGeoModel<DumbBlockEntity> model = new DefaultedBlockGeoModel<>(new ResourceLocation(MOD_ID, getRegisterName()));

        /**
         * This is the constructor for the {@link Entities} enum.
         * It takes two arguments: a blockEntityConstructor and a renderer.
         *
         * @param blockEntityConstructor A {@link TriFunction} that takes an {@link Entities} enum constant, a {@link BlockPos}, and a {@link BlockState} as arguments, and returns a new instance of a {@link DumbBlockEntity}.
         * @param renderer A {@link BiFunction} that takes an {@link Entities} enum constant and a {@link BlockEntityRendererProvider.Context} as arguments, and returns a new instance of a {@link GeoBlockRenderer} for a {@link DumbBlockEntity}.
         *
         * The blockEntityConstructor is stored as a lambda function that takes a {@link BlockPos} and a {@link BlockState} as arguments (a {@link BlockEntityType.BlockEntitySupplier}).
         * The renderer is stored as a lambda function that takes a {@link BlockEntityRendererProvider.Context} as an argument (a {@link BlockEntityRendererProvider}).
         */
        Entities(TriFunction<Entities, BlockPos, BlockState, DumbBlockEntity> blockEntityConstructor, BiFunction<Entities, BlockEntityRendererProvider.Context, ? extends GeoBlockRenderer<DumbBlockEntity>> renderer) {
            this.blockEntityConstructor = (pos, state) -> blockEntityConstructor.apply(this, pos, state);
            this.renderer = context -> renderer.apply(this, context);
        }

        /**
         * Returns the registry of the block entity type.
         * The registry is an instance of the {@link Registry} record, which contains a {@link RegistryObject} that represents the block entity type in the registry.
         *
         * @return the registry of the block entity type
         */
        public Registry getRegistry() {
            return this.registry;
        }

        /**
         * Returns the 3D model of the block entity.
         * The model is an instance of the {@link DefaultedBlockGeoModel} class, which represents the 3D model of the block entity.
         *
         * @return the 3D model of the block entity
         */
        public DefaultedBlockGeoModel<DumbBlockEntity> getModel() {
            return this.model;
        }

        /**
         * Returns the renderer of the block entity.
         * The renderer is a provider function that creates a new instance of a {@link BlockEntityRenderer} for a {@link DumbBlockEntity}.
         *
         * @return the renderer of the block entity
         */
        public BlockEntityRendererProvider<DumbBlockEntity> getRenderer() {
            return this.renderer;
        }

        /**
         * Returns the block entity constructor of the block entity.
         * The block entity constructor is a supplier function that creates a new instance of a {@link DumbBlockEntity}.
         *
         * @return the block entity constructor of the block entity
         */
        public BlockEntityType.BlockEntitySupplier<DumbBlockEntity> getBlockEntityConstructor() {
            return this.blockEntityConstructor;
        }

        /**
         * Returns the name of the enum constant, converted to lower case.
         * This is used as the register name for the block entity type in the registry.
         *
         * @return the register name of the block entity type
         */
        public @NotNull String getRegisterName() {
            return this.name().toLowerCase();
        }
    }

    /**
     * Holds a single field, {@link #blockEntityType()}, which is a {@link RegistryObject} of {@link BlockEntityType} for {@link DumbBlockEntity}.
     * This record is used to encapsulate the blockEntityType as a record component.
     *
     * @param blockEntityType A {@link RegistryObject} that represents the block entity type in the registry.
     */
    public record Registry(RegistryObject<BlockEntityType<DumbBlockEntity>> blockEntityType) {

        /**
         * This is a static factory method for creating a new instance of the {@link Registry} record.
         * It takes a {@link RegistryObject} of {@link BlockEntityType} for {@link DumbBlockEntity} as an argument and returns a new {@link Registry} record with the given blockEntityType.
         *
         * @param blockEntityType A {@link RegistryObject} that represents the block entity type in the registry.
         * @return a new {@link Registry} record with the given blockEntityType.
         */
        @Contract("_ -> new")
        public static @NotNull Registry of(RegistryObject<BlockEntityType<DumbBlockEntity>> blockEntityType) {
            return new Registry(blockEntityType);
        }
    }
}
