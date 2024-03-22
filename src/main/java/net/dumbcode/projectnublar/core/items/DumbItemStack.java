package net.dumbcode.projectnublar.core.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class DumbItemStack {
    private final ItemStack itemStack;

    public DumbItemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public DumbItemStack(@NotNull ItemLike item) {
        this(new ItemStack(item));
    }

    public DumbItemStack(@NotNull ItemLike item, int count) {
        this(new ItemStack(item, count));
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
