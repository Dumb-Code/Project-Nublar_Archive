package net.dumbcode.projectnublar.core.tags;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.items.DumbItems;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public final class DumbTags {

    public enum Blocks implements EnumTagRegistry<Block> {
        OVERWORLD_ORES(builder -> builder
            .addTag(Tags.Blocks.ORES)
        );

        private final TagKey<Block> tagKey;
        private final Function<IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block>, IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block>> tagBuilderOperator;

        Blocks(@NotNull UnaryOperator<IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block>> tagBuilder) {
            this.tagBuilderOperator = tagBuilder
                .andThen(builder ->
                    builder.add(Arrays.stream(DumbBlocks.Blocks.values())
                        .filter(x -> x.getTags().dumbBlockTags().contains(this))
                        .map(x -> x.getRegistry().block().get())
                        .toArray(Block[]::new))
                );
            this.tagKey = EnumTagRegistry.tag(this);
        }

        @Override
        public Function<IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block>, IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block>> getTagBuilderOperator() {
            return this.tagBuilderOperator;
        }

        @Override
        public @NotNull String getRegisterName() {
            return this.name().toLowerCase();
        }

        public TagKey<Block> getTagKey() {
            return this.tagKey;
        }
    }

    public enum Items implements EnumTagRegistry<Item> {
        ;

        private final TagKey<Item> tagKey;
        private final Function<IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item>, IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item>> tagBuilderOperator;

        Items(@NotNull UnaryOperator<IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item>> tagBuilder) {
            this.tagBuilderOperator = tagBuilder
                .andThen(builder -> builder
                        .add(
                            Arrays.stream(DumbBlocks.Blocks.values())
                                .filter(x -> x.getTags().dumbItemTags().contains(this))
                                .map(x -> x.getRegistry().item().get())
                                .toArray(Item[]::new)
                        )
                        .add(
                            Arrays.stream(DumbItems.Items.values())
                                .filter(x -> x.getTags().dumbItemTags().contains(this))
                                .map(x -> x.getRegistry().item().get())
                                .toArray(Item[]::new)
                        )
                );
            this.tagKey = EnumTagRegistry.tag(this);
        }

        @Override
        public Function<IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item>, IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item>> getTagBuilderOperator() {
            return this.tagBuilderOperator;
        }

        @Override
        public @NotNull String getRegisterName() {
            return this.name().toLowerCase();
        }

        public TagKey<Item> getTagKey() {
            return this.tagKey;
        }
    }

    interface EnumTagRegistry<T extends ItemLike> {
        @NotNull String getRegisterName();
        TagKey<T> getTagKey();

        Function<IntrinsicHolderTagsProvider.IntrinsicTagAppender<T>, IntrinsicHolderTagsProvider.IntrinsicTagAppender<T>> getTagBuilderOperator();

        @SuppressWarnings("unchecked")
        static <T extends ItemLike, U extends Enum<U> & EnumTagRegistry<T>> @NotNull TagKey<T> tag(@NotNull U key) {
            final ResourceLocation resourceLocation = new ResourceLocation(MOD_ID, key.getRegisterName());
            if (key instanceof Blocks) {
                return (TagKey<T>) BlockTags.create(resourceLocation);
            } else if (key instanceof Items) {
                return (TagKey<T>) ItemTags.create(resourceLocation);
            }
            throw new IllegalArgumentException("Unknown tag type: " + key.getClass().getName());
        }
    }
}
