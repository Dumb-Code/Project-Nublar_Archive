package net.dumbcode.projectnublar.core.blocks;

import net.dumbcode.projectnublar.ProjectNublar;
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
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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
import java.util.stream.Stream;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

/**
 * Now this is where it gets spicy! <p>
 * Specifically, this utility class is primarily used to define all the blocks for this project. <p>
 * This also applies to other utility classes such as {@link DumbItems} and {@link DumbBlockEntities}. <p>
 * <p>
 * The enum {@link Blocks} is used to define all the blocks in the project. <p>
 * Each block defined has a constructor, which for its first argument, takes either takes a <p>
 * <p>
 * - {@link Supplier} of {@link IDumbBlock} (standalone block with no entity, block has constructor with no arguments)
 * <p>
 * - {@link Function} of {@link DumbBlockEntities.Entities} and {@link IDumbBlock} (block with entity, block has constructor with 1 argument as shown)
 * <p>
 * For blocks with entities, you must specify the associated entity using {@link Metadata.Builder#associatedEntity(DumbBlockEntities.Entities)}
 * in the metadata builder.
 *<p>
 * The enum key, for example {@link Blocks#TEST_BLOCK}, is taken as the register name for the block and corresponding, automatically created item. <p>
 * For example, the register name for {@link Blocks#TEST_BLOCK} is "test_block", or in full terms "projectnublar:test_block". <p>
 * Enum keys should **always** be in uppercase and separated by underscores. <p>
 * <p>
 * The second argument for the constructor is a {@link UnaryOperator} of {@link Metadata.Builder} which is used to define the properties of the block. <p>
 * You should return the builder directly and do not call {@link Metadata.Builder#build()} as it is called automatically. <p>
 * The {@link Metadata} is used to define: <p>
 *     - Tags for the block <p>
 *        ‎ ‎ ‎   - Dumb block tags (e.g. {@link DumbTags.Blocks#OVERWORLD_ORES}) <p>
 *        ‎ ‎ ‎   - Vanilla block tags (e.g. {@link BlockTags#MINEABLE_WITH_PICKAXE}) <p>
 *        ‎ ‎ ‎   - Dumb item tags (e.g. {@link DumbTags.Items#INGOTS}) <p>
 *        ‎ ‎ ‎   - Vanilla item tags (e.g. {@link BlockTags#NEEDS_IRON_TOOL}) <p>
 *     - Loot table for the block <p>
 *     - (Optionally) Recipe(s) for the block <p>
 *     - (Optionally) Associated entity for the block <p>
 * <p>
 * Take a look at the given examples to understand how to define blocks and their metadata, such as {@link Blocks#TEST_BLOCK} and {@link Blocks#SOUND_BLOCK}. <p>
 */
public final class DumbBlocks {

    /**
     * This enum represents the different types of custom blocks in the game, added by this mod {@link ProjectNublar}
     * Each block is defined with a constructor and metadata.
     * The constructor is a supplier or function that provides an instance of the block.
     * The metadata is a unary operator that configures the properties of the block.
     * Take a look at the documentation provided by {@link DumbBlocks} for more information.
     */
    public enum Blocks {
        /**
         * The TEST_BLOCK is a block that is mineable with a pickaxe and requires an iron tool.
         * It drops itself when mined and has an associated block entity.
         */
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
        /**
         * The TEST_ORE_BLOCK is an ore block that is mineable with a pickaxe and requires an iron tool.
         * It drops a test item when mined.
         */
        TEST_ORE_BLOCK(
            TestOreBlock::new,
            metadata -> metadata
                .tags(tags -> tags
                    .dumbBlocks(DumbTags.Blocks.OVERWORLD_ORES)
                    .blocks(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
                )
                .lootTable(loot -> loot.createOreDrop(DumbItems.Items.TEST_ITEM.getRegistry().item().get()))
        ),
        /**
         * The SOUND_BLOCK is a block that is mineable with a pickaxe and requires a diamond tool.
         * It drops itself when mined.
         */
        SOUND_BLOCK(
            SoundBlock::new,
            metadata -> metadata
                .tags(tags -> tags.blocks(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL))
                .lootTable(ModBlockLootTables.Builder::dropSelf)
        );

        /**
         * A {@link Supplier} that provides an instance of {@link IDumbBlock}.
         * This is used to construct the block when needed.
         */
        private final Supplier<IDumbBlock> blockConstructor;
        /**
         * A {@link Registry} object that holds the block and item {@link RegistryObject} for this block.
         * The block and item are registered with the same name, derived from the enum name with {@link Blocks#getRegisterName()}
         * Registration is handled by {@link Registrar}
         */
        private final Registry registry = Registry.of(
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.BLOCKS.getRegistryKey(), MOD_ID),
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.ITEMS.getRegistryKey(), MOD_ID)
        );
        /**
         * The metadata of the block.
         * This contains information about the block's tags, loot table, recipes, and associated entity.
         */
        private final Metadata metadata;

