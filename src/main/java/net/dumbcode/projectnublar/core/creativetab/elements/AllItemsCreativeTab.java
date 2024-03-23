package net.dumbcode.projectnublar.core.creativetab.elements;

import net.dumbcode.projectnublar.core.creativetab.DumbCreativeTab;
import net.dumbcode.projectnublar.core.creativetab.DumbCreativeTabs;
import net.dumbcode.projectnublar.core.items.DumbItems;
import net.minecraft.world.item.ItemStack;

public class AllItemsCreativeTab extends DumbCreativeTab {
    private static final DumbCreativeTabs.CreativeTabs ENUM_REFERENCE = DumbCreativeTabs.CreativeTabs.ALL_ITEMS;

    public AllItemsCreativeTab() {
        super(
            ENUM_REFERENCE,
            () -> new ItemStack(DumbItems.Items.TEST_ITEM.get())
        );
    }
}
