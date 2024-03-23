package net.dumbcode.projectnublar.core.blocks;

import net.dumbcode.projectnublar.core.creativetab.DumbCreativeTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.UnaryOperator;

public interface DumbBlock {

    default Item createItem(Item.Properties properties) {
        return new BlockItem((Block) this, properties);
    }

    default Item createItem() {
        return createItem(new Item.Properties());
    }

    default Item createItem(@NotNull UnaryOperator<Item.Properties> properties) {
        return createItem(properties.apply(new Item.Properties()));
    }

    default List<DumbCreativeTabs.CreativeTabs> getCreativeTabs() {
        return List.of(DumbCreativeTabs.CreativeTabs.ALL_ITEMS);
    }
}
