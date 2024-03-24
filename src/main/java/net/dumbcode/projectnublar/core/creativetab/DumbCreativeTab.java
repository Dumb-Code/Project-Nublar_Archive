package net.dumbcode.projectnublar.core.creativetab;

import net.dumbcode.projectnublar.core.blocks.IDumbBlock;
import net.dumbcode.projectnublar.core.blocks.DumbBlocks;
import net.dumbcode.projectnublar.core.items.DumbItem;
import net.dumbcode.projectnublar.core.items.DumbItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class DumbCreativeTab {

    private static final String KEY = "creativetab.";
    private final CreativeModeTab creativeTab;

    protected DumbCreativeTab(DumbCreativeTabs.@NotNull CreativeTabs enumReference, Supplier<ItemStack> icon) {
        this.creativeTab = CreativeModeTab.builder()
            .icon(icon)
            .title(Component.translatable(KEY + enumReference.getRegisterName()))
            .displayItems((params, output) -> {
                for (RegistryObject<Block> registryBlock : DumbBlocks.Blocks.registryBlocks()) {
                    Block block = registryBlock.get();
                    IDumbBlock dumbBlock = (IDumbBlock) block;
                    if (!dumbBlock.getCreativeTabs().contains(enumReference)) continue;
                    output.accept(block);
                }
                for (RegistryObject<Item> registryItem : DumbItems.Items.registryItems()) {
                    Item item = registryItem.get();
                    DumbItem dumbItem = (DumbItem) item;
                    if (!dumbItem.getCreativeTabs().contains(enumReference)) continue;
                    output.accept(item);
                }
            })
            .build();
    }

    public @NotNull CreativeModeTab get() {
        return this.creativeTab;
    }
}
