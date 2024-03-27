package net.dumbcode.projectnublar.core.blocks.entity;

import net.dumbcode.projectnublar.core.blocks.entity.elements.TestBlockEntity;
import net.dumbcode.projectnublar.core.registry.Registrar;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class DumbBlockEntities {
    public enum Entities {
        TEST_BLOCK(
            TestBlockEntity::new,
            TestBlockEntity.Renderer::new
        );

        private final BlockEntityType.BlockEntitySupplier<DumbBlockEntity> blockEntityConstructor;
        private final BlockEntityRendererProvider<DumbBlockEntity> renderer;
        private final Registry registry = Registry.of(
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.BLOCK_ENTITY_TYPES.getRegistryKey(), MOD_ID)
        );
        private final DefaultedBlockGeoModel<DumbBlockEntity> model = new DefaultedBlockGeoModel<>(new ResourceLocation(MOD_ID, getRegisterName()));

        Entities(BlockEntityType.BlockEntitySupplier<DumbBlockEntity> blockEntityConstructor, BlockEntityRendererProvider<DumbBlockEntity> renderer) {
            this.blockEntityConstructor = blockEntityConstructor;
            this.renderer = renderer;
        }

        public Registry getRegistry() {
            return this.registry;
        }

        public DefaultedBlockGeoModel<DumbBlockEntity> getModel() {
            return this.model;
        }

        public BlockEntityRendererProvider<DumbBlockEntity> getRenderer() {
            return this.renderer;
        }

        public BlockEntityType.BlockEntitySupplier<DumbBlockEntity> getBlockEntityConstructor() {
            return this.blockEntityConstructor;
        }

        public @NotNull String getRegisterName() {
            return this.name().toLowerCase();
        }
    }

    public record Registry(RegistryObject<BlockEntityType<DumbBlockEntity>> blockEntityType) {
        @Contract("_ -> new")
        public static @NotNull Registry of(RegistryObject<BlockEntityType<DumbBlockEntity>> blockEntityType) {
            return new Registry(blockEntityType);
        }
    }
}
