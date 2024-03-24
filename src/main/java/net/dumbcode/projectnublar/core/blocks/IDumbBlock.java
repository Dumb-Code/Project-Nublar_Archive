package net.dumbcode.projectnublar.core.blocks;

import net.dumbcode.projectnublar.core.creativetab.ICreativeTabElement;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public interface IDumbBlock extends ICreativeTabElement {

    default Item createItem(Item.Properties properties) {
        return new BlockItem((Block) this, properties);
    }

    default Item createItem() {
        return createItem(new Item.Properties());
    }

    default Item createItem(@NotNull UnaryOperator<Item.Properties> properties) {
        return createItem(properties.apply(new Item.Properties()));
    }
}
