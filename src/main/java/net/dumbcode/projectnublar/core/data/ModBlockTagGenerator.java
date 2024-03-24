package net.dumbcode.projectnublar.core.data;

import net.dumbcode.projectnublar.ProjectNublar;
import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.tags.DumbTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static net.dumbcode.projectnublar.ProjectNublar.MOD_ID;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (DumbTags.Blocks block : DumbTags.Blocks.values()) {
            block.getTagBuilderOperator().apply(tag(block.getTagKey()));
        }

        Field[] fields = BlockTags.class.getFields();
        for (Field field : fields) {
            if (!field.getType().equals(TagKey.class)) continue;
            try {
                TagKey<Block> tagKey = (TagKey<Block>) field.get(null);
                if (tagKey == null) continue;
                Block[] array = Arrays.stream(DumbBlocks.Blocks.values())
                    .filter(x -> x.getTags().blockTags().contains(tagKey))
                    .map(x -> x.getRegistry().block().get())
                    .toArray(Block[]::new);
                if (array.length == 0) continue;
                this.tag(tagKey).add(array);
            } catch (IllegalAccessException e) {
                ProjectNublar.LOGGER.error("Failed to add tag to block tag: " + e.getMessage());
            }
        }
    }
}
