package net.dumbcode.projectnublar.core.data;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.items.DumbItems;
import net.dumbcode.projectnublar.core.utils.QuantumBoolean;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    public static class Builder {

        private enum RecipeType {
            SHAPED,
            SHAPELESS,
            ONE_TO_ONE,
            SIMPLE_COOKING,
            SMITHING_TRANSFORM,
            SMITHING_TRIM,
            SINGLE_ITEM
        }

        private final ModRecipeProvider provider;
        private final RecipeOutput output;
        private ItemLike ingredient;
        private final ItemLike result;

        public record StringData(@Nullable String prefix, @Nullable String suffix) {}
        private StringData stringData;

        private String resultName;
        private RecipeType recipeType;


        //#region Builders
        private ShapedRecipeBuilder shapedBuilder;
        private String shapedSaveId;
        private ShapelessRecipeBuilder shapelessBuilder;
        private SimpleCookingRecipeBuilder simpleCookingBuilder;
        private SmithingTransformRecipeBuilder smithingTransformBuilder;
        private SmithingTrimRecipeBuilder smithingTrimBuilder;
        private SingleItemRecipeBuilder singleItemBuilder;

        @SuppressWarnings("unchecked")
        public <T> T getUnderlyingBuilder() {
            return switch (recipeType) {
                case SHAPED -> (T) shapedBuilder;
                case SHAPELESS -> (T) shapelessBuilder;
                case SIMPLE_COOKING -> (T) simpleCookingBuilder;
                case SMITHING_TRANSFORM -> (T) smithingTransformBuilder;
                case SMITHING_TRIM -> (T) smithingTrimBuilder;
                case SINGLE_ITEM -> (T) singleItemBuilder;
                default -> throw new IllegalStateException("Unexpected value: " + recipeType);
            };
        }

        public <T> T getUnderlyingBuilder(@NotNull Class<T> builderClass) {
            return builderClass.cast(getUnderlyingBuilder());
        }
        //#endregion

        public Builder(ModRecipeProvider provider, RecipeOutput output, ItemLike result, String resultName) {
            this.provider = provider;
            this.output = output;
            this.result = result;
            this.resultName = resultName;
        }

        public Builder(ModRecipeProvider provider, RecipeOutput output, ItemLike result, StringData stringData) {
            this.provider = provider;
            this.output = output;
            this.result = result;
            this.stringData = stringData;
        }

        public Builder(ModRecipeProvider provider, RecipeOutput output, ItemLike result, ItemLike ingredient, StringData stringData, SimpleCookingRecipeBuilder builder) {
            this.simpleCookingBuilder = builder;
            this.recipeType = RecipeType.SIMPLE_COOKING;
            this.provider = provider;
            this.output = output;
            this.stringData = stringData;
            this.result = result;
            this.ingredient = ingredient;
        }

        public Builder shaped(RecipeCategory category, @NotNull UnaryOperator<ShapedRecipeBuilder> builder) {
            this.recipeType = RecipeType.SHAPED;
            this.shapedBuilder = builder.apply(ShapedRecipeBuilder.shaped(category, this.result));
            return this;
        }

        public Builder shaped(RecipeCategory category, int count, @NotNull UnaryOperator<ShapedRecipeBuilder> builder) {
            this.recipeType = RecipeType.SHAPED;
            ShapedRecipeBuilder shaped = ShapedRecipeBuilder.shaped(category, this.result, count);
            builder.apply(shaped);
            this.shapedBuilder = shaped;
            return this;
        }

        public Builder shapeless(RecipeCategory category, @NotNull UnaryOperator<ShapelessRecipeBuilder> builder) {
            this.recipeType = RecipeType.SHAPELESS;
            this.shapelessBuilder = builder.apply(ShapelessRecipeBuilder.shapeless(category, this.result));
            return this;
        }

        public Builder shapeless(RecipeCategory category, int count, @NotNull UnaryOperator<ShapelessRecipeBuilder> builder) {
            this.recipeType = RecipeType.SHAPELESS;
            ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(category, this.result, count);
            builder.apply(shapeless);
            this.shapelessBuilder = shapeless;
            return this;
        }

        public Builder singleItem(RecipeCategory category, int count, ItemLike material, @NotNull UnaryOperator<SingleItemRecipeBuilder> builder) {
            this.recipeType = RecipeType.SINGLE_ITEM;
            SingleItemRecipeBuilder singleItem = SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(material),
                category,
                this.result,
                count
            );
            builder.apply(singleItem);
            this.singleItemBuilder = singleItem;
            return this;
        }

        public enum CookingType {
            CAMPFIRE(SimpleCookingRecipeBuilder::campfireCooking),
            BLASTING(SimpleCookingRecipeBuilder::blasting),
            SMELTING(SimpleCookingRecipeBuilder::smelting),
            SMOKING(SimpleCookingRecipeBuilder::smoking);

            private final PropertyDispatch.PentaFunction<Ingredient, RecipeCategory, ItemLike, Float, Integer, SimpleCookingRecipeBuilder> function;

            CookingType(PropertyDispatch.PentaFunction<Ingredient, RecipeCategory, ItemLike, Float, Integer, SimpleCookingRecipeBuilder> function) {
                this.function = function;
            }
        }

        public Builder simpleCooking(@NotNull CookingType type, RecipeCategory category, ItemLike material, float experience, int cookingTime, @NotNull UnaryOperator<SimpleCookingRecipeBuilder> builder) {
            this.recipeType = RecipeType.SIMPLE_COOKING;
            SimpleCookingRecipeBuilder simpleCooking = type.function.apply(Ingredient.of(material), category, this.result, experience, cookingTime);
            builder.apply(simpleCooking);
            this.simpleCookingBuilder = simpleCooking;
            return this;
        }

        public <T extends AbstractCookingRecipe> Builder simpleCooking(RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> recipeFactory, @NotNull ItemLike requires, RecipeCategory category, float experience, int cookingTime, @NotNull UnaryOperator<SimpleCookingRecipeBuilder> builder) {
            this.recipeType = RecipeType.SIMPLE_COOKING;
            SimpleCookingRecipeBuilder generic = SimpleCookingRecipeBuilder.generic(
                Ingredient.of(requires),
                category,
                this.result,
                experience,
                cookingTime,
                serializer,
                recipeFactory
            );
            builder.apply(generic);
            this.simpleCookingBuilder = generic;
            return this;
        }

        public abstract static class SmithingConstructor<T> {
            protected Ingredient template;
            protected Ingredient base;
            protected Ingredient addition;

            protected SmithingConstructor() {

            }

            public SmithingConstructor<T> template(Ingredient template) {
                this.template = template;
                return this;
            }

            public SmithingConstructor<T> base(Ingredient base) {
                this.base = base;
                return this;
            }

            public SmithingConstructor<T> addition(Ingredient addition) {
                this.addition = addition;
                return this;
            }
        }

        public static class SmithingTransformConstructor extends SmithingConstructor<SmithingTransformRecipeBuilder> {
            @Contract(value = " -> new", pure = true)
            public static @NotNull SmithingTransformConstructor builder() {
                return new SmithingTransformConstructor();
            }

            @Override
            public SmithingTransformConstructor template(Ingredient template) {
                return (SmithingTransformConstructor) super.template(template);
            }

            @Override
            public SmithingTransformConstructor base(Ingredient base) {
                return (SmithingTransformConstructor) super.base(base);
            }

            @Override
            public SmithingTransformConstructor addition(Ingredient addition) {
                return (SmithingTransformConstructor) super.addition(addition);
            }

            public @NotNull SmithingTransformRecipeBuilder construct(RecipeCategory category, @NotNull ItemLike result) {
                return SmithingTransformRecipeBuilder.smithing(template, base, addition, category, result.asItem());
            }
        }

        public Builder smithingTransform(RecipeCategory category, @NotNull UnaryOperator<SmithingTransformConstructor> constructor, @NotNull UnaryOperator<SmithingTransformRecipeBuilder> builder) {
            this.recipeType = RecipeType.SMITHING_TRANSFORM;
            SmithingTransformConstructor smithingConstructor = SmithingTransformConstructor.builder();
            constructor.apply(smithingConstructor);
            this.smithingTransformBuilder = smithingConstructor.construct(category, this.result);
            builder.apply(this.smithingTransformBuilder);
            return this;
        }

        public static class SmithingTrimConstructor extends SmithingConstructor<SmithingTrimRecipeBuilder> {
            @Contract(value = " -> new", pure = true)
            public static @NotNull SmithingTrimConstructor builder() {
                return new SmithingTrimConstructor();
            }

            @Override
            public SmithingTrimConstructor template(Ingredient template) {
                return (SmithingTrimConstructor) super.template(template);
            }

            @Override
            public SmithingTrimConstructor base(Ingredient base) {
                return (SmithingTrimConstructor) super.base(base);
            }

            @Override
            public SmithingTrimConstructor addition(Ingredient addition) {
                return (SmithingTrimConstructor) super.addition(addition);
            }

            public @NotNull SmithingTrimRecipeBuilder construct(RecipeCategory category) {
                return SmithingTrimRecipeBuilder.smithingTrim(template, base, addition, category);
            }
        }

        public Builder smithingTrim(RecipeCategory category, @NotNull UnaryOperator<SmithingTrimConstructor> constructor, @NotNull UnaryOperator<SmithingTrimRecipeBuilder> builder) {
            this.recipeType = RecipeType.SMITHING_TRIM;
            SmithingTrimConstructor smithingConstructor = SmithingTrimConstructor.builder();
            constructor.apply(smithingConstructor);
            this.smithingTrimBuilder = smithingConstructor.construct(category);
            builder.apply(this.smithingTrimBuilder);
            return this;
        }

        public Builder trimSmithing(Item requires) {
            return smithingTrim(
                RecipeCategory.MISC,
                constructor -> constructor
                    .template(Ingredient.of(requires))
                    .base(Ingredient.of(ItemTags.TRIMMABLE_ARMOR))
                    .addition(Ingredient.of(ItemTags.TRIM_MATERIALS)),
                builder -> builder
                    .unlocks("has_smithing_trim_template", has(requires))
            );
        }

        public Builder oneToOneConversion(ItemLike requires) {
            return oneToOneConversion(requires, 1);
        }

        public Builder oneToOneConversion(ItemLike requires, int resultCount) {
            ingredient = requires;
            return shapeless(RecipeCategory.MISC, resultCount, builder ->
                builder
                    .requires(requires)
                    .group(resultName)
                    .unlockedBy(
                        getHasName(requires),
                        has(requires)
                    )
            );
        }

        public Builder oreSmelting(RecipeCategory category, List<ItemLike> requires, float experience, int smeltingTime) {
            oreCooking(RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, requires, category, experience, smeltingTime, "_from_smelting");
            return this;
        }

        public Builder oreBlasting(RecipeCategory category, List<ItemLike> requires, float experience, int blastingTime) {
            oreCooking(RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, requires, category, experience, blastingTime, "_from_blasting");
            return this;
        }

        private <T extends AbstractCookingRecipe> void oreCooking(RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> recipeFactory, @NotNull List<ItemLike> requires, RecipeCategory category, float experience, int cookingTime, String suffix) {
            Iterator iterator = requires.iterator();
            while (iterator.hasNext()) {
                ItemLike itemLike = (ItemLike) iterator.next();
                SimpleCookingRecipeBuilder builder = SimpleCookingRecipeBuilder.generic(Ingredient.of(itemLike), category, this.result, experience, cookingTime, serializer, recipeFactory)
                    .group(resultName)
                    .unlockedBy(getHasName(itemLike), has(itemLike));
                Builder newBuilder = new Builder(provider, output, result, itemLike, new StringData(null, suffix), builder);
                newBuilder.build();
            }
        }

        public Builder netheriteSmithing(RecipeCategory category, Item requires) {
            return smithingTransform(
                category,
                constructor -> constructor
                    .template(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                    .base(Ingredient.of(requires))
                    .addition(Ingredient.of(Items.NETHERITE_INGOT)),
                builder -> builder
                    .unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
            );
        }

        public Builder twoByTwoPacker(RecipeCategory category, ItemLike unpacked) {
            return shaped(
                category,
                builder -> builder
                    .pattern("##")
                    .pattern("##")
                    .define('#', unpacked)
                    .unlockedBy(getHasName(unpacked), has(unpacked))
            );
        }

        public Builder threeByThreePacker(RecipeCategory category, ItemLike unpacked) {
            return shapeless(
                category,
                builder -> builder
                    .requires(unpacked, 9)
                    .group(resultName)
                    .unlockedBy(getHasName(unpacked), has(unpacked))
            );
        }

        public Builder planksFromLog(int count, TagKey<Item> logs) {
            return shapeless(
                RecipeCategory.BUILDING_BLOCKS,
                count,
                builder -> builder
                    .requires(logs)
                    .group("planks")
                    .unlockedBy("has_log", has(logs))
            );
        }

        public Builder planksFromLogs(int count, TagKey<Item> logs) {
            return shapeless(
                RecipeCategory.BUILDING_BLOCKS,
                count,
                builder -> builder
                    .requires(logs)
                    .group("planks")
                    .unlockedBy("has_logs", has(logs))
            );
        }

        public Builder woodFromLogs(ItemLike log) {
            return shaped(
                RecipeCategory.BUILDING_BLOCKS,
                3,
                builder -> builder
                    .pattern("##")
                    .pattern("##")
                    .define('#', log)
                    .group("bark")
                    .unlockedBy("has_log", has(log))
            );
        }

        public Builder woodenBoat(ItemLike material) {
            return shaped(
                RecipeCategory.TRANSPORTATION,
                builder -> builder
                    .pattern("# #")
                    .pattern("###")
                    .define('#', material)
                    .group("boat")
                    .unlockedBy("in_water", insideOf(Blocks.WATER))
            );
        }

        public Builder chestBoat(ItemLike material) {
            return shapeless(
                RecipeCategory.TRANSPORTATION,
                builder -> builder
                    .requires(Blocks.CHEST)
                    .requires(material)
                    .group("chest_boat")
                    .unlockedBy("has_boat", has(ItemTags.BOATS))
            );
        }

        public Builder button(Ingredient material) {
            return shapeless(
                RecipeCategory.REDSTONE,
                builder -> builder
                    .requires(material)
            );
        }

        public Builder door(Ingredient material) {
            return shaped(
                RecipeCategory.REDSTONE,
                3,
                builder -> builder
                    .pattern("##")
                    .pattern("##")
                    .pattern("##")
                    .define('#', material)
            );
        }

        public Builder fence(Ingredient material) {
            int i = this.result == Blocks.NETHER_BRICK_FENCE ? 6 : 3;
            Item item = this.result == Blocks.NETHER_BRICK_FENCE ? Items.NETHER_BRICK : Items.STICK;
            return shaped(
                RecipeCategory.DECORATIONS,
                i,
                builder -> builder
                    .define('W', material)
                    .define('#', item)
                    .pattern("W#W")
                    .pattern("W#W")
            );
        }

        public Builder fenceGate(Ingredient material) {
            return shaped(
                RecipeCategory.REDSTONE,
                builder -> builder
                    .define('#', Items.STICK)
                    .define('W', material)
                    .pattern("#W#")
                    .pattern("#W#")
            );
        }

        public Builder pressurePlate(RecipeCategory category, Ingredient material) {
            return shaped(
                category,
                builder -> builder
                    .define('#', material)
                    .pattern("##")
            );
        }

        public Builder pressurePlate(ItemLike material) {
            Builder builder = pressurePlate(RecipeCategory.REDSTONE, Ingredient.of(material));
            builder
                .getUnderlyingBuilder(ShapedRecipeBuilder.class)
                .unlockedBy(getHasName(material), has(material));
            return builder;
        }

        public Builder slab(RecipeCategory category, Ingredient material) {
            return shaped(
                category,
                6,
                builder -> builder
                    .define('#', material)
                    .pattern("###")
            );
        }

        public Builder slab(RecipeCategory category, ItemLike material) {
            Builder builder = slab(category, Ingredient.of(material));
            builder
                .getUnderlyingBuilder(ShapedRecipeBuilder.class)
                .unlockedBy(getHasName(material), has(material));
            return builder;
        }

        public Builder stair(Ingredient material) {
            return shaped(
                RecipeCategory.BUILDING_BLOCKS,
                4,
                builder -> builder
                    .define('#', material)
                    .pattern("#  ")
                    .pattern("## ")
                    .pattern("###")
            );
        }

        public Builder trapdoor(Ingredient material) {
            return shaped(
                RecipeCategory.REDSTONE,
                2,
                builder -> builder
                    .define('#', material)
                    .pattern("###")
                    .pattern("###")
            );
        }

        public Builder sign(Ingredient material) {
            return shaped(
                RecipeCategory.DECORATIONS,
                3,
                builder -> builder
                    .group("sign")
                    .define('#', material)
                    .define('S', Items.STICK)
                    .pattern("###")
                    .pattern("###")
                    .pattern(" S ")
            );
        }

        public Builder handingSign(ItemLike material) {
            return shaped(
                RecipeCategory.DECORATIONS,
                6,
                builder -> builder
                    .group("hanging_sign")
                    .define('#', material)
                    .define('X', Items.CHAIN)
                    .pattern("X X")
                    .pattern("###")
                    .pattern("###")
                    .unlockedBy("has_stripped_logs", has(material))
            );
        }

        /**
         *
         * @param itemDyePairs A list of pairs of items, where the first item is the item to be colored and the second item is the dye to be used
         * @return The current builder (unchanged)
         */
        public Builder colorBlockWithDye(@NotNull List<Pair<Item, Item>> itemDyePairs) {
            for (Pair<Item, Item> pair : itemDyePairs) {
                Builder builder = new Builder(provider, output, result, new StringData("dye_", null));
                builder.colorBlockWithDye(pair).build();
            }
            return this;
        }

        public Builder colorBlockWithDye(Pair<Item, Item> @NotNull ... itemDyePairs) {
            return colorBlockWithDye(List.of(itemDyePairs));
        }

        public Builder colorBlockWithDye(@NotNull Pair<Item, Item> itemDye) {
            return colorBlockWithDye(itemDye.getLeft(), itemDye.getRight());
        }

        public Builder colorBlockWithDye(Item item, Item dye) {
            return shapeless(
                RecipeCategory.BUILDING_BLOCKS,
                b -> b
                    .requires(item)
                    .requires(Ingredient.of(new ItemStack(dye)))
                    .group(resultName)
                    .unlockedBy("has_needed_dye", has(dye))
            );
        }

        public Builder carpet(ItemLike material){
            return shaped(
                RecipeCategory.DECORATIONS,
                3,
                builder -> builder
                    .define('#', material)
                    .pattern("##")
                    .group("carpet")
                    .unlockedBy(getHasName(material), has(material))
            );
        }

        public Builder bedFromPlanksAndWool(ItemLike wool) {
            return shaped(
                RecipeCategory.DECORATIONS,
                builder -> builder
                    .define('#', wool)
                    .define('X', ItemTags.PLANKS)
                    .pattern("###")
                    .pattern("XXX")
                    .group("bed")
                    .unlockedBy(getHasName(wool), has(wool))
            );
        }

        public Builder banner(ItemLike material) {
            return shaped(
                RecipeCategory.DECORATIONS,
                builder -> builder
                    .define('#', material)
                    .define('|', Items.STICK)
                    .pattern("###")
                    .pattern("###")
                    .pattern(" | ")
                    .group("banner")
                    .unlockedBy(getHasName(material), has(material))
            );
        }

        public Builder stainedGlassFromGlassAndDye(ItemLike dye) {
            return shaped(
                RecipeCategory.BUILDING_BLOCKS,
                8,
                builder -> builder
                    .define('#', Blocks.GLASS)
                    .define('X', dye)
                    .pattern("###")
                    .pattern("#X#")
                    .pattern("###")
                    .group("stained_glass")
                    .unlockedBy("has_glass", has(Blocks.GLASS))
            );
        }

        public Builder stainedGlassPaneFromStainedGlass(ItemLike stainedGlass) {
            return shaped(
                RecipeCategory.DECORATIONS,
                16,
                builder -> builder
                    .define('#', stainedGlass)
                    .pattern("###")
                    .pattern("###")
                    .group("stained_glass_pane")
                    .unlockedBy("has_glass", has(stainedGlass))
            );
        }

        public Builder stainedGlassPaneFromGlassPaneAndDye(ItemLike dye) {
            shapedSaveId = getConversionRecipeName(this.result, Blocks.GLASS_PANE);
            return shaped(
                RecipeCategory.DECORATIONS,
                8,
                builder -> builder
                    .define('#', Blocks.GLASS_PANE)
                    .define('$', dye)
                    .pattern("###")
                    .pattern("#$#")
                    .pattern("###")
                    .group("stained_glass_pane")
                    .unlockedBy("has_glass_pane", has(Blocks.GLASS_PANE))
                    .unlockedBy(getHasName(dye), has(dye))
            );
        }

        public Builder coloredTerracottaFromTerracottaAndDye(ItemLike dye) {
            return shaped(
                RecipeCategory.BUILDING_BLOCKS,
                8,
                builder -> builder
                    .define('#', Blocks.TERRACOTTA)
                    .define('X', dye)
                    .pattern("###")
                    .pattern("#X#")
                    .pattern("###")
                    .group("stained_terracotta")
                    .unlockedBy("has_terracotta", has(Blocks.TERRACOTTA))
            );
        }

        public Builder concretePowder(ItemLike dye) {
            return shapeless(
                RecipeCategory.BUILDING_BLOCKS,
                8,
                builder -> builder
                    .requires(dye)
                    .requires(Blocks.SAND, 4)
                    .requires(Blocks.GRAVEL, 4)
                    .group("concrete_powder")
                    .unlockedBy("has_sand", has(Blocks.SAND))
                    .unlockedBy("has_gravel", has(Blocks.GRAVEL))
            );
        }

        public Builder candle(ItemLike dye) {
            return shapeless(
                RecipeCategory.DECORATIONS,
                builder -> builder
                    .requires(Blocks.CANDLE)
                    .requires(dye)
                    .group("dyed_candle")
                    .unlockedBy(getHasName(dye), has(dye))
            );
        }

        public Builder wall(RecipeCategory category, ItemLike material) {
            Builder builder = wall(category, Ingredient.of(material));
            builder
                .getUnderlyingBuilder(ShapedRecipeBuilder.class)
                .unlockedBy(getHasName(material), has(material));
            return builder;
        }

        public Builder wall(RecipeCategory category, Ingredient material) {
            return shaped(
                category,
                6,
                builder -> builder
                    .define('#', material)
                    .pattern("###")
                    .pattern("###")
            );
        }

        public Builder polished(RecipeCategory category, ItemLike material) {
            Builder builder = polished(category, Ingredient.of(material));
            builder
                .getUnderlyingBuilder(ShapedRecipeBuilder.class)
                .unlockedBy(getHasName(material), has(material));
            return builder;
        }

        public Builder polished(RecipeCategory category, Ingredient material) {
            return shaped(
                category,
                4,
                builder -> builder
                    .define('S', material)
                    .pattern("SS")
                    .pattern("SS")
            );
        }

        public Builder cut(RecipeCategory category, ItemLike material) {
            Builder builder = cut(category, Ingredient.of(material));
            builder
                .getUnderlyingBuilder(ShapedRecipeBuilder.class)
                .unlockedBy(getHasName(material), has(material));
            return builder;
        }

        public Builder cut(RecipeCategory category, Ingredient material) {
            return shaped(
                category,
                4,
                builder -> builder
                    .define('#', material)
                    .pattern("##")
                    .pattern("##")
            );
        }

        public Builder chiseled(RecipeCategory category, ItemLike material) {
            Builder builder = chiseled(category, Ingredient.of(material));
            builder
                .getUnderlyingBuilder(ShapedRecipeBuilder.class)
                .unlockedBy(getHasName(material), has(material));
            return builder;
        }

        public Builder chiseled(RecipeCategory category, Ingredient material) {
            return shaped(
                category,
                builder -> builder
                    .define('#', material)
                    .pattern("#")
                    .pattern("#")
            );
        }

        public Builder mosaic(RecipeCategory category, ItemLike material) {
            return shaped(
                category,
                builder -> builder
                    .define('#', material)
                    .pattern("#")
                    .pattern("#")
                    .unlockedBy(getHasName(material), has(material))
            );
        }

        public Builder stonecutterResultFromBase(RecipeCategory category, ItemLike material) {
            return stonecutterResultFromBase(category, 1, material);
        }

        public Builder stonecutterResultFromBase(RecipeCategory category, int count, ItemLike material) {
            this.stringData = new StringData(null, getConversionRecipeName(this.result, material) + "_stonecutting");
            return singleItem(
                category,
                count,
                material,
                builder -> builder
                    .unlockedBy(getHasName(material), has(material))
            );
        }

        public Builder smeltingResultFromBase(ItemLike requires) {
            return simpleCooking(
                CookingType.SMELTING,
                RecipeCategory.BUILDING_BLOCKS,
                requires, 0.1F,
                200,
                builder -> builder
                    .unlockedBy(getHasName(requires), has(requires))
            );
        }

        /**
         * Creates a recipe for a 9-block storage block
         * @param packed Is the result packed or unpacked? If it's somehow both two recipes will be created: one for packed, one for unpacked
         * @param category
         * @param item
         * @param group
         * @return An iterable of two recipes if the packed state is undefined, otherwise a single recipe (already built)
         */
        public Iterable<Builder> nineBlockStorage(@NotNull QuantumBoolean packed, RecipeCategory category, ItemLike item, String group) {
            Supplier<Builder> newBuilder = () -> new Builder(this.provider, this.output, this.result, this.resultName);
            if (packed.isUndefined()) {
                Builder packedBuilder = newBuilder.get().nineBlockStoragePacked(category, item, group);
                packedBuilder.getUnderlyingBuilder(ShapedRecipeBuilder.class).save(this.output, new ResourceLocation(resultName));
                Builder unpackedBuilder = newBuilder.get().nineBlockStorageUnpacked(category, item, group);
                unpackedBuilder.getUnderlyingBuilder(ShapelessRecipeBuilder.class).save(this.output, new ResourceLocation(resultName));
                return List.of(
                    packedBuilder,
                    unpackedBuilder
                );
            } else {
                return List.of(nineBlockStorage(packed.isTrue(), category, item, group));
            }
        }

        public Builder nineBlockStorage(boolean packed, RecipeCategory category, ItemLike item, String group) {
            return packed ? nineBlockStoragePacked(category, item, group) : nineBlockStorageUnpacked(category, item, group);
        }

        public Builder nineBlockStoragePacked(RecipeCategory category, ItemLike item, String group) {
            return shaped(
                category,
                builder -> builder
                    .define('#', item)
                    .pattern("###")
                    .pattern("###")
                    .pattern("###")
                    .group(group)
                    .unlockedBy(getHasName(item), has(item))
            );
        }

        public Builder nineBlockStorageUnpacked(RecipeCategory category, ItemLike item, String group) {
            return shapeless(
                category,
                9,
                builder -> builder
                    .requires(item)
                    .group(group)
                    .unlockedBy(getHasName(item), has(item))
            );
        }

        public Builder copySmithingTemplate(TagKey<Item> baseMaterial) {
            return shaped(
                RecipeCategory.MISC,
                2,
                builder -> builder
                    .define('#', Items.DIAMOND)
                    .define('C', baseMaterial)
                    .define('S', this.result)
                    .pattern("#S#")
                    .pattern("#C#")
                    .pattern("###")
                    .unlockedBy(getHasName(this.result), has(this.result))
            );
        }

        public Builder copySmithingTemplate(ItemLike baseItem) {
            return shaped(
                RecipeCategory.MISC,
                2,
                builder -> builder
                    .define('#', Items.DIAMOND)
                    .define('C', baseItem)
                    .define('S', this.result)
                    .pattern("#S#")
                    .pattern("#C#")
                    .pattern("###")
                    .unlockedBy(getHasName(this.result), has(this.result))
            );
        }

        public Builder grate(Block material) {
            return shaped(
                RecipeCategory.BUILDING_BLOCKS,
                4,
                builder -> builder
                    .define('M', material)
                    .pattern(" M ")
                    .pattern("M M")
                    .pattern(" M ")
                    .unlockedBy(getHasName(material), has(material))
            );
        }

        public Builder copperBulb(Block material) {
            return shaped(
                RecipeCategory.REDSTONE,
                4,
                builder -> builder
                    .define('C', material)
                    .define('R', Items.REDSTONE)
                    .define('B', Items.BLAZE_ROD)
                    .pattern(" C ")
                    .pattern("CBC")
                    .pattern(" R ")
                    .unlockedBy(getHasName(material), has(material))
            );
        }

        private static final String MOD_PREFIX = MOD_ID + ":";

        private void build() {
            if (recipeType == null) return;
            switch (recipeType) {
                case SHAPED -> {
                    if (shapedSaveId != null) {
                        shapedBuilder.save(this.output, MOD_PREFIX + shapedSaveId);
                    } else {
                        shapedBuilder.save(this.output);
                    }
                }
                case SHAPELESS -> {
                    if (stringData != null && stringData.prefix != null) {
                        shapelessBuilder.save(this.output, MOD_PREFIX + stringData.prefix + getItemName(this.result));
                    } else {
                        shapelessBuilder.save(this.output);
                    }
                }
                case ONE_TO_ONE -> shapelessBuilder.save(this.output, MOD_PREFIX + getConversionRecipeName(this.result, ingredient));
                case SIMPLE_COOKING -> {
                    if (stringData != null && stringData.suffix != null) {
                        simpleCookingBuilder.save(this.output, MOD_PREFIX + getItemName(this.result) + stringData.suffix + "_" + getItemName(this.ingredient));
                    } else {
                        simpleCookingBuilder.save(this.output);
                    }
                }
                case SMITHING_TRANSFORM -> smithingTransformBuilder.save(this.output, MOD_PREFIX + getItemName(this.result) + "_smithing");
                case SMITHING_TRIM -> smithingTrimBuilder.save(this.output, new ResourceLocation(MOD_ID, getItemName(this.result) + "_trim"));
                case SINGLE_ITEM -> singleItemBuilder.save(this.output, MOD_PREFIX + stringData.suffix);
            }
        }
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        for (DumbBlocks.Blocks block : DumbBlocks.Blocks.values()) {
            for (UnaryOperator<Builder> recipeBuilder : block.getMetadata().recipeBuilders()) {
                Builder builder = new Builder(this, recipeOutput, block.getRegistry().item().get(), block.getRegisterName());
                recipeBuilder.apply(builder);
                builder.build();
            }
        }

        for (DumbItems.Items item : DumbItems.Items.values()) {
            for (UnaryOperator<Builder> recipeBuilder : item.getMetadata().recipeBuilders()) {
                Builder builder = new Builder(this, recipeOutput, item.getRegistry().item().get(), item.getRegisterName());
                recipeBuilder.apply(builder);
                builder.build();
            }
        }
    }

    public static @NotNull Criterion<EnterBlockTrigger.TriggerInstance> insideOf(@NotNull Block block) {
        return CriteriaTriggers.ENTER_BLOCK.createCriterion(new EnterBlockTrigger.TriggerInstance(Optional.empty(), Optional.of(block.builtInRegistryHolder()), Optional.empty()));
    }
}
