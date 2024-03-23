package net.dumbcode.projectnublar.core.items.elements;

import net.dumbcode.projectnublar.core.items.DumbItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public class TestItem extends Item implements DumbItem {
    public TestItem() {
        super(new Item.Properties());
    }

    public TestItem(Properties properties) {
        super(properties);
    }

    public TestItem(@NotNull UnaryOperator<Properties> properties) {
        super(properties.apply(new Properties()));
    }
}
