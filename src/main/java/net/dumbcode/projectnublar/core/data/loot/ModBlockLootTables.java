package net.dumbcode.projectnublar.core.data.loot;

import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlockLootTables extends BlockLootSubProvider {

    public static class Builder {
        private final ModBlockLootTables provider;
        private final Block block;

        Builder(ModBlockLootTables provider, Block block) {
            this.provider = provider;
            this.block = block;
        }

        public LootTable.Builder createSingleItemTable() {
            return provider.createSingleItemTable(block);
        }

        public LootTable.Builder createSingleItemTableWithSilkTouch(ItemLike pItem) {
            return provider.createSingleItemTableWithSilkTouch(block, pItem);
        }

        public LootTable.Builder createSingleItemTable(NumberProvider pCount) {
            return provider.createSingleItemTable(block, pCount);
        }

        public LootTable.Builder createSingleItemTableWithSilkTouch(ItemLike pItem, NumberProvider pCount) {
            return provider.createSingleItemTableWithSilkTouch(block, pItem, pCount);
        }

        public LootTable.Builder createPotFlowerItemTable() {
            return provider.createPotFlowerItemTable(block);
        }

        public LootTable.Builder createSlabItemTable() {
            return provider.createSlabItemTable(block);
        }

        public <T extends Comparable<T> & StringRepresentable> LootTable.Builder createSinglePropConditionTable(Property<T> pProperty, T pValue) {
            return provider.createSinglePropConditionTable(block, pProperty, pValue);
        }

        public LootTable.Builder createNameableBlockEntityTable() {
            return provider.createNameableBlockEntityTable(block);
        }

        public LootTable.Builder createShulkerBoxDrop() {
            return provider.createShulkerBoxDrop(block);
        }

        public LootTable.Builder createCopperOreDrops() {
            return provider.createCopperOreDrops(block);
        }

        public LootTable.Builder createLapisOreDrops() {
            return provider.createLapisOreDrops(block);
        }

        public LootTable.Builder createRedstoneOreDrops() {
            return provider.createRedstoneOreDrops(block);
        }

        public LootTable.Builder createBannerDrop() {
            return provider.createBannerDrop(block);
        }

        public LootTable.Builder createOreDrop(Item pItem) {
            return provider.createOreDrop(block, pItem);
        }

        public LootTable.Builder createMushroomBlockDrop(ItemLike pItem) {
            return provider.createMushroomBlockDrop(block, pItem);
        }

        public LootTable.Builder createGrassDrops() {
            return provider.createGrassDrops(block);
        }

        public LootTable.Builder createStemDrops(Item pItem) {
            return provider.createStemDrops(block, pItem);
        }

        public LootTable.Builder createAttachedStemDrops(Item pItem) {
            return provider.createAttachedStemDrops(block, pItem);
        }

        public LootTable.Builder createMultifaceBlockDrops(LootItemCondition.Builder pBuilder) {
            return provider.createMultifaceBlockDrops(block, pBuilder);
        }

        public LootTable.Builder createLeavesDrops(Block pSaplingBlock, float... pChances) {
            return provider.createLeavesDrops(block, pSaplingBlock, pChances);
        }

        public LootTable.Builder createOakLeavesDrops(Block pSaplingBlock, float... pChances) {
            return provider.createOakLeavesDrops(block, pSaplingBlock, pChances);
        }

        public LootTable.Builder createMangroveLeavesDrops() {
            return provider.createMangroveLeavesDrops(block);
        }

        public LootTable.Builder createCropDrops(Item pGrownCropItem, Item pSeedsItem, LootItemCondition.Builder pDropGrownCropCondition) {
            return provider.createCropDrops(block, pGrownCropItem, pSeedsItem, pDropGrownCropCondition);
        }

        public LootTable.Builder createDoublePlantWithSeedDrops(Block pSheared) {
            return provider.createDoublePlantWithSeedDrops(block, pSheared);
        }

        public LootTable.Builder createCandleDrops() {
            return provider.createCandleDrops(block);
        }

        public LootTable.Builder createPetalsDrops() {
            return provider.createPetalsDrops(block);
        }

        public LootTable.Builder addNetherVinesDropTable(Block pPlant) {
            provider.addNetherVinesDropTable(block, pPlant);
            return null;
        }

        public LootTable.Builder createDoorTable() {
            return provider.createDoorTable(block);
        }

        public LootTable.Builder dropPottedContents() {
            provider.dropPottedContents(block);
            return null;
        }

        public LootTable.Builder otherWhenSilkTouch(Block pOther) {
            provider.otherWhenSilkTouch(block, pOther);
            return null;
        }

        public LootTable.Builder dropOther(ItemLike pItem) {
            provider.dropOther(block, pItem);
            return null;
        }

        public LootTable.Builder dropWhenSilkTouch() {
            provider.dropWhenSilkTouch(block);
            return null;
        }

        public LootTable.Builder dropSelf() {
            provider.dropSelf(block);
            return null;
        }

        public LootTable.Builder add(Function<Block, LootTable.Builder> pFactory) {
            provider.add(block, pFactory);
            return null;
        }

        public LootTable.Builder add(LootTable.Builder pBuilder) {
            provider.add(block, pBuilder);
            return null;
        }

        private void build(LootTable.@NotNull Builder pBuilder) {
            provider.add(block, pBuilder);
        }
    }

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        for (DumbBlocks.Blocks block : DumbBlocks.Blocks.values()) {
            Builder builder = new Builder(this, block.getRegistry().block().get());
            LootTable.Builder lootBuilder = block.getMetadata().lootTableBuilder().apply(builder);
            if (lootBuilder != null) {
                builder.build(lootBuilder);
            }
        }
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return Arrays.stream(DumbBlocks.Blocks.values()).map(x -> x.getRegistry().block().get())::iterator;
    }
}
