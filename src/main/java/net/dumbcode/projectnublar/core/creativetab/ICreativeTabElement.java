package net.dumbcode.projectnublar.core.creativetab;

import java.util.List;

public interface ICreativeTabElement {
    default List<DumbCreativeTabs.CreativeTabs> getCreativeTabs() {
        return List.of(DumbCreativeTabs.CreativeTabs.ALL_ITEMS);
    }
}
