package net.dumbcode.projectnublar.core.blocks;

import net.dumbcode.projectnublar.core.blocks.elements.SoundBlock;
import net.dumbcode.projectnublar.core.blocks.elements.TestBlock;
import net.dumbcode.projectnublar.core.blocks.elements.TestOreBlock;
import net.dumbcode.projectnublar.core.blocks.entity.DumbBlockEntities;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public final class DumbBlocks {

    public enum Blocks {
        TEST_BLOCK(
            TestBlock::new,
            metadata -> metadata
                .tags(tags -> tags.blocks(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL))
                .lootTable(ModBlockLootTables.Builder::dropSelf)
                .recipe(recipe -> recipe.nineBlockStorage(
                    true,
                    RecipeCategory.BUILDING_BLOCKS,
                    DumbItems.Items.TEST_ITEM.getRegistry().item().get(),
                    MOD_ID
                ))
                .associatedEntity(DumbBlockEntities.Entities.TEST_BLOCK)
        ),
        TEST_ORE_BLOCK(
            TestOreBlock::new,
            metadata -> metadata
                .tags(tags -> tags
                    .dumbBlocks(DumbTags.Blocks.OVERWORLD_ORES)
                    .blocks(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
                )
                .lootTable(loot -> loot.createOreDrop(DumbItems.Items.TEST_ITEM.getRegistry().item().get()))
        ),
        SOUND_BLOCK(
            SoundBlock::new,
            metadata -> metadata
                .tags(tags -> tags.blocks(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL))
                .lootTable(ModBlockLootTables.Builder::dropSelf)
        );

        private final Supplier<IDumbBlock> blockConstructor;
        private final Registry registry = Registry.of(
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.BLOCKS.getRegistryKey(), MOD_ID),
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.ITEMS.getRegistryKey(), MOD_ID)
        );
        private final Metadata metadata;

        public static @Unmodifiable @NotNull List<RegistryObject<Block>> registryBlocks() {
            return Arrays.stream(values()).map(x -> x.registry.block).toList();
        }

        public static @Unmodifiable @NotNull List<RegistryObject<Item>> registryItems() {
            return Arrays.stream(values()).map(x -> x.registry.item).toList();
        }

        Blocks(Supplier<IDumbBlock> blockConstructor, @NotNull UnaryOperator<Metadata.Builder> metadata) {
            this.blockConstructor = blockConstructor;
            this.metadata = metadata.apply(new Metadata.Builder()).build();
        }

        Blocks(Function<DumbBlockEntities.Entities, IDumbBlock> blockConstructor, @NotNull UnaryOperator<Metadata.Builder> metadata) {
            this.metadata = metadata.apply(new Metadata.Builder()).build();
            this.blockConstructor = () -> blockConstructor.apply(this.metadata.associatedEntity);
        }

        public @NotNull Registry getRegistry() {
            return registry;
        }

        public @NotNull Metadata getMetadata() {
            return metadata;
        }

        public Supplier<IDumbBlock> getBlockConstructor() {
            return this.blockConstructor;
        }


        public @NotNull String getRegisterName() {
            return this.name().toLowerCase();
        }
    }

    public record Metadata(Tags tags, Function<ModBlockLootTables.Builder, LootTable.@Nullable Builder> lootTableBuilder, List<UnaryOperator<ModRecipeProvider.Builder>> recipeBuilders, @Nullable DumbBlockEntities.Entities associatedEntity) {
        public static class Builder {
            private Tags tags = Tags.of();
            private Function<ModBlockLootTables.Builder, LootTable.@Nullable Builder> lootTableBuilder;
            private List<UnaryOperator<ModRecipeProvider.Builder>> recipeBuilders;
            private @Nullable DumbBlockEntities.Entities associatedEntity = null;

            Builder() {
            }

            public Builder tags(@NotNull UnaryOperator<Tags.Builder> tags) {
                this.tags = tags.apply(new Tags.Builder()).build();
                return this;
            }

            public Builder lootTable(Function<ModBlockLootTables.Builder, LootTable.Builder> lootTable) {
                this.lootTableBuilder = lootTable;
                return this;
            }

            public Builder recipe(UnaryOperator<ModRecipeProvider.Builder> recipe) {
                if (this.recipeBuilders == null) {
                    this.recipeBuilders = new ArrayList<>();
                }
                this.recipeBuilders.add(recipe);
                return this;
            }

            public Builder recipe(UnaryOperator<ModRecipeProvider.Builder>... recipes) {
                if (this.recipeBuilders == null) {
                    this.recipeBuilders = Arrays.asList(recipes);
                } else {
                    this.recipeBuilders.addAll(Arrays.asList(recipes));
                }
                return this;
            }

            public Builder associatedEntity(DumbBlockEntities.Entities associatedEntity) {
                this.associatedEntity = associatedEntity;
                return this;
            }

            public Metadata build() {
                if (lootTableBuilder == null) {
                    throw new IllegalStateException("Loot table builder is not set");
                }
                return new Metadata(tags, lootTableBuilder, recipeBuilders == null ? List.of() : recipeBuilders, associatedEntity);
            }
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

        public static final class Builder {
            private final Tags tags;

            private Builder() {
                this.tags = Tags.of();
            }

            public @NotNull Builder dumbBlocks(@NotNull DumbTags.Blocks... dumbBlockTags) {
                return new Builder(
                    Tags.of(
                        Arrays.asList(dumbBlockTags),
                        tags.blockTags,
                        tags.dumbItemTags,
                        tags.itemTags
                    )
                );
            }

            @SafeVarargs
            public final @NotNull Builder blocks(@NotNull TagKey<Block>... blockTags) {
                return new Builder(
                    Tags.of(
                        tags.dumbBlockTags,
                        Arrays.asList(blockTags),
                        tags.dumbItemTags,
                        tags.itemTags
                    )
                );
            }

            public @NotNull Builder dumbItems(@NotNull DumbTags.Items... dumbItemTags) {
                return new Builder(
                    Tags.of(
                        tags.dumbBlockTags,
                        tags.blockTags,
                        Arrays.asList(dumbItemTags),
                        tags.itemTags
                    )
                );
            }

            public @NotNull Builder items(@NotNull TagKey<Item>... itemTags) {
                return new Builder(
                    Tags.of(
                        tags.dumbBlockTags,
                        tags.blockTags,
                        tags.dumbItemTags,
                        Arrays.asList(itemTags)
                    )
                );
            }

            public @NotNull Tags build() {
                return tags;
            }

            private Builder(@NotNull Tags tags) {
                this.tags = tags;
            }
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
