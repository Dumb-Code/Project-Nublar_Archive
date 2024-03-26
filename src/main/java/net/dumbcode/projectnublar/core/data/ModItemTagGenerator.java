package net.dumbcode.projectnublar.core.data;

import net.dumbcode.projectnublar.ProjectNublar;
import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.items.DumbItems;
import net.dumbcode.projectnublar.core.tags.DumbTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (DumbTags.Items item : DumbTags.Items.values()) {
            item.getTagBuilderOperator().apply(tag(item.getTagKey()));
        }

        Field[] fields = ItemTags.class.getFields();
        for (Field field : fields) {
            if (!field.getType().equals(TagKey.class)) continue;
            try {
                TagKey<Item> tagKey = (TagKey<Item>) field.get(null);
                if (tagKey == null) continue;
                Item[] itemsArray = Arrays.stream(DumbItems.Items.values())
                    .filter(x -> x.getMetadata().tags().itemTags().contains(tagKey))
                    .map(x -> x.getRegistry().item().get())
                    .toArray(Item[]::new);
                Item[] blocksArray = Arrays.stream(DumbBlocks.Blocks.values())
                    .filter(x -> x.getMetadata().tags().itemTags().contains(tagKey))
                    .map(x -> x.getRegistry().item().get())
                    .toArray(Item[]::new);
                Item[] array = ArrayUtils.addAll(itemsArray, blocksArray);
                if (array.length == 0) continue;
                this.tag(tagKey).add(array);
            } catch (IllegalAccessException e) {
                ProjectNublar.LOGGER.error("Failed to add tag to item tag: " + e.getMessage());
            }
        }
    }
}