        /**
         * Returns a {@link Unmodifiable} {@link NotNull} {@link List} of all the {@link RegistryObject}s of the blocks in the registry
         * as defined by the {@link Blocks} enum.
         * This method uses {@link Arrays#stream(Object[])} to {@link Stream#map(Function)} each value to its {@link Registry#block()}
         *
         * @return a list of all the registry objects of the blocks in the registry.
         */
        public static @Unmodifiable @NotNull List<RegistryObject<Block>> registryBlocks() {
            return Arrays.stream(values()).map(x -> x.registry.block).toList();
        }

        /**
         * Returns a {@link Unmodifiable} {@link NotNull} {@link List} of all the {@link RegistryObject}s of the items in the registry
         * as defined by the {@link Blocks} enum.
         * This method uses {@link Arrays#stream(Object[])} to {@link Stream#map(Function)} each value to its {@link Registry#item()}
         *
         * @return a list of all the registry objects of the items in the registry.
         */
        public static @Unmodifiable @NotNull List<RegistryObject<Item>> registryItems() {
            return Arrays.stream(values()).map(x -> x.registry.item).toList();
        }

        /**
         * Constructor for blocks without an associated block entity.
         *
         * @param blockConstructor a supplier that provides an instance of the block
         * @param metadata a unary operator of {@link Metadata.Builder} that configures the properties of the block.
         */
        Blocks(Supplier<IDumbBlock> blockConstructor, @NotNull UnaryOperator<Metadata.Builder> metadata) {
            this.blockConstructor = blockConstructor;
            this.metadata = metadata.apply(new Metadata.Builder()).build();
        }

        /**
         * Constructor for blocks with an associated block entity.
         * This constructor requires an associated entity to be set in the metadata with {@link Metadata.Builder#associatedEntity(DumbBlockEntities.Entities)}.
         *
         * @param blockConstructor a function that provides an instance of the block with an associated entity, taking {@link DumbBlockEntities.Entities} as a parameter.
         * @param metadata a unary operator of {@link Metadata.Builder} that configures the properties of the block.
         */
        Blocks(Function<DumbBlockEntities.Entities, IDumbBlock> blockConstructor, @NotNull UnaryOperator<Metadata.Builder> metadata) {
            this.metadata = metadata.apply(new Metadata.Builder()).build();
            if (this.metadata.associatedEntity == null) {
                throw new IllegalStateException("Associated entity is required when using this constructor. Call it with Metadata#associatedEntity method.");
            }
            this.blockConstructor = () -> blockConstructor.apply(this.metadata.associatedEntity);
        }

        /**
         * Returns the {@link Registry} object associated with this block.
         * The {@link Registry} object contains the block and item registry objects for this block.
         *
         * @return the {@link Registry} object for this block.
         */
        public @NotNull Registry getRegistry() {
            return registry;
        }

        /**
         * Returns the {@link Metadata} object associated with this block.
         * The {@link Metadata} object contains information about the block's tags, loot table, recipes, and associated entity.
         *
         * @return the {@link Metadata} object for this block.
         */
        public @NotNull Metadata getMetadata() {
            return metadata;
        }

        /**
         * Returns the block constructor for this block.
         * The block constructor is a supplier that provides an instance of the block.
         *
         * @return the block constructor for this block.
         */
        public Supplier<IDumbBlock> getBlockConstructor() {
            return this.blockConstructor;
        }

