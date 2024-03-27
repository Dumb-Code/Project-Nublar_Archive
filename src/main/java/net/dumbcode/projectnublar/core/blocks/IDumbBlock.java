package net.dumbcode.projectnublar.core.blocks;

import net.dumbcode.projectnublar.core.creativetab.ICreativeTabElement;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

/**
 * It provides methods to create an item from a block.
 * It also ensures that any blocks created using our abstract classes (as you should be doing) will be added to the creative tab.
 * In your block classes, you can override {@link ICreativeTabElement#getCreativeTabs()} to add your block to a different creative tab (or multiple).
 */
public interface IDumbBlock extends ICreativeTabElement {

    /**
     * Creates an {@link Item} from the block with the given {@link Item.Properties}.
     *
     * @param properties An instance of {@link Item.Properties} that represents the properties of the item. Must not be null.
     * @return a new instance of {@link BlockItem} with the given properties.
     */
    default Item createItem(Item.Properties properties) {
        return new BlockItem((Block) this, properties);
    }

    /**
     * Creates an {@link Item} from the block with default {@link Item.Properties}.
     *
     * @return a new instance of {@link BlockItem} with default properties.
     */
    default Item createItem() {
        return createItem(new Item.Properties());
    }

    /**
     * Creates an {@link Item} from the block with properties obtained by applying the given {@link UnaryOperator} to the default {@link Item.Properties}.
     *
     * @param properties A {@link UnaryOperator} that takes an instance of {@link Item.Properties} and returns an instance of {@link Item.Properties}. Must not be null.
     * @return a new instance of {@link BlockItem} with properties obtained by applying the given unary operator to the default properties.
     */
    default Item createItem(@NotNull UnaryOperator<Item.Properties> properties) {
        return createItem(properties.apply(new Item.Properties()));
    }
}
