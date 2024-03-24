package net.dumbcode.projectnublar.core.items.elements;

import net.dumbcode.projectnublar.core.items.DumbFuelItem;
import net.minecraft.world.item.Rarity;

public class AcornItem extends DumbFuelItem {
    public AcornItem() {
        super(properties -> properties
            .burnTime(200)
            .rarity(Rarity.COMMON)
        );
    }
}