        /**
         * Returns the register name for this block.
         * The register name is the name of the enum value in lowercase.
         * This is used as the identifier for the block in the registry.
         *
         * @return the register name for this block.
         */
        public @NotNull String getRegisterName() {
            return this.name().toLowerCase();
        }
    }

    /**
     * A {@link Record} that represents the metadata of a block.
     * The metadata contains information about the block's tags, loot table, recipes, and associated entity.
     *
     * @param tags The tags associated with the block.
     * @param lootTableBuilder A function that builds the loot table for the block.
     * @param recipeBuilders A list of unary operators that build the recipes for the block.
     * @param associatedEntity The associated entity of the block, if any.
     */
    public record Metadata(Tags tags, Function<ModBlockLootTables.Builder, LootTable.@Nullable Builder> lootTableBuilder, List<UnaryOperator<ModRecipeProvider.Builder>> recipeBuilders, @Nullable DumbBlockEntities.Entities associatedEntity) {
        /**
         * A builder class for the {@link Metadata} record.
         * This builder is used to construct an instance of {@link Metadata} with the desired properties.
         */
        public static class Builder {
            /**
             * The tags associated with the block.
             */
            private Tags tags = Tags.of();
            /**
             * A function that builds the loot table for the block.
             */
            private Function<ModBlockLootTables.Builder, LootTable.@Nullable Builder> lootTableBuilder;
            /**
             * A list of unary operators that build the recipes for the block.
             */
            private List<UnaryOperator<ModRecipeProvider.Builder>> recipeBuilders;
            /**
             * The associated entity of the block, if any.
             */
            private @Nullable DumbBlockEntities.Entities associatedEntity = null;

            /**
             * Default constructor for the {@link Builder} class.
             * Intentionally package private to prevent instantiation outside of this package.
             */
            Builder() {
            }

            /**
             * Sets the tags for the block.
             * @param tags a {@link UnaryOperator} of {@link Tags.Builder} that configures the tags of the block.
             * @return the {@link Builder} instance with the updated tags.
             */
            public Builder tags(@NotNull UnaryOperator<Tags.Builder> tags) {
                this.tags = tags.apply(new Tags.Builder()).build();
                return this;
            }

            /**
             * Sets the loot table for the block.
             * Required for all blocks.
             *
             * @param lootTable a {@link Function} that builds the loot table for the block.
             * @return the {@link Builder} instance with the updated loot table.
             */
            public Builder lootTable(Function<ModBlockLootTables.Builder, LootTable.Builder> lootTable) {
                this.lootTableBuilder = lootTable;
                return this;
            }

            /**
             * Adds a recipe for the block.
             *
             * @param recipe a {@link UnaryOperator} of {@link ModRecipeProvider.Builder} that builds a recipe for the block.
             * @return the {@link Builder} instance with the updated list of recipes.
             */
            public Builder recipe(UnaryOperator<ModRecipeProvider.Builder> recipe) {
                if (this.recipeBuilders == null) {
                    this.recipeBuilders = new ArrayList<>();
                }
                this.recipeBuilders.add(recipe);
                return this;
            }

            /**
             * Adds multiple recipes for the block.
             *
             * @param recipes an array of {@link UnaryOperator} of {@link ModRecipeProvider.Builder} that build recipes for the block.
             * @return the {@link Builder} instance with the updated list of recipes.
             */
            @SafeVarargs
            public final Builder recipe(UnaryOperator<ModRecipeProvider.Builder>... recipes) {
                if (this.recipeBuilders == null) {
                    this.recipeBuilders = Arrays.asList(recipes);
                } else {
                    this.recipeBuilders.addAll(Arrays.asList(recipes));
                }
                return this;
            }

            /**
             * Sets the associated entity for the block.
             * Required for blocks with an associated entity.
             *
             * @param associatedEntity the associated entity of the block, which is an instance of {@link DumbBlockEntities.Entities}.
             * @return the {@link Builder} instance with the updated associated entity.
             */
            public Builder associatedEntity(DumbBlockEntities.Entities associatedEntity) {
                this.associatedEntity = associatedEntity;
                return this;
            }

            /**
             * Builds an instance of {@link Metadata} with the properties set in the {@link Builder}.
             *
             * @return a new instance of {@link Metadata}.
             * @throws IllegalStateException if the loot table builder is not set.
             */
            public Metadata build() {
                if (lootTableBuilder == null) {
                    throw new IllegalStateException("Loot table builder is not set");
                }
                return new Metadata(tags, lootTableBuilder, recipeBuilders == null ? List.of() : recipeBuilders, associatedEntity);
            }
        }
    }

    /**
     * A {@link Record} that represents the tags of a block.
     * The tags contain information about the block's dumb block tags, block tags, dumb item tags, and item tags.
     *
     * @param dumbBlockTags The {@link DumbTags.Blocks} associated with the block.
     * @param blockTags The block tags associated with the block as provided by {@link BlockTags}
     * @param dumbItemTags The {@link DumbTags.Items} associated with the block.
     * @param itemTags The item tags associated with the block as provided by {@link ItemTags}
     */
    public record Tags(
        @NotNull List<DumbTags.Blocks> dumbBlockTags,
        @NotNull List<TagKey<Block>> blockTags,
        @NotNull List<DumbTags.Items> dumbItemTags,
        @NotNull List<TagKey<Item>> itemTags
    ) {
        /**
         * Creates a new instance of {@link Tags} with the provided lists of tags.
         * If any of the provided lists are null, they are replaced with an empty list via {@link List#of()}.
         *
         * @param dumbBlockTags A {@link List} of {@link DumbTags.Blocks} associated with the block, or null.
         * @param blockTags A {@link List} of {@link BlockTags} associated with the block, or null
         * @param dumbItemTags A {@link List} of {@link DumbTags.Items} associated with the block, or null
         * @param itemTags A {@link List} of {@link ItemTags} associated with the block, must not be null.
         * @return a new instance of {@link Tags} with the provided tags.
         */
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

        /**
         * Creates a new instance of {@link Tags} with the provided lists of tags.
         * If the provided list of dumbBlockTags or blockTags is null, it is replaced with an empty list via {@link List#of()}.
         * {@link Tags#itemTags()} is always an empty list in this method.
         *
         * @param dumbBlockTags A {@link List} of {@link DumbTags.Blocks} associated with the block, or null.
         * @param blockTags A {@link List} of {@link BlockTags} associated with the block, or null
         * @param dumbItemTags A {@link List} of {@link DumbTags.Items} associated with the block, must not be null.
         * @return a new instance of {@link Tags} with the provided tags and an empty list of item tags.
         */
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

        /**
         * Creates a new instance of {@link Tags} with the provided lists of tags.
         * If the provided list of dumbBlockTags is null, it is replaced with an empty list via {@link List#of()}.
         * {@link Tags#dumbItemTags()} and {@link Tags#itemTags()} are always empty in this method.
         *
         * @param dumbBlockTags A {@link List} of {@link DumbTags.Blocks} associated with the block, or null.
         * @param blockTags A {@link List} of {@link BlockTags} associated with the block, must not be null.
         * @return a new instance of {@link Tags} with the provided tags and empty lists for dumb item tags and item tags.
         */
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

        /**
         * Creates a new instance of {@link Tags} with the provided lists of tags.
         * If the provided list of dumbBlockTags is null, it is replaced with an empty list via {@link List#of()}.
         * {@link Tags#blockTags()}, {@link Tags#dumbItemTags()} and {@link Tags#itemTags()} are always empty in this method.
         *
         * @param dumbBlockTags A {@link List} of {@link DumbTags.Blocks} associated with the block. Must not be null.
         * @return a new instance of {@link Tags} with the provided dumb block tags and empty lists for the other types of tags.
         */
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

        /**
         * This method is a factory method that creates a new instance of {@link Tags} with all types of tags (dumb block tags, block tags, dumb item tags, and item tags) initialized as empty lists via {@link List#of()}.
         *
         * @return a new instance of {@link Tags} with all types of tags initialized as empty lists.
         */
        private static @NotNull Tags of() {
            return new Tags(List.of(), List.of(), List.of(), List.of());
        }

        /**
         * This is a builder class for the {@link Tags} record.
         * The builder is used to construct an instance of {@link Tags} with the desired properties.
         * It provides methods {@link Tags.Builder#dumbBlocks(DumbTags.Blocks...)}, {@link Tags.Builder#blocks(TagKey[])}, {@link Tags.Builder#dumbItems(DumbTags.Items...)}, and {@link Tags.Builder#items(TagKey[])} to set the tags for the block.
         * Each method in this {@link Builder} returns the {@link Builder} instance itself, allowing for method chaining.
         */
        public static final class Builder {
            /**
             * The tags associated with the block.
             * This is an instance of {@link Tags} which contains lists of different types of tags associated with the block.
             * These include dumb block tags, block tags, dumb item tags, and item tags.
             */
            private final Tags tags;

            /**
             * Default constructor for the {@link Builder} class.
             * Initializes the tags for the block to an empty instance of {@link Tags} using {@link Tags#of()}.
             */
            private Builder() {
                this.tags = Tags.of();
            }

            /**
             * Sets the dumb block tags for the block.
             *
             * @param dumbBlockTags An array of {@link DumbTags.Blocks} associated with the block. Must not be null.
             * @return a new instance of {@link Builder} with the updated dumb block tags and the other types of tags unchanged.
             */
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

            /**
             * Sets the block tags for the block.
             *
             * @param blockTags An array of {@link TagKey<Block>} provided by {@link BlockTags} associated with the block. Must not be null.
             * @return a new instance of {@link Builder} with the updated block tags and the other types of tags unchanged.
             */
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

            /**
             * Sets the dumb item tags for the block.
             *
             * @param dumbItemTags An array of {@link DumbTags.Items} associated with the block. Must not be null.
             * @return a new instance of {@link Builder} with the updated dumb item tags and the other types of tags unchanged.
             */
            public @NotNull Builder dumbItems(@NotNull DumbTags.Items ... dumbItemTags) {
                return new Builder(
                    Tags.of(
                        tags.dumbBlockTags,
                        tags.blockTags,
                        Arrays.asList(dumbItemTags),
                        tags.itemTags
                    )
                );
            }

            /**
             * Sets the item tags for the block.
             *
             * @param itemTags An array of {@link TagKey<Item>} provided by {@link ItemTags} associated with the block. Must not be null.
             * @return a new instance of {@link Builder} with the updated item tags and the other types of tags unchanged.
             */
            @SafeVarargs
            public final @NotNull Builder items(@NotNull TagKey<Item>... itemTags) {
                return new Builder(
                    Tags.of(
                        tags.dumbBlockTags,
                        tags.blockTags,
                        tags.dumbItemTags,
                        Arrays.asList(itemTags)
                    )
                );
            }

            /**
             * Builds and returns the {@link Tags} instance.
             *
             * @return the {@link Tags} instance that was built.
             */
            public @NotNull Tags build() {
                return tags;
            }

            /**
             * Constructor for the Builder class.
             *
             * @param tags An instance of {@link Tags} that represents the tags associated with the block. Must not be null.
             */
            private Builder(@NotNull Tags tags) {
                this.tags = tags;
            }
        }
    }

    /**
     * A {@link Record} that represents a registry for a block and its corresponding item.
     *
     * @param block A {@link RegistryObject<Block>} that represents the block in the registry.
     * @param item A {@link RegistryObject<Item>} that represents the item in the registry.
     */
    public record Registry(
        @NotNull RegistryObject<Block> block, @NotNull RegistryObject<Item> item
    ) {
        /**
         * A factory method that creates a new instance of {@link Registry} with the provided block and item.
         *
         * @param block A {@link RegistryObject<Block>} that represents the block in the registry. Must not be null.
         * @param item A {@link RegistryObject<Item>} that represents the item in the registry. Must not be null.
         * @return a new instance of {@link Registry} with the provided block and item.
         */
        public static @NotNull Registry of(@NotNull RegistryObject<Block> block, @NotNull RegistryObject<Item> item) {
            return new Registry(block, item);
        }
    }

    /**
     * Private constructor for the {@link DumbBlocks} utility class.
     *
     * This constructor is private to prevent instantiation of the utility class.
     * If called, it throws a {@link UtilityClassException}.
     *
     * @throws UtilityClassException always, as this class is not meant to be instantiated.
     */
    private DumbBlocks() {
        throw new UtilityClassException();
    }
}
