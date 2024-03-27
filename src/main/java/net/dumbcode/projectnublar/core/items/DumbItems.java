package net.dumbcode.projectnublar.core.items;

import net.dumbcode.projectnublar.core.data.ModRecipeProvider;
import net.dumbcode.projectnublar.core.items.elements.AcornItem;
import net.dumbcode.projectnublar.core.items.elements.OreDetectorItem;
import net.dumbcode.projectnublar.core.items.elements.StrawberryItem;
import net.dumbcode.projectnublar.core.items.elements.TestItem;
import net.dumbcode.projectnublar.core.registry.Registrar;
import net.dumbcode.projectnublar.core.tags.DumbTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public final class DumbItems {
    public enum Items {
        TEST_ITEM(TestItem::new, metadata -> metadata),
        ORE_DETECTOR(OreDetectorItem::new, metadata -> metadata),
        STRAWBERRY(StrawberryItem::new, metadata -> metadata),
        ACORN(AcornItem::new, metadata -> metadata);

        private final Supplier<DumbItem> itemConstructor;

        private final Registry registry = Registry.of(
            RegistryObject.create(new ResourceLocation(MOD_ID, getRegisterName()), Registrar.ITEMS.getRegistryKey(), MOD_ID)
        );
        private final Metadata metadata;

        public static @Unmodifiable @NotNull List<RegistryObject<Item>> registryItems() {
            return Arrays.stream(Items.values()).map(x -> x.registry.item).toList();
        }

        Items(Supplier<DumbItem> itemConstructor, @NotNull UnaryOperator<Metadata.Builder> metadataBuilder) {
            this.itemConstructor = itemConstructor;
            this.metadata = metadataBuilder.apply(new Metadata.Builder()).build();
        }

        public Supplier<DumbItem> getItemConstructor() {
            return this.itemConstructor;
        }

        public Registry getRegistry() {
            return this.registry;
        }

        public Metadata getMetadata() {
            return this.metadata;
        }

        public @NotNull String getRegisterName() {
            return this.name().toLowerCase();
        }
    }

    public record Metadata(Tags tags, List<UnaryOperator<ModRecipeProvider.Builder>> recipeBuilders) {
        public static class Builder {
            private Tags tags = Tags.of();
            private List<UnaryOperator<ModRecipeProvider.Builder>> recipeBuilders;

            Builder() {}

            public Builder tags(@NotNull UnaryOperator<Tags.Builder> tags) {
                this.tags = tags.apply(new Tags.Builder()).build();
                return this;
            }

            public Builder recipe(UnaryOperator<ModRecipeProvider.Builder> recipe) {
                if (this.recipeBuilders == null) {
                    this.recipeBuilders = new ArrayList<>();
                }
                this.recipeBuilders.add(recipe);
                return this;
            }

            public Builder recipe(UnaryOperator<ModRecipeProvider.Builder> ... recipes) {
                if (this.recipeBuilders == null) {
                    this.recipeBuilders = Arrays.asList(recipes);
                } else {
                    this.recipeBuilders.addAll(Arrays.asList(recipes));
                }
                return this;
            }

            public Metadata build() {
                return new Metadata(this.tags, this.recipeBuilders == null ? List.of() : this.recipeBuilders);
            }
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

        public static final class Builder {
            private final Tags tags;

            private Builder() {
                this.tags = Tags.of();
            }

            public Builder(@NotNull Tags tags) {
                this.tags = tags;
            }

            public @NotNull Builder dumbItems(@NotNull DumbTags.Items... tags) {
                return new Builder(
                    Tags.of(
                        Arrays.asList(tags),
                        this.tags.itemTags
                    )
                );
            }

            @SafeVarargs
            public final @NotNull Builder items(@NotNull TagKey<Item>... itemTags) {
                return new Builder(
                    Tags.of(
                        this.tags.dumbItemTags,
                        Arrays.asList(itemTags)
                    )
                );
            }

            public @NotNull Tags build() {
                return this.tags;
            }
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
