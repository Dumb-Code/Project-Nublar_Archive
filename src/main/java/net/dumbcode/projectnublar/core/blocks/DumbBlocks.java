package net.dumbcode.projectnublar.core.blocks;

import net.dumbcode.projectnublar.core.blocks.elements.TestBlock;
import net.dumbcode.projectnublar.core.exceptions.UtilityClassException;
import net.dumbcode.projectnublar.core.registry.Registrar;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class DumbBlocks {

    public enum Blocks {
         TEST_BLOCK(TestBlock.class);

        private final Class<? extends DumbBlock> blockClass;
        private final RegistryObject<Block> registryBlock = RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.BLOCKS.getRegistryKey(), MOD_ID);
        private final RegistryObject<Item> registryItem = RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.ITEMS.getRegistryKey(), MOD_ID);

        public static @Unmodifiable @NotNull List<Class<? extends DumbBlock>> classes() {
            return Arrays.stream(values()).map(Blocks::getBlockClass).collect(Collectors.toUnmodifiableList());
        }

        public static @Unmodifiable @NotNull List<RegistryObject<Block>> registryBlocks() {
            return Arrays.stream(values()).map(x -> x.registryBlock).toList();
        }

        public static @Unmodifiable @NotNull List<RegistryObject<Item>> registryItems() {
            return Arrays.stream(values()).map(x -> x.registryItem).toList();
        }

        Blocks(Class<? extends DumbBlock> blockClass) {
            this.blockClass = blockClass;
        }

        public @NotNull Block get() {
            return registryBlock.get();
        }

        public @NotNull Item getItem() {
            return registryItem.get();
        }

        public Class<? extends DumbBlock> getBlockClass() {
            return this.blockClass;
        }

        public @NotNull String getRegisterName() {
            return this.name().toLowerCase();
        }
    }

    private DumbBlocks() {
        throw new UtilityClassException();
    }
}
