package net.dumbcode.projectnublar.core.items;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.data.ModRecipeProvider;
import net.dumbcode.projectnublar.core.items.elements.AcornItem;
import net.dumbcode.projectnublar.core.items.elements.OreDetectorItem;
import net.dumbcode.projectnublar.core.items.elements.StrawberryItem;
import net.dumbcode.projectnublar.core.items.elements.TestItem;
import net.dumbcode.projectnublar.core.registry.Registrar;
import net.dumbcode.projectnublar.core.tags.DumbTags;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class DumbItems {
    public enum Items {
        TEST_ITEM(
            TestItem.class,
            recipe -> recipe.nineBlockStorage(false, RecipeCategory.MISC, DumbBlocks.Blocks.TEST_BLOCK.getRegistry().block().get(), MOD_ID)
        ),
        ORE_DETECTOR(OreDetectorItem.class),
        STRAWBERRY(StrawberryItem.class),
        ACORN(AcornItem.class);

        private final Class<? extends DumbItem> itemClass;

        private final Registry registry = Registry.of(
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.ITEMS.getRegistryKey(), MOD_ID)
        );
        private final Tags tags;
        private final List<UnaryOperator<ModRecipeProvider.Builder>> recipeBuilders;

        public static @Unmodifiable @NotNull List<Class<? extends DumbItem>> classes() {
            return Arrays.stream(Items.values()).map(Items::getItemClass).collect(Collectors.toUnmodifiableList());
        }

        public static @Unmodifiable @NotNull List<RegistryObject<Item>> registryItems() {
            return Arrays.stream(Items.values()).map(x -> x.registry.item).toList();
        }

        @SafeVarargs
        Items(Class<? extends DumbItem> itemClass, UnaryOperator<ModRecipeProvider.Builder> ... builders) {
            this.itemClass = itemClass;
            this.tags = Tags.of();
            this.recipeBuilders = Arrays.asList(builders);
        }

        @SafeVarargs
        Items(Class<? extends DumbItem> itemClass, @NotNull Tags tags, UnaryOperator<ModRecipeProvider.Builder> ... builders) {
            this.itemClass = itemClass;
            this.tags = tags;
            this.recipeBuilders = Arrays.asList(builders);
        }

        public Class<? extends DumbItem> getItemClass() {
            return this.itemClass;
        }

        public Registry getRegistry() {
            return this.registry;
        }

        public List<UnaryOperator<ModRecipeProvider.Builder>> getRecipeBuilders() {
            return this.recipeBuilders;
        }

        public Tags getTags() {
            return this.tags;
        }

        public String getRegisterName() {
            return this.name().toLowerCase();
        }
    }

    public record Tags(
        @NotNull List<DumbTags.Items> dumbItemTags, @NotNull List<TagKey<Item>> itemTags
    ) {
        private static @NotNull Tags of(
            @Nullable List<DumbTags.Items> dumbItemTags,
            @NotNull List<TagKey<Item>> itemTags
        ) {
            return new Tags(dumbItemTags == null ? List.of() : dumbItemTags, itemTags);
        }

        private static @NotNull Tags of(
            @NotNull List<DumbTags.Items> dumbItemTags
        ) {
            return new Tags(dumbItemTags, List.of());
        }

        private static @NotNull Tags of() {
            return new Tags(List.of(), List.of());
        }
    }

    public record Registry(
        @NotNull RegistryObject<Item> item
    ) {
        public static @NotNull Registry of(@NotNull RegistryObject<Item> item) {
            return new Registry(item);
        }
    }
}
