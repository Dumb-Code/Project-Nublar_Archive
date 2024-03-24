package net.dumbcode.projectnublar.core.items;

import net.dumbcode.projectnublar.core.creativetab.ICreativeTabElement;
import net.minecraft.world.item.Item;

public abstract class DumbItem extends Item implements ICreativeTabElement {
    protected DumbItem() {
        super(new Item.Properties());
    }

    protected DumbItem(Properties properties) {
        super(properties);
    }
}
