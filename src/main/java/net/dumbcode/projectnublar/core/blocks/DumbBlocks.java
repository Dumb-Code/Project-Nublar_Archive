package net.dumbcode.projectnublar.core.blocks;

import net.dumbcode.projectnublar.core.blocks.elements.SoundBlock;
import net.dumbcode.projectnublar.core.blocks.elements.TestBlock;
import net.dumbcode.projectnublar.core.blocks.elements.TestOreBlock;
import net.dumbcode.projectnublar.core.data.ModRecipeProvider;
import net.dumbcode.projectnublar.core.data.loot.ModBlockLootTables;
import net.dumbcode.projectnublar.core.exceptions.UtilityClassException;
import net.dumbcode.projectnublar.core.items.DumbItems;
import net.dumbcode.projectnublar.core.registry.Registrar;
import net.dumbcode.projectnublar.core.tags.DumbTags;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class DumbBlocks {

    public enum Blocks {
        TEST_BLOCK(
            TestBlock.class,
            Tags.of(
                null,
                List.of(BlockTags.MINEABLE_WITH_PICKAXE, net.minecraftforge.common.Tags.Blocks.NEEDS_NETHERITE_TOOL)
            ),
            ModBlockLootTables.Builder::dropSelf,
            recipe -> recipe.nineBlockStorage(true, RecipeCategory.BUILDING_BLOCKS, DumbItems.Items.TEST_ITEM.getRegistry().item().get(), MOD_ID),
            recipe -> recipe.oreSmelting(RecipeCategory.BUILDING_BLOCKS, List.of(Blocks.valueOf("TEST_ORE_BLOCK").getRegistry().item().get()), 1F, 200),
            recipe -> recipe.oreBlasting(RecipeCategory.BUILDING_BLOCKS, List.of(Blocks.valueOf("TEST_ORE_BLOCK").getRegistry().item().get()), 1F, 200)
        ),
        TEST_ORE_BLOCK(
            TestOreBlock.class,
            Tags.of(
                List.of(DumbTags.Blocks.OVERWORLD_ORES),
                List.of(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE)
            ),
            lootTable -> lootTable.createOreDrop(DumbItems.Items.TEST_ITEM.getRegistry().item().get())
        ),
        SOUND_BLOCK(
            SoundBlock.class,
            Tags.of(
                null,
                List.of(BlockTags.MINEABLE_WITH_PICKAXE)
            ),
            ModBlockLootTables.Builder::dropSelf
        );

        private final Class<? extends IDumbBlock> blockClass;
        private final Registry registry = Registry.of(
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.BLOCKS.getRegistryKey(), MOD_ID),
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.ITEMS.getRegistryKey(), MOD_ID)
        );
        private final Tags tags;
        private final Consumer<ModBlockLootTables.Builder> lootTableBuilder;
        private final List<UnaryOperator<ModRecipeProvider.Builder>> recipeBuilders;

        public static @Unmodifiable @NotNull List<Class<? extends IDumbBlock>> classes() {
            return Arrays.stream(values()).map(Blocks::getBlockClass).collect(Collectors.toUnmodifiableList());
        }

        public static @Unmodifiable @NotNull List<RegistryObject<Block>> registryBlocks() {
            return Arrays.stream(values()).map(x -> x.registry.block).toList();
        }

        public static @Unmodifiable @NotNull List<RegistryObject<Item>> registryItems() {
            return Arrays.stream(values()).map(x -> x.registry.item).toList();
        }

        Blocks(Class<? extends IDumbBlock> blockClass) {
            this.blockClass = blockClass;
            this.tags = Tags.of();
            this.lootTableBuilder = (builder -> {});
            this.recipeBuilders = List.of();
        }

        @SafeVarargs
        Blocks(Class<? extends IDumbBlock> blockClass, Consumer<ModBlockLootTables.Builder> lootTableBuilder, UnaryOperator<ModRecipeProvider.Builder> ... builders) {
            this.blockClass = blockClass;
            this.tags = Tags.of();
            this.lootTableBuilder = lootTableBuilder;
            this.recipeBuilders = Arrays.asList(builders);
        }

        Blocks(Class<? extends IDumbBlock> blockClass, @NotNull Tags tags) {
            this.blockClass = blockClass;
            this.tags = tags;
            this.lootTableBuilder = (builder -> {});
            this.recipeBuilders = List.of();
        }

        @SafeVarargs
        Blocks(Class<? extends IDumbBlock> blockClass, @NotNull Tags tags, Consumer<ModBlockLootTables.Builder> lootTableBuilder, UnaryOperator<ModRecipeProvider.Builder> ... builders) {
            this.blockClass = blockClass;
            this.tags = tags;
            this.lootTableBuilder = lootTableBuilder;
            this.recipeBuilders = Arrays.asList(builders);
        }

        public @NotNull Registry getRegistry() {
            return registry;
        }

        public @NotNull Tags getTags() {
            return tags;
        }

        public Class<? extends IDumbBlock> getBlockClass() {
            return this.blockClass;
        }

        public @NotNull List<UnaryOperator<ModRecipeProvider.Builder>> getRecipeBuilders() {
            return this.recipeBuilders;
        }

        public Consumer<ModBlockLootTables.Builder> getLootTableBuilder() {
            return this.lootTableBuilder;
        }

        public @NotNull String getRegisterName() {
            return this.name().toLowerCase();
        }
    }

    public record Tags(
        @NotNull List<DumbTags.Blocks> dumbBlockTags, @NotNull List<TagKey<Block>> blockTags, @NotNull List<DumbTags.Items> dumbItemTags, @NotNull List<TagKey<Item>> itemTags) {
        private static @NotNull Tags of(
            @Nullable List<DumbTags.Blocks> dumbBlockTags,
            @Nullable List<TagKey<Block>> blockTags,
            @Nullable List<DumbTags.Items> dumbItemTags,
            @NotNull List<TagKey<Item>> itemTags
        ) {
            return new Tags(
                dumbBlockTags == null ? List.of() : dumbBlockTags,
                blockTags == null ? List.of() : blockTags,
                dumbItemTags == null ? List.of() : dumbItemTags,
                itemTags
            );
        }

        private static @NotNull Tags of(
            @Nullable List<DumbTags.Blocks> dumbBlockTags,
            @Nullable List<TagKey<Block>> blockTags,
            @NotNull List<DumbTags.Items> dumbItemTags
        ) {
            return new Tags(
                dumbBlockTags == null ? List.of() : dumbBlockTags,
                blockTags == null ? List.of() : blockTags,
                dumbItemTags,
                List.of()
            );
        }

        private static @NotNull Tags of(
            @Nullable List<DumbTags.Blocks> dumbBlockTags,
            @NotNull List<TagKey<Block>> blockTags
        ) {
            return new Tags(
                dumbBlockTags == null ? List.of() : dumbBlockTags,
                blockTags,
                List.of(),
                List.of()
            );
        }

        private static @NotNull Tags of(
            @NotNull List<DumbTags.Blocks> dumbBlockTags
        ) {
            return new Tags(
                dumbBlockTags,
                List.of(),
                List.of(),
                List.of()
            );
        }

        private static @NotNull Tags of() {
            return new Tags(List.of(), List.of(), List.of(), List.of());
        }

    }

    public record Registry(
        @NotNull RegistryObject<Block> block, @NotNull RegistryObject<Item> item
    ) {
        public static @NotNull Registry of(@NotNull RegistryObject<Block> block, @NotNull RegistryObject<Item> item) {
            return new Registry(block, item);
        }
    }

    private DumbBlocks() {
        throw new UtilityClassException();
    }
}
