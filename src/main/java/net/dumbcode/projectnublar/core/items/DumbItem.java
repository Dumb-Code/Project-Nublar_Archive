package net.dumbcode.projectnublar.core.items;

import net.dumbcode.projectnublar.core.creativetab.DumbCreativeTabs;

import java.util.List;

public interface DumbItem {
    default List<DumbCreativeTabs.CreativeTabs> getCreativeTabs() {
        return List.of(DumbCreativeTabs.CreativeTabs.ALL_ITEMS);
    }
}
